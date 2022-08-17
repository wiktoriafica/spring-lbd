package pl.fis.springlbdday2.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

@Aspect
@Component
@Slf4j
public class ServiceAspect {

    @Around("execution(* pl.fis.springlbdday2.service..*.*(..))")
    public Object logCallingServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        Object value;
        try {
            log.info("Passed arguments:");
            for(Object argument : arguments)
                log.info("Argument: " + argument);
            value = joinPoint.proceed();
            log.info("Returned value: " + value);
            return value;
        } catch(Throwable exception) {
            log.info("Error occurred while proceeding the service method.");
            Constructor<? extends Throwable> constructor = exception.getClass().getConstructor(String.class);
            throw constructor.newInstance(exception.getMessage());
        }
    }
}
