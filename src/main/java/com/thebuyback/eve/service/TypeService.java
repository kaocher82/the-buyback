package com.thebuyback.eve.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mashape.unirest.http.JsonNode;
import com.thebuyback.eve.domain.Type;
import com.thebuyback.eve.domain.TypeResolveException;
import com.thebuyback.eve.repository.TypeRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class TypeService {

    private final TypeRepository repository;
    private final JsonRequestService requestService;

    public TypeService(final TypeRepository repository, final JsonRequestService requestService) {
        this.repository = repository;
        this.requestService = requestService;
    }

    public String getNameByTypeId(long typeId) {
        return getType(typeId).getTypeName();
    }

    public double getVolume(long typeId) {
        final Type type = getType(typeId);
        Double volume = type.getPackagedVolume();
        if (volume == null) {
            volume = type.getVolume();
        }
        return volume;
    }

    private Type getType(long typeId) {
        final Optional<Type> optional = repository.findByTypeId(typeId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            final Optional<JsonNode> responseNode = requestService.getTypeInfo(typeId);
            if (!responseNode.isPresent()) {
                throw new TypeResolveException(typeId);
            }
            final JSONObject responseObj = responseNode.get().getObject();
            final String name = responseObj.getString("name");
            final long groupId = responseObj.getLong("group_id");
            final double volume = responseObj.getDouble("volume");
            Double packagedVolume = null;
            if (responseObj.has("packaged_volume")) {
                packagedVolume = responseObj.getDouble("packaged_volume");
            }
            final Type type = new Type(typeId, name, groupId, null, null, null, volume, packagedVolume);
            repository.save(type);
            return type;
        }
    }

    public String getGroupNameByTypeId(long typeId) {
        return getType(typeId).getGroupName();
    }

    public long getGroupIdByTypeId(long typeId) {
        return getType(typeId).getGroupId();
    }

    private void addGroupNameAndCategoryId(final Type type) {
        final Optional<JsonNode> responseNode = requestService.getGroupInfo(type.getGroupId());
        if (!responseNode.isPresent()) {
            throw new TypeResolveException(type.getGroupId());
        }
        final JSONObject responseObj = responseNode.get().getObject();
        final String groupName = responseObj.getString("name");
        final long categoryId = responseObj.getLong("category_id");
        final JSONArray types = responseObj.getJSONArray("types");
        final Set<Long> typeIds = new HashSet<>();
        for (int i = 0; i < types.length(); i++) {
            typeIds.add(types.getLong(i));
        }
        repository.save(Stream.concat(Stream.of(type), repository.findByTypeIdIn(typeIds)).peek(t -> {
            t.setGroupName(groupName);
            t.setCategoryId(categoryId);
        }).collect(Collectors.toList()));
    }

    public long getCategoryIdByTypeId(long typeId) {
        final Type type = getType(typeId);
        if (null == type.getCategoryId()) {
            addGroupNameAndCategoryId(type);
        }
        return type.getCategoryId();
    }

    public String getCategoryNameByTypeId(long typeId) {
        final Type type = getType(typeId);
        if (null == type.getCategoryName()) {
            addCategoryName(type);
        }
        return type.getCategoryName();
    }

    private void addCategoryName(final Type type) {
        if (null == type.getCategoryId()) {
            addGroupNameAndCategoryId(type);
        }
        final Optional<JsonNode> optional = requestService.getCategoryInfo(type.getCategoryId());
        if (!optional.isPresent()) {
            throw new TypeResolveException(type.getCategoryId());
        }
        final JSONObject responseObj = optional.get().getObject();
        final String categoryName = responseObj.getString("category_name");
        final JSONArray groups = responseObj.getJSONArray("groups");
        final Set<Long> groupIds = new HashSet<>();
        for (int i = 0; i < groups.length(); i++) {
            groupIds.add(groups.getLong(i));
        }
        repository.save(Stream.concat(Stream.of(type), repository.findByGroupIdIn(groupIds))
                              .peek(t -> t.setCategoryName(categoryName))
                              .collect(Collectors.toList()));
    }

    private Type getType(final String typeName) {
        final Optional<Type> optional = repository.findByTypeName(typeName);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            final Optional<JsonNode> responseNode = requestService.searchType(typeName);
            if (!responseNode.isPresent()) {
                throw new TypeResolveException(typeName);
            }
            final JSONObject jsonObject = responseNode.get().getArray().getJSONObject(0);
            if (!jsonObject.has("inventory_type")) {
                throw new TypeResolveException("No typeIds returned for " + typeName);
            }
            final JSONArray array = jsonObject.getJSONArray("inventory_type");
            if (array.length() == 0) {
                throw new TypeResolveException("No typeIds returned for " + typeName);
            } else {
                return getType(array.getLong(0));
            }
        }
    }

    public long getGroupIdByTypeId(String typeName) {
        return getType(typeName).getGroupId();
    }

    public long getCategoryIdByTypeId(String typeName) {
        final Type type = getType(typeName);
        if (null == type.getCategoryId()) {
            addGroupNameAndCategoryId(type);
        }
        return type.getCategoryId();
    }

    long getTypeIdByName(final String name) {
        return getType(name).getTypeId();
    }
}
