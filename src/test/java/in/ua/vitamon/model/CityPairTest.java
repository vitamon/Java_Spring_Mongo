package in.ua.vitamon.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CityPairTest {

    @Test
    public void testCreateKey() {
        assertThat(CityPair.createKey("aaa", "bbb"), is("aaa::bbb"));
        assertThat(CityPair.createKey("bbb", "aaa"), is("aaa::bbb"));
    }

    @Test
    public void testParse() {
        CityPair a = CityPair.parse("Kiev", "Moscow", "123");
        assertThat(a.getCityPair(), is("KIEV::MOSCOW"));
        assertThat(a.getDistance(), is(123d));
    }

    @Test
    public void testParseReverse() {
        CityPair a = CityPair.parse("Moscow", "Kiev", "123.56");
        assertThat(a.getCityPair(), is("KIEV::MOSCOW"));
        assertThat(a.getDistance(), is(123.56d));
    }

    @Test
    public void testParseWrong1() {
        CityPair a = CityPair.parse("", "Kiev", "123.56");
        assertNull(a);
    }

    @Test
    public void testParseWrong2() {
        CityPair a = CityPair.parse("asd", null, "123.56");
        assertNull(a);
    }

    @Test
    public void testParseWrong3() {
        CityPair a = CityPair.parse("asd", null, "asdf");
        assertNull(a);
    }

    @Test
    public void testParseWrong4() {
        CityPair a = CityPair.parse("asd", "", null);
        assertNull(a);
    }
}
