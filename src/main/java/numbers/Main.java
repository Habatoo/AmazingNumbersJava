package numbers;

import java.util.Scanner;
import java.util.StringJoiner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static long number;
    static long count;
    static String[] requestParts;

    public static void main(String[] args) {
        readNumber();
    }

    public static void readNumber() {
        showIntro();

        while (true) {
            System.out.println("Enter a request:");
            int scenario = chooseScenario();
            if (scenario == 1) {
                int result = isNatural(requestParts[0]);
                if (result == -1) {
                    System.out.println("The first parameter should be a natural number or zero.");
                } else if (result == 0) {
                    System.out.println("Goodbye! ");
                    break;
                } else {
                    number = Long.parseLong(requestParts[0], 10);
                    showFirstScenarioResult();
                }
            } else {
                int resultFirst = isNatural(requestParts[0]);
                int resultSecond = isNatural(requestParts[1]);
                if (resultFirst == 0) {
                    System.out.println("Goodbye! ");
                    break;
                } else if (resultFirst == -1){
                    System.out.println("The first parameter should be a natural number or zero.");
                } else if (resultSecond == -1){
                    System.out.println("The second parameter should be a natural number.");
                } else {
                    number = Long.parseLong(requestParts[0], 10);
                    count = Long.parseLong(requestParts[1], 10);
                    for (int i = 0; i < count; i++) {
                        showSecondScenarioResult();
                        number++;
                    }
                }
            }
        }
    }

    private static void showIntro() {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
    }

    private static void showSecondScenarioResult() {
        StringJoiner joiner = new StringJoiner(", ");
        if (isOddOrEven()) joiner.add("even");
        if (!isOddOrEven()) joiner.add("odd");
        if (isBuzz()) joiner.add("buzz");
        if (isDuck()) joiner.add("duck");
        if (isPalindromic()) joiner.add("palindromic");
        if (isGapful()) joiner.add("gapful");
        String result = joiner.toString().trim();

        System.out.printf("\t%d is %s\n", number, result);
    }

    private static void showFirstScenarioResult() {
        System.out.printf("Properties of %d\n", number);
        System.out.printf("\tbuzz:\t%b\n", isBuzz());
        System.out.printf("\tduck:\t%b\n", isDuck());
        System.out.printf("\tpalindromic:\t%b\n", isPalindromic());
        System.out.printf("\tgapful:\t%b\n", isGapful());
        System.out.printf("\teven:\t%b\n", !isOddOrEven());
        System.out.printf("\todd:\t%b\n", isOddOrEven());
    }

    private static int chooseScenario() {
        int scenario = 1;
        requestParts = scanner.nextLine().split(" ");
        if (requestParts.length > 1) {
            scenario = 2;
        }
        return scenario;
    }

    private static int isNatural(String string) {
        int natural = 1;
        try {
            Long number = Long.parseLong(string, 10);
            if (number < 0) {
                natural = -1;
            } else if (number == 0) {
                natural = 0;
            }
        } catch (Exception e) {
            natural = -1;
        }
        return natural;
    }

    private static boolean isOddOrEven() {
        boolean odd = true;
        if (number % 2 == 0) {
            odd = false;
        }
        return odd;
    }

    private static boolean isBuzz() {
        boolean buzz = false;
        if (number >= 7 && number % 7 == 0 || number % 10 == 7) {
            buzz = true;
        }
        return buzz;
    }

    private static boolean isDuck() {
        boolean duck = true;
        String string = Long.toString(number);
        if (!string.substring(1).contains("0")) {
            duck = false;
        }
        return duck;
    }

    private static boolean isPalindromic(){
        boolean palindromic = true;
        String string = Long.toString(number);
        String stringRev = new StringBuilder(string).reverse().toString();
        if (!string.equals(stringRev)) {
            palindromic = false;
        }
        return palindromic;
    }

    private static boolean isGapful(){
        boolean gapful = true;
        if (number < 100) {
            gapful = false;
        } else {
            long first = number;
            while(first > 9) {
                first /= 10;
            }
            long last = number % 10;
            long div = first * 10 + last;
            gapful = number % div == 0 ? true : false;
        }
        return gapful;
    }
}