package org.sdrc.evm.util;

public class Constants {
      public static final  String USER_PRINCIPAL="UserPrincipal"; 
      public static final  String REFERER ="referer"; 
      public static final  String REDIRECT ="redirect:";
      public static final  String ATTRIBUTE_EMAIL ="email";
      public static final  String BAD_CREDENTIALS_LOGIN="badCredentials.login";
      public static final  String ACCESS_DENIED="accessDenied";
      
      //Notification Constants
      public static final  String SMTP_HOST_KEY ="smtp.host.key";
      public static final  String SOCKETFACTORY_PORT_KEY ="socketFactory.port.key";
      public static final  String SOCKETFACTORY_CLASS_KEY ="socketFactory.class.key";
      public static final  String SMTP_AUTH_KEY ="smtp.auth.key";
      public static final  String SMTP_PORT_KEY ="smtp.port.key";
      
      public static final  String SMTP_HOST ="smtp.host";
      public static final  String SOCKETFACTORY_PORT ="socketFactory.port";
      public static final  String SOCKETFACTORY_CLASS ="socketFactory.class";
      public static final  String SMTP_AUTH ="smtp.auth";
      public static final  String SMTP_PORT ="smtp.port";
      public static final  String AUTHENTICATION_USERID ="authentication.userid";
      public static final  String AUTHENTICATION_PASSWORD ="authentication.password";
      public static final  String MESSAGE_SETFORM ="message.setFrom";
      
      //BRCC file upload notification
      public static final  String BRCC_FILEUPLOAD_NOTIFICATION_SUBJECT = "brcc.fileupload.notification.subject";
      public static final  String BRCC_FILEUPLOAD_NOTIFICATION_MSG = "brcc.fileupload.notification.msg";
      public static final  String BRCC_FILEUPLOAD_NOTIFICATION_MSG1 = "brcc.fileupload.notification.msg1";
      public static final  String BRCC_FILEUPLOAD_NOTIFICATION_MSG2 = "brcc.fileupload.notification.msg2";
      
      //PTC On clicking notify button notification
      public static final  String PTC_NOTIFYBUTTON_NOTIFICATION_SUBJECT = "ptc.notifybutton.notification.subject";
      public static final  String PTC_NOTIFYBUTTON_NOTIFICATION_MSG = "ptc.notifybutton.notification.msg";
      
      //DPC On clicking notify button notification
      public static final  String DPC_NOTIFYBUTTON_NOTIFICATION_SUBJECT = "dpc.notifybutton.notification.subject";
      public static final  String DPC_NOTIFYBUTTON_NOTIFICATION_MSG = "dpc.notifybutton.notification.msg";
	  public static final String ERROR_LIST = "errorList";
	
	  //PTC On clicking Attach button Role Change
	  public static final String PTC_ATTACH_USER_SUBJECT = "ptc.attach.user.subject";
	  public static final String PTC_ATTACH_USER_MSG = "ptc.attach.user.msg";
	  
	  public static final String PTC_ATTACH_USER_MSG_FROM_ROLE  = "ptc.attach.user.msg.from.role"; 
	  public static final String PTC_ATTACH_USER_MSG_FROM_AREA  = "ptc.attach.user.msg.from.area";
	  public static final String PTC_ATTACH_USER_MSG_TO_ROLE  = "ptc.attach.user.msg.to.role"; 
      public static final String PTC_ATTACH_USER_MSG_TO_AREA  = "ptc.attach.user.msg.to.area";
	  
      //PTC On clicking Detach button Role Change
      public static final String PTC_DETACH_USER_SUBJECT = "ptc.detach.user.subject";
	  public static final String PTC_DETACH_USER_MSG = "ptc.detach.user.msg.from.msg";
	  public static final String PTC_DETACH_USER_MSG1 = "ptc.detach.user.msg.from.msg1";
	  public static final String PTC_DETACH_USER_MSG2 = "ptc.detach.user.msg.from.msg2";
	  public static final String ESAMIKSHA_ADMIN_SIGNATURE = "esamiksha.admin.sinature";
	  
	  //Regarding WORKSPACE constants
	  //file validation categories
	  public static final String VALIDATE_AREACODE = "validate.areacode";
	  public static final String VALIDATE_FORMCODE = "validate.formcode";
	  public static final String VALIDATE_DATE = "validate.date";
	  
