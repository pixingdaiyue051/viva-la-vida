package org.component.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import org.util.ToolsUtil;

@Component
@Aspect
public class BaseInfoAspect {
	private static final String CREATETIME = "createTime";// 创建时间
	private static final String MODIFYTIME = "modifyTime";// 修改时间
	private static final String CREATEBY = "createById";// 创建人id
	private static final String MODIFYBY = "modifyById";// 修改人id

	@Pointcut("execution(* com.teuqueno.service.**.*.insert*(..))")
	public void insertAspect() {
	}

	@Before("insertAspect()")
	public void beforeInsert(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (null != args && args.length > 0) {
			Object argument = args[0];
			BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
			if (beanWrapper.isWritableProperty(CREATETIME)) {
				beanWrapper.setPropertyValue(CREATETIME, new Date());
			}
			if (beanWrapper.isWritableProperty(CREATEBY)) {
				beanWrapper.setPropertyValue(CREATEBY, ToolsUtil.getCurrentUser().getId());
			}
			if (beanWrapper.isWritableProperty(MODIFYTIME)) {
				beanWrapper.setPropertyValue(MODIFYTIME, new Date());
			}
			if (beanWrapper.isWritableProperty(MODIFYBY)) {
				beanWrapper.setPropertyValue(MODIFYBY, ToolsUtil.getCurrentUser().getId());
			}
		}
	}

	@Pointcut("execution(* com.teuqueno.service.**.*.update*(..))")
	public void updateAspect() {
	}

	@Before("updateAspect()")
	public void beforeUpdate(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (null != args && args.length > 0) {
			Object argument = args[0];
			BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
			if (beanWrapper.isWritableProperty(MODIFYTIME)) {
				beanWrapper.setPropertyValue(MODIFYTIME, new Date());
			}
			if (beanWrapper.isWritableProperty(MODIFYBY)) {
				beanWrapper.setPropertyValue(MODIFYBY, ToolsUtil.getCurrentUser().getId());
			}
		}
	}

}