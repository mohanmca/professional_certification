package code.challenge.sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
//        int[] pi_values = new int[]{3, 1, 4, 2, 8, 5, 7, 1, 4, 2, 8, 6};
        int[] pi_values = new int[]{3, 1, 4, 2, 8, 5, 7};
        quickSort(pi_values, 0, pi_values.length-1);
        Arrays.stream(pi_values).forEach(System.out::print);
    }

    public static int[] quickSort(int[] input, int low, int high) {
        if (low < high) {
            int pivot = partition(input, low, high);
            quickSort(input, low, pivot - 1);
            quickSort(input, pivot + 1, high);
        }
        return input;
    }

    public static int partition1(int[] input, int low, int high) {
        int pivot = input[(high + low) / 2];
        int i = low;
        int j = high;
        System.out.printf("Pivot index, and Pivot  :: %d, %d\n", ( (high + low) / 2 ), pivot );
        while (true) {
            while (input[i] < pivot && i < high) {
                i = i + 1;
            }
            while (j > -1 && input[j] > pivot) {
                j = j - 1;
            }
            if (i >= j) {
                //System.out.printf("Partitioned index  :: %d\n", j );
                return j;
            }
            swap(input, i, j);
        }
    }

    private static int partition(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,end);
        System.out.printf("Partitioned index of (%d, %d)  :: %d\n", begin, end, (i+1) );

        return i+1;
    }

    private static void swap(int[] input, int i, int j) {
        if(i==j) return ;
        System.out.println("Before :: " + Arrays.toString(input) + " - " + i + " - " + j);
        int tmp = input[i];
        input[i] = input[j];
        input[j] = tmp;

        System.out.println("After  :: " +Arrays.toString(input));
    }
}
