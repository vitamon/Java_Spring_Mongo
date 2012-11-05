package in.ua.vitamon.services;

import in.ua.vitamon.model.CityPair;

public interface IDistanceService {

    boolean upsert(CityPair data);

    CityPair lookup(String key);
}
