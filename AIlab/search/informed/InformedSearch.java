package AIlab.search.informed;

import AIlab.city.City;

import java.util.LinkedList;

public interface InformedSearch {
    public LinkedList<City> search (City startCity, City finishCity);
}
