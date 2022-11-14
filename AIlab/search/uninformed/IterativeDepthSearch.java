package AIlab.search.uninformed;

import AIlab.City;

import java.util.LinkedList;


public class IterativeDepthSearch {
    public static LinkedList<City> search (City startCity, City finishCity, int startingLimit, int endingLimit) {
        LinkedList<City> cityPath = new LinkedList<>();

        for (int i = startingLimit; i<=endingLimit; i++) {
            LinkedList<City> currentIterationResult = RestrictedDepthSearch.search(startCity, finishCity, i);
//            currentIterationResult.forEach(o ->System.out.printf("   %s",o));
//            System.out.printf("\t\t [%d]\n",i);
            cityPath.addAll(currentIterationResult);
            if (cityPath.getLast().equals(finishCity)) return cityPath;
        }
        return cityPath;
    }
}
