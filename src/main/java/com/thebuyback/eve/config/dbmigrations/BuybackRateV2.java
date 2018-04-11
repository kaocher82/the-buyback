package com.thebuyback.eve.config.dbmigrations;

import java.util.List;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.thebuyback.eve.domain.ItemBuybackRate;
import com.thebuyback.eve.domain.Type;
import com.thebuyback.eve.domain.TypeBuybackRate;
import com.thebuyback.eve.domain.enumeration.TypeCategory;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "005")
public class BuybackRateV2 {

    @ChangeSet(order = "05", author = "initiator", id = "05-migrateBuybackRates")
    public void migrateBuybackRates(MongoTemplate mongoTemplate) {
        final List<Type> types = mongoTemplate.findAll(Type.class);

        mongoTemplate.findAll(ItemBuybackRate.class).stream().map(old -> {
            final TypeBuybackRate newRate = new TypeBuybackRate();
            newRate.setRate(old.getRate());
            newRate.setTypeId(old.getTypeId());
            final Type type = types.stream().filter(t -> t.getTypeId() == old.getTypeId()).findFirst().orElse(null);
            newRate.setTypeName(type == null ? "UNKNOWN" : type.getTypeName());
            newRate.setCategory(TypeCategory.GENERAL);

            if (newRate.getTypeName().startsWith("Compressed")) {
                newRate.setRate(0.95);
                newRate.setCategory(TypeCategory.COMPRESSED_ORE);
            }

            return newRate;
        }).forEach(mongoTemplate::save);
    }

}
