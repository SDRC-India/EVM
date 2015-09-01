<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html lang="en" ng-app="esamapp">

<jsp:include page="fragments/headTag.jsp" />

<body>
	<div id="wrapper">
		<jsp:include page="fragments/bodyHeader.jsp" />
		<div id="containerId" class="content"
			ng-controller="DashboardController" ng-cloak>
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2">
						<h2 class="header_style evm-font-blue">Dashboard</h2>
					</div>
					<div class="col-md-4"></div>
					<div class="col-md-3">
						<div class="btn-toolbar" role="toolbar ">
							<div class="input-group samikshya-filter mar-top-10">

								<input type="text" placeholder="Select indicator"
									class="form-control" ng-model="selectedIndicator.value"
									readonly></input>
								<div class="input-group-btn" style="position: relative;">
									<button data-toggle="dropdown" id="ind-list"
										class="btn btn-primary dropdown-toggle" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu"
										aria-labelledby="ind-list" id="ind_drop">
										<li ng-repeat="indicator in indicators"
											title="{{indicator.value}}"><a
											ng-click="selectIndicator(indicator)" href="#">
												{{indicator.value}}</a></li>
									</ul>
								</div>
								<!-- /btn-group -->
							</div>
						</div>


					</div>
					<div class="col-md-3">
						<div class="btn-toolbar" role="toolbar">
							<div class="input-group samikshya-filter mar-top-10">

								<input type="text" placeholder="Select month"
									class="form-control" ng-model="selectedTimeperiod.value"
									readonly></input>
								<div class="input-group-btn">
									<button data-toggle="dropdown" id="tp-list"
										class="btn btn-primary dropdown-toggle pull-left"
										type="button">
										<i class="fa fa-lg fa-calendar"></i>
									</button>
									<ul class="dropdown-menu" role="menu" aria-labelledby="tp-list"
										id="tp_drop">
										<li ng-repeat="timeformat in timeformats"><a
											ng-click="selectTimeperiod(timeformat)" href="#">
												{{timeformat.value}}</a></li>
									</ul>
								</div>
								<!-- /btn-group -->
							</div>
						</div>
					</div>

				</div>

				<section>
					<div class="mid-bar bar  mar-bot-10">
						<div class="container-fluid">
							<div class="row">

								<div class="col-md-6  pull-right mar-top-5 mar-bot-5 bar_style"
									id="right_exportshare">
									<jsp:include page="fragments/exportandShare.jsp">
										<jsp:param value="mapContainerId" name="mapContainer" />
										<jsp:param value="chartContainerId" name="chartContainer" />
										<jsp:param value="{{selectedGranularity.key}}" name="areaId" />
										<jsp:param value="{{parentArea}}" name="parentAreaName" />
										<jsp:param value="{{selectedIndicator.description}}"
											name="indicatorId" />
										<jsp:param value="{{selectedTimeperiod.key}}"
											name="timePeriodId" />
										<jsp:param value="{{selectedGranularity.value}}" name="area" />
										<jsp:param value="{{selectedIndicator.value}}"
											name="indicator" />
										<jsp:param value="{{selectedTimeperiod.value}}"
											name="timePeriod" />
										<jsp:param value="{{selectedSector.key}}" name="sectorId" />
										<jsp:param value="{{secondSelectedTimeperiod.key}}"
											name="secondTimeperiodId" />
										<jsp:param value="{{secondSelectedTimeperiod.value}}"
											name="secondTimeperiod" />

										<jsp:param value="{{selectedSector.value}}" name="sectorName" />
										<jsp:param value="{{selectedSubSector.value}}"
											name="subSectorName" />
										<jsp:param value="{{selectedSubSector.key}}"
											name="subSectorKey" />
										<jsp:param value="{{selectedGranularitySpider.key}}"
											name="granularitySpiderKey" />
										<jsp:param value="{{selectedGranularitySpider.value}}"
											name="granularitySpiderValue" />
									</jsp:include>

								</div>



							</div>
						</div>
					</div>
				</section>

				<!---- End of right buttons --------- -->
				<div class="map_popover">
					<div class="map_popover_close"></div>
					<div class="map_popover_content"></div>

				</div>

				<!-- Map loading portion -->
				<div id="mapContainerId">
					<div>
						<samiksha-map ng-show="isGoogleMapVisible" ng-style="style()"
							style="display:block;margin-top: -15px;"></samiksha-map>
						<!---- End of map loading portion -------->
					</div>
					<div class="col-md-4 area_level" data-html2canvas-ignore="true">
						<!-- 									<h4 class="bar_style">Vector View</h4> -->
						<ul class="list-unstyled list-inline arealist">
							<li ng-repeat="sector in sectors"><a
								ng-click="selectSector(sector)"
								ng-class="{active:selectedSector.value == sector.value}"
								href="#"> <i class="fa fa-map-marker"></i>{{sector.value}}
							</a></li>
						</ul>
					</div>
					<section class="selection-desc">
						<h5 class="top">{{selectedSector.value}}</h5>
						<i class="fa fa-lg fa-angle-double-right"></i>
						<h5 ng-show="isGranularityVisible">{{selectedGranularitySpider.value}}</h5>
						<i class="fa fa-lg fa-angle-double-right"
							ng-show="isGranularityVisible"></i>
						<h5>{{selectedIndicator.value}}</h5>
						<i class="fa fa-lg fa-angle-double-right"></i>
						<h5>{{selectedTimeperiod.value}}</h5>
					</section>
					<div style="position: relative;">
						<section class="legends">
								
							<ul>
							<li class="legend_list">
							<h4>{{selectedIndicator.shortName}}</h4>
							</li>
								<li class="legend_list ng-scope"><span class="legend_key ">60
										and below</span> <span class="firstslices legnedblock"> </span></li>
								<!-- end ngRepeat: legend in legends -->
								<li class="legend_list "><span class="legend_key ">61
										to 79</span> <span class="secondslices legnedblock"> </span></li>
								<!-- end ngRepeat: legend in legends -->
								<li class="legend_list "><span
									class="legend_key ng-binding">80 and above</span> <span
									class="fourthslices legnedblock"> </span></li>
								<!-- end ngRepeat: legend in legends -->
								<!-- 								<li class="legend_list" ><span -->
								<!-- 									class="legend_key">Not Available</span> <span -->
								<!-- 									class="fifthslices legnedblock"> </span></li> -->
								<!-- end ngRepeat: legend in legends -->
							</ul>
						</section>
						<google-map ng-hide="isGoogleMapVisible" center="map.center"
							zoom="map.zoom" draggable="true"> <markers class="pushpin"
							models="map.markers" coords="'self'" icon="'icon'"
							events="map.events"> 
							<windows show="'showWindow'" closeClick="'closeClick'" ng-cloak options='pixelOffset'>
							<p ng-non-bindable style="width: 120px;">
								{{title}}<br> Score:{{dataValue}}%
							</p>
							</windows> 
							</markers> 
						</google-map>
					</div>

					<div class="row location_images" ng-show="isImagesVisible">

						<div class="showimage">
							<h3>
								<i class="fa fa-angle-double-down" ng-click="showImages()"
								data-html2canvas-ignore="true"></i>
							</h3>
						</div>
						<div id="links">
							<ul>
								<li ng-repeat="selectedImage in selectedImages track by $index"
									class="list-unstyled"><a href="{{selectedImage}}"
									dashimage data-gallery> <img class="img-responsive"
										src='{{selectedImage}}' style="cursor: pointer"></img></a></li>
							</ul>
						</div>
						<div id="blueimp-gallery" class="blueimp-gallery">
							<div class="slides"></div>
							<h3 class="title"></h3>
							<a class="prev">&lt;</a> <a class="next">&gt;</a> <a
								class="close">×</a> <a class="play-pause"></a>
							<ol class="indicator"></ol>
							<!-- The - dialog, which will be used to wrap the lightbox content -->
							<div class="modal fade">

								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" aria-hidden="true">&times;</button>
											<h4 class="modal-title"></h4>
										</div>
										<div class="modal-body next mapimage"></div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default pull-left prev">
												<i class="glyphicon glyphicon-chevron-left"></i> Previous
											</button>
											<button type="button" class="btn btn-primary next">
												Next <i class="glyphicon glyphicon-chevron-right"></i>
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 					<button id="mapToggleButton" ng-click="showMap()" -->
					<!-- 						class="map-toggle-btn" data-html2canvas-ignore="true"></button> -->

				</div>

				<!-- Spider chart loading portion -->
				<div class="row" style="margin-bottom: -47px">
					<div class="col-md-3 pull-right">
						<div class="btn-toolbar" role="toolbar">
							<div class="input-group samikshya-filter mar-top-10">

								<input type="text" placeholder="Select month"
									class="form-control" ng-model="secondSelectedTimeperiod.value"
									readonly></input>
								<div class="input-group-btn">
									<button data-toggle="dropdown" id="tp-list"
										class="btn btn-primary dropdown-toggle pull-left"
										type="button">
										<i class="fa fa-lg fa-calendar"></i>
									</button>
									<ul class="dropdown-menu" role="menu" aria-labelledby="tp-list"
										id="tp_drop">
										<li ng-repeat="secondTimeformat in secondTimeformats"
											ng-if=secondTimeformat.value!=selectedTimeperiod.value
											ng-continue><a
											ng-click="secondSelectTimeperiod(secondTimeformat)"
											href="javascript:void(0)">{{secondTimeformat.value}}</a></li>
									</ul>
								</div>
								<!-- /btn-group -->
							</div>
						</div>
					</div>

				</div>

				<div class="row" id="chartContainerId">

					<div class="tabs mar-top-15">
						<ul id="myTab" class="nav nav-tabs" role="tablist"
							data-html2canvas-ignore="true">

							<li ng-repeat="subSector in subSectors"
								ng-class="{active:selectedSubSector.value == subSector.value}"><a
								data-toggle="tab" role="tab" ng-click="getspiderdata(subSector)"
								href="#home">{{subSector.value}}</a></li>

						</ul>
						<div class="col-md-6">
							<div id="myTabContent" class="tab-content">
								<div id="home" class="tab-pane fade active in">

									<sdrc-spider></sdrc-spider>

								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div id="myTabContent1" class="tab-content">
								<div id="home1" class="tab-pane fade active in">
									<sdrc-bar-chart></sdrc-bar-chart>
								</div>
							</div>
						</div>
