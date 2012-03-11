package in.ua.vitamon.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author: vit.tam@gmail.com
 */
public class DataEntityTest {
    
    Map<String, String[]> params;

    @Before
    public void before() {
        params = new HashMap<String, String[]>();
    }

    @Test
    public void testParseEntry() throws Exception {
        params.put("p1", new String[]{"BLABLA", "423"});
        params.put("p2", new String[]{"33333", "dfg"});
        params.put(ParamID.APP_ID, new String[]{"MYAPP0001", "222"});

        DataEntity d = DataEntity.parseEntry(params);

        assertThat(d.getAppId(), is("MYAPP0001"));

        assertThat(d.getParams().size(), is(2));
        assertThat(d.getParams().get(1).getValue(), is("BLABLA"));
        assertThat(d.getParams().get(0).getKey(), is("p2"));

    }
}
