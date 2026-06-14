package com.assignment4.Sorting;

import java.util.*;

public class MergeInsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;
    static int mergeCount = 0;
    InsertionSort<T> insertionSorter = new InsertionSort<T>();

    public void insertMergeSort(T[] arr) {
        InsertionSort.insertCount = 0;
        mergeCount = 0;

        insertMergeSort(arr, 0, arr.length - 1);
        // update this classes insertCount when the array has been sorted
        insertCount = InsertionSort.insertCount;
    }

    // Using this to call recursively makes things easier
    private void insertMergeSort(T[] arr, int left, int right) {
        if (left < right) {
            int size = right - left + 1;
            // Base case for insertion
            if (size < 10) {
                insertionSorter.insertionSort(arr, left, right);  // sort small chunk
                return;
            }

            int middle = left + (right - left) / 2;

            insertMergeSort(arr, left, middle);
            insertMergeSort(arr, middle + 1, right);

            merge(arr, left, middle, right);
        }
    }

    //merge two sub-arrays which are beside each other
    private void merge(T[] arr, int leftStart, int center, int rightEnd) {
        mergeCount++;

        int leftSize = center - leftStart + 1;
        int rightSize = rightEnd - center;

        // Copy contents to separate arrays
        T[] leftArray = (T[]) new Comparable[leftSize];
        T[] rightArray = (T[]) new Comparable[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = arr[leftStart + i];
        }

        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = arr[center + 1 + j];
        }

        int i = 0, j = 0;
        int k = leftStart;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i].compareTo(rightArray[j]) <= 0) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }

    }


}
