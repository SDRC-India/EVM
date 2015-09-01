<!DOCTYPE html>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>
<%@taglib prefix="serror" uri="/WEB-INF/ErrorDescripter.tld"%>

<html lang="en" >

<jsp:include page="fragments/headTag.jsp" />

<body>

	<div id="wrapper">
		<jsp:include page="fragments/bodyHeader.jsp" />
		
		
		<div id="containerId" class="content">
			<div class="container-fluid">
				<h2 class="page-header">Factsheets</h2>
				
				<datatables:table id="workspace" data="${factsheetList}" cdn="true"
					row="workspace" theme="bootstrap2" cssClass="table table-striped"
					paginate="true">

					<datatables:column title="DISTRICT NAME">
					
						<c:out value="${workspace.name}" />
					</datatables:column>
				    <datatables:column title="FACTSHEET">
                        <a href="<c:out value="${workspace.factsheet}" />" download=""
                            class="${workspace.factsheet != '' ?  'btn btn-sm btn-link' : 'btn btn-sm btn-link disabled'}">
                            <i class="fa fa-lg fa-download"></i>Download</a>
                    </datatables:column>
                    <datatables:column title="STRENGTH">
                        <a href="<c:out value="${workspace.strength}" />" download=""
                            class="${workspace.strength != '' ? 'btn btn-sm btn-link' : 'btn btn-sm btn-link disabled'}"><i
                            class="fa fa-lg fa-download"></i>Download</a>
                    </datatables:column>
                    <datatables:column title="IMPROVEMENT PLAN">
                        <a href="<c:out value="${workspace.improvementPlan}" />"
                            download=""
                            class="${workspace.improvementPlan != '' ? 'btn btn-sm btn-link' : 'btn btn-sm btn-link disabled'}"><i
                            class="fa fa-lg fa-download"></i>Download</a>
                    </datatables:column>
				</datatables:table>

			</div>
		</div>

	
	
	</div>
<!-- 	<script src="//maps.googleapis.com/maps/api/js?sensor=false"></script> -->
<%-- 	<spring:url value="/webjars/angularjs/1.2.16/angular.min.js" --%>
<%-- 		var="angularmin" /> --%>
<%-- 	<script src="${angularmin}" type="text/javascript"></script> --%>
<%-- 	<spring:url value="/webjars/angularjs/1.2.16/angular-animate.min.js" --%>
<%-- 		var="angularaAnimatemin" /> --%>
<%-- 	<script src="${angularaAnimatemin}" type="text/javascript"></script> --%>
<%-- 	<spring:url --%>
<%-- 		value="/webjars/angular-loading-bar/0.4.3/loading-bar.min.js" --%>
<%-- 		var="loadingbarmin" /> --%>
<%-- 	<script src="${loadingbarmin}" type="text/javascript"></script> --%>

<%-- 	<spring:url value="/webjars/d3js/3.4.6/d3.min.js" var="d3js" /> --%>
<%-- 	<script src="${d3js}"></script> --%>

	<jsp:include page="fragments/footer.jsp" />
<!-- 	<script src="resources/js/lodash.min.js" type="text/javascript"></script> -->
<!-- 	<script src="resources/js/angular-google-maps.min.js" -->
<!-- 		type="text/javascript"></script> -->
<!-- 	<script src="resources/js/ng-table.min.js" type="text/javascript"></script>	 -->
<!-- 	<script src="resources/js/sdrc.dashboard.js" type="text/javascript"></script> -->
<!-- 	<script src="resources/js/angcontrollers/dashboardctrl.js" -->
<!-- 		type="text/javascript"></script> -->
		<script>
		$(document).ready(function(){
			
			$('html, body').animate({
	            scrollTop: '60px'
	        }, 1500);
	        return false;
		});
		</script>
	
	
</body>

</html>
