package AIlab;

import AIlab.search.*;
import AIlab.search.uninformed.*;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        CityParser cityParser = new CityParser("src\\input.txt", " ");
        cityParser.parse();

        City start = cityParser.getCityFromMap("Москва");
        City finish = cityParser.getCityFromMap("Ярославль");

        System.out.println("[поиск в глубину]"); printList(DepthSearch.search(start, finish));
        System.out.println("[поиск в ширину]"); printList(BreadthSearch.search(start, finish));
        System.out.println("[поиск с ограничением глубины]"); printList(RestrictedDepthSearch.search(start, finish, 5));
        System.out.println("[поиск с итеративным углублением]"); printList(IterativeDepthSearch.search(start, finish, 5, 5));
        System.out.println("[двунаправленный поиск]"); printList(BidirectionalSearch.search(start, finish));

        System.out.println("[жадный поиск]"); printList(BestFirstSearch.search(start, finish));
        System.out.println("[А звездочка]"); printList(MASearch.search(start, finish));
    }

    private static void printList (LinkedList<City> linkedlist) {
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
            System.out.printf(" [%d]\n", length);
        } else {
            System.out.println("-не удалось найти путь-");
        }
    }
}
