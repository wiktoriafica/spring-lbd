package pl.fis.springlbdday2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ServiceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Around("execution(* pl.fis.springlbdday2.service..*.*(..))")
    public void logCallingServiceMethods(ProceedingJoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        try {
            LOGGER.info("Passed arguments:");
            for(Object argument : arguments)
              LOGGER.info("Argument: " + argument);
            Object returnedValue = joinPoint.proceed();
            if(returnedValue != null)
                LOGGER.info("Returned value: " + returnedValue);
            else
                LOGGER.info("Method does not return any value");
        } catch(Throwable exception) {
            LOGGER.info("Error occurred while proceeding the service method.");
        }
    }
}
