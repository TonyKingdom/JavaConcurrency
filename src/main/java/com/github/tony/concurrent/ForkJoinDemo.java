package com.github.tony.concurrent;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by Tony on 2016/8/28.
 *
 * <p>
 * A simple demo demonstrates how to use ForkJoinPool to compute the sum of an given array.
 * </p>
 */
public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] array = IntStream.range(0, 101).toArray();
        Sum sum = new Sum(array);
        ForkJoinPool pool = new ForkJoinPool(4);
        ForkJoinTask<Integer> task = pool.submit(sum);
        System.out.println(task.get());
    }
}

class Sum extends RecursiveTask<Integer> {

    private final int[] array;

    Sum(int[] array) {
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if (array.length == 0)
            return 0;
        if (array.length == 1)
            return array[0];
        int mid = array.length / 2;
        int[] low = Arrays.copyOfRange(array, 0, mid);
        int[] high = Arrays.copyOfRange(array, mid, array.length);
        return new Sum(low).fork().join() + new Sum(high).fork().join();
    }
}