package study;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.exec(args);
    }

    private void exec(String[] args) {
        /* Needs preview flag */
//        stringTemplateProcessing();
        /* Does NOT need preview flag */
        switchTest();
    }

    /* Needs preview flag */
/*
    private static void stringTemplateProcessing() {
        StringTemplate.Processor<String, RuntimeException> STR = StringTemplate::interpolate;
        String name = "Erling";
        String lastName = "Molde";
        String occupation = "Programmer";
        String result = STR."""

                My name is \
                "\{ name + ' ' + lastName }"
                and my occupation is \
                "\{ occupation.toLowerCase() }" """;
        System.out.println(result);
    }
*/

    private void switchTest() {
        var boss = new Boss("Leo", "Java 21 Inc.");
        var man = new Man("Erling", 1957, boss);
        var woman = new Woman("Shiloh", "Bergen", boss);
        var child = new Child("Marion", 2020, man, woman);
        doSwitch(List.of(man, woman, child));
    }

    private void doSwitch(List<? extends Person> list) {
        list.forEach(p -> System.out.println(stringify(p)));
    }

    private String stringify(Person p) {
        return switch (p) {
            case Boss boss -> "Boss, name: %s, works for %s".formatted(boss.name, boss.company);
            case Child(var name, var birthYear, var father, var mother) -> """
                    Child, name: %s, born: %d,
                       father: %s,
                       father's age at child's birth: %d
                       mother: %s""".formatted(name, birthYear, stringify(father), birthYear -
                    father.birthYear, stringify(mother));
            case Man man -> """
                    Man: name: %s, born: %d,
                       boss: %s""".formatted(man.name, man.birthYear, stringify(man.boss));
            case Woman woman -> """
                    Woman: name: %s, city: %s,
                       boss: %s""".formatted(woman.name, woman.city, stringify(woman.boss));
        };
    }

    sealed interface Person permits Man, Woman, Child, Boss {
    }

    record Man(String name, int birthYear, Boss boss) implements Person {}

    record Woman(String name, String city, Boss boss) implements Person {}

    record Child(String name, int birthYear, Man father, Woman mother) implements Person {}

    record Boss(String name, String company) implements Person {}
}
