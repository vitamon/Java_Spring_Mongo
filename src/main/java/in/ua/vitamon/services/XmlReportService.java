package in.ua.vitamon.services;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.server.DataPersisterService;
import in.ua.vitamon.util.SimpleXMLMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * @author: vit.tam@gmail.com
 */
public class XmlReportService implements IXMLReportService {

    private static final Logger log = LoggerFactory.getLogger(XmlReportService.class);

    @Inject
    private DataPersisterService dataPersisterService;

    @Override
    public String getXMLreport(String appID) {
        List<DataEntity> entries = dataPersisterService.findAllMatches(appID);
        log.debug("found " + entries.size() + " entries");
        return toXML(entries);
    }

    private static String toXML(List<DataEntity> entries) {
        StringBuilder xml = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<result>\n");
        for (DataEntity data : entries) {
            xml.append(SimpleXMLMarshaller.toXML(data)).append("\n");
        }
        xml.append("</result>");
        return xml.toString();
    }
}
