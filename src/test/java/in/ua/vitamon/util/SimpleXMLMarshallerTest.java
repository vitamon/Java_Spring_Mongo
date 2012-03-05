package in.ua.vitamon.util;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.model.ParamEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author: vit.tam@gmail.com
 */
public class SimpleXMLMarshallerTest {
    @Test
    public void testToXML() throws Exception {
        List<ParamEntry> entries = new ArrayList<ParamEntry>();
        entries.add(new ParamEntry("name", "vit"));
        entries.add(new ParamEntry("surname", "tam"));
        DataEntity dataEntity = new DataEntity("MY_APP", entries);
        System.out.println(SimpleXMLMarshaller.toXML(dataEntity));
        assertThat(SimpleXMLMarshaller.toXML(dataEntity), is("...."));
    }


}
