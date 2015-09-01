package org.sdrc.odkaggregate.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sdrc.evm.service.AggregationService;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Aggregation extends QuartzJobBean {

	private AggregationService aggregationService;

	public void setAggregationService(AggregationService aggregationService) {
		this.aggregationService = aggregationService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			
			aggregationService.doAggregateByForm();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
