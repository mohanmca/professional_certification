package code.challenge;


/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("aaabaaaa"));
        System.out.println( longestPalindrome("a") );
        System.out.println( longestPalindrome("babad") );
        System.out.println( longestPalindrome("cbbd") );
    }

    public static String longestPalindrome(String s) {
        int beginIndex = 0, endIndex = 0, length = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (int j = s.length()-1; j >= i; j--) {
                int c1 = s.charAt(j);
                if (c1==c && j >= i && (j-i) > length) {
                    String text = s.substring(i, j + 1);
                    if (new StringBuilder(text).reverse().toString().equals(text)) {
                        if ((j - i) > length) {
                            beginIndex = i;
                            endIndex = j;
                            length = j - i;
                            break;
                        }
                    }
                }
            }
        }

        if (length > -1) return s.substring(beginIndex, endIndex + 1);
        return "";
    }
}
