package program;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    private static final Map<Integer, Integer> memory = new HashMap<>();

    public static int recursiveFibonacci(int x) {
        if (x == 0 || x == 1) {
            return 1;
        }
        return recursiveFibonacci(x - 1) + recursiveFibonacci(x - 2);
    }

    public static int memoizedRecursiveFibonacci(int x) {
        if (x == 0 || x == 1) {
            return 1;
        }
        if (memory.containsKey(x)) {
            return memory.get(x);
        }
        int result = memoizedRecursiveFibonacci(x - 1) + memoizedRecursiveFibonacci(x - 2);
        memory.put(x, result);
        return result;
    }
}
