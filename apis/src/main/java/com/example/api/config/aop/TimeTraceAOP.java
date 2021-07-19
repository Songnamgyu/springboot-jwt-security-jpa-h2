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

@Aspect
@Component
public class TimeTraceAOP {

	private static final Logger log = LoggerFactory.getLogger(TimeTraceAOP.class);


	@Around("within(com.example.api..*)") //1
	public Object logging(ProceedingJoinPoint JoinPoint) throws Throwable { //2

		//1. 메소드 호출시간 console로 찍기
		String params = getRequestParams(); // request 값 가져오기

		long startAt = System.currentTimeMillis();

		log.info("-----------> REQUEST : {}({}) = {}", JoinPoint.getSignature().getDeclaringTypeName(),
				JoinPoint.getSignature().getName(), params);

		Object result = JoinPoint.proceed(); // 4

		long endAt = System.currentTimeMillis();

		log.info("-----------> RESPONSE : {}({}) = {} ({}ms)", JoinPoint.getSignature().getDeclaringTypeName(),
				JoinPoint.getSignature().getName(), result, endAt - startAt);

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

		String params = "없음";

		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes(); // 3

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
