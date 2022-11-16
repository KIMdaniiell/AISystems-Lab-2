package AIlab.city;

import AIlab.city.City;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Scanner;


public class CityParser {
    private final String PATH;
    private final String DELIMITER;
    private final HashMap <String, City> stringCityHashMap;

    public CityParser() {
        this("src\\input.txt");
    }
    public CityParser(String path){
        this("src\\input.txt", ",");
    }
    public CityParser(String path, String delimiter){
        this.PATH = path;
        this.DELIMITER = delimiter;
        this.stringCityHashMap = new HashMap<>();
    }

    public int parse() {
        //Opens file and processes lines via parseCities()
        try {
            Scanner scanner = new Scanner(new File(PATH));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!parseCities(line)) {
                    return 2;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return 1;
        }
        return 0;
    }

    public void printAll() {
        stringCityHashMap.keySet().stream().sorted().forEach(System.out::println);
    }

    public City getCityFromMap(String name) {
        return stringCityHashMap.get(name);
    }

    private boolean parseCities(String line) {
        //Fills the map and city's maps.
        String[] data = line.split(DELIMITER);

        if (data.length != 3) {
            return false;
        }

        City cityA = getSingleToneCity(data[0].trim());
        City cityB = getSingleToneCity(data[1].trim());
        Integer distance = Integer.valueOf(data[2].trim());

        cityA.putNext(cityB, distance);
        cityB.putNext(cityA, distance);
        return true;
    }

    private City getSingleToneCity(String name) {
        //Sures if city instance is in the map and returns it
        City city;
        if (stringCityHashMap.containsKey(name)) {
            city = stringCityHashMap.get(name);
        } else {
            city = new City(name);
            stringCityHashMap.put(name, city);
        }
        return city;
    }
}
