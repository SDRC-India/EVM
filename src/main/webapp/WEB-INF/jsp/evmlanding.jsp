<!DOCTYPE html>

<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en" ng-app="evmmapp">
<jsp:include page="fragments/headTag.jsp" />

<body>

	<div id="wrapper" >
	
		<jsp:include page="fragments/bodyHeader.jsp" />
		<div id="containerId" class="content" ng-controller="EvmDashboardController">

<!-- 			<section id="gse" data-html2canvas-ignore="true"> -->
<!-- 				<div class="container-fluid"> -->
<!-- 				<h2 class="page-header evm-font-blue">Layout</h2> -->
<!-- 				</div> -->
<!-- 			</section> -->
			<div id="mapcontainerId" class="container-fluid"  >
			

<%--           <%System.out.println(" the req param is " + request.getParameter("key")); %> --%>
<!-- <input type="hidden"  value="key"> -->
         <h2 class="page-header evm-font-blue">${feature}</h2>
         
         <form id="landingForm" action="landingredirect" method="post" >
         	<input type="hidden"  name="featureName" value="${feature}" />
         	<input type="hidden"  name="areaName"  id="lAreaName" />
         	<input type="hidden"  name="areaCode"  id="lAreaCode" />
         </form>
				<!---- End of right buttons --------- -->
				<div class="map_popover">
					<div class="map_popover_close"></div>
					<div class="map_popover_content"></div>

				</div>
				<!-- Map loading portion -->
				<div id="evmMapBg" class="map-${feature}" style="margin-top: -10px">
					<p style="text-align: center;">
						<mark id="mark-${feature}" style="padding: 0 2px 1px 10px;"><i>Click on the state to view the corresponding
								${feature} <br> P.S: This database includes Bihar and Assam only.</i></mark>
					</p>
					<samiksha-map ng-style="style()" style="display:block;padding:32px"></samiksha-map>
					<!---- End of map loading portion -------->
				</div>
			</div>
		</div>

		<div class="clearfooter"></div>
	</div>
	<spring:url value="/webjars/angularjs/1.2.16/angular.min.js"
		var="angularmin" />
	<script src="${angularmin}" type="text/javascript"></script>
	<spring:url value="/webjars/angularjs/1.2.16/angular-animate.min.js"
		var="angularaAnimatemin" />
	<script src="${angularaAnimatemin}" type="text/javascript"></script>
	<spring:url
		value="/webjars/angular-loading-bar/0.4.3/loading-bar.min.js"
		var="loadingbarmin" />
	<script src="${loadingbarmin}" type="text/javascript"></script>

	<spring:url value="/webjars/d3js/3.4.6/d3.min.js" var="d3js" />
	<script src="${d3js}"></script>

	<jsp:include page="fragments/footer.jsp" />
	<script>
		var angFeature = "${feature}";
	</script>
	<script src="resources/js/evm.landing.js" type="text/javascript"></script>
	<script src="resources/js/angcontrollers/evmlandingctrl.js" type="text/javascript"></script>
    <script>
               $(document).ready(function(){
                       
                       $('html, body').animate({
                   scrollTop: '61px'
               }, 1500);
               return false;
               });
               </script>    
</body>

</html>
