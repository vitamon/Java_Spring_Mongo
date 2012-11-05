package in.ua.vitamon.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DistanceRepository extends MongoRepository<String, Double> {
    Double findByCityPair(String cityPair);
}
