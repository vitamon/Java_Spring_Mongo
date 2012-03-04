package in.ua.vitamon.model;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    public DataEntity() {
    }

    public DataEntity(String appId) {
        this.appId = appId;
        this.dateCreated = new Date();
    }

    public DataEntity(String appId, List<ParamEntry> params) {
        this(appId);
        this.params = params;
    }

    /**
     * Holds application APP_ID from which the data is originated
     */
    @Persistent
    private String appId;

    @Persistent
    private Date dateCreated;

    @Persistent
    private List<ParamEntry> params;

    /**
     * Creates entry from map of parameters
     *
     * @param params -- needs to have at least APP_ID
     * @return null -- if parsing failed
     */

    public static DataEntity parseEntry(final Map<String, String[]> params) {
        Map<String, String> flatParams = toFlatMap(params);
        
        if (!flatParams.containsKey(ParamIDs.APP_ID)) return null;
        // set app_id
        DataEntity dataEntity = new DataEntity(params.get(ParamIDs.APP_ID)[0]);
        
        flatParams.remove(ParamIDs.APP_ID);
        dataEntity.setParams(ParamEntry.toArrayList(flatParams));
        return dataEntity;
    }

    /**
     * Converts Map<String, String[]> --> Map<String, String[0]>
     * @param params
     * @return
     */
    public static Map<String, String> toFlatMap(final Map<String, String[]> params) {
        Map<String, String> uniqueParams = new HashMap<String, String>();
        for (String key:params.keySet()) {
            uniqueParams.put(key, params.get(key)[0]);
        }
        return uniqueParams;
    }

    /*
     *
     * getters and setters
     *
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<ParamEntry> getParams() {
        return params;
    }

    public void setParams(List<ParamEntry> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", dateCreated=" + dateCreated +
                ", params=" + params +
                '}';
    }
}
