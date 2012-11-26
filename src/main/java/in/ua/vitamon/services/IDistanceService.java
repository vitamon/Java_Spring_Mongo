package in.ua.vitamon.services;

import in.ua.vitamon.domain.CityPair;

public interface IDistanceService {

    boolean upsert(CityPair data);

    CityPair findOne(String key);
}
