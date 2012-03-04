package in.ua.vitamon.model;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public DataEntity(String appId, List<String> params) {
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
    private List<String> params;

    /**
     * Creates entry from map of parameters
     *
     * @param params -- needs to have at least APP_ID
     * @return null -- if parsing failed
     */

    public static DataEntity parseEntry(Map<String, String> params) {
        if (!params.containsKey(ParamIDs.APP_ID)) return null;

        DataEntity dataEntity = new DataEntity(params.get(ParamIDs.APP_ID));
        params.remove(ParamIDs.APP_ID);
        dataEntity.setParams(new ArrayList(params.values()));
        return dataEntity;
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

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

}
