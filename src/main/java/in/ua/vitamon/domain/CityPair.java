package in.ua.vitamon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CityPair {

    public static final String KEY_DIVIDER = "::";

    @Id
    private String cityPair;

    private double distance;

    /**
     * Parses and creates valid city pair object
     *
     * @param fromCity
     * @param toCity
     * @param distance
     * @return valid CityPair object or null
     */
    public static CityPair parse(String fromCity, String toCity, String distance) {

        // required params should not be null
        if (fromCity == null || toCity == null) return null;

        // required params should not be empty
        fromCity = fromCity.trim().toUpperCase();
        toCity = toCity.trim().toUpperCase();
        if (fromCity.equals("") || toCity.equals("")) return null;

        // parse distance
        double dist = 0.0d;
        try {
            dist = Double.parseDouble(distance);
        } catch (NumberFormatException e) {
            return null;
        }

        // create DTO
        CityPair data = new CityPair();
        data.distance = dist;
        data.cityPair = createKey(fromCity, toCity);
        return data;
    }

    /**
     * Create a "City1::City2" key, where City1<=City2
     */
    public static String createKey(String fromCity, String toCity) {
        if (fromCity.compareTo(toCity) >= 0) {
            String tmp = toCity;
            toCity = fromCity;
            fromCity = tmp;
        }
        return new StringBuilder().append(fromCity).append(KEY_DIVIDER).append(toCity).toString();
    }

    public static CityPair parse(String fromCity, String toCity) {
        return parse(fromCity, toCity, "0.0");
    }

    //
    // getters/setters
    //

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCityPair() {
        return cityPair;
    }

    public void setCityPair(String cityPair) {
        this.cityPair = cityPair;
    }


    @Override
    public String toString() {
        return "CityPair{" +
                "cityPair='" + cityPair + '\'' +
                ", distance=" + distance +
                '}';
    }


}
