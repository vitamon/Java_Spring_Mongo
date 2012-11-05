package in.ua.vitamon.services;

import in.ua.vitamon.model.CityPair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class DistanceService implements IDistanceService {
    private static final Logger log = Logger.getLogger(DistanceService.class);

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public boolean upsert(CityPair data) {
        try {
            CityPair upd = lookup(data.getCityPair());
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
        return mongoOps.findById(key, CityPair.class);
    }
}
