package com.example.demo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class AopTest implements CoreConcern {

	private static final Logger logger = LoggerFactory.getLogger(AopTest.class);

	@Override
	public int coreLogic(int totalCount) {
		logger.info("비즈니스 로직 실행");

		int count = 0;
		for(int i = 0; i < totalCount; i ++) {
			count++;
		}
		return count;
	}


	@Ignore
	@DisplayName("AOP 테스트")
	void aopTest() {
		//부가적인 로직
		StopWatch sw = new StopWatch();
		sw.start();

		//비즈니스 로직
		AopTest at = new AopTest();
		int result = at.coreLogic(100);

		//부가적인로직
		sw.stop();
		logger.info("소요시간 : " + sw.getTotalTimeSeconds());
	}

	@Test
	@DisplayName("AOP 테스트 2")
	public void aopTest2( ) {
		CoreConcern coreConcern = new AopTest();
		CoreConcern proxyCoreConcern = (CoreConcern) Proxy.newProxyInstance(AopTest.class.getClassLoader(), new Class[] {CoreConcern.class}, /*앞뒤 부가로직 구현*/new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// 부가적이 로직
				StopWatch sw = new StopWatch();
				sw.start();
				/////////////

				int result = (int) method.invoke(coreConcern, args);

				//부가적인 로직
				sw.stop();
				logger.info("소요시간 : " + sw.getTotalTimeSeconds());

				return result;
			}
		}
				);
		
		//실행
		// - 비즈니스 로직을 실행하면, 프록시 패턴에 의해 앞뒤로 부가기능이 실행된다. 
		// - 부가기능을 제거하거나 내용을 변경하면서 일괄적으로 적용된다
		proxyCoreConcern.coreLogic(1000); 
		proxyCoreConcern.coreLogic(2000); 
		proxyCoreConcern.coreLogic(3000); 
	}

}
