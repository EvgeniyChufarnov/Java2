package lesson1;

public interface Obstacle {
    boolean overcome(Movable subject);
    String getName();
    int getDistance();
}
