package org.component.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeLineAspect {

	private long beginSecond;

	private long endSecond;

	@Pointcut("execution(* com.teuqueno.controller.**.*.*(..))")
	public void controllerAspect() {
	}

	@Before("controllerAspect()")
	public void beforeControl(JoinPoint joinPoint) {
		setBeginSecond(System.currentTimeMillis());
	}

	@After("controllerAspect()")
	public void afterControl(JoinPoint joinPoint) {
		setEndSecond(System.currentTimeMillis());
		Signature s = joinPoint.getSignature();
		String msg = s.getDeclaringType().getSimpleName() + "类的" + s.getName() + "方法执行时间是" + (endSecond - beginSecond)
				+ "ms";
		System.out.println(msg);
	}

	public void setBeginSecond(long beginSecond) {
		this.beginSecond = beginSecond;
	}

	public void setEndSecond(long endSecond) {
		this.endSecond = endSecond;
	}
}