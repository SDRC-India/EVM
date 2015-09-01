package org.sdrc.odkaggregate.gateway;

public class ODKConstants {
	public static final String ORIGINAL_FORM_URL = "/formXml";
	public static final String DOWNLOAD_FORM_URL = "/view/downloadSubmission";
	public static final String VIEW_PREFIX = "/submission/data";
	public static final String VIEW_SUBMISSION_LIST_PATH = "/view/submissionList";
	public static final String ENKETO_API_V1_INSTANCE_URL = "https://enketo.org/api_v1/instance";
	public static final String ENKETO_RETURN_URL = "https://enketo.org";
	public static final String USER_AGENT = "Mozilla/5.0";
	public static final String CONTENT_TYPE_APPLICATION = "application/x-www-form-urlencoded;charset=UTF-8";
	public static final String ACCEPT_LANGUAGE_EN_US = "en-US,en;q=0.5";
	
	public static final String ENKETO_ERROR_400 = "Malformed request, maybe parameters are missing";
	public static final String ENKETO_ERROR_401 = "Authentication failed, incorrect or expired API token used or none at all";
	public static final String ENKETO_ERROR_403 = "Authentication succeeded, but account is not active or quota is filled up";
	public static final String ENKETO_ERROR_404 = "Resource was not found in database";
	public static final String ENKETO_ERROR_405 = "Request not allowed. You may not have API access on your plan";
	public static final String ENKETO_ERROR_410 = "This API endpoint is deprecated in this version";
	public static final String ENKETO_ERROR_MORE = "There was an error while opening enketo webform. Please try again after some time..";
	
	
}
