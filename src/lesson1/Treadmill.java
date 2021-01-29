package lesson1;

public class Treadmill implements Obstacle {
    private final String NAME = "treadmill";
    private int distance;

    public Treadmill(int distance) {
        this.distance = distance;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean overcome(Movable subject) {
        return subject.run(distance);
    }
}
