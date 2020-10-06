package code.challenge;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class ReverseShuffleMerge {

    // Input string was created by merge(reverse(s), shuffle(s))
    // find actual s from given input
    static String reverseShuffleMerge(String s) {
        String firstHalf = s.substring(0, s.length()/2);
        String secondHalf = s.substring(s.length()/2);
        String orig = AlgorithmUtils.reverse(firstHalf);
        if( orig.charAt(0) <= secondHalf.charAt(0) ) {
            return orig;
        }
        return secondHalf;
    }





    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        AlgorithmUtils.printEquals(reverseShuffleMerge("abab"), "ab");
        AlgorithmUtils.printEquals(reverseShuffleMerge("eggegg"), "egg");
        AlgorithmUtils.printEquals(reverseShuffleMerge("abcdefgabcdefg"), "abcdefg");
        AlgorithmUtils.printEquals(reverseShuffleMerge("aeiouuoiea"), "aeiou");


    }
}
