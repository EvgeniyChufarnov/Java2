package lesson5;

import java.util.concurrent.RecursiveAction;

public class ComputeArrayTask extends RecursiveAction {
    private final int THRESHOLD = 10000;
    private float[] data;
    private int start;
    private int end;

    public ComputeArrayTask(float[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if ((end - start) < THRESHOLD) {
            for (int i = start; i < end; i++) {
                data[i] = (float)(data[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new ComputeArrayTask(data, start, middle),
                    new ComputeArrayTask(data, middle + 1, end));
        }
    }
}
