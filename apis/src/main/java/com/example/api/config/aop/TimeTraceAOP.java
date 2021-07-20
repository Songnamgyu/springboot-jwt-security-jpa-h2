package com.example.api.config.aop;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Joiner;

//@Aspect : AOP임을 알려주는 annotation
@Aspect
@Component
public class TimeTraceAOP {

	/*
	 * 전체 플로우
	 * 
	 * 요청 메소드 실행 전에 컨트롤러와 메소드 이름, Request 값의 정보를 담은 Request 로그 출력 
	 * procced()메소드를 이용해 원래 실행해야 하는 메소드 실행 실행 후에 컨트롤러와 메소드 이름, 반환된 값의 정보를 담은 Response 로그 출력 응답
	 */	

	private static final Logger logger = LoggerFactory.getLogger(TimeTraceAOP.class);





	/*@Around : 대상객체(within으로 범위설정가능)의 메서드 실행 전 후, 시점에 메소드를 실행*/
	@Around("within(com.example.api..*)") //1
	/*ProceedingJoinPoint : 호출되는 객체에 대한 정보, 실행될 메소드에 대한 정보가 존재*/
	public Object logging(ProceedingJoinPoint pjp ) throws Throwable { //2

		//1. 메소드 호출시간 console로 찍기
		String params = getRequestParams(); // request 값 가져오기

		long startAt = System.currentTimeMillis();

		logger.info("-----------> REQUEST : {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), params);


		/*proceed() : 메소드를 이용해 원래 메소드를 실행시켜 결과를 가지고온다*/
		Object result = pjp.proceed(); // 4

		long endAt = System.currentTimeMillis();

		logger.info("-----------> RESPONSE : {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), result, endAt - startAt);

		return result;
	}

	/*===============================================================================================================*/	

	// 2. parameter console창에 확인 하는거 설정시작
	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet().stream()
				.map(entry -> String.format("%s -> (%s)",
						entry.getKey(), Joiner.on(",").join(entry.getValue())))
				.collect(Collectors.joining(", "));
	}

	// Get request values 
	private String getRequestParams() {

		/*parameter가 없는경우 알려주기 위한 설정*/
		String params = "없음(no param)";

		/* getRequestAttributes : 호출된 값의 Request 값을 얻을 때 사용, 없으면 null 반환, currentRequestAttributes와 비슷하지만 currentRequestAttributes는 값이 없으면 예외 발생*/
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes(); // 3 

		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest(); 

			Map<String, String[]> paramMap = request.getParameterMap();
			if (!paramMap.isEmpty()) {
				params = " [" + paramMapToString(paramMap) + "]";
			}
		}

		return params;

	}
}





//	//1. 메소드 호출시간 console로 찍기
//	String params = getRequestParams(); // request 값 가져오기
//
//	// 2. parameter console창에 확인 하는거 설정시작
//	public String paramMapToString(Map<String, String[]> paramMap) {
//		return paramMap.entrySet().stream()
//				.map(entry -> String.format("%s -> (%s)",
//						entry.getKey(), Joiner.on(",").join(entry.getValue())))
//				.collect(Collectors.joining(", "));
//	}
//
//		//	 Get request values 
//		private String getRequestParams() {
//
//			/*parameter가 없는경우 알려주기 위한 설정*/
//			String params = "없음(no param)";
//
//			/* getRequestAttributes : 호출된 값의 Request 값을 얻을 때 사용, 없으면 null 반환, currentRequestAttributes와 비슷하지만 currentRequestAttributes는 값이 없으면 예외 발생*/
//			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes(); // 3 
//
//			if (requestAttributes != null) {
//				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//						.getRequestAttributes()).getRequest(); 
//
//				Map<String, String[]> paramMap = request.getParameterMap();
//				if (!paramMap.isEmpty()) {
//					params = " [" + paramMapToString(paramMap) + "]";
//				}
//			}
//
//			return params;
//		}
//
//		// 공통으로 사용될 포인트컷을 지정합니다. 
//		// com.example.api 패키지 안의 Controller 로 끝나는 클래스의 모든 메소드에 적용됩니다. 
//		@Pointcut("execution(* com.example.api..*Controller.*(..))") 
//		public void commonPointcut() { } 
//
//		// Before Advice 입니다. 위에서 정의한 공통 포인터 컷을 사용합니다.
//		@Before("commonPointcut()") 
//		public void beforeMethod(JoinPoint jp) throws Exception { 
//
//			logger.info("beforeMethod() called.....");
//			// 호출될 메소드의 인자들으 얻을 수 있습니다.
//			Object arg[] = jp.getArgs(); 
//			// 인자의 갯수 출력
//			logger.info("args length : " + arg.length); 
//			// 첫 번재 인자의 클래스 명 출력
//			logger.info("arg0 name : " + arg[0].getClass().getName()); 
//
//			//sad
//			logger.info(params);
//			// 호출될 메소드 명 출력
//			logger.info(jp.getSignature().getName()); 
//		} 
//
//
//		// After Advice 입니다. 
//		@After("commonPointcut()") public void afterMethod(JoinPoint jp) throws Exception {
//			logger.info("afterMethod() called....."); 
//		} 
//
//
//		// After Returning Advice 입니다. 
//		// 이 어드바이스는 반환값을 받을 수 있습니다.
//		@AfterReturning(pointcut="commonPointcut()", returning="returnString") 
//		public void afterReturningMethod(JoinPoint jp, String returnString) throws Exception { 
//			logger.info("afterReturningMethod() called....."); 
//			// 호출된 메소드 반환값 출력
//			logger.info("afterReturningMethod() returnString : " + returnString);
//		}
//		// Around Advice 입니다. // 포인트컷을 직접 지정했습니다. 
//
//		@Around("execution(* com.tistory.pentode.*Controller.*(..))")
//		public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
//			logger.info("aroundMethod() before called....."); Object result = pjp.proceed(); 
//			logger.info("aroundMethod() after called....."); return result; 
//		} 
//
//		// 예외가 발생했을때 Advice 입니다. 
//		@AfterThrowing(pointcut="commonPointcut()", throwing="exception") 
//		public void afterThrowingMethod(JoinPoint jp, Exception exception) throws Exception { 
//			logger.info("afterThrowingMethod() called.....");
//			// 발생한 예외의 메세지를 출력합니다. 
//			logger.info(exception.getMessage()); }
//	}

