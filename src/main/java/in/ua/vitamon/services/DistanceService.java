package in.ua.vitamon.services;

import in.ua.vitamon.model.CityPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class DistanceService implements IDistanceService {

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public void upsert(CityPair data)
    {
        CityPair upd = mongoOps.findOne(query(where("id").is(data.getCityPair())), CityPair.class);
        if (null == upd)
        {
            mongoOps.insert(data);
        } else
        {
            upd.setDistance(data.getDistance());
        }
    }

    @Override
    public CityPair lookup(String key)
    {
        CityPair data = mongoOps.findById(key, CityPair.class);
        return data;
    }
}
