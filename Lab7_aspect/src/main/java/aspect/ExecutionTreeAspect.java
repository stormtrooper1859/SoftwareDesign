package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ExecutionTreeAspect {

    @Around("@annotation(aspect.ExecutionTreeProfile) && execution(* *(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        ExecutionTreeLogger executionTreeLogger = ExecutionTreeLogger.getInstance();

        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().toString();
        executionTreeLogger.enterMethod(methodName, joinPoint.getArgs());

        Object result = joinPoint.proceed(joinPoint.getArgs());

        executionTreeLogger.leaveMethod();

        return result;
    }

}
