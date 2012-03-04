package in.ua.vitamon.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: vit.tam@gmail.com
 */

@PersistenceCapable
public class ParamEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent
    private String key;
    @Persistent
    private String value;

    public ParamEntry() {
    }

    public ParamEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static List<ParamEntry> toArrayList(final Map<String, String> flatParams) {
        List<ParamEntry> paramEntryList = new ArrayList<ParamEntry>(flatParams.size());
        for (String key : flatParams.keySet()) {
            paramEntryList.add(new ParamEntry(key, flatParams.get(key)));
        }
        return paramEntryList;
    }

    /*
     * getters and setters
     */
    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ParamEntry{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
