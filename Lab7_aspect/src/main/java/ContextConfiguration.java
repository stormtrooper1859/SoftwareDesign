import aspect.ExecutionTimeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import program.Fibonacci;
import program.MemoizedRecursiveFibonacci;
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
    public ExecutionTimeAspect aspect() {
        return new ExecutionTimeAspect();
    }
}
