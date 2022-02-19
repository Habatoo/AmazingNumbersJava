package numbers;

import java.util.*;
import java.util.StringJoiner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static long number;
    static long count;
    static String property;
    static String[] propertyArray = new String[]{
            "BUZZ",
            "DUCK",
            "PALINDROMIC",
            "GAPFUL",
            "SPY",
            "EVEN",
            "ODD"};
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
                    System.out.println("Goodbye!");
                    break;
                } else {
                    number = Long.parseLong(requestParts[0], 10);
                    showFirstScenarioResult();
                }
            } else if (scenario == 2) {
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
            } else if (scenario == 3) {
                int resultFirst = isNatural(requestParts[0]);
                int resultSecond = isNatural(requestParts[1]);
                boolean resultThird = isInProperty(requestParts[2]);

                if (resultFirst == 0) {
                    System.out.println("Goodbye! ");
                    break;
                } else if (resultFirst == -1){
                    System.out.println("The first parameter should be a natural number or zero.");
                } else if (resultSecond == -1){
                    System.out.println("The second parameter should be a natural number.");
                } else if (!resultThird){
                    System.out.printf("The property [%s]  is wrong.\n", requestParts[2].toUpperCase());
                    System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD]");
                } else {
                    number = Long.parseLong(requestParts[0], 10);
                    count = Long.parseLong(requestParts[1], 10);
                    property = requestParts[2];
                    while (count > 0) {
                        switch (property) {
                            case ("EVEN"):
                                if (!isOddOrEven()) {
                                    showThirdScenarioResult();
                                    count--;
                                }
                                break;
                            case ("ODD"):
                                if (isOddOrEven()) {
                                    showThirdScenarioResult();
                                    count--;
                                }
                                break;
                            case ("SPY"):
                                if (isSpy()) {
                                    showThirdScenarioResult();
                                    count--;
                                }
                                break;
                            case ("GAPFUL"):
                                if (isGapful()) {
                                    showThirdScenarioResult();
                                    count--;
                                }
                                break;
                            case ("PALINDROMIC"):
                                if (isPalindromic()) {
                                    showThirdScenarioResult();
                                    count--;
                                }
                                break;
                            case ("DUCK"):
                                if (isDuck()) {
                                    showThirdScenarioResult();
                                    count--;
                                }
                                break;
                            case ("BUZZ"):
                                if (isBuzz()) {
                                    showThirdScenarioResult();
                                    count--;
                                }
                                break;
                        }
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
        System.out.println("- two natural numbers and a property to search for;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
    }

    private static void showFirstScenarioResult() {
        System.out.printf("Properties of %d\n", number);
        System.out.printf("\tbuzz:\t%b\n", isBuzz());
        System.out.printf("\tduck:\t%b\n", isDuck());
        System.out.printf("\tpalindromic:\t%b\n", isPalindromic());
        System.out.printf("\tgapful:\t%b\n", isGapful());
        System.out.printf("\tspy:\t%b\n", isSpy());
        System.out.printf("\teven:\t%b\n", !isOddOrEven());
        System.out.printf("\todd:\t%b\n", isOddOrEven());
    }

    private static void showSecondScenarioResult() {
        StringJoiner joiner = new StringJoiner(", ");
        if (isOddOrEven()) joiner.add("even");
        if (!isOddOrEven()) joiner.add("odd");
        if (isBuzz()) joiner.add("buzz");
        if (isDuck()) joiner.add("duck");
        if (isSpy()) joiner.add("spy");
        if (isPalindromic()) joiner.add("palindromic");
        if (isGapful()) joiner.add("gapful");
        String result = joiner.toString().trim();
        System.out.printf("\t%d is %s\n", number, result);
    }

    private static void showThirdScenarioResult() {
        StringJoiner joiner = new StringJoiner(", ");
        if (isOddOrEven()) joiner.add("even");
        if (!isOddOrEven()) joiner.add("odd");
        if (isBuzz()) joiner.add("buzz");
        if (isDuck()) joiner.add("duck");
        if (isSpy()) joiner.add("spy");
        if (isPalindromic()) joiner.add("palindromic");
        if (isGapful()) joiner.add("gapful");
        String result = joiner.toString().trim();

        System.out.printf("\t%d is %s\n", number, result);
    }

    private static int chooseScenario() {
        int scenario = 1;
        requestParts = scanner.nextLine().split(" ");
        if (requestParts.length == 2) {
            scenario = 2;
        } else if (requestParts.length == 3) {
            scenario = 3;
        }
        return scenario;
    }

    private static boolean isInProperty(String string) {
        return Arrays.stream(propertyArray).anyMatch(string.toUpperCase()::equals);
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

    private static boolean isSpy(){
        boolean spy = true;
        long n = number;
        long sum = 0L;
        long mul = 1L;

        if (number >= 10) {
            while(n > 0) {
                sum += n % 10;
                mul *= n % 10;
                n /= 10;
            }
            spy = sum == mul ? true : false;
        }

        return spy;
    }

}