package in.ua.vitamon.web;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.server.DataPersister;
import in.ua.vitamon.util.SimpleXMLMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author: vit.tam@gmail.com
 */
public class XmlStatServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(XmlStatServlet.class);

    private DataPersister dataPersister = new DataPersister();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        log.debug("doGet");
        DataEntity d = DataEntity.parseEntry(request.getParameterMap());
        if (d != null) {
            Collection<DataEntity> entries = dataPersister.findAllMatches(d.getAppId());
            log.debug("found " + entries.size() + " entries: " + entries.toString());
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write(toXML(entries));

        } else {
            log.info("wrong or no data in request: " + request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private static String toXML(Collection<DataEntity> entries) {
        StringBuilder xml = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<result>\n");
        for (DataEntity data : entries) {
            xml.append(SimpleXMLMarshaller.toXML(data)).append("\n");
        }
        xml.append("</result>");
        return xml.toString();
    }
}
