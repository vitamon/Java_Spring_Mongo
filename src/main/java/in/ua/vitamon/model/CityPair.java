package in.ua.vitamon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CityPair {

    @Id
    private String cityPair;

    private double distance;

    //
    // parser/validator
    //

    /**
     * Parses and creates valid city pair object
     *
     * @param fromCity
     * @param toCity
     * @param distance
     * @return valid CityPair object or null
     */
    public static CityPair parse(String fromCity, String toCity, double distance)
    {
        if (fromCity == null || toCity == null) return null;

        CityPair data = new CityPair();
        data.distance = distance;
        data.setPair(fromCity.trim().toUpperCase(), toCity.trim().toUpperCase());
        return data;
    }

    public static CityPair parse(String fromCity, String toCity)
    {
        return parse(fromCity, toCity, 0);
    }

    public void setPair(String fromCity, String toCity)
    {
        cityPair = new StringBuilder().append(fromCity).append("::").append(toCity).toString();
    }

    //
    // getters/setters
    //

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public String getCityPair()
    {
        return cityPair;
    }

    public void setCityPair(String cityPair)
    {
        this.cityPair = cityPair;
    }

    public void reverse()
    {
        //To change body of created methods use File | Settings | File Templates.
    }
}
