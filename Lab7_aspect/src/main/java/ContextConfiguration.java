import aspect.ExecutionTreeAspect;
import aspect.PerformanceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import program.Fibonacci;
import program.RecursiveFibonacci;
import program2.Inside;
import program2.Outside;

@Configuration
@EnableAspectJAutoProxy
public class ContextConfiguration {
    @Bean
    public Fibonacci fibonacci() {
        return new RecursiveFibonacci();
//        return new MemoizedRecursiveFibonacci();
    }

    @Bean
    public Inside inside() {
        return new Inside();
    }

    @Bean
    public Outside outside() {
        return new Outside();
    }

    @Bean
    public PerformanceAspect aspectTime() {
        return new PerformanceAspect();
    }

    @Bean
    public ExecutionTreeAspect aspectTree() {
        return new ExecutionTreeAspect();
    }
}