	  //file validation error message
	  public static final String FILE_UNSELECTED = "file.unselected";
	  public static final String FILE_WRONGCODE = "file.worngcode";
	  public static final String FILE_WRONGMONTH = "file.wrongmonth";
	  public static final String FILE_WRONGYEAR = "file.worngyear";
	  public static final String FILE_WRONGFILE = "file.wrongfile";
	  public static final String FILE_INVALIDFILE = "file.invalidfile";
	  public static final String FILE_WRONGAREA = "file.worngarea";
	  public static final String FILE_NOT_FOUND = "file.notfound";
	  
	  //form type
	  public static final String FORMTYPE_BRCC = "formtype.brcc";
	  public static final String FORMTYPE_DPC = "formtype.dpc";
	  
	  //File cell location
	  public static final String FILE_FORMTYPE_CELL ="file.formtype.cell";
	  public static final String FILE_MONTH_CELL = "file.month.cell";
	  public static final String FILE_YEAR_CELL = "file.year.cell";
	  public static final String FILE_BLOCK_CELL = "file.block.cell";
	  public static final String FILE_DISTRICT_CELL = "file.district.cell";
	  public static final String FILE_CLUSTERS_NUMBER_CELL = "file.clusters.number.cell";
	  
	  public static final String FILE_FEATURES_COLUMN_CELL = "file.features.column.cell";
	  public static final String FILE_HEADING_CELL = "file.heading.cell";
	  public static final String FILE_BRCC_FIRST_LOCATION = "file.brcc.first.location";
	  public static final String FILE_BRCC_LAST_LOCATION = "file.brcc.last.location";
	  
	  //Form style
	  public static final String FILE_FONT_NAME = "file.font.name";
	  public static final String PTC_DCF_VALIDATION_DATE = "ptc.dcf.validation.date";
	  public static final String TIMEPERIOD_FORMAT = "timePeriod.format";
	  
	  //DPC File 
	  public static final String MONITORING_FORM_IUS_NIDS = "monitoringform.iusnids";
	  public static final String DPC_FILE_FORMTYPE_CELL = "dpc.file.formtype.cell";
	  public static final String DPC_FILE_MONTH_CELL = "dpc.file.month.cell";
	  public static final String DPC_FILE_YEAR_CELL = "dpc.file.year.cell";
	  public static final String DPC_FILE_DISTRICT_CELL = "dpc.file.district.cell";
	  public static final String DPC_FILE_BRCC_NUMBER_CELL = "dpc.file.brcc.number.cell";
	  public static final String DPC_FILE_CRCC_NUMBER_CELL = "dpc.file.crcc.number.cell";
	  public static final String DPC_FILE_FEATURES_COLUMN_CELL = "dpc.file.features.column.cell";
	  public static final String DPC_FILE_HEADING_CELL = "dpc.file.heading.cell";
	  public static final String DPC_FILE_FIRST_LOCATION = "dpc.file.first.location";
	  public static final String DPC_FILE_LAST_LOCATION = "dpc.file.last.location";
	  public static final String DPC_FILE_TOTAL_SCHOOL_LOCATION = "dpc.file.total.school.location";
	  //END WORKCPACE 
	  
	  //CONATACT US
	  public static final String CONTACT_US_CAPTCHA_VALIDATION_FAIL = "contact.us.captcha.validation.fail";
	  public static final String CAPTCHA_PRIVATE_KEY = "captcha.private.key";
	  public static final String CONTACT_US_MAIL_SEND_FAIL = "contact.us.mail.send.fail";
	  public static final String CONTACT_US_MAIL_SEND_SUCCESS = "contact.us.mail.send.success";
	  public static final String CONTACT_US_MAIL_SUBJECT = "contact.us.mail.subject";
	  public static final String CONTACT_US_MAIL_TOMAILID = "contact.us.mail.tomailid";
	  public static final String CONTACT_US_MAIL_TOUSERNAME = "contact.us.mail.tousername";
	  public static final String CONTACT_US_MAIL_SENDER_MAILID = "contact.us.mail.sender.mailid";
	  
	  //PTC USER ACTIVE/DEACTIVE
	  public static final String PTC_USER_ACTIVE_SUCCESS = "ptc.user.active.success";
	  public static final String PTC_USER_ACTIVE_FAIL = "ptc.user.active.fail";
	  public static final String PTC_USER_ACTIVE_FAIL1 = "ptc.user.active.fail1";	  
	  
