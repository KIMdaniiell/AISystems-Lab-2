package AIlab.search.uninformed;

import AIlab.city.City;

import java.util.LinkedList;


public class IterativeDepthSearch implements UninformedSearch{
    private int startingLimit = 5, maxLimit = 5;

    public IterativeDepthSearch(int startingLimit, int maxLimit) {
        this.startingLimit = startingLimit;
        this.maxLimit = maxLimit;
    }

    public LinkedList<City> search (City startCity, City finishCity) {
        LinkedList<City> cityPath = new LinkedList<>();

        for (int i = startingLimit; i<=maxLimit; i++) {
            RestrictedDepthSearch restrictedDepthSearch = new RestrictedDepthSearch(i);
            LinkedList<City> currentIterationResult = restrictedDepthSearch.search(startCity, finishCity);
//            currentIterationResult.forEach(o ->System.out.printf("   %s",o));
//            System.out.printf("\t\t [%d]\n",i);
            cityPath.addAll(currentIterationResult);
            if (cityPath.getLast().equals(finishCity)) return cityPath;
        }
        return cityPath;
    }
}
