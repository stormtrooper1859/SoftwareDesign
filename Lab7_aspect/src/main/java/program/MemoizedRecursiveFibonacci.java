package program;

import java.util.HashMap;
import java.util.Map;

public class MemoizedRecursiveFibonacci implements Fibonacci {
    private final Map<Integer, Long> memory = new HashMap<>();

    public long calculate(int x) {
        if (x == 0 || x == 1) {
            return 1;
        }
        if (memory.containsKey(x)) {
            return memory.get(x);
        }
        long result = calculate(x - 1) + calculate(x - 2);
        memory.put(x, result);
        return result;
    }
}
