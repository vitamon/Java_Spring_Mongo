package in.ua.vitamon.services;

import in.ua.vitamon.model.CityPair;

public interface IDistanceService {

    void upsert(CityPair data);

    CityPair lookup(String key);
}
