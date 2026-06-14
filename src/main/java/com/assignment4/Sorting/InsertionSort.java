package com.assignment4.Sorting;

public class InsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;

    // Given a start and end index, sort the array from the start index to the end index
    public void insertionSort(T[] arr, int startIndex, int endIndex) {
        insertCount++;

        for (int i = startIndex + 1; i <= endIndex; i++) {
            T current = arr[i];

            int j = i - 1;

            while (j >= startIndex && arr[j].compareTo(current) >= 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = current;
        }
    }


    //Binary search already provided
    public int binarySearch(T[] arr, T target, int left, int right) {
        if (right >= left) {
            int mid = left + (right - left) / 2;
            if (arr[mid].compareTo(target) == 0) {
                return mid;
            }
            if (arr[mid].compareTo(target) > 0) {
                return binarySearch(arr, target, left, mid - 1);
            }
            return binarySearch(arr, target, mid + 1, right);
        }
        return left;
    }
}
