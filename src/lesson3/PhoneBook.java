package lesson3;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {
    // Используем HashSet, чтобы одинаковые номера не записывались несколько раз под одним именем
    private final HashMap<String, HashSet<String>> phones;

    public PhoneBook() {
        this.phones = new HashMap<>();
    }

    public void add(String name, String phoneNumber) {
        if (!phones.containsKey(name)) {
            HashSet<String> numbers = new HashSet<>();
            numbers.add(phoneNumber);
            phones.put(name, numbers);
        } else {
            phones.get(name).add(phoneNumber);
        }
    }

    public HashSet<String> get(String name) {
        if (phones.containsKey(name)) {
            return phones.get(name);
        } else {
            throw new RuntimeException("Phone number was not found");
        }
    }

    public void print() {
        phones.forEach((k,v) -> System.out.println(k + " " + v));
    }
}
