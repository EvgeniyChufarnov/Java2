package lesson2.exceptions;

public class MyArraySizeException extends RuntimeException {
    public MyArraySizeException() {
        super("Wrong size of the input array");
    }
}
