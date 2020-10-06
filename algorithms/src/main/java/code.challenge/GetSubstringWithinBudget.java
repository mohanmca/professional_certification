package code.challenge;

import java.util.Arrays;

/**
 * You are given two strings s and t of the same length. You want to change s to t. Changing the i-th character of s to i-th character of t costs |s[i] - t[i]| that is, the absolute difference between the ASCII values of the characters.
 * You are also given an integer maxCost.
 * Return the maximum length of a substring of s that can be changed to be the same as the corresponding substring of twith a cost less than or equal to maxCost.
 * If there is no substring from s that can be changed to its corresponding substring from t, return 0.
 * <p>
 * Input: s = "abcd", t = "bcdf", maxCost = 3, Output: 3
 * Explanation: "abc" of s can change to "bcd". That costs 3, so the maximum length is 3.
 * <p>
 * Input: s = "abcd", t = "cdef", maxCost = 3
 * Output: 1
 * Explanation: Each character in s costs 2 to change to charactor in t, so the maximum length is 1.
 */
public class GetSubstringWithinBudget {

    public static int[] costDifference(String one, String two) {
        int[] cost = new int[one.length()];
        for (int i = 0; i < one.length() ; i++) {
            cost[i] = two.charAt(i) - one.charAt(i);
        }
        return cost;
    }

    public static int[] findSlidingCosts(int[] cost, int slidingLength) {
        int[] slidingCosts =  new int[(cost.length - slidingLength) + 1];
        int slidingCost = 0;
        for (int i = 0; i < (cost.length+1 - slidingLength); i++) {
            slidingCost += cost[i];
            if(i >= slidingLength) {
                slidingCosts[i-slidingLength] = slidingCost;
                slidingCost -= cost[i-slidingLength];
            }
        }
        System.out.printf("Sliding costs %1$s: = [ %1$s ]\n", Arrays.toString(slidingCosts));
        return slidingCosts;
    }

    public static boolean findContigiousCost(int[] cost, int fixed) {
        for (int i = cost.length-1; i >= 0; i--) {
            int actualCost = 0;
            String indices = "";
            for (int j = i; j >= 0; j--) {
                indices = indices + ", " + j;
                actualCost = actualCost + cost[j];
            }
            System.out.printf("Actual cost - %2$d, for length %1$d - Inclusive of %3$s \n", actualCost, i, indices);
        }
        return true;
    }

    public static void main(String[] args) {
        int[] costDifferences = costDifference("test", "uftu");
        System.out.println(Arrays.toString(costDifferences));
        System.out.println(findContigiousCost(costDifferences, 3));
        System.out.println(Arrays.toString(findSlidingCosts(costDifferences, 2)));
        System.out.println(Arrays.toString(findSlidingCosts(costDifferences, 3)));
        System.out.println(Arrays.toString(findSlidingCosts(costDifferences, 4)) );
    }


}
