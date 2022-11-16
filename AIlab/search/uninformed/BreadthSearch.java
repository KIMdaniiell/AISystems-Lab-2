package AIlab.search.uninformed;

import AIlab.city.City;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BreadthSearch implements UninformedSearch{
    private LinkedList<City> cityPath;
    private HashMap<City, City> visitedCitiesWithPrevious;

    public LinkedList<City> search(City startCity, City finishCity) {
        cityPath = new LinkedList<>();
        visitedCitiesWithPrevious = new HashMap<>();

        if (startCity == null || finishCity == null || startCity.getNextCities().isEmpty()) return cityPath;
        searchR(startCity, finishCity);
        return getCityPath(startCity, finishCity);
    }

    private void searchR(City startCity, City finishCity) {
        HashSet<City> currentCities = new HashSet<>();
        HashSet<City> nextCities = new HashSet<>();
        currentCities.add(startCity);
        visitedCitiesWithPrevious.put(startCity, null);

        while (!visitedCitiesWithPrevious.containsKey(finishCity) && !currentCities.isEmpty()) {
            currentCities.forEach(
                    city -> {
                        city.getNextCities().keySet().forEach(
                                nextCity -> {
                                    if (!visitedCitiesWithPrevious.containsKey(nextCity)) {
                                        visitedCitiesWithPrevious.put(nextCity, city);
                                        nextCities.add(nextCity);
                                    }
                                }
                        );
                    }
            );
            currentCities = (HashSet<City>) nextCities.clone();
            nextCities.clear();
        }
    }

    private LinkedList<City> getCityPath(City start, City finish) {
        if (visitedCitiesWithPrevious.containsKey(finish)) {
            City nextCity, city;
            city = finish;
            cityPath.addFirst(finish);
            do {
                nextCity = visitedCitiesWithPrevious.get(city);
                cityPath.addFirst(nextCity);
                city = nextCity;
            } while (nextCity != null && !nextCity.equals(start));
        }
        return cityPath;
    }
}
