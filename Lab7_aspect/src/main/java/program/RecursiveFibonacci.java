package program;

import aspect.ExecutionTreeProfile;
import aspect.PerformanceProfile;

public class RecursiveFibonacci implements Fibonacci {

    @PerformanceProfile
    @ExecutionTreeProfile
    public long calculate(Fibonacci it, int x) {
        if (x == 0) {
            return 0;
        }
        if (x == 1) {
            return 1;
        }
        return it.calculate(it, x - 1) + it.calculate(it, x - 2);
    }
}
