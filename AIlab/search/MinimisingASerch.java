/*
package AIlab.search;

import AIlab.City;
import AIlab.CityParser;

import java.util.*;

public class MinimisingASerch {
    private static LinkedList<City> cityPath;
    private static HashMap<City, Integer> visitedCitiesWithDistance;
    private static CityParser cityDistanceParser;
    private static String finishCityName;

    public static LinkedList<City> search (City startCity, City finishCity) {
        cityPath = new LinkedList<>();
        visitedCitiesWithDistance = new HashMap<>();

        if (startCity != null && finishCity != null){
            finishCityName = finishCity.getName();
            cityDistanceParser = new CityParser("src\\input2.txt", " ");
            visitedCitiesWithDistance.put(startCity, 0);
            cityPath.add(startCity);

            //TODO ШАГ 1: осматриваемся.
            //TODO ШАГ 2: расчитываем оценки для соседних городов.
            //TODO ШАГ 3: смотрим, есть ли смысл здесь идти
            //TODO ШАГ 4: если нет - возвращаемся
            //TODO ШАГ 5: если да - выбираем наиближайший город
            searchR(startCity);
        }
        return cityPath;
    }

    private static void searchR (City city) {


        while (!visitedCitiesWithDistance.keySet().contains(finishCityName)) {
            try { Thread.sleep(300); } catch (InterruptedException e) {}
            System.out.printf("[%s]\n",city.getName());
            if (!visitedCitiesWithDistance.keySet().contains(city)) {
                visitedCitiesWithDistance.put(city, visitedCitiesWithDistance.get(cityPath.getLast()) + getDistance(city, cityPath.getLast()));
                cityPath.addLast(city);
            }
            city = chooseNextCity(city);
            System.out.printf("\t Выбрали %s\n",city.getName());
        }
    }

    private static City chooseNextCity(City city) {
        LinkedList<City> list = sortCities(city.getNextCities());

        for (City c : list) {
            if (visitedCitiesWithDistance.containsKey(c) ) {
            System.out.printf("\t-%s--%d-+-(%d)-=-%d-\n",
                    c.getName(),
                    getDistance(city, c),
                    getLinearDistance(c),
                    getDistance(city, c)+getLinearDistance(c));}
            else { System.out.printf("\t%s  %d + (%d) = %d\n",
                    c.getName(),
                    getDistance(city, c),
                    getLinearDistance(c),
                    getDistance(city, c)+getLinearDistance(c));}
        }

        for (City c: list) {
            if (!visitedCitiesWithDistance.containsKey(c)) return c;
        }
        try {
            City nextCity = goBack(city);
            return nextCity;
        } catch (Exception e) {
            System.out.println(e.getMessage());System.exit(1);}
        return null;
    }

    private static City goBack (City city) throws Exception {
        cityPath.removeLast();
        if (cityPath.isEmpty()) throw new Exception("В очереди закончились города!");
        return cityPath.getLast();
    }

    private static LinkedList<City> sortCities(Map<City, Integer> map) {
        LinkedList<City> list = new LinkedList<>(map.keySet());
        list.sort((city1, city2) -> {
            int distance1 = getDistanceFromFinish(city1);
            int distance2 = getDistanceFromFinish(city2);
            return distance1 - distance2;
        });
        return list;
    }

    private static void updateDistance(City city, City nextCity) {
        if (visitedCitiesWithDistance.containsKey(nextCity)
                && visitedCitiesWithDistance.get(nextCity)
                < (getDistance(city, nextCity)) ) {
        } else visitedCitiesWithDistance.put(nextCity, getDistance(city, nextCity) );
    }

    private static int getDistance(City city, City nextCity) {
        if (visitedCitiesWithDistance.containsKey(nextCity)) return visitedCitiesWithDistance.get(nextCity);
        return city.getNextCities().get(nextCity);
    }

    private static int getDistanceFromFinish (City city) {
        cityDistanceParser.parse();
        City finalCity = cityDistanceParser.getCityFromMap(finishCityName);
        String cityName = city.getName();
        City cityDistancedFromFinish = cityDistanceParser.getCityFromMap(cityName);
        return finalCity.getNextCities().get(cityDistancedFromFinish);
    }
}
*/
