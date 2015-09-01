package org.sdrc.evm.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sdrc.evm.service.AggregationService;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AggregationJob extends QuartzJobBean {
	
	private AggregationService aggregationService;

	public void setAggregationService(AggregationService aggregationService) {
		this.aggregationService = aggregationService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
//			aggregationService.doPercentAggregate();
//			aggregationService.doRankAggregateByArea();
//			aggregationService.doRankAggregateByAreaLevel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
