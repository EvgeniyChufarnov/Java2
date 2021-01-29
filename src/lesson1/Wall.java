package lesson1;

public class Wall implements Obstacle {
    private final String NAME = "wall";
    private int distance;

    public Wall(int distance) {
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
        return subject.jump(distance);
    }
}
