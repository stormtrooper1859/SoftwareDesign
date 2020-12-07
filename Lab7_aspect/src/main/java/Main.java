import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import program.Fibonacci;
import program2.Inside;
import program2.Outside;

public class Main {

    public void program1() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        Fibonacci fibonacci = applicationContext.getBean(Fibonacci.class);

        int x = 5;

        long fib = fibonacci.calculate(fibonacci, x);
        System.out.println("Fibonacci of " + x + ": " + fib);
    }

    public void program2() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        Outside outside = applicationContext.getBean(Outside.class);
        Inside inside = applicationContext.getBean(Inside.class);

        outside.doSomething(outside, inside);
    }

    public static void main(String[] args) {
        new Main().program1();
//        new Main().program2();
    }
}
