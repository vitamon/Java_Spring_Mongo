package in.ua.vitamon.domain;

import in.ua.vitamon.services.MongoDistanceServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("IntegrationTest-context.xml")
public class IntegrationTest {

    @Autowired
    MongoDistanceServiceImpl mongoDistanceServiceImpl;

    @Autowired
    private MongoTemplate mongoTempl;

    @Before
    public void setUp() {
        mongoTempl.dropCollection(CityPair.class);
    }

    @Test
    public void testSomething() {
        System.out.println(mongoTempl.getCollectionName(CityPair.class));
    }

    @Test
    public void testUpsert() {
        CityPair data = CityPair.parse("Moscow", "Kiev", "123");
        CityPair found = mongoDistanceServiceImpl.findOne(data.getCityPair());
        assertNull(found);
        // save data
        assertTrue(mongoDistanceServiceImpl.upsert(data));
        found = mongoDistanceServiceImpl.findOne(data.getCityPair());
        assertNotNull(found);
        assertThat(found.getCityPair(), is(data.getCityPair()));
        assertThat(found.getDistance(), is(123d));

        // update data
        CityPair update = CityPair.parse("Moscow", "Kiev", "456");
        mongoDistanceServiceImpl.upsert(update);

        found = mongoDistanceServiceImpl.findOne(update.getCityPair());
        assertThat(found.getDistance(), is(456d));
    }

}