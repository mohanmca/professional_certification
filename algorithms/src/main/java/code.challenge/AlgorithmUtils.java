package code.challenge;

public class AlgorithmUtils {
    public static void main(String[] args) {
        printEquals(3, 3);
        printEquals(3, 4);
        AlgorithmUtils.printEquals("mohan", "nahom");
    }

    public static void printEquals(Integer amount, Integer amount3) {
        if (amount.equals(amount3)) {
            System.out.printf("Integer is equal :: %1$d\n", amount);
        } else {
            System.err.printf("X - Integer is un-equal :: %1$d\n", amount);
        }
    }

    public static void printEquals(String first, String second) {
        if (first.equals(second)) {
            System.out.printf("String is equal :: %1$s\n", first);
        } else {
            System.err.printf("X - String is un-equal :: %1$s vs %2$s\n", first, second);
        }
    }

    public static String reverse(String s){
        char[] text = s.toCharArray();
        for(int i=0;  i < text.length/2; i++) {
            char temp  = text[(text.length-1)-i];
            text[(text.length-1)-i] = text[i];
            text[i] = temp;
        }
        return new String(text);
    }

}
