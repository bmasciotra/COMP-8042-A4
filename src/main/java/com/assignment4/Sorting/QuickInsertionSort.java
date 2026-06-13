package com.assignment4.Sorting;

import java.util.Random;

public class QuickInsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;
    static int pivotCount = 0;
    // If you want to randomly choose pivots, you can use this (not necessary)
    static Random random = new Random();
    InsertionSort<T> sorter = new InsertionSort<>();


    public void quickInsertionSort(T[] arr) {
        InsertionSort.insertCount = 0;
        pivotCount                = 0;

        quickInsertionSort(arr, 0, arr.length - 1);

        insertCount = InsertionSort.insertCount;
    }

    //Using this to call recursively makes things easier
    public void quickInsertionSort(T[] arr, int low, int high) {
        if (low >= high) return;

        int p = partition(arr, low, high);

        quickInsertionSort(arr, low, p - 1);
        quickInsertionSort(arr, p + 1, high);
    }

    //returns the index of the pivot element
    //everything to the left of the pivot is less than the pivot and everything to the right is greater than the pivot
    private int partition(T[] arr, int left, int right) {
        T pivot = arr[right];
        int divider = left - 1;

        for (int i = left; i < right; i++) {
            if (arr[i].compareTo(pivot) < 0) {
                divider++;
                swap(arr, divider, i);
            }
        }

        swap(arr, divider + 1, right);
        return divider + 1;
    }

    //helper method
    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
