package program;

import java.util.HashMap;
import java.util.Map;

public class RecursiveFibonacci implements Fibonacci {
    public long calculate(int x) {
        if (x == 0 || x == 1) {
            return 1;
        }
        return calculate(x - 1) + calculate(x - 2);
    }
}
