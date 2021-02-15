package gr.iotlabsgr.commons.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DataStructureUtils {

    /**
     * It returns the number of the occurrences of an element into a list.
     * 
     * @param list
     * @return Map<K:element, V:#occurrences>
     */
    public static Map<String, Integer> countListElementOccurrences(ArrayList<String> list) {
	Map<String, Integer> elementsMap = new HashMap<String, Integer>();
	for (String e : list) {
	    elementsMap.put(e, (elementsMap.get(e) != null) ? elementsMap.get(e) + 1 : 1);
	}
	return elementsMap;
    }

    /**
     * It sorts a Map<K,V> by the key.
     * 
     * @param unsortedMap
     * @param sortingOrder Use the SortingOrder Enumeration for getting the
     *                     available options
     * @return Sorted List of Map Entries
     */
    public static List<Entry<String, Integer>> sortMapByKey(Map<String, Integer> unsortedMap,
	    final SortingOrder sortingOrder) {
	/* Create an empty list for storing the ordered entries */
	List<Entry<String, Integer>> sortedList = new LinkedList<Entry<String, Integer>>(unsortedMap.entrySet());
	/* Sort the list using the key of the entry */
	Collections.sort(sortedList, new Comparator<Entry<String, Integer>>() {
	    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		return (sortingOrder.equals(SortingOrder.DESCENDING)) ? (o2.getKey()).compareTo(o1.getKey())
			: (o1.getKey()).compareTo(o2.getKey());
	    }
	});
	return sortedList;
    }

    /**
     * It sorts a Map<K,V> by the value.
     * 
     * @param unsortedMap
     * @param sortingOrder Use the SortingOrder Enumeration for getting the
     *                     available options
     * @return Sorted List of Map Entries
     */
    public static List<Entry<String, Integer>> sortMapByValue(Map<String, Integer> unsortedMap,
	    final SortingOrder sortingOrder) {
	/* Create an empty list for storing the ordered entries */
	List<Entry<String, Integer>> sortedList = new LinkedList<Entry<String, Integer>>(unsortedMap.entrySet());
	/* Sort the list using the value of the entry */
	Collections.sort(sortedList, new Comparator<Entry<String, Integer>>() {
	    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		return (sortingOrder.equals(SortingOrder.DESCENDING)) ? (o2.getValue()).compareTo(o1.getValue())
			: (o1.getValue()).compareTo(o2.getValue());
	    }
	});
	return sortedList;
    }

}