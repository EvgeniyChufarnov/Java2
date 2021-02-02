package lesson2.exceptions;

public class MyArrayDataException extends RuntimeException {
    public MyArrayDataException(String value, int i, int j) {
        super(String.format("Value \"%s\" in [%d, %d] is not integer", value, i, j));
    }
}
