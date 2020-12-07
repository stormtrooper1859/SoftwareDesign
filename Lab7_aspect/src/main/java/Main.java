import program.MemoizedRecursiveFibonacci;
import program.RecursiveFibonacci;

public class Main {

    public static void main(String[] args) {
        int x = 45;

        long recFib29 = new RecursiveFibonacci().calculate(x);
        System.out.println("Fibonacci of " + x + " (recursive): " + recFib29);
        long memFib29 = new MemoizedRecursiveFibonacci().calculate(x);
        System.out.println("Fibonacci of " + x + " (memoized): " + memFib29);
    }

}
