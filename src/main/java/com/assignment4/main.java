package com.assignment4;

import com.assignment4.Sorting.QuickInsertionSort;

public class main {

    public static void main(String[] args){

        QuickInsertionSort<Integer> q = new QuickInsertionSort<Integer>();
        Integer[] unsorted            = new Integer[]{10, 15, 2, 34, 8 ,19, 4};

        q.quickInsertionSort(unsorted,0, unsorted.length - 1);
        System.out.println(unsorted);


    }
}
