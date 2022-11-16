package AIlab.search.uninformed;

import AIlab.city.City;

import java.util.LinkedList;

public interface UninformedSearch {
    public LinkedList<City> search(City startCity, City finishCity);
}
