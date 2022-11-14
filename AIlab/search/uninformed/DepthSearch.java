package AIlab.search.uninformed;

import AIlab.City;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

public class DepthSearch {
    private static LinkedList<City> cityPath;
    private static HashSet<City> visitedCities;

    public static LinkedList<City> search (City startCity, City finishCity) {
        cityPath  = new LinkedList<>();
        visitedCities = new HashSet<>();

        if (startCity != null && !startCity.getNextCities().isEmpty())
            searchR(startCity, finishCity);
        return cityPath;
    }

    private static void searchR (City startCity, City finishCity) {
        City city = startCity;
        do {
            //Добавить город в путь
            cityPath.addLast(city);

            //Добавить город в список посещенных городов
            if (!wasVisited(city))
                visitedCities.add(city);

            //Найти следующий город
            city = chooseNextCity(city);
            //if ((city.equals(startCity) && cityPath.size()>1)) {
            if (city.equals(startCity) && findNextNotVisited(startCity) == null ) {
                break;
            }
        } while (!city.equals(finishCity));
        cityPath.addLast(city);
    }

    private static City chooseNextCity(City city) {
        City nextCity = findNextNotVisited(city);
        if (nextCity == null) {
            nextCity = goBack(city);
        }
        return nextCity;
    }

    private static City findNextNotVisited (City city) {
        for (City nextCity : city.getNextCities().keySet()) {
            if (!wasVisited(nextCity)) return nextCity;
        }
        return null;
    }

    private static City goBack (City city) {
        LinkedList<City> reverseCityPath = new LinkedList<>();
        ListIterator<City> cityPathIterator =  cityPath.listIterator(cityPath.size()-1);
        while (findNextNotVisited(city) == null && !city.equals(cityPath.getFirst())) {
            city = cityPathIterator.previous();
            if (reverseCityPath.contains(city)) {
                while (!reverseCityPath.getLast().equals(city)) reverseCityPath.removeLast();
                reverseCityPath.removeLast();
            }
            reverseCityPath.add(city);
        }
        // добавить города в путь
        if (!reverseCityPath.isEmpty())
            reverseCityPath.removeLast();
        for (City c : reverseCityPath) cityPath.addLast(c);
        return city;
    }

    private static boolean wasVisited (City city) {
        return visitedCities.contains(city);
    }
}
