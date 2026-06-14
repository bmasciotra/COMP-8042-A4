package com.assignment4.Sorting;

import java.util.Random;

public class QuickInsertionSort<T extends Comparable<T>> {
    private final int Size = 20;
    static int insertCount = 0;
    static int pivotCount = 0;
    static Random random = new Random();
    InsertionSort<T> sorter = new InsertionSort<>();


    public void quickInsertionSort(T[] arr) {
        InsertionSort.insertCount = 0;
        pivotCount = 0;
        // Setting the seed for random tests
        random.setSeed(281);

        quickInsertionSort(arr, 0, arr.length - 1);

        insertCount = InsertionSort.insertCount;
    }

    //Using this to call recursively makes things easier
    public void quickInsertionSort(T[] arr, int low, int high) {
        if (high - low + 1 < Size) {
            sorter.insertionSort(arr, low, high);
            return;
        }

        int p = partition(arr, low, high);

        quickInsertionSort(arr, low, p - 1);
        quickInsertionSort(arr, p + 1, high);
    }

    //returns the index of the pivot element
    //everything to the left of the pivot is less than the pivot and everything to the right is greater than the pivot
    private int partition(T[] arr, int left, int right) {
        pivotCount++;

        int pivotIndex = left + random.nextInt(right - left + 1);

        swap(arr, pivotIndex, right);

        T pivot = arr[right];

        int i = left - 1;

        for (int j = left; j < right; j++) {
            T element = arr[j];
            if (element.compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, right);
        return i + 1;
    }

    //helper method
    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
