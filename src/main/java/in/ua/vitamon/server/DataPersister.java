package in.ua.vitamon.server;

import in.ua.vitamon.model.DataEntity;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataPersister {

    final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    public Collection<DataEntity> getAll() {
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
    }

    public void create(DataEntity dto) {
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try {
            pm.makePersistent(dto);
        } finally {
            pm.close();
        }
    }

    public void deleteById(Long id) {
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try {
            pm.deletePersistent(pm.getObjectById(DataEntity.class, id));
        } finally {
            pm.close();
        }
    }

}
