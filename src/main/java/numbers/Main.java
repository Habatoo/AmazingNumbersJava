package numbers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringJoiner;

import static java.util.Arrays.binarySearch;

public final class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showIntro();
        run();
    }

    private static void run() {
        while (true) {
            String[] request = readRequest();

            if ("0".equals(request[0])) {
                System.out.println("Goodbye!");
                break;
            }
            if (notNatural(request[0])) {
                System.out.println("The first parameter should be a natural number or zero.");
                continue;
            }

            NaturalNumber naturalNumber = new NaturalNumber(request[0]);

            if (request.length == 1) {
                naturalNumber.printCard();
                continue;
            }
            if (notNatural(request[1])) {
                System.out.println("The second parameter should be a natural number.");
                continue;
            }
            int count = Integer.parseInt(request[1]);
            String[] query = request.length == 3 ? request[2].split(" ") : new String[0];
            String wrong = NaturalNumber.getWrongProperties(query);
            if (wrong.length() > 0) {
                System.out.printf(wrong.contains(", ") ?
                        "The properties %s are wrong" : "The property %s is wrong", wrong);
                System.out.println("Available properties: ");
                System.out.println(Arrays.toString(NaturalNumber.PROPERTIES));
                continue;
            }

            String mutuallyExclusivePairs = NaturalNumber.getMutuallyExclusive(query);

            if (mutuallyExclusivePairs.length() > 0) {
                System.out.print("The request contains mutually exclusive properties: ");
                System.out.println(mutuallyExclusivePairs);
                System.out.println("There are no numbers with these properties.");
                continue;
            }

            while (count > 0) {
                if (naturalNumber.hasProperties(query)) {
                    naturalNumber.printLine();
                    count--;
                }
                naturalNumber.increment();
            }
        }
    }

    private static String[] readRequest() {
        System.out.println("Enter a request:");
        return scanner.nextLine().toLowerCase().split(" ", 3);
    }

    private static void showIntro() {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be processed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
    }

    static boolean notNatural(String string) {
        boolean notNatural = false;
        try {
            Long number = Long.parseLong(string, 10);
            if (number < 0) {
                notNatural = true;
            }
        } catch (Exception e) {
            notNatural = true;
        }
        return notNatural;
    }

}

class NaturalNumber {
    static final String[] PROPERTIES = new String[]{
            "even",
            "odd",
            "buzz",
            "duck",
            "palindromic",
            "gapful",
            "spy",
            "square",
            "sunny",
            "jumping",
            "happy",
            "sad"
    };

    private static final String[][] EXCLUSIVE = new String[][]{
            {"even", "odd"}, {"spy", "duck"}, {"sunny", "square"},
            {"happy", "sad"}, {"-even", "-odd"}, {"-happy", "-sad"}
    };

    public static final String[][] MUTUALLY_EXCLUSIVE = new String[EXCLUSIVE.length + PROPERTIES.length][];

    static {
        Arrays.sort(PROPERTIES);
        int index = 0;
        for (String[] pair : EXCLUSIVE) {
            MUTUALLY_EXCLUSIVE[index++] = pair;
        }
        for (String property : PROPERTIES) {
            MUTUALLY_EXCLUSIVE[index++] = new String[]{property, "-" + property};
        }
    }

    private String digits;
    private long number;

    NaturalNumber(String value) {
        digits = value;
        number = Long.parseLong(value);
    }

    NaturalNumber(long value) {
        digits = String.valueOf(value);
        number = value;
    }

    static boolean isWrong(String property) {
        return binarySearch(PROPERTIES, property) < 0;
    }

    static String getWrongProperties(String[] query) {
        StringJoiner wrong = new StringJoiner(", ");
        for (String property : query) {
            String name = property.charAt(0) == '-' ? property.substring(1) : property;
            if (NaturalNumber.isWrong(name)) {
                wrong.add(property);
            }
        }
        return wrong.toString();
    }

    static String getMutuallyExclusive(String[] query) {
        Arrays.sort(query);
        StringJoiner me = new StringJoiner(", ");
        for (String[] pair : MUTUALLY_EXCLUSIVE) {
            boolean containsPair = binarySearch(query, pair[0]) >= 0 && binarySearch(query, pair[1]) >= 0;
            if (containsPair) {
                me.add(pair[0] + " and " + pair[1]);
            }
        }
        return me.toString();
    }

    void printCard() {
        System.out.printf("Properties of %,d%n", number);
        for (String property : PROPERTIES) {
            boolean hasProperty = test(property);
            System.out.printf("%12s: %s%n", property, hasProperty);
        }
    }

    void printLine() {
        StringJoiner properties = new StringJoiner(", ");
        for (String property : PROPERTIES) {
            if (test(property)) {
                properties.add(property);
            }
        }
        System.out.printf("%,12d is %s%n", number, properties);
    }

    private boolean test(String property) {
        switch (property) {
            case "even":
                return number % 2 == 0;
            case "odd":
                return number % 2 != 0;
            case "buzz":
                return number % 7 == 0 || number % 10 == 7;
            case "duck":
                return digits.indexOf('0') != -1;
            case "palindromic":
                return new StringBuilder(digits).reverse().toString().equals(digits);
            case "gapful":
                long divider = (digits.charAt(0) - '0') * 10 + number % 10;
                return number >= 100 && number % divider == 0;
            case "spy":
                return isSpy();
            case "square":
                return (long) Math.pow((long) Math.sqrt(number), 2) == number;
            case "sunny":
                return new NaturalNumber(number + 1).test("square");
            case "jumping":
                return isJumping();
            case "happy":
                return isHappy();
            case "sad":
                return !isHappy();
        }
        return false;
    }

    void increment() {
        number++;
        digits = String.valueOf(number);
    }

    boolean hasProperties(String[] query) {
        for (String property : query) {
            boolean isNegative = property.charAt(0) == '-';
            if (isNegative ? test(property.substring(1)) : !test(property)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSpy() {
        long sum = 0L;
        long mul = 1L;
        for (long i = number; i > 0; i /= 10) {
            mul *= i % 10;
            sum += i % 10;
        }
        return mul == sum;
    }

    private boolean isJumping() {
        boolean isJumping = true;
        for (long numPrev = number % 10, next = number / 10; next > 0; next /= 10) {
            long numNext = next % 10;
            if (Math.abs(numPrev - numNext) != 1) {
                isJumping = false;
                break;
            }
            numPrev = numNext;
        }
        return isJumping;
    }

    private boolean isHappy() {
        boolean isHappy = false;
        HashSet sequence = new HashSet<Long>();
        for (long i = number; !sequence.contains(i); i = happyNext(i)) {
            if (i == 1) {
                isHappy = true;
            }
            sequence.add(i);
        }
        return isHappy;
    }

    private long happyNext(long number) {
        long result = 0;
        for (long i = number; i > 0; i /= 10) {
            int digit = (int) (i % 10);
            result += digit * digit;
        }
        return result;
    }

}