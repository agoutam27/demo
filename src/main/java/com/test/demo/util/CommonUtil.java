package com.test.demo.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtil {

    /**
     * Add new item in sorted order in the list and returns the index where element was added.
     *
     * @param <T>        the type parameter
     * @param list       the list
     * @param item       the item
     * @param comparator the comparator
     * @return the index where element was inserted
     */
    public static <T> int addInSortedOrder(List<T> list, T item, Comparator<T> comparator) {

        if (list.isEmpty() || comparator.compare(item, list.get(list.size() - 1)) >= 0) {
            list.add(item);
            return list.size() - 1;
        }

        if (comparator.compare(item, list.get(0)) <= 0) {
            list.add(0, item);
            return 0;
        }

        int low = 0;
        int high = list.size() - 1;

        while (low < high - 1) {
            int pivot = (low + high) / 2;

            int compare = comparator.compare(item, list.get(pivot));

            if (compare < 0) {
                high = pivot;
            } else {
                low = pivot;
            }
        }
        list.add(high, item);
        return high;
    }

    /**
     * Check if a list is subset of another list.
     *
     * @param mainList  Big list
     * @param subList   Small list
     * @param <T>       Type parameter
     * @return  True if subList is subset of mainList else false
     */
    public static <T> boolean isSubset(List<T> mainList, List<T> subList) {
        if (subList.size() > mainList.size()) return false;

        Set<T> subListItems = new HashSet<>(subList);

        for (T mainListItem : mainList) {
            subListItems.remove(mainListItem);
        }

        return subListItems.isEmpty();
    }

    /**
     * Is collection object blank.
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the boolean
     */
    public static <T> boolean isBlank(Collection<T> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * Is string blank.
     *
     * @param obj the obj
     * @return the boolean
     */
    public static boolean isBlank(String obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * Is collection object not blank.
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the boolean
     */
    public static <T> boolean isNotBlank(Collection<T> obj) {
        return !isBlank(obj);
    }

    /**
     * Is string not blank.
     *
     * @param obj the obj
     * @return the boolean
     */
    public static boolean isNotBlank(String obj) {
        return !isBlank(obj);
    }
}
