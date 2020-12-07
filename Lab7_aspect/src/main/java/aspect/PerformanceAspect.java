package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class PerformanceAspect {

    @Around("@annotation(aspect.PerformanceProfile) && execution(* *(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        PerformanceLogger performanceLogger = PerformanceLogger.getInstance();
        boolean shouldPullData = performanceLogger.isFirstAndLock();
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed(joinPoint.getArgs());

        long endTime = System.nanoTime();

        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().toString();
        performanceLogger.writeRecord(methodName, endTime - startTime);

        if (shouldPullData) {
            System.out.println(performanceLogger.pullLog());
        }

        return result;
    }

}