	  //PTC USER ATTACH/DETACH
	  public static final String PTC_USER_DETACH_SUCCESS = "ptc.user.detach.success";
	  public static final String PTC_USER_DETACH_SUCCESS1 = "ptc.user.detach.success1";
	  public static final String PTC_USER_DETACH_FAIL = "ptc.user.detach.fail";
	  public static final String PTC_USER_DETACH_FAIL1 = "ptc.user.detach.fail1";
	  public static final String PTC_USER_ATTACH_SUCCESS = "ptc.user.attach.success";
	  public static final String PTC_USER_ATTACH_SUCCESS1 = "ptc.user.attach.success1";
	  public static final String PTC_USER_ATTACH_FAIL = "ptc.user.attach.fail";
	  public static final String PTC_USER_ATTACH_FAIL1 = "ptc.user.attach.fail1";
	  
	  //PTC ADD NEW USER
	  public static final String PTC_ADD_NEW_USER_UNIQUE_MAILID = "ptc.add.new.user.unique.mailid";
	  public static final String PTC_ADD_NEW_USER_UNIQUE_MAILID1 = "ptc.add.new.user.unique.mailid1";
	  public static final String PTC_ADD_NEW_USER_SUCCESS = "ptc.add.new.user.success";
	  public static final String PTC_ADD_NEW_USER_SUCCESS1 = "ptc.add.new.user.success1";
	  public static final String PTC_ADD_NEW_USER_FAIL = "ptc.add.new.user.fail";
	  public static final String PTC_ADD_NEW_USER_FAIL1 = "ptc.add.new.user.fail1";
	  
	  //workspace aggregation
	  public static final String AGGREGATION_SOURCENID = "aggregation.sourceNid";
	  
//	  User Roles
	  public static final String USER_BRCC_ROLE_NAME = "usertype.brcc";
	  public static final String USER_DPC_ROLE_NAME = "usertype.dpc";
	  public static final String USER_PTC_ROLE_NAME = "usertype.ptc";
	  
	  public static final String BCF_DEFAULT_FORM_PATH = "default.bcfform";
	  
//	  Factsheets path
	  public static final String FACTSHEETS_PATH = "factsheets.path";
      
	 public static class Features
	 {
		 public static final String FEATURE_HOMESCREEN = "Home_Screen";
		 public static final String FEATURE_ABOUTSCREEN = "About_Screen";
		 public static final String FEATURE_PRESENTATIONS = "Presentations";
		 public static final String FEATURE_PHOTOGRAPHS = "Photographs";
		 public static final String FEATURE_REPORTS = "Reports";
		 public static final String FEATURE_LOGIN = "Login";
		 public static final String FEATURE_DASHBOARD = "Dashboard";
		 public static final String FEATURE_CONTACT = "Contact";
		 public static final String FEATURE_USERMANAGEMENT = "User_Management";
		 public static final String FEATURE_LOGSGENERATION = "Logs_Generation";
		 public static final String FEATURE_WORKSPACE = "Workspace";
		 public static final String FEATURE_ROLEMANAGEMENT = "Role_Management";


	 }
//	  public static final String FEATURE_WORKSPACE = "workspace";
//	  public static final String FEATURE_AUDIT = "audit";
	  
	//PTC On clicking ACTIVE button Role Change
	public static final String PTC_ACTIVE_USER_SUBJECT = "ptc.active.user.subject";
	public static final String PTC_ACTIVE_USER_MSG = "ptc.active.user.msg";
	public static final String PTC_DEACTIVE_USER_SUBJECT = "ptc.deactive.user.subject";
	public static final String PTC_DEACTIVE_USER_MSG = "ptc.deactive.user.msg";
	
	//new evm
	public static final int AREA_STATE_LEVEL = 1;
	public static final int AREA_DISTRICT_LEVEL = 2;
	
	public static class Slices{
		public static String FIRST_SLICE = "firstslices";
		public static String SECOND_SLICE = "secondslices";
		public static String THIRD_SLICE = "thirdslices";
		public static String FOUTRH_SLICE = "fourthslices";
	}
	
	/*
	 * Aggregation Info
	 */
	public static final String DEVINFO_MAX_AREA_LEVEL="devinfo.max.area.level";
	public static final String DEVINFO_MIN_AREA_LEVEL="devinfo.min.area.level";
	public static final String DEVINFO_TIMEPERIOD_NID="devinfo.timeperiod.nid";
	public static final String DEVINFO_SOURCE_NID="devinfo.source.nid";
	public static final String DEVINFO_PERCENT_UNIT_NID="devinfo.percent.unit.nid";
	public static final String DEVINFO_RANK_UNIT_NID="devinfo.rank.unit.nid";
} 
