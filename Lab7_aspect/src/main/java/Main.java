import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import program.Fibonacci;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        Fibonacci fibonacci = applicationContext.getBean(Fibonacci.class);

        int x = 5;

        long fib = fibonacci.calculate(fibonacci, x);
        System.out.println("Fibonacci of " + x + ": " + fib);
    }
}
