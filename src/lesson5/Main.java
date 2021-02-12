package lesson5;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int SIZE = 10_000_000;
        float[] array = new float[SIZE];
        float[] array1 = new float[SIZE];
        float[] array2 = new float[SIZE];;
        Arrays.fill(array, 1f);
        Arrays.fill(array1, 1f);
        Arrays.fill(array2, 1f);

        long beginningTime = System.currentTimeMillis();
        computeArray(array);
        System.out.println("Compute Array time is " + (System.currentTimeMillis() - beginningTime));

        beginningTime = System.currentTimeMillis();
        computeArrayWith2Threads(array1);
        System.out.println("Compute with 2 threads time is " + (System.currentTimeMillis() - beginningTime));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ComputeArrayTask task = new ComputeArrayTask(array2, 0, array2.length - 1);

        beginningTime = System.currentTimeMillis();
        forkJoinPool.invoke(task);
        System.out.println("Fork Join time time is " + (System.currentTimeMillis() - beginningTime));
    }

    public static void computeArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void computeArrayWith2Threads(float[] arr) {
        if (arr.length < 2) {
            computeArray(arr);
            return;
        }

        int start = 0;
        int end = arr.length - 1;
        int middle = (start + end) / 2;

        float[] leftPart = new float[middle + 1];
        float[] rightPart = new float[end - middle ];

        System.arraycopy(arr, 0, leftPart, 0, leftPart.length);
        System.arraycopy(arr, middle + 1, rightPart, 0, rightPart.length);

        Thread t1 = new Thread(() -> computeArray(leftPart));
        Thread t2 = new Thread(() -> computeArray(rightPart));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(leftPart, 0, arr, 0, leftPart.length);
        System.arraycopy(rightPart, 0, arr, middle + 1, rightPart.length);
    }
}
