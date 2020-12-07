package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ExecutionTreeAspect {

    @Around("@annotation(aspect.Profile) && execution(* *(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        CallTreeLogger callTreeLogger = CallTreeLogger.getInstance();

        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().toString();
        callTreeLogger.enterMethod(methodName, joinPoint.getArgs());

        Object result = joinPoint.proceed(joinPoint.getArgs());

        callTreeLogger.leaveMethod();

        return result;
    }

}
