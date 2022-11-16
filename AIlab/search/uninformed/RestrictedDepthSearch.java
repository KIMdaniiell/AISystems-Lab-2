package AIlab.search.uninformed;

import AIlab.city.City;

import java.util.HashMap;
import java.util.LinkedList;

public class RestrictedDepthSearch implements UninformedSearch {
    private LinkedList<City> cityPath;
    private HashMap<City, Integer> visitedCitiesWithDepth ;
    private int depthLimit = 5;

    public RestrictedDepthSearch (int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public LinkedList<City> search (City startCity, City finishCity) {
        cityPath = new LinkedList<>();
        visitedCitiesWithDepth = new HashMap<>();

        if (startCity != null && !startCity.getNextCities().isEmpty())
            searchR(startCity, finishCity, depthLimit+1);
        return cityPath;
    }

    protected void searchR (City startCity, City finishCity, int limit) {
        City city = startCity;
        do {
            //Добавить город в путь
            cityPath.addLast(city);

            //Добавить город в список посещенных городов
            if (!wasVisited(city) || visitedCitiesWithDepth.get(cityPath.get(cityPath.size()-2)) + 1 < visitedCitiesWithDepth.get(city) ) {
                int previousCityDepth;
                if (cityPath.size() == 1) {
                    previousCityDepth = 0;
                } else {
                    previousCityDepth = visitedCitiesWithDepth.get(cityPath.get(cityPath.size() - 2 ));
                }
                visitedCitiesWithDepth.put(city, previousCityDepth+1);
            }

            //Найти следующий город
            city = chooseNextCity(city, limit);
            if (city.equals(startCity) && findNextAbleToVisit(startCity, limit) == null ) {
                break;
            }
        } while (!city.equals(finishCity));
        cityPath.addLast(city);
    }

    protected City chooseNextCity(City city, int limit) {
        City nextCity = findNextAbleToVisit(city, limit);
        if (nextCity == null) {
            nextCity = goBack(city, limit);
        }
        return nextCity;
    }

    protected City findNextAbleToVisit (City city, int limit) {
        for (City nextCity : city.getNextCities().keySet()) {
            if (isAbleToVisit(nextCity, limit)) return nextCity;
        }
        return null;
    }

    protected City goBack (City city, int limit) {
        int cursor = cityPath.size()-2;
        do {
            city = cityPath.get(cursor);;
            cityPath.addLast(city);
            cursor--;
        } while (findNextAbleToVisit(city, limit) == null && !city.equals(cityPath.getFirst()));
        cityPath.removeLast();
        return city;
    }

    protected boolean wasVisited (City city) {
        return visitedCitiesWithDepth.containsKey(city);
    }

    private boolean isAbleToVisit (City city, int limit) {
        int currentDepth = visitedCitiesWithDepth.get(cityPath.getLast());
        int currentDepthToNext = currentDepth + 1;
        if (wasVisited(city)) {
            int depthFromMap = visitedCitiesWithDepth.get(city);
            return depthFromMap - currentDepthToNext > 1;
        }
        return currentDepthToNext <= limit;
    }
}
