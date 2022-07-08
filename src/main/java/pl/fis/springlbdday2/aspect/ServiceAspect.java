package pl.fis.springlbdday2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Around("execution(* pl.fis.springlbdday2.service..*.*(..))")
    public Object logCallingServiceMethods(ProceedingJoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        Object value = joinPoint.getArgs()[0];
        try {
            LOGGER.info("Passed arguments:");
            for(Object argument : arguments)
              LOGGER.info("Argument: " + argument);
            value = joinPoint.proceed();
            LOGGER.info("Returned value: " + value);
            return value;
        } catch(Throwable exception) {
            LOGGER.info("Error occurred while proceeding the service method.");
            return value;
        }
    }
}
