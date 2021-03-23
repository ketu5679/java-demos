package test;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import pool.ThreadPoolTest;


public class ThreadPoolTestTest {
    @Benchmark
    @Warmup(iterations = 1,time = 3)
    @Fork(5)
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 1, time = 4)
    public void testForeach() {
        ThreadPoolTest.foreach();
    }
}