<%-- 						<input type="hidden"  id="parentArea" value="${areaName}" ng-model="parentArea" /> --%>
<!-- 						<div class="col-md-2"> -->
<!-- 							<ul class="list-unstyled list-inline pull-right legendlist"> -->
<!-- 								<li ng-if="selectedTimeperiod.value != null"><span class="legend_year">{{selectedTimeperiod.value}}</span><span -->
<!-- 									class="legend_block y1"></span></li> -->
<!-- 								<li ng-if="secondSelectedTimeperiod.value != null"><span class="legend_year">{{secondSelectedTimeperiod.value}}</span><span -->
<!-- 									class="legend_block y2"></span></li> -->
<!-- 							</ul> -->
<!-- 						</div> -->
						<!-- 							<table ng-table="tableParams" class="table"> -->
						<!-- 							<tr> -->
						<!--     <th ng-repeat="column in $columns" -->
						<!--         ng-class="{ -->
						<!--                     'sortable': parse(column.sortable), -->
						<!--                     'sort-asc': params.sorting()[parse(column.sortable)]=='asc', -->
						<!--                     'sort-desc': params.sorting()[parse(column.sortable)]=='desc' -->
						<!--                   }" -->
						<!--         ng-click="sortBy(column, $event)" -->
						<!--         ng-show="column.show(this)" -->
						<!--         ng-init="template = column.headerTemplateURL(this)" -->
						<!--         class="header {{column.class}}"> -->
						<!--         <div ng-if="!template" ng-show="!template" ng-bind="parse(column.title)"></div> -->
						<!--         <div ng-if="template" ng-show="template"><div ng-include="template"></div></div> -->
						<!--     </th> -->
						<!-- </tr> -->
						<!-- <tr ng-show="true" class="ng-table-filters"> -->
						<!--     <th ng-repeat="column in $columns" ng-show="column.show(this)" class="filter"> -->
						<!--         <div ng-repeat="(name, filter) in column.filter"> -->
						<!--             <div ng-if="column.filterTemplateURL" ng-show="column.filterTemplateURL"> -->
						<!--                 <div ng-include="column.filterTemplateURL"></div> -->
						<!--             </div> -->
						<!--             <div ng-if="!column.filterTemplateURL" ng-show="!column.filterTemplateURL"> -->
						<!--                 <div ng-include="'ng-table/filters/' + filter + '.html'"></div> -->
						<!--             </div> -->
						<!--         </div> -->
						<!--     </th> -->
						<!-- </tr> -->
						<!--         <tr ng-repeat="user in $data"> -->
						<!--             <td data-title="'Name'">{{user.axis}}</td> -->
						<!--             <td data-title="'Age'">{{user.value}}</td> -->
						<!--         </tr> -->
						<!--         </table> -->

					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="//maps.googleapis.com/maps/api/js?sensor=false"></script>
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
	<script src="resources/js/lodash.min.js" type="text/javascript"></script>
	<script src="resources/js/angular-google-maps.min.js"
		type="text/javascript"></script>
<!-- 	<script src="resources/js/ng-table.min.js" type="text/javascript"></script> -->
	<script src="resources/js/sdrc.dashboard.js" type="text/javascript"></script>
	<script src="resources/js/angcontrollers/dashboardctrl.js"
		type="text/javascript"></script>
	<script>
		$(document).ready(function() {

			$('html, body').animate({
				scrollTop : '60px'
			}, 1500);
			return false;
		});
	</script>
</body>

</html>
