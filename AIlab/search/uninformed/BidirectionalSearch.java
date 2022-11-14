package AIlab.search.uninformed;

import AIlab.City;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BidirectionalSearch {
    private static LinkedList<City> cityPath;
    private static HashMap<City, City> citiesLeft, citiesRight;

    public static LinkedList<City> search(City startCity, City finishCity) {
        cityPath = new LinkedList<>();
        citiesLeft = new HashMap<>();
        citiesRight = new HashMap<>();

        if (startCity == null || finishCity == null) return cityPath;
        searchR(startCity, finishCity);
        return cityPath;
    }

    private static void searchR(City startCity, City finishCity) {
        HashSet<City> currentCitiesLeft = new HashSet<>();
        HashSet<City> currentCitiesRight = new HashSet<>();
        HashSet<City> nextCitiesLeft = new HashSet<>();
        HashSet<City> nextCitiesRight = new HashSet<>();

        currentCitiesLeft.add(startCity);
        currentCitiesRight.add(finishCity);
        citiesLeft.put(startCity, null);
        citiesRight.put(finishCity, null);

        while (!currentCitiesLeft.isEmpty() && !currentCitiesRight.isEmpty()) {
            currentCitiesLeft.forEach(
                    city -> {
                        city.getNextCities().keySet().forEach(
                                nextCity -> {
                                    if (!citiesLeft.containsKey(nextCity)) {
                                        citiesLeft.put(nextCity, city);
                                        nextCitiesLeft.add(nextCity);
                                    }
                                }
                        );
                    }
            );
            currentCitiesLeft.clear();
            currentCitiesLeft.addAll( nextCitiesLeft);
            nextCitiesLeft.clear();

            currentCitiesRight.forEach(
                    city -> {
                        city.getNextCities().keySet().forEach(
                                nextCity -> {
                                    if (!citiesRight.containsKey(nextCity)) {
                                        citiesRight.put(nextCity, city);
                                        nextCitiesRight.add(nextCity);
                                    }
                                }
                        );
                    }
            );
            currentCitiesRight.clear();
            currentCitiesRight.addAll(nextCitiesRight);
            nextCitiesRight.clear();

            for (City city: citiesLeft.keySet()){
                if (citiesRight.containsKey(city)){
                    cityPath =  getCityPath(startCity, city, finishCity);
                    return;
                }
            }
        }
    }

    private static LinkedList<City> getCityPath(City start, City middle, City finish) {
        if (citiesLeft.containsKey(middle)) {
            City nextCity, city;
            city = middle;
            cityPath.addFirst(middle);
            do {
                nextCity = citiesLeft.get(city);
                cityPath.addFirst(nextCity);
                city = nextCity;
            } while (nextCity != null && !nextCity.equals(start));
        }
        if (citiesRight.containsKey(middle)) {
            City nextCity, city;
            city = middle;
            //cityPath.addLast(middle);
            do {
                nextCity = citiesRight.get(city);
                cityPath.addLast(nextCity);
                city = nextCity;
            } while (nextCity != null && !nextCity.equals(finish));
        }
        return cityPath;
    }
}
