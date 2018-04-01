package com.thebuyback.eve.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.thebuyback.eve.domain.AssetFilter;

import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeLog(order = "004")
public class AddAssetFilters {

    @ChangeSet(order = "04", author = "initiator", id = "04-addAssetFilters")
    public void addAssetFilters(MongoTemplate mongoTemplate) {
        mongoTemplate.save(new AssetFilter(34));
        mongoTemplate.save(new AssetFilter(36));
    }
}
