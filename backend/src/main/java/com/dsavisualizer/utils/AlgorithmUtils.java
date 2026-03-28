package com.dsavisualizer.utils;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmUtils {

    public static List<String> generateBubbleSortSteps(int[] arr) {
        List<String> steps = new ArrayList<>();
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                steps.add(String.format("Compare arr[%d](%d) and arr[%d](%d)", j, arr[j], j + 1, arr[j + 1]));

                if (arr[j] > arr[j + 1]) {
                    // Swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    steps.add(String.format("Swap arr[%d] and arr[%d]", j, j + 1));
                }
            }
        }

        return steps;
    }

    public static String getTimeComplexity(String algorithmName) {
        return switch (algorithmName.toLowerCase()) {
            case "bubble sort", "insertion sort", "selection sort" -> "O(n²)";
            case "merge sort", "quick sort", "heap sort" -> "O(n log n)";
            case "counting sort", "radix sort" -> "O(n + k)";
            case "binary search" -> "O(log n)";
            case "linear search" -> "O(n)";
            default -> "Unknown";
        };
    }

    public static String getSpaceComplexity(String algorithmName) {
        return switch (algorithmName.toLowerCase()) {
            case "bubble sort", "selection sort", "insertion sort" -> "O(1)";
            case "merge sort" -> "O(n)";
            case "quick sort" -> "O(log n)";
            case "heap sort" -> "O(1)";
            default -> "Unknown";
        };
    }
}
