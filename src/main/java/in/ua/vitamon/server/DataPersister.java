package in.ua.vitamon.server;

import javax.jdo.Query;
import in.ua.vitamon.model.DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.PersistenceManager;
import java.util.List;

public class DataPersister {
    private static final Logger log = LoggerFactory.getLogger(DataPersister.class);


/*    public Collection<DataEntity> getAll() {
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try {
            List<DataEntity> messages = new ArrayList<DataEntity>();
            Extent<DataEntity> extent = pm.getExtent(DataEntity.class, false);
            for (DataEntity message : extent) {
                messages.add(message);
            }
            extent.closeAll();

            return messages;
        } finally {
            pm.close();
        }
    }*/

    public void create(DataEntity dto) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(dto);
            log.info("saved data for :" + dto.toString());
        } finally {
            pm.close();
        }
    }

/*    public Collection<DataEntity> findAllMatches(DataEntity dto) {
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try {
            List<DataEntity> items = new ArrayList<DataEntity>();
            Extent<DataEntity> extent = pm.getExtent(DataEntity.class, false);
            for (DataEntity message : extent) {
                items.add(message);
            }
            extent.closeAll();

            return items;
        } finally {
            pm.close();
        }
    }*/

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

        //query.setRange(indexOffset, indexOffset + ENTITIES_PER_PAGE + 1);
        return (List<DataEntity>) query.execute(appId);
    }

}
