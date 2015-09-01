package org.sdrc.odkaggregate.job;

import org.sdrc.evm.service.AggregationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring/application-config.xml",
						"spring/mvc-core-config.xml", "spring/tools-config.xml" });

		AggregationService aggregationService = (AggregationService) context
				.getBean("aggregationService");
		long startTime = System.currentTimeMillis();
		aggregationService.doAggregateByForm();
//		aggregationService.insertAggregationDetails();
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
	}

}
