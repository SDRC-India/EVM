package org.sdrc.evm.util;

public enum TranFormStatus {
	PROCESSED("Processed"),
	UNDER_PROCESS("Under process"),
	VALIDATION_FAILED("Validation failed"),
	NOT_SUBMITED("Not submited"),
	AGGREGATED("Aggregated"),
	NOT_AGGREGATED("Not aggregated"),
	AGGREGATION_FAILED("Aggregation failed");
	
	 private String statusCode;
	 
	 private TranFormStatus(String statusCode){
		 this.statusCode=statusCode;
	 }
	 
	 public String getStatusCode(){
			return statusCode;
	 }
}
