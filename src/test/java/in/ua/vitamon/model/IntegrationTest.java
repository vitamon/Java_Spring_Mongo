package in.ua.vitamon.model;

import in.ua.vitamon.services.DistanceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class IntegrationTest {

    @Autowired
    DistanceService distanceService;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void setUp() {
        mongoOps.dropCollection(CityPair.class);
    }

    @Test
    public void testUpsert() {
        CityPair data = CityPair.parse("Moscow", "Kiev", "123");
        CityPair found = distanceService.lookup(data.getCityPair());
        assertNull(found);
        assertTrue(distanceService.upsert(data));
        found = distanceService.lookup(data.getCityPair());
        assertNotNull(found);
        assertThat(found.getCityPair(), is(data.getCityPair()));
        assertThat(found.getDistance(), is(123d));
    }
}