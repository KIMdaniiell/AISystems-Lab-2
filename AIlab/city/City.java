package AIlab.city;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class City {
    private final String name;
    private final LinkedHashMap <City, Integer> nextCities;

    public City(String name) {
        this.name = name;
        nextCities = new LinkedHashMap<>();
    }

    public boolean isNext(City city) {
        return this.nextCities.containsKey(city);
    }

    public boolean putNext(City city, Integer distance) {
        //returns false if given city was already in Map and true otherwise
        if (isNext(city)) {
            return false;
        } else {
            nextCities.put(city, distance);
            return true;
        }
    }

    public HashMap<City, Integer> getNextCities() {
        return this.nextCities;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals (Object object) {
        if (this == object) return true;
        if (object == null
                || this.getClass() != object.getClass()) return false;

        City city = (City) object;
        return (this.name.equals(city.getName()));
    }

    @Override
    public String toString () {
        return this.name;
    }
}
