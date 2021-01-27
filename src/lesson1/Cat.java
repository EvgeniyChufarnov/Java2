package lesson1;

public class Cat implements Movable {
    private String name;
    private int runningLimit;
    private int jumpingLimit;

    public Cat(String name, int runningLimit, int swimmingLimit) {
        this.name = name;
        this.runningLimit = runningLimit;
        this.jumpingLimit = swimmingLimit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean run(int distance) {
        return runningLimit >= distance;
    }

    @Override
    public boolean jump(int distance) {
        return jumpingLimit >= distance;
    }

    @Override
    public String getInfo() {
        return String.format("The cat %s can run %dm and swim %dm.", name, runningLimit, jumpingLimit);
    }
}
