package org.homenet.easimon.gasmeter.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
// @Component
public class PerformanceMeasuringAspect {

	@Pointcut("execution(public * *(..))")
	public void pubMethod() {
	}

	@Pointcut("within(org.homenet.easimon.gasmeter..*)")
	public void gasmeterBean() {
	}

	@Pointcut("@within(org.springframework.stereotype.Controller)")
	public void controller() {
	}

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void restController() {
	}

	@Pointcut("@within(org.springframework.stereotype.Repository)")
	public void repository() {
	}

	@Pointcut("gasmeterBean() && pubMethod() && (restController() || controller() || repository())")
	public void measurable() {
	}

	@Around("measurable()")
	public Object measurePerformance(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		getLogger(pjp).info("Start of Method {}", pjp.getSignature().getName());
		Object result = pjp.proceed();
		long end = System.currentTimeMillis();
		long diff = end - start;
		getLogger(pjp).info(" End of Method {}, duration {}", pjp.getSignature().getName(), diff);
		return result;
	}

	private Logger getLogger(ProceedingJoinPoint pjp) {
		return LoggerFactory.getLogger(pjp.getTarget().getClass());
	}

}
