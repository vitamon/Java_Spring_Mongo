package in.ua.vitamon.util;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.io.Writer;

/**
 * @author: vit.tam@gmail.com
 */
public class SimpleXMLMarshaller {
    private static final Logger log = LoggerFactory.getLogger(SimpleXMLMarshaller.class);

    public static <T> String toXML(T object) {
        Serializer serializer = new Persister();
        Writer writer = new StringWriter();
        try {
            log.debug(object.toString());
            serializer.write(object, writer);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return writer.toString();
    }
}
