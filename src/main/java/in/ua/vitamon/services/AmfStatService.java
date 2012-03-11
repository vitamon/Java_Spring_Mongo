package in.ua.vitamon.services;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.server.DataPersisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * For GraniteDS endpoint
 *
 * @author: vit.tam@gmail.com
 */
public class AmfStatService implements Serializable {
    private static final long serialVersionUID = 12L;

    private static final Logger log = LoggerFactory.getLogger(AmfStatService.class);

    private DataPersisterService dataPersisterService = new DataPersisterService();

    public Collection<DataEntity> searchAllEntries(String appId) {
        Collection<DataEntity> entries = dataPersisterService.findAllMatches(appId);
        log.info("found " + entries.size() + " entries");
        return entries;
    }
}
