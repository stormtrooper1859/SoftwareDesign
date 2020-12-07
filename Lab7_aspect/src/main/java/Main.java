import program.Fibonacci;

public class Main {

    public static void main(String[] args) {
        int x = 45;

        int recFib29 = Fibonacci.recursiveFibonacci(x);
        System.out.println("Fibonacci of " + x + " (recursive): " + recFib29);
        int memFib29 = Fibonacci.memoizedRecursiveFibonacci(x);
        System.out.println("Fibonacci of " + x + " (memoized): " + memFib29);
    }

}
