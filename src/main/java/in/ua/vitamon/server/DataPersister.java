package in.ua.vitamon.server;

import in.ua.vitamon.model.DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.io.Serializable;
import java.util.List;

public class DataPersister implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(DataPersister.class);

    public void create(DataEntity dto) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(dto);
            log.info("saved data for :" + dto.toString());
        } finally {
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

    public List<DataEntity> findAllMatches(String appId) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        StringBuilder filter = new StringBuilder().append("appId == applicationId");
        Query query = pm.newQuery(DataEntity.class);
        query.declareParameters("String applicationId");
        query.setFilter(filter.toString());
        return (List<DataEntity>) query.execute(appId);
    }

}
