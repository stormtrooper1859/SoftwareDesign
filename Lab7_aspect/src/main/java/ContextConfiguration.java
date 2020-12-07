import aspect.PerformanceAspect;
import aspect.ExecutionTreeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import program.Fibonacci;
import program.RecursiveFibonacci;

@Configuration
@EnableAspectJAutoProxy
public class ContextConfiguration {
    @Bean
    public Fibonacci fibonacci() {
        return new RecursiveFibonacci();
//        return new MemoizedRecursiveFibonacci();
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
