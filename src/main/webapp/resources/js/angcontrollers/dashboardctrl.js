function ValueObject(key, value) {
	this.key = key;
	this.value = value;
};

function DashboardController($scope, $http, $window, $timeout, cfpLoadingBar) {

	var w = angular.element($window);
	$scope.getWindowDimensions = function() {
		return {
			"h" : w.height(),
			"w" : (w.width() * 90 / 100)
		};
	};
	// this is to make sure that scope gets changes as window get resized.
	w.on("resize", function() {
		$scope.$apply();
	});

	$scope.pixelOffset = {

		pixelOffset : new google.maps.Size(0, -28)
	};



	$scope.map = {
		center : {
			latitude : areaPName == 'Bihar' ? 25.863421 : 26.344159, 
			longitude : areaPName == 'Bihar' ? 85.780955 : 92.673618
		},
		bounds : {},
		clickMarkers : [],
		zoom : areaPName == 'Bihar' ?8 :7,
		events : {
			"mouseover" : function(mapModel, eventName, originalEventArgs) {
				for (var i = 0; i < $scope.map.markers.length; i++) {
					if ($scope.map.markers[i].id == originalEventArgs.id) {
						$scope.map.markers[i].showWindow = true;
						break;
					}
				}

				// clickMarkers.windows.showWindow = true;
				$scope.$apply();
			},
			"mouseout" : function(mapModel, eventName, originalEventArgs) {
				for (var i = 0; i < $scope.map.markers.length; i++) {
					if ($scope.map.markers[i].id == originalEventArgs.id) {
						$scope.map.markers[i].showWindow = false;
						break;
					}
				}
				// clickMarkers.windows.showWindow = true;
				$scope.$apply();
			},
			"click" : function(mapModel, eventName, originalEventArgs) {
				$scope.selectedImages = "[]";
				// $scope.selectedGranularity = new
				// ValueObject(originalEventArgs.areaID,
				// originalEventArgs.title);
				$scope.selectedGranularity = null;
				$scope.selectedGranularitySpider = null;
				$scope.isUrl = false;
				$scope.selectedGranularitySpider = new ValueObject(
						originalEventArgs.areaID, originalEventArgs.title);
				var imagePaths = originalEventArgs.images;
				if (imagePaths) {
					var arrOfPaths = imagePaths.split(',');
					$scope.selectedImages = arrOfPaths;
					$scope.isImagesVisible = true;
				} else {
					$scope.isImagesVisible = false;
				}
				$scope.getspiderdata();
				$scope.isGranularityVisible = true;

			}
		}
	};

	// Scope properties
	// $scope.indicators = [ new ValueObject("1", "Select Indicator") ];---
	$scope.indicators = [];
	$scope.timeformats = [];
	$scope.secondTimeformats = [];
	$scope.sectors = [];// [ new ValueObject("1", "Basic Info") ];
	$scope.subSectors = [];
	$scope.utdata = [];
	$scope.spiderdata = [];
	$scope.legends = [];
	$scope.topPerformers = [];
	$scope.bottomPerformers = [];
	$scope.parentArea = areaPName;
	// var parentAreaName = ${areaName};
	// console.log(parentAreaName);

	// select the first user of the list
	$scope.selectedTimeperiod = $scope.timeformats[0];
	$scope.secondSelectedTimeperiod = $scope.secondTimeformats[0];
	$scope.selectedSector = $scope.sectors[0];
	$scope.selectedIndicator = $scope.indicators[0];
	// $scope.selectedGranularity = new ValueObject("IND010", "Bihar");
	$scope.selectedGranularity = "";
	$scope.selectedGranularitySpider = "";
	$scope.selectedSubSector = $scope.subSectors[0];
	$scope.isTrendVisible = false;
	$scope.isGoogleMapVisible = false;
	$scope.selectedArea = "[]";
	$scope.show = false;
	$scope.shouldDrilldown = true;
	$scope.googleMapData = "[]";
	$scope.map.markers = "[]";
	$scope.selectedImages = "[]";
	$scope.isImagesVisible = false;
	$scope.isGranularityVisible = false;
	$scope.isUrl = false;
	// Scope methods
	// expose a callable method to the view

	$scope.selectIndicator = function(indicator) {
		$scope.selectedIndicator = indicator;
		$scope.selectedGranularitySpider = null;
		$scope.isUrl = false;
		$scope.getSelectedGranularity();
		$scope.isGranularityVisible = false;
		$scope.isImagesVisible = false;
		// $scope.getutdata();
	};

	// expose a callable method for time period
	$scope.selectTimeperiod = function(timeformat) {
		$scope.selectedTimeperiod = timeformat;
		$scope.selectedGranularitySpider = null;
		$scope.isUrl = false;
		$scope.getSelectedGranularity();
		$scope.isGranularityVisible = false;
		$scope.isImagesVisible = false;
		if ($scope.selectedTimeperiod.value == $scope.secondSelectedTimeperiod.value) {
			$scope.secondSelectedTimeperiod = "";
		}
		// $scope.getutdata();

	};

	$scope.secondSelectTimeperiod = function(timeformat) {
		$scope.secondSelectedTimeperiod = timeformat;
		$scope.getspiderdata();

	};
	// expose a callable method for sectors
	$scope.selectSector = function(sector) {
		$scope.selectedSector = sector;
		$scope.selectedGranularitySpider = null;
		$scope.selectedImages = "[]";
		$scope.getSubSectors();
		$scope.getindicators();
		$scope.isGranularityVisible = false;
		$scope.isImagesVisible = false;
		$scope.isUrl = false;
	};

	$scope.showMap = function() {
		$scope.isGoogleMapVisible = !$scope.isGoogleMapVisible;
	};
	$scope.showImages = function() {
		$scope.isImagesVisible = false;
	};
	$scope.showViz = function(areacode) {
		if (areacode && $scope.selectedArea != areacode) {
			$scope.isTrendVisible = true;
			// TODO: this is fishy i am changing
			// visualization in angular context. i should
			// not be doing $scope.$apply().
			$scope.selectedArea = areacode;
		} else {

			$scope.isTrendVisible = false;
			$scope.selectedArea = [];
		}
		$scope.$apply();
	};
	$scope.closeViz = function() {
		$scope.isTrendVisible = false;
	};

	$scope.start = function() {
		cfpLoadingBar.start();
		$('.left-arrow').click(function() {
			if ($('.active-sector').length == 0) {
				$('.sectorlist').first().addClass('active-sector');
			}

			$visiblesec_width = $('.sector_wrap').outerWidth(true);
			$prevWidth = 0;
			$('.active-sector').prevAll().each(function() {
				$prevWidth += parseInt($(this).outerWidth(true), 10);
			});
			$ulwidth = 0;
			$('.sectorlist').each(function(index) {
				$ulwidth += parseInt($(this).outerWidth(true), 10);
			});
			// get length of all lists present before active lists

			if ($visiblesec_width + $prevWidth < $ulwidth) {
				$(".sectorlists").animate({
					'left' : "-=" + $('.active-sector').outerWidth(true)
				}, 100, function() {
					var el = $('.active-sector');
					el.removeClass('active-sector');
					el.next().addClass('active-sector');
				});
			} else {
				$('.left-arrow').addClass('disable');
			}
		});
		$('.right-arrow').click(
				function() {
					if ($('.active-sector').prev()) {

						if ($visiblesec_width + $prevWidth > $ulwidth) {
							$(".sectorlists").animate(
									{
										'left' : "+="
												+ $('.active-sector').prev()
														.outerWidth(true)
									}, 100, function() {
										var el = $('.active-sector');
										el.removeClass('active-sector');
										el.prev().addClass('active-sector');
									});
						} else {
							$('.right-arrow').addClass('disable');
						}
					} else {
						$('.right-arrow').addClass('disable');
					}
				});

		// Http calls
		$http
				.get('timeformats?parentAreaName=' + $scope.parentArea)
				.success(
						function(data) {
							$scope.timeformats = data;
							$scope.secondTimeformats = data;
							if ($scope.timeformats) {
								// $scope.selectedTimeperiod =
								// $scope.timeformats[0];

								// $scope.selectedTimeperiod = $.urlParam("st")
								// != 0 ? $
								// .getValueFromValueObect($.urlParam("st"),$scope.timeformats)
								// : $scope.timeformats[0];

								$scope.selectedTimeperiod = shareSt ? $.getValueFromValueObect(shareSt,$scope.timeformats)
										: $scope.timeformats[0];

								if ($scope.selectedTimeperiod) {

									$http
											.get('sectors')
											.success(
													function(data) {
														$scope.sectors = data;
														if ($scope.sectors) {
															// $scope.selectedSector
															// =
															// $scope.sectors[1];
															// $scope.selectedSector
															// =
															// $.urlParam("ss")
															// != 0 ? $
															// .getValueFromValueObect($.urlParam("ss"),$scope.sectors)
															// :
															// $scope.sectors[1];

															$scope.selectedSector = shareSs ? $.getValueFromValueObect(shareSs,$scope.sectors)

																	: $scope.sectors[1];

															$http
																	.get(
																			'subSectors?sector='
																					+ $scope.selectedSector.key)
																	.success(
																			function(
																					data) {
																				$scope.subSectors = data;

																				// $scope.selectedSubSector
																				// =
																				// $.urlParam("sss")
																				// != 0
																				// ? $
																				// .getValueFromValueObect($.urlParam("sss"),$scope.subSectors)
																				// :$scope.subSectors[0];

																				$scope.selectedSubSector = shareSss ? $.getValueFromValueObect(shareSss,$scope.subSectors)
																						: $scope.subSectors[0];

																				$http
																						.get(
																								'indicators?sector='
																										+ $scope.selectedSector.key)
																						.success(
																								function(
																										data) {
																									$scope.indicators = data;

																									if ($scope.indicators) {

																										// $scope.selectedIndicator
																										// =
																										// $.urlParam("si")
																										// != 0
																										// ? $
																										// .getValueFromValueObect($.urlParam("si"),$scope.indicators)
																										// :
																										// $scope.indicators[0];
																										//																
																										$scope.selectedIndicator = shareSi ? $.getValueFromValueObect(shareSi,$scope.indicators)
																												: $scope.indicators[0];

																										$http
																												.get(
																														'selectedGranularity?sectorName='
																																+ $scope.selectedSector.value
																																+ '&parentAreaName='
																																+ $scope.parentArea)
																												.success(
																														function(
																																data) {

																															// $scope.selectedGranularity
																															// =
																															// $.urlParam("sgky")
																															// != 0
																															// &&
																															// $.urlParam("sgval")
																															// !=
																															// 0?
																															// new
																															// ValueObject($.urlParam("sgky"),
																															// $.urlParam("sgval").replace("_",
																															// "
																															// "))
																															// :
																															// data;

																															$scope.selectedGranularity = shareSgky
																																	&& shareSgval ? new ValueObject(
																																	shareSgky,
																																	shareSgval
																																			.replace(
																																					"_",
																																					" "))
																																	: data;

																															if ($scope.selectedGranularity) {
																																var url = 'googleMapData';
																																var query = '';
																																if ($scope.selectedSector)
																																	query += "sectorName="
																																			+ $scope.selectedSector.value
																																			+ "&";
																																if ($scope.selectedIndicator)
																																	query += "indicatorId="
																																			+ $scope.selectedIndicator.description
																																			+ "&";
																																if ($scope.selectedTimeperiod)
																																	query += "timeperiodId="
																																			+ $scope.selectedTimeperiod.key
																																			+ "&";
																																if ($scope.selectedGranularity)
																																	query += "selectedGranularity="
																																			+ $scope.selectedGranularity.key
																																			+ "&";
																																if ($scope.parentArea)
																																	query += "parentAreaName="
																																			+ $scope.parentArea
																																			+ "&";

																																if (query != '')
																																	url += "?"
																																			+ query
																																					.trim("&");

																																$http
																																		.get(
																																				url)
																																		.success(
																																				function(
																																						data) {
																																					$scope.googleMapData = data;
																																					$scope.map.markers = data;

																																					// $scope.selectedGranularitySpider
																																					// =
																																					// $.urlParam("sgky1")
																																					// != 0
																																					// &&
																																					// $.urlParam("sgval1")
																																					// != 0
																																					// &&
																																					// $.urlParam("sgval1").replace("_",
																																					// " ")
																																					// !=
																																					// 'RVS'
																																					// &&
																																					// 'DVS'
																																					// &&
																																					// 'SVS'
																																					// &&
																																					// 'Health
																																					// facility'?
																																					// new
																																					// ValueObject($.urlParam("sgky1"),
																																					// $.urlParam("sgval1").replace("_",
																																					// "
																																					// "))
																																					// :
																																					// null;

																																					$scope.selectedGranularitySpider = shareSgky1
																																							&& shareSgval1
																																							&& shareSgval1
																																									.replace(
																																											"_",
																																											" ") != 'RVS'
																																							&& 'DVS'
																																							&& 'SVS'
																																							&& 'Health facility' ? new ValueObject(
																																							shareSgky1,
																																							shareSgval1
																																									.replace(
																																											"_",
																																											" "))
																																							: null;

																																					// $scope.isGranularityVisible
																																					// =
																																					// $.urlParam("sgky1")
																																					// != 0
																																					// &&
																																					// $.urlParam("sgval1")
																																					// != 0
																																					// &&
																																					// $.urlParam("sgval1").replace("_",
																																					// " ")
																																					// !=
																																					// 'RVS'
																																					// &&
																																					// 'DVS'
																																					// &&
																																					// 'SVS'
																																					// &&
																																					// 'Health
																																					// facility'?
																																					// true
																																					// :
																																					// false;

																																					$scope.isGranularityVisible = shareSgky1
																																							&& shareSgval1
																																							&& shareSgval1
																																									.replace(
																																											"_",
																																											" ") != 'RVS'
																																							&& 'DVS'
																																							&& 'SVS'
																																							&& 'Health facility' ? true
																																							: false;

																																					// if($.urlParam("sgky1")
																																					// != 0
																																					// &&
																																					// $.urlParam("sgval1")
																																					// != 0
																																					// &&
																																					// $.urlParam("sgval1").replace("_",
																																					// " ")
																																					// !=
																																					// 'RVS'
																																					// &&
																																					// 'DVS'
																																					// &&
																																					// 'SVS'
																																					// &&
																																					// 'Health
																																					// facility'){
																																					// $http.get('getImages?granularityId='+
																																					// $.urlParam("sgky1")).success(

																																					if (shareSgky1
																																							&& shareSgval1
																																							&& shareSgval1
																																									.replace(
																																											"_",
																																											" ") != 'RVS'
																																							&& 'DVS'
																																							&& 'SVS'
																																							&& 'Health facility') {
																																						$http
																																								.get(
																																										'getImages?granularityId='
																																												+ shareSgky1)
																																								.success(

																																										function(
																																												data) {
																																											var imagePaths = data;
																																											if (imagePaths) {
																																												var arrOfPaths = imagePaths
																																														.split(',');
																																												$scope.selectedImages = arrOfPaths;
																																												$scope.isImagesVisible = true;
																																											} else {
																																												$scope.isImagesVisible = false;
																																											}
																																										});
																																					}

																																					if ($scope.selectedGranularitySpider) {

																																						$scope.selectedGranularity = null;
																																						var url = 'spiderData';
																																						var query = '';
																																						// alert("first
																																						// time
																																						// id:
																																						// "+$scope.selectedTimeperiod.value);
																																						// alert("second
																																						// time
																																						// id:
																																						// "+$scope.secondSelectedTimeperiod.value);
																																						if ($scope.selectedSubSector)
																																							query += "sector="
																																									+ $scope.selectedSubSector.key
																																									+ "&";
																																						if ($scope.selectedTimeperiod)
																																							query += "timeperiodId="
																																									+ $scope.selectedTimeperiod.key
																																									+ "&";

																																						if ($scope.selectedGranularitySpider)
																																							query += "areaId="
																																									+ $scope.selectedGranularitySpider.key
																																									+ "&";
																																						if (shareSst) {
																																							query += "secondTimeperiodId="
																																									+ shareSst
																																									+ "&";
																																						} else if ($scope.secondSelectedTimeperiod) {
																																							query += "secondTimeperiodId="
																																									+ $scope.secondSelectedTimeperiod.key
																																									+ "&";
																																						}

																																						if (query != '')
																																							url += "?"
																																									+ query
																																											.trim("&");

																																						$http
																																								.get(
																																										url)
																																								.success(
																																										function(
																																												data) {
																																											$scope.isUrl = true;
																																											$scope.spiderdata = data;
																																										});

																																					} else if ($scope.selectedGranularity) {

																																						var url = 'spiderData';
																																						var query = '';
																																						// alert("first
																																						// time
																																						// id:
																																						// "+$scope.selectedTimeperiod.value);
																																						// alert("second
																																						// time
																																						// id:
																																						// "+$scope.secondSelectedTimeperiod.value);
																																						if ($scope.selectedSubSector)
																																							query += "sector="
																																									+ $scope.selectedSubSector.key
																																									+ "&";
																																						if ($scope.selectedTimeperiod)
																																							query += "timeperiodId="
																																									+ $scope.selectedTimeperiod.key
																																									+ "&";

																																						if ($scope.selectedGranularity)
																																							query += "areaId="
																																									+ $scope.selectedGranularity.key
																																									+ "&";
																																						if (shareSst) {
																																							query += "secondTimeperiodId="
																																									+ shareSst
																																									+ "&";
																																						} else if ($scope.secondSelectedTimeperiod) {
																																							query += "secondTimeperiodId="
																																									+ $scope.secondSelectedTimeperiod.key
																																									+ "&";
																																						}

																																						if (query != '')
																																							url += "?"
																																									+ query
																																											.trim("&");

																																						$http
																																								.get(
																																										url)
																																								.success(
																																										function(
																																												data) {
																																											$scope.isUrl = true;
																																											$scope.spiderdata = data;
																																										});

																																					}

																																				});
																															}
																															// $scope.getspiderdata();
																														});

																									}
																								});
																			});
														}
													});

								}
							}

							// $scope.getutdata();
						});

	};

	$scope.getSelectedGranularity = function() {
		$http.get(
				'selectedGranularity?sectorName=' + $scope.selectedSector.value
						+ '&parentAreaName=' + $scope.parentArea).success(
				function(data) {
					$scope.selectedGranularity = data;

					$scope.map.markers = "[]";
					$scope.getGoogleMapData();
					$scope.getspiderdata();
				});
	};

	$scope.getSubSectors = function() {
		$http.get('subSectors?sector=' + $scope.selectedSector.key).success(
				function(data) {
					$scope.subSectors = data;
					$scope.selectedSubSector = $scope.subSectors[0];
				});
	};

	$scope.getindicators = function() {
		$http.get('indicators?sector=' + $scope.selectedSector.key).success(
				function(data) {
					$scope.indicators = data;
					if ($scope.indicators) {
						$scope.selectedIndicator = $scope.indicators[0];
						$scope.getSelectedGranularity();
					}
				});
		// $scope.getutdata();
	};

	$scope.getGoogleMapData = function() {
		var url = 'googleMapData';
		var query = '';
		if ($scope.selectedSector)
			query += "sectorName=" + $scope.selectedSector.value + "&";
		if ($scope.selectedIndicator)
			query += "indicatorId=" + $scope.selectedIndicator.description
					+ "&";
		if ($scope.selectedTimeperiod)
			query += "timeperiodId=" + $scope.selectedTimeperiod.key + "&";
		if ($scope.selectedGranularity)
			query += "selectedGranularity=" + $scope.selectedGranularity.key
					+ "&";
		if ($scope.parentArea)
			query += "parentAreaName=" + $scope.parentArea + "&";
		if (query != '')
			url += "?" + query.trim("&");

		$http.get(url).success(function(data) {
			$scope.googleMapData = data;
			$scope.map.markers = data;
		});
	};
//	$scope.getutdata = function() {
//		var url = 'data';
//		var query = '';
//		if ($scope.selectedIndicator)
//			query += "indicatorId=" + $scope.selectedIndicator.description
//					+ "&";
//		if ($scope.selectedTimeperiod)
//			query += "timeperiodId=" + $scope.selectedTimeperiod.key + "&";
//		if ($scope.selectedGranularity)
//			query += "areaId=" + $scope.selectedGranularity.key + "&";
//
//		if (query != '')
//			url += "?" + query.trim("&");
//		$http.get(url).success(function(data) {
//			$scope.utdata = data;
//			$scope.legends = data.legends;
//			$scope.topPerformers = data.topPerformers;
//			$scope.bottomPerformers = data.bottomPerformers;
//		});
//	};

	$scope.getspiderdata = function(subSector) {
		var url = 'spiderData';
		var query = '';
		// alert("first time id: "+$scope.selectedTimeperiod.value);
		// alert("second time id: "+$scope.secondSelectedTimeperiod.value);
		if (subSector != null) {
			query += "sector=" + subSector.key + "&";
			$scope.selectedSubSector = subSector;
		} else if ($scope.selectedSubSector)
			query += "sector=" + $scope.selectedSubSector.key + "&";
		if ($scope.selectedTimeperiod)
			query += "timeperiodId=" + $scope.selectedTimeperiod.key + "&";
		if ($scope.isUrl == true && shareSgky && shareSgval) {

			$scope.selectedGranularity = new ValueObject(shareSgky, shareSgval
					.replace("_", " "));
			$scope.selectedGranularitySpider = null;
			query += "areaId=" + $scope.selectedGranularity.key + "&";

		} else if ($scope.isUrl == true && shareSgky1 && shareSgval1
				&& shareSgval1.replace("_", " ") != 'RVS' && 'DVS' && 'SVS'
				&& 'Health facility') {

			$scope.selectedGranularitySpider = new ValueObject(shareSgky1,
					shareSgval1.replace("_", " "));
			$scope.selectedGranularity = null;
			query += "areaId=" + $scope.selectedGranularitySpider.key + "&";

		} else if ($scope.selectedGranularitySpider) {
			query += "areaId=" + $scope.selectedGranularitySpider.key + "&";
		} else if ($scope.selectedGranularity) {
			query += "areaId=" + $scope.selectedGranularity.key + "&";
		}
		if ($scope.secondSelectedTimeperiod)
			query += "secondTimeperiodId="
					+ $scope.secondSelectedTimeperiod.key + "&";

		if (query != '')
			url += "?" + query.trim("&");

		$http.get(url).success(function(data) {
			$scope.spiderdata = data;
		});
	};

	$scope.complete = function() {
		cfpLoadingBar.complete();
	};
	// fake the initial load so first time users can see the bar right away:
	$scope.start();
	$scope.fakeIntro = true;
	$timeout(function() {
		$scope.complete();
		$scope.fakeIntro = false;
	}, 30000);
	$scope.style = function() {

	};

}
