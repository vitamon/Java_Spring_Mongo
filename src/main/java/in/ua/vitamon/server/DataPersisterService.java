package in.ua.vitamon.server;

import in.ua.vitamon.model.DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.io.Serializable;
import java.util.Collection;

public class DataPersisterService implements Serializable {
    private static final long serialVersionUID = 11L;

    private static final Logger log = LoggerFactory.getLogger(DataPersisterService.class);

    public void create(DataEntity dto) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(dto);
            log.info("saved data for :" + dto.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        finally {
            pm.close();
        }
    }

    public void deleteById(Long id) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.deletePersistent(pm.getObjectById(DataEntity.class, id));
        } finally {
            pm.close();
        }
    }

    public Collection<DataEntity> findAllMatches(String appId) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        StringBuilder filter = new StringBuilder().append("appId == applicationId");
        Query query = pm.newQuery(DataEntity.class);
        query.declareParameters("String applicationId");
        query.setFilter(filter.toString());
        return (Collection<DataEntity>) query.execute(appId);
    }

}
