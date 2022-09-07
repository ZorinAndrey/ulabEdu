import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * https://habr.com/ru/company/luxoft/blog/270383/ useful link
 */
public class FilterExamples {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("sasha");
        names.add("masha");
        names.add("petya");
        names.add("lesha");
        names.add("pasha");
        names.add("pasha");
        names.add("pasha");
        names.add(null);
        names.add("gosha");


//        String foundName = names.stream()
//                .filter(name -> "gosha".equalsIgnoreCase(name)) // "masha".equalsIgnoreCase(name)
//                .findFirst()
//                .orElse(null);
//        System.out.println(foundName);

        List<String> foundNames = names.stream()
                .filter(name -> Objects.nonNull(name) && name.startsWith("p") && name.endsWith("a"))
                .toList();
        System.out.println(foundNames);
//
//        List<String> noRepeated = names.stream()
//                .distinct()
//                .collect(Collectors.toList());
//        System.out.println(noRepeated);
//
        List<String> nullFiltered = names.stream()
                .filter(Objects::nonNull)
                .toList();
        System.out.println(nullFiltered);
    }
}
