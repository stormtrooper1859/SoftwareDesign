package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ExecutionTimeAspect {

    @Around("@annotation(aspect.Profile) && execution(* *(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        PerformanceTimeLogger performanceTimeLogger = PerformanceTimeLogger.getInstance();
        boolean shouldPullData = performanceTimeLogger.isFirstAndLock();
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed(joinPoint.getArgs());

        long endTime = System.nanoTime();

        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().toString();
        performanceTimeLogger.writeRecord(methodName, endTime - startTime);

        if (shouldPullData) {
            System.out.println(performanceTimeLogger.pullLog());
        }

        return result;
    }

}
