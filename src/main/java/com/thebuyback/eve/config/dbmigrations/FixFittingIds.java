package com.thebuyback.eve.config.dbmigrations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.thebuyback.eve.domain.stock.Doctrine;
import com.thebuyback.eve.domain.stock.Fitting;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "002")
public class FixFittingIds {

    @ChangeSet(order = "02", author = "initiator", id = "02-fixFittingIds")
    public void fixFittings(MongoTemplate mongoTemplate) {
        final List<Fitting> allFittings = mongoTemplate.findAll(Fitting.class);
        final List<Doctrine> allDoctrines = mongoTemplate.findAll(Doctrine.class);

        final Collection<Fitting> newFittings = new ArrayList<>();
        final Collection<Doctrine> newDoctrines = new ArrayList<>();

        for (final Doctrine doctrine : allDoctrines) {
            Map<String, Integer> newMapping = new HashMap<>();
            doctrine.getFittingsQuantities().forEach((fittingId, value) -> {
                String newFittingId = null;
                for (Fitting fitting : allFittings) {
                    if (fittingId.equals(fitting.getId())) {
                        newFittingId = fitting.getName();
                    }
                }
                if (newFittingId == null) {
                    throw new RuntimeException("Couldn't find a fitting for the ID " + fittingId);
                }
                newMapping.put(newFittingId, value);
            });
            doctrine.setFittingsQuantities(newMapping);
            newDoctrines.add(doctrine);
        }

        for (Fitting fitting : allFittings) {
            fitting.setId(fitting.getName());
            newFittings.add(fitting);
        }

        mongoTemplate.dropCollection(Fitting.class);
        mongoTemplate.dropCollection(Doctrine.class);

        mongoTemplate.createCollection(Fitting.class);
        mongoTemplate.createCollection(Doctrine.class);

        newFittings.forEach(mongoTemplate::save);
        newDoctrines.forEach(mongoTemplate::save);
    }

}
