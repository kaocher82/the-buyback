package com.thebuyback.eve.config.dbmigrations;

import com.thebuyback.eve.domain.Authority;
import com.thebuyback.eve.security.AuthoritiesConstants;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "003")
public class InitialSetupMigration {

    @ChangeSet(order = "03", author = "initiator", id = "03-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority assets = new Authority();
        assets.setName(AuthoritiesConstants.ASSETS);
        mongoTemplate.save(assets);
    }
}
