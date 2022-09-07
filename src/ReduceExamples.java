import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;

public class ReduceExamples {
    public static void main(String[] args) {

        Predicate<String> p = (g) -> {
            return Objects.nonNull(g) && g.length() > 5 && g.length() < 100;
        };

        Consumer<String> consumer = (s) -> {
            System.out.println(s);
        };

        Supplier<String> supplier = () -> {
            return "ssss";
        };

        Function<String, String> func = (s1) -> {
            return s1 + " functioned";
        };

        BiFunction<String, String, String> func2 = (s1, s2) -> {
            return s1.length() + " functioned: " + s2;
        };

        List<String> names = List.of("Victor", "Pavel", "Mariya", "Nadejda");

        List<String> modified = names.stream()
                .map(s -> func2.apply(s, "lol "))
                .peek(s -> System.out.println(s))
                .toList();
        System.out.println(modified);

//        String longest = names.stream()
//                .max(Comparator.comparingInt(String::length))
//                .orElse(null);
//        System.out.println(longest);

//        String longestReduce = names.stream()
//                .reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2)
//                .orElse(null);
//        System.out.println(longestReduce);
//
        List<Integer> ages = Arrays.asList(25, 30, 45, 28, 32);
        int computedAges = ages.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println(computedAges);
    }
}
