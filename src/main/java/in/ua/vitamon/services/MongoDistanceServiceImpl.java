package in.ua.vitamon.services;

import com.mongodb.WriteResult;
import in.ua.vitamon.domain.CityPair;
import in.ua.vitamon.helpers.Logger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDistanceServiceImpl implements IDistanceService {

    @Logger
    private Log log;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean upsert(CityPair data) {
        WriteResult wr = mongoTemplate.upsert(
                new Query(Criteria.where("cityPair").is(data.getCityPair())),
                Update.update("distance", data.getDistance()),
                CityPair.class
        );

        if (!wr.getLastError().ok()) {
            log.error("Error in upsert:" + wr.getLastError().getErrorMessage());
            return false;
        }

        return true;
    }

    @Override
    public CityPair findOne(String key) {
        Query query = new Query(Criteria.where("cityPair").is(key));
        return mongoTemplate.findOne(query, CityPair.class);
    }
}
