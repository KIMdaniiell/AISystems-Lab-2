package AIlab;

import AIlab.city.City;
import AIlab.city.CityParser;
import AIlab.io.IOManagerl10n;
import AIlab.search.informed.BestFirstSearch;
import AIlab.search.informed.MASearch;
import AIlab.search.uninformed.*;

import java.util.LinkedList;

public class Main {
    private static IOManagerl10n ioManagerl10n;

    public static void main(String[] args) {
        System.out.println("\n"+"=".repeat(100)+"\n");
        /* Инициализация обработчика ввода вывода*/
        ioManagerl10n = initIOManager("ru");

        /* Чтение информации о городах и их связях */
        String citiesDataPath = getEnvData("CITIES_DATA");
        CityParser citiesDataParser = initCityParser(citiesDataPath, " ");
        System.out.printf(ioManagerl10n.getMessage("fileNameParseSuccess")+"\n", "данные о графе городов в "+citiesDataPath);
        citiesDataPath= getEnvData("TARGET_CITY_DATA");
        CityParser targetCityDataParser = initCityParser(citiesDataPath, " ");
        System.out.printf(ioManagerl10n.getMessage("fileNameParseSuccess")+"\n", "данные о графе городов в "+citiesDataPath);

        System.out.println("\n"+"=".repeat(100)+"\n");

        /*Запуск неинформированных алгоритмов поиска*/
        City start = citiesDataParser.getCityFromMap("Самара");
        City finish = citiesDataParser.getCityFromMap("Ярославль");



        printList("[поиск в глубину]", new DepthSearch().search(start, finish));
        System.out.println("-".repeat(100));
        printList("[поиск в ширину]", new BreadthSearch().search(start, finish));
        System.out.println("-".repeat(100));
        printList("[поиск с ограничением глубины]", new RestrictedDepthSearch(5).search(start, finish));
        System.out.println("-".repeat(100));
        printList("[поиск с итеративным углублением]", new IterativeDepthSearch(5,5).search(start, finish));
        System.out.println("-".repeat(100));
        printList("[двунаправленный поиск]", new BidirectionalSearch().search(start, finish));

        /*Запуск неинформированных алгоритмов поиска*/
        System.out.println("\n"+"=".repeat(100)+"\n");

        printList("[жадный поиск]", new BestFirstSearch(targetCityDataParser).search(start, finish));
        System.out.println("-".repeat(100));
        printList("[А звездочка]", new MASearch(targetCityDataParser).search(start, finish));
    }

    private static void printList (String searchName,  LinkedList<City> linkedlist) {
        System.out.println(searchName);
        if (!linkedlist.isEmpty()) {
            int length = 0;
            for (int i = 0; i<linkedlist.size()-1; i++) {
                if (linkedlist.get(i) != null && linkedlist.get(i+1) != null){
                    if (linkedlist.get(i).getNextCities().get(linkedlist.get(i+1)) == null) {
                        //System.out.printf("\nУзлы [%s] и [%s] не связаны!\n",linkedlist.get(i).getName(), linkedlist.get(i+1).getName() );
                    } else {
                        length += linkedlist.get(i).getNextCities().get(linkedlist.get(i + 1));
                    }
                }
            }
            City last = linkedlist.removeLast();
            System.out.print("\t");
            linkedlist.forEach(city -> System.out.print(city.toString() + " - "));
            if (last != null) System.out.print(last.toString());
            System.out.printf("\nДлина получившегося пути = [%d]\n", length);
        } else {
            System.out.println("-не удалось найти путь-");
        }
    }

    private static IOManagerl10n initIOManager (String LOCAL) {
        if (System.getenv("MESSAGES_SOURCE") == null) {
            System.out.println("ERROR!\t" + "Environment variable [MESSAGES_SOURCE] not found!");
            System.exit(1);
        }
        IOManagerl10n ioManagerl10n = new IOManagerl10n(
                System.getenv("MESSAGES_SOURCE"), LOCAL);
        if (!ioManagerl10n.parseMessageFile()) {
            System.out.printf("ERROR!\t" + "Localisation file [MESSAGES_SOURCE] not found!", "MESSAGES_SOURCE="+System.getenv("MESSAGES_SOURCE"));
            System.exit(1);
        }
        System.out.println(ioManagerl10n.getMessage("l10nSuccess"));
        return ioManagerl10n;
    }

    private static String getEnvData(String envName) {
        if (System.getenv(envName) == null) {
            System.out.printf(ioManagerl10n.getMessage("error")
                    + ioManagerl10n.getMessage("enVarNamedNotFound")
                    + "\n", envName);
            return null;
        }
        return System.getenv(envName);
    }

    private static CityParser initCityParser (String PATH, String DELIMITER) {
        if (PATH == null) {
            System.out.println(ioManagerl10n.getMessage("error")
                    + ioManagerl10n.getMessage("fileNotFound"));
            System.exit(2);
        }
        CityParser cityParser = new CityParser(PATH, DELIMITER);
        switch (cityParser.parse()) {
            case 1:
                System.out.printf(ioManagerl10n.getMessage("error")
                        + ioManagerl10n.getMessage("fileNameNotFound")
                        +"\n", "CITIES_DATA="+PATH);
                System.exit(2);
            case 2:
                System.out.printf(ioManagerl10n.getMessage("error")
                        + ioManagerl10n.getMessage("dataNameReadError")
                        +"\n", "delimiter issue in file "+PATH);
                System.exit(2);
        }
        return cityParser;
    }
}
