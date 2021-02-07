package lesson3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
            1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
            Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
            Посчитать, сколько раз встречается каждое слово.
        */

        String[] words = new String[] {"Январь", "Июнь", "Ноябрь", "Сентябрь", "Февраль", "Ноябрь", "Октябрь", "Март",
                "Апрель", "Май", "Июнь", "Октябрь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Ноябрь",
                "Сентябрь", "Декабрь", "Июнь", "Июнь", "Июль"};

        HashMap<String, Integer> wordsCounter = new HashMap<>();
        Arrays.stream(words).forEach(s -> wordsCounter.merge(s, 1, Integer::sum));
        wordsCounter.forEach((k,v) -> System.out.printf("Слово %s встречается %d раз(а)%n", k, v));
        System.out.println("--------------------------------------");
        // Отдельно уникальные номера можно вывести, к примеру, так:
        // System.out.println(wordsCounter.keySet());

        /*
            2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
            В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать
            номер телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов
            (в случае однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.
         */

        PhoneBook phoneBook = new PhoneBook();
        for (int i = 0; i < 50; i++) {
            addSome(phoneBook);
        }
        phoneBook.print();
        System.out.println("--------------------------------------");

        try {
            System.out.println("Номера Петрова:" + phoneBook.get("Петров"));
        } catch (RuntimeException e) {
            System.out.println("Номера Петрова отсутствует");
        }
    }

    private static void addSome(PhoneBook phoneBook) {
        String[] surnames = new String[] {"Иванов", "Петров", "Сидоров", "Волков", "Зайцев", "Лебедев", "Воробьёв"};
        Random random = new Random();
        String randomSurname = surnames[random.nextInt(surnames.length)];
        phoneBook.add(randomSurname, "+7(" + (random.nextInt(900) + 100) + ")" +
                (random.nextInt(9000000) + 1000000));
    }
}
