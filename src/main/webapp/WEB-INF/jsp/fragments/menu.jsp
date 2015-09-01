<%@page import="org.apache.catalina.connector.Request"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="org.sdrc.evm.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="org.sdrc.evm.model.User"%>
<%@page import="org.sdrc.evm.model.UserRoleSchemeMapping"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	User user = null;
	String role = "";
	List<String> features = new ArrayList<String>();
	List<String> permissions = new ArrayList<String>();
%>
<%
	user = (User) request.getSession().getAttribute(
			Constants.USER_PRINCIPAL);
	List<UserRoleSchemeMapping> ursMappings = new ArrayList<UserRoleSchemeMapping>();
	ursMappings = user != null ? user.getUserRoleSchemeMappings()
			: null;
	if (ursMappings != null && !ursMappings.isEmpty()) {
		for (UserRoleSchemeMapping ursm : ursMappings) {

			role = ursm.getRoleScheme().getSamikshyaRole()
					.getRoleName();
			features.add(ursm.getRoleScheme()
					.getFeaturePermissionMapping().getFeature()
					.getFeatureName());
			permissions.add(ursm.getRoleScheme()
					.getFeaturePermissionMapping().getPermission()
					.getPermissionName());
		}
	}
%>

<button class="navbar-toggle" data-toggle="collapse"
	data-target=".navHeaderCollapse2">
	<span class="icon-bar"></span> <span class="icon-bar"></span> <span
		class="icon-bar"></span>
</button>
<div
	class="collapse navbar-collapse navHeaderCollapse2 pull-left navbar-width min-height300">
	<ul class="nav navbar-nav navbar-left samikshya-main-nav">
		<li><a
			class="<%=request.getRequestURI().contains("welcome") ? "menu-active"
					: ""%>"
			href="<spring:url value="/" htmlEscape="true" />" style="padding-left: 0px"> Home </a></li>


		<li><a
			class="<%=request.getRequestURI().contains("about") ? "menu-active"
					: ""%>"
			href="<spring:url value="/about" htmlEscape="true" />"> About </a></li>
<!--  <li><a -->
<%-- 			class="<%=request.getRequestURI().contains("") ? "menu-active" --%>
<%-- 					: ""%>" --%>
<%-- 			href="<spring:url value="" htmlEscape="true" />"> Dataentry </a></li> --%>
 <li><a
			class="<%=request.getRequestURI().contains("dashboard") ? "menu-active"
					: ""%>"
			href="<spring:url value="landing?feature=Dashboard" htmlEscape="true" />"> Dashboard </a></li>

		<%
			//	for (String feature : features) {
			if (features != null
					&& features
							.contains(Constants.Features.FEATURE_USERMANAGEMENT)) {
		%>
		<li><a
			class="<%=request.getRequestURI().contains("allUserList") ? "menu-active"
						: ""%>"
			href="./userMaster"> <i class="fa fa-lg fa-users"> </i> User
				Management
		</a></li>
		<%
			//	break;
			}
			//	}
		%>
		<%
			if (features != null
					&& features.contains(Constants.Features.FEATURE_WORKSPACE)) {
		%>
		<li><a
			class="<%=request.getRequestURI().contains("workspace") ? "active-menu"
						: ""%>"
			href="<spring:url value="/workspace" htmlEscape="true" />"><i
				class="fa fa-lg fa-retweet"></i> Workspace </a><span class="arrow"></span></li>

		<%
			}
		%>
		<%
			//for (String feature : features) {
			if (features != null
					&& features
							.contains(Constants.Features.FEATURE_LOGSGENERATION)) {
				//if (Constants.Features.FEATURE_LOGSGENERATION.equalsIgnoreCase(feature)) {
		%>
		<li><a
			class="<%=request.getRequestURI().contains("adminaudit") ? "menu-active"
						: ""%>"
			href="./logs"> <i class="fa fa-lg fa-undo"> </i> Logs Generation
		</a></li>
		<%
			//	break;
				//		}
			}
		%>

	</ul>
	<div class="pull-right">
		<form class="navbar-form navbar-right searchform" role="search"
			action="gcsearch" method="post">
			<input class="form-control" type="text" placeholder="Enter Keyword"
				name="search">
			<button type="submit" class="btn btn-sm mainnav-form-btn">
				<i class="fa fa-lg fa-search ripas_blue"></i>
			</button>
		</form>
	</div>
</div>


