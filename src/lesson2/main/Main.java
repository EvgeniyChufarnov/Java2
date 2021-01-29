package lesson2.main;

import lesson2.exceptions.MyArrayDataException;
import lesson2.exceptions.MyArraySizeException;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        /*
        1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4,
        при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
        2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
        Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
        должно быть брошено исключение MyArrayDataException – с детализацией, в какой именно ячейке лежат неверные данные.
        3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и
        MyArrayDataException и вывести результат расчета.
         */

        String[][] array = new String[4][4];
        Random random = new Random();
        int limit = 1000;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = Integer.toString(random.nextInt(limit));
            }
        }

        String[][] wrongSizeArray = new String[4][4];
        wrongSizeArray[2] = new String[5];

        try {
            System.out.println(sumOf4x4Array(array));
            System.out.println(sumOf4x4Array(wrongSizeArray));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }

        array[3][1] = "s";

        try {
            System.out.println(sumOf4x4Array(array));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public static int sumOf4x4Array(String[][] array) {
        final int SIZE = 4;

        if (array.length != SIZE)
            throw new MyArraySizeException();

        for (int i = 0; i < array.length; i++) {
            if (array[i].length != SIZE)
                throw new MyArraySizeException();
        }

        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(array[i][j], i, j);
                }
            }
        }

        return sum;
    }
}
