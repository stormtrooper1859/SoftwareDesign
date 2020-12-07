package program;

import aspect.ExecutionTreeProfile;
import aspect.PerformanceProfile;

import java.util.HashMap;
import java.util.Map;

public class MemoizedRecursiveFibonacci implements Fibonacci {
    private final Map<Integer, Long> memory = new HashMap<>();

    @PerformanceProfile
    @ExecutionTreeProfile
    public long calculate(Fibonacci it, int x) {
        if (x == 0) {
            return 0;
        }
        if (x == 1) {
            return 1;
        }
        if (memory.containsKey(x)) {
            return memory.get(x);
        }
        long result = it.calculate(it, x - 1) + it.calculate(it, x - 2);
        memory.put(x, result);
        return result;
    }
}
