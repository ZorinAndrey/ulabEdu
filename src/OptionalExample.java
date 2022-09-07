import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("name1");
        names.add("name2");
        names.add("name3");

        String name = Optional.of(names)
                .orElseThrow(NullPointerException::new)
                .stream()
                .findFirst()
                .orElse(null);
        System.out.println(name);


        List<String> namesNulls = null;

        String namesNull = Optional.ofNullable(namesNulls)
                .orElseThrow(NullPointerException::new)
                .stream()
                .findFirst()
                .orElse(null);
        System.out.println(namesNull);
    }
}
