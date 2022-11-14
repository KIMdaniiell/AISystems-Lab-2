package AIlab.search;

import AIlab.City;
import AIlab.CityParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class MASearch {
    private static LinkedList<City> cityPath;
    private static HashMap<City, Integer> visitedCitiesWithDistance;
    private static CityParser cityDistanceParser;
    private static String finishCityName;

    public static LinkedList<City> search (City startCity, City finishCity) {
        cityPath = new LinkedList<>();
        visitedCitiesWithDistance = new HashMap<>();

        if (startCity != null && !startCity.getNextCities().isEmpty())
            finishCityName = finishCity.getName();
            cityDistanceParser = new CityParser("src\\input2.txt", " ");
            searchR(startCity, finishCity);
        return cityPath;
    }

    private  static void searchR (City startCity, City finishCity) {
        City city = startCity;
        do {
            //Добавить город в путь
            cityPath.addLast(city);

            //Добавить город в список посещенных городов
            if (!wasVisited(city)) {
                int previousCityDistance;
                int distanceFromPrevious;
                if (cityPath.size() == 1) {
                    previousCityDistance = 0;
                    distanceFromPrevious = 0;
                } else {
                    City previousCity = cityPath.get(cityPath.size() - 2 );
                    previousCityDistance = visitedCitiesWithDistance.get(previousCity);
                    distanceFromPrevious = previousCity.getNextCities().get(city);
                }
                visitedCitiesWithDistance.put(city, previousCityDistance + distanceFromPrevious);
            }

            /**System.out.printf("\t%s\t ----> \n", city.getName());
            for (City c: city.getNextCities().keySet()) {
                if (wasVisited(c)) {
                    System.out.printf("\t\t\t%s\t [ %d + ( %d ) = %d] \n",
                            c.getName(),
                            visitedCitiesWithDistance.get(c),
                            getDistanceFromFinish(c),
                            getDistanceFromFinish(c) + visitedCitiesWithDistance.get(c));
                } else {
                    int previousCityDistance;
                    int distanceFromPrevious;
                    if (cityPath.size() < 1) {
                        previousCityDistance = 0;
                        distanceFromPrevious = 0;
                    } else {
                        previousCityDistance = visitedCitiesWithDistance.get(city);
                        distanceFromPrevious = city.getNextCities().get(c);
                    }
                    System.out.printf("\t\t\t%s\t [ %d + %d  + ( %d ) = %d ] \n",
                            c.getName(),
                            previousCityDistance,
                            distanceFromPrevious,
                            getDistanceFromFinish(c),
                            previousCityDistance + distanceFromPrevious + getDistanceFromFinish(c));
                }
            }*/

            //Найти следующий город
            city = chooseNextCity(city);
            /**System.out.printf("\t ----> \t%s\n\n", city.getName());*/
            if ((city.equals(startCity) && cityPath.size()>1)) {
                break;
            }
        } while (!city.equals(finishCity));
        cityPath.addLast(city);
    }

    private static City chooseNextCity(City city) {
        City nextCity = findNextAbleToVisit(city);
        if (nextCity == null) {
            nextCity = goBack(city);
        }
        return nextCity;
    }

    private static City findNextAbleToVisit (City city) {
        LinkedList<City> list = sortCities(city.getNextCities().keySet());
        for (City nextCity : list) {
            if (isAbleToVisit(nextCity)) return nextCity;
        }
        return null;
    }

    private static City goBack (City city) {
        do {
            cityPath.removeLast();
            city = cityPath.getLast();
        } while (findNextAbleToVisit(city) == null && !city.equals(cityPath.getFirst()));
        cityPath.removeLast();
        return city;
    }

    private static boolean wasVisited (City city) {
        return visitedCitiesWithDistance.containsKey(city);
    }

    private static boolean isAbleToVisit (City city) {
        City currentCity = cityPath.getLast();
        int currentDistance = visitedCitiesWithDistance.get(currentCity);
        int currentDepthToNext = currentDistance + currentCity.getNextCities().get(city);
        if (wasVisited(city)) {
            int distanceFromMap = visitedCitiesWithDistance.get(city);
            return distanceFromMap > currentDepthToNext;
        }
        return true;
    }

    private static LinkedList<City> sortCities(Set<City> cities) {
        LinkedList<City> list = new LinkedList<>(cities);
        list.sort((city1, city2) -> {
            int distance1 = getDistanceFromFinish(city1);
            if (wasVisited(city1)) distance1 +=  + visitedCitiesWithDistance.get(city1);
            int distance2 = getDistanceFromFinish(city2);
            if (wasVisited(city2)) distance2 += + visitedCitiesWithDistance.get(city2);
            return distance1 - distance2;
        });
        return list;
    }

    private static int getDistanceFromFinish (City city) {
        cityDistanceParser.parse();
        City finalCity = cityDistanceParser.getCityFromMap(finishCityName);
        String cityName = city.getName();
        City cityDistancedFromFinish = cityDistanceParser.getCityFromMap(cityName);
        return finalCity.getNextCities().get(cityDistancedFromFinish);
    }
}
