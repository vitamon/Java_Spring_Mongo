package in.ua.vitamon.services;

import in.ua.vitamon.model.CityPair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class DistanceService implements IDistanceService {
    private static final Logger log = Logger.getLogger(DistanceService.class);

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public boolean upsert(CityPair data) {
        try {
            CityPair upd = mongoOps.findOne(query(where("id").is(data.getCityPair())), CityPair.class);
            if (null == upd) {
                mongoOps.insert(data);
            } else {
                upd.setDistance(data.getDistance());
            }
        } catch (Exception e) {
            log.error("Error when processing upsert:" + data.toString());
            return false;
        }
        return true;
    }

    @Override
    public CityPair lookup(String key) {
        CityPair data = null;
        try {
            data = mongoOps.findById(key, CityPair.class);
        } catch (Exception e) {
            log.error("Error when processing lookup:" + key);
        }
        return data;
    }
}
