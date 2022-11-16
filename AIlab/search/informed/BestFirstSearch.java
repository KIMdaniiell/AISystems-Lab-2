package AIlab.search.informed;

import AIlab.city.City;
import AIlab.city.CityParser;

import java.util.*;

public class BestFirstSearch implements InformedSearch{
    private LinkedList<City> cityPath;
    private HashSet<City> visitedCities;
    private CityParser cityDistanceParser;
    private String finishCityName;

    public BestFirstSearch (CityParser cityDistanceParser) {
        this.cityDistanceParser = cityDistanceParser;
    }

    public LinkedList<City> search (City startCity, City finishCity) {
        cityPath  = new LinkedList<>();
        visitedCities = new HashSet<>();

        if (startCity != null && !startCity.getNextCities().isEmpty())
            finishCityName = finishCity.getName();
            searchR(startCity, finishCity);
        return cityPath;
    }

    private void searchR (City startCity, City finishCity) {
        City city = startCity;
        do {
            //Добавить город в путь
            /**System.out.printf("\t%s\t ----> \n", city.getName());
            for (City c: city.getNextCities().keySet())
                System.out.printf("\t\t\t%s\t [ %d ] \n",c.getName(),getDistanceFromFinish(c));**/
            cityPath.addLast(city);

            //Добавить город в список посещенных городов
            if (!wasVisited(city))
                visitedCities.add(city);

            //Найти следующий город
            city = chooseNextCity(city);
            //System.out.printf("\t ----> \t%s\n", city.getName());
            if ((city.equals(startCity) && cityPath.size()>1)) {
                break;
            }
        } while (!city.equals(finishCity));
        cityPath.addLast(city);

    }

    private City chooseNextCity(City city) {
        City nextCity = findNextNotVisited(city);
        if (nextCity == null) {
            nextCity = goBack(city);
        }
        return nextCity;
    }

    protected City findNextNotVisited (City city) {
        LinkedList<City> list = sortCities(city.getNextCities().keySet());
        for (City nextCity : list) {
            if (!wasVisited(nextCity)) return nextCity;
        }
        return null;
    }

    private City goBack (City city) {
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

    private boolean wasVisited (City city) {
        return visitedCities.contains(city);
    }

    private LinkedList<City> sortCities(Set<City> cities) {
        LinkedList<City> list = new LinkedList<>(cities);
        list.sort((city1, city2) -> {
            int distance1 = getDistanceFromFinish(city1);
            int distance2 = getDistanceFromFinish(city2);
            return distance1 - distance2;
        });
        return list;
    }

    private int getDistanceFromFinish (City city) {
        City finalCity = cityDistanceParser.getCityFromMap(finishCityName);
        String cityName = city.getName();
        City cityDistancedFromFinish = cityDistanceParser.getCityFromMap(cityName);
        return finalCity.getNextCities().get(cityDistancedFromFinish);
    }
}


