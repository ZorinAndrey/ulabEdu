package homework;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntPredicate;

import static java.util.stream.Collectors.*;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться
                Key:Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        removeDuplicatesSortByIdGroupByName(RAW_DATA);

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("The first pair in the array [3, 4, 2, 7] that adds up to 10 using the Stream API:");
        System.out.println();
        
        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */

        int sum = 10;
        int[] incomingArray = new int[]{3, 4, 2, 7};

        // Вариант 1. С использованием Stream API

        outputPairThatGivesTheSumUsingStreamAPI(sum, incomingArray);

        // Вариант 2. С использованием циклов

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("The first pair in the array [3, 4, 2, 7] that adds up to 10 using the loops:");
        System.out.println();

        outputPairThatGivesTheSumUsingLoops(sum, incomingArray);

        /*
        Task3
            Реализовать функцию нечеткого поиска
                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Fuzzy search function with loops:");
        System.out.println();

        fuzzySearch("car", "ca6$$#_rtwheel");
        fuzzySearch("cwhl", "cartwheel");
        fuzzySearch("cwhee", "cartwheel");
        fuzzySearch("cartwheel", "cartwheel");
        fuzzySearch("cwheeel", "cartwheel");
        fuzzySearch("lw", "cartwheel");

        System.out.println();
        System.out.println("**************************************************");
    }

    private static void removeDuplicatesSortByIdGroupByName(Person[] people) {
        Arrays.stream(people)
                .distinct()
                .sorted(Comparator.comparing(Person::getId, Integer::compareTo)) // На конечный результат не влияет, но по условиям задачи нужен
                .collect(groupingBy(Person::getName, mapping(Person::getId, counting())))
                .forEach((key, value) -> {
                    System.out.println("Key: " + key);
                    System.out.println("Value: " + value);
                });
    }

    private static void outputPairThatGivesTheSumUsingStreamAPI(int sum, int[] incomingArray) {
        int[] firstResultArray = Arrays.stream(incomingArray)
                .filter(equalityOfTheSumOfTwoArrayElements(incomingArray, sum))
                .toArray();

        System.out.println(Arrays.toString(firstResultArray));
    }

    private static IntPredicate equalityOfTheSumOfTwoArrayElements(int[] incomingArray, int sumValue) {
        return x -> {
            AtomicBoolean isTrue = new AtomicBoolean(false);

            Arrays.stream(incomingArray).forEach(y -> {
                if (x + y == sumValue) {
                    isTrue.set(true);
                }
            });

            return isTrue.get();
        };
    }

    private static void outputPairThatGivesTheSumUsingLoops(int sum, int[] incomingArray) {
        List<Integer> resultList = new ArrayList<>();

        for (int i : incomingArray) {
            for (int i1 : incomingArray) {
                if (i + i1 == sum) {
                    resultList.add(i);
                }
            }
            if (resultList.size() == 2) {
                break;
            }
        }
        System.out.println(Arrays.toString(resultList.toArray()));
    }

    private static void fuzzySearch(String firstString, String secondString) {
        int count = 0, start = 0;
        boolean result = false;

        for (int i = 0; i < firstString.length(); i++) {
            for (int j = start; j < secondString.length(); j++) {
                if (firstString.charAt(i) == secondString.charAt(j)) {
                    count++;
                    start = j + 1;
                    break;
                }
            }
        }
        if (count == firstString.length()) {
            result = true;
        }

        System.out.printf("fuzzySearchUsingLoops(%s, %s): %b", firstString, secondString, result);
        System.out.println();
    }
}
