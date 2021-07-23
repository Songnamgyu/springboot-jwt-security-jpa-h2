package com.example.api.config.aop;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
	
	//AOP의 특징
	//ㅁ Before, After Returning, After Throwing, After 어드바이스는 JoinPoint 를 매개변수로 사용하고,
    //ㅁ Around 어드바이스에서만 ProceedingJoinPoint 를 매개변수로 사용한다는 점!
	//ㅁ 이는 Around 어드바이스에서만 proceed() 메소드가 필요하기 때문이다.
	
	private static final Logger logger = LoggerFactory.getLogger(TimeTraceAOP.class);
	
	/* 메소드 종류
		 * 1. getThis() : AOP 프록시 객체를 반환한다
		 * 2. getTarget() : AOP가 적용된 대상객체를 반환한다. 프록시가 벗겨진 상태이다.
		 * 3. getArgs() : Joinpoint에 전달된 인자를 배열로 반환한다.(인자는 AOP를 사용하는 메소드의 인자를 말함)
		 * 4. getKind() : 어떤 종류의 JoinPoint인지 문자열로 반환, 보통은 메소드 호출이므로  "method-execution"
		 * 5. getSignature() : 클라이언트가 호출한 메소드의 시그니처( 리턴타입, 이름, 매개변수) 정보가 저장된 Signature 객체를 리턴
		 * 6. getName() : 클라이언트가 호출한 메소드 이름 리턴
		 * 7 .toLongString() : 클라이언트가 호출한 메소드의 리턴타입, 이름, 매개변수를 패키지 경로까지 포함해서 리턴
		 * 8. toShortString() : 클라이언트가 호출한 메소드의 시그니처를 축약한 문자열로 리턴
		 * 9. getDeclaringType() : JointPoint를 선언하고 있는 타입을 반환한다. 즉, JoinPoint가 메소드이면, 해당 메소드의 클래스를 반환한다.
		 * 10. getDeclaringTypeName() : JoinPoint를 선언하고 있는 타입의 이름을 반환한다. 즉, JoinPoint가 메소드이면, 해당 메소드의 클래스 이름을 반환한다.
	 * */

	/*
	 * 전체 플로우
	 * 
	 * 요청 메소드 실행 전에 컨트롤러와 메소드 이름, Request 값의 정보를 담은 Request 로그 출력 
	 * procced()메소드를 이용해 원래 실행해야 하는 메소드 실행 실행 후에 컨트롤러와 메소드 이름, 반환된 값의 정보를 담은 Response 로그 출력 응답
	 */	



	/*@Around : 대상객체(within으로 범위설정가능)의 메서드 실행 전 후, 시점에 메소드를 실행*/
	@Around("within(com.example.api..*Controller)") //1
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

		logger.info("-----------> RESPONSE : {}({}) = {} (동작시간 : {}ms)", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), result, endAt - startAt);

		return result;
	}

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
				params = " [입력된 params : " + paramMapToString(paramMap) + "]";
			}
		}

		return params;

	}
	
}


