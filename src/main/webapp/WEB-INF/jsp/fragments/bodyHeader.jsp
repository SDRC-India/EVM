<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="org.sdrc.evm.util.Constants"%>
<%@page import="org.sdrc.evm.model.UserRoleSchemeMapping"%>
<%@ page import="org.sdrc.evm.model.User"%>
<%@ page import="org.sdrc.evm.model.Role"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/resources/images/evm_logo.png"
	var="EVMlogo" />
<!-- Top bar -->

<header class="navbar-static-top bar" id="head1">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="./"> <img alt="evm_logo"
				src="${EVMlogo}" class="img-responsive">
<!-- 				<span class="evm-font-blue">Effective Vaccine Management</span> -->
			</a>
		</div>
				<%User user = null;
	String role = "";
	String area="";
	List<String> features = new ArrayList<String>();
	List<String> permissions = new ArrayList<String>();%>
				<%
					user = (User) request.getSession().getAttribute(
							Constants.USER_PRINCIPAL);
					role = user != null ? user.getUserRoleSchemeMappings() != null
							&& !user.getUserRoleSchemeMappings().isEmpty() ? user
							.getUserRoleSchemeMappings().get(0).getRoleScheme()
							.getSamikshyaRole().getRoleName() : null : null;
					area = user != null ? user.getUserRoleSchemeMappings() != null
							&& !user.getUserRoleSchemeMappings().isEmpty() ? user
							.getUserRoleSchemeMappings().get(0).getRoleScheme()
							.getRoleSchemeName().split("_")[0] : null : null;
					
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
				<ul class="nav navbar-nav navbar-right evm-nav">
					<%
						if (role == null) {
					%>
					<li><a class="" href="./login"><i class="fa fa-lg fa-user">
						</i> Login </a></li>
					<%
						}
					%>
					
					<%
						if (role != null && area !=null) {
					%>
					<li><a class="" href="javascript:void(0)">Welcome &nbsp;<%=role%>&nbsp;(&nbsp;<%=area%>&nbsp;),&nbsp;&nbsp;<%=user.getUserName()%><span>&nbsp;&nbsp;<img
								src="resources/images/avatar.png" width="16px" height="16px" /></span>
					</a></li>
					<li><a id="logoutid" class=""
						href="<c:url value="/logout" />"><i
							class="fa fa-lg fa-power-off"> </i> Logout </a></li>
					<%
						}
					%>
					<li><a class="" href="./contactus"> <i
							class="fa fa-lg fa-envelope-o"> </i> Contact
					</a></li>
				</ul>
<!-- 			</nav> -->
<!-- 		</div> -->
	</div>

</header>
<!-- END Top Bar	 -->
<div class="navbar navbar-default navbar-static-top bar menu" id="head2">
	<div class="container-fluid">
		<jsp:include page="menu.jsp" />
	</div>
</div>