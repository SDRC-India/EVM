// Angular app creation
var app = angular.module("esamapp", [ 'chieffancypants.loadingBar',
		'ngAnimate', 'google-maps' ]);

app.config([ "$httpProvider", function($httpProvider) {
	$httpProvider.interceptors.push('apis');
} ]);
app.config(function(cfpLoadingBarProvider) {
	// true is the default, but I left this here as an example:
	cfpLoadingBarProvider.includeSpinner = true;
});
app.factory('apis', function() {
	return {
		request : function(config) {

			// need more controlling when there is more than 1 domain involved
			config.url = rootURL + "/api/" + config.url;
			return config;
		}
	};

});
app.currentStateUrl = function(scope) {
	var scope = angular.element($("#containerId")).scope();
	// return "http://" + location.hostname
	// + (location.port ? ":" + location.port : "") + "/evm/dashboard?"

	var st = scope.selectedTimeperiod != null ? scope.selectedTimeperiod.key
			: "";

	var pa = scope.parentArea != null ? scope.parentArea : "";

	var ss = scope.selectedSector != null ? scope.selectedSector.key : "";
	var sss = scope.selectedSubSector != null ? scope.selectedSubSector.key
			: "";
	var si = scope.selectedIndicator != null ? scope.selectedIndicator.key : "";
	var sgky = scope.selectedGranularity != null ? scope.selectedGranularity.key
			: "";
	var sgval = scope.selectedGranularity != null ? scope.selectedGranularity.value
			.replace(" ", "_")
			: "";
	var sgky1 = scope.selectedGranularitySpider != null ? scope.selectedGranularitySpider.key
			: "";
	var sgval1 = scope.selectedGranularitySpider != null ? scope.selectedGranularitySpider.value
			.replace(" ", "_")
			: "";
	var sst = scope.secondSelectedTimeperiod != null ? scope.secondSelectedTimeperiod.key
			: "";

	return rootURL + "/dashboard?" + "st=" + st + "%26pa=" + pa + "%26ss=" + ss
			+ "%26sss=" + sss + "%26si=" + si + "%26sgky=" + sgky + "%26sgval="
			+ sgval + "%26sgky1=" + sgky1 + "%26sgval1=" + sgval1 + "%26sst="
			+ sst;
};

app.currentStateMailUrl = function(scope) {
	var scope = angular.element($("#containerId")).scope();

	var st = scope.selectedTimeperiod != null ? scope.selectedTimeperiod.key
			: "";
	
	var pa = scope.parentArea != null ? scope.parentArea : "";
	
	var ss = scope.selectedSector != null ? scope.selectedSector.key : "";
	var sss = scope.selectedSubSector != null ? scope.selectedSubSector.key
			: "";
	var si = scope.selectedIndicator != null ? scope.selectedIndicator.key : "";
	var sgky = scope.selectedGranularity != null ? scope.selectedGranularity.key
			: "";
	var sgval = scope.selectedGranularity != null ? scope.selectedGranularity.value
			.replace(" ", "_")
			: "";
	var sgky1 = scope.selectedGranularitySpider != null ? scope.selectedGranularitySpider.key
			: "";
	var sgval1 = scope.selectedGranularitySpider != null ? scope.selectedGranularitySpider.value
			.replace(" ", "_")
			: "";
	var sst = scope.secondSelectedTimeperiod != null ? scope.secondSelectedTimeperiod.key
			: "";

	return rootURL + "/dashboard?" + "st=" + st + "&pa=" + pa + "&ss=" + ss + "&sss=" + sss
			+ "&si=" + si + "&sgky=" + sgky + "&sgval=" + sgval + "&sgky1="
			+ sgky1 + "&sgval1=" + sgval1 + "&sst=" + sst;
};

app
		.directive(
				"samikshaMap",
				function($window) {
					function link(scope, el) {
						var primary_url = "";
						var el = el[0];

						$(".backbtn")
								.click(
										function() {
											scope.shouldDrilldown = true;
											scope.selectedGranularity = new ValueObject(
													"IND021", "odisha_map");
											scope.getutdata();
											scope.mapSetup(primary_url);
											$(".backbtn").toggleClass("hidden");
										});
						function onmousemove(d) {
							d3
									.select(".map_popover")
									.style("display", "block")
									.style("left", (d3.event.pageX) - 80 + "px")// TODO:
									// make
									// it
									// dynamic
									// so
									// that position would be
									// according to the text
									// length
									.style("top", (d3.event.pageY - 70) + "px")
									.style("opacity", "1");

						}

						function onover(d) {
							d3.selectAll(".activehover").classed("activehover",
									false);
							var rank;
							d3.select(".map_popover_content").html(
									"<strong>District:</strong> <span style='color:red'>"
											+ d.properties.NAME1_ + "</span>");

							if (d.properties.utdata && d.properties.utdata.rank) {
								rank = d.properties.utdata.rank;
							} else {
								rank = "Not Available";
							}

							d3.select(".map_popover_close").html(
									"<strong>Rank:</strong> <span style='color:red'>"
											+ rank + "</span>");

							d3.select(this.parentNode.appendChild(this))
									.classed("activehover", true);
						}

						function drilldown(d) {
							if (d.properties.NAME1_ && scope.shouldDrilldown) {
								scope.shouldDrilldown = false;
								scope.closeViz();
								d3.select(".map_popover").style("display",
										"none");
								scope.selectedGranularity = new ValueObject(
										d.properties.ID_, d.properties.NAME1_);
								var url = "resources/geomaps/"
										+ d.properties.NAME1_ + ".json";
								scope.mapSetup(url, "district");
								scope.getutdata();
								$(".backbtn").toggleClass("hidden");
							}
						}

						scope.mapSetup = function(url, id) {
							d3.select(el).selectAll("*").remove();
							var w = scope.getWindowDimensions();
							var feature = "";
							var width = w.w, height = w.h - 190, centered;
							var projection = d3.geo.mercator().scale(1);
							var path = d3.geo.path().projection(projection);

							var svg = d3.select(el).append("svg").attr("id",
									"mapsvg").attr("width", width).attr(
									"height", height);
							svg.append("rect").attr({
								style : "fill:none;pointer-events:all;"
							}).attr("width", width).attr("height", height).on(
									"click", clicked).on(
									"mouseover",
									function() {
										d3.select(".map_popover").style(
												"display", "none");
										d3.selectAll(".activehover").classed(
												"activehover", false);
									});

							var g = svg.append("g").attr("id", "mapg");

							d3
									.json(
											url,
											function(error, us) {
												feature = topojson.feature(us,
														us.objects.layer1);

												var b = path.bounds(feature), s = 0.95 / Math
														.max(
																(b[1][0] - b[0][0])
																		/ width,
																(b[1][1] - b[0][1])
																		/ height);
												projection.scale(s);
												b = d3.geo.bounds(feature);
												projection
														.center([
																(b[1][0] + b[0][0]) / 2,
																(b[1][1] + b[0][1]) / 2 ]);
												projection
														.translate([ width / 2,
																height / 2 ]);

												g
														.append("g")
														.attr("id", "districts")
														.selectAll("path")
														.data(
																topojson
																		.feature(
																				us,
																				us.objects.layer1).features)
														.enter().append("path")
														.attr("d", path).on(
																'mouseover',
																onover).on(
																"mousedown",
																clicked).on(
																"dblclick",
																drilldown);

												g.on("mousemove", onmousemove);

											});
							function clicked(d) {
								var x, y, k;
								if (d && centered !== d) {
									var centroid = path.centroid(d);
									x = centroid[0];
									y = centroid[1];
									k = 2.5;
									centered = d;
								} else {
									x = width / 2;
									y = height / 2;
									k = 1;
									centered = null;
								}

								g.selectAll("path").classed("active",
										centered && function(d) {
											return d === centered;
										});

								g.transition().duration(750).attr(
										"transform",
										"translate(" + width / 2 + "," + height
												/ 2 + ")scale(" + k
												+ ")translate("
												+ (-x - width * 3 / 100) + ","
												+ -y + ")").style(
										"stroke-width", 1.5 / k + "px");
								scope.showViz(d);
							}
						};
						scope
								.$watch(
										"utdata",
										function() {
											d3
													.select("#mapsvg")
													.selectAll("path")
													.attr(
															"class",
															function(d) {
																if (!(scope.utdata && scope.utdata.dataCollection))
																	return;
																for (var i = 0; i < scope.utdata.dataCollection.length; i++) {
																	if (d.properties
																			&& d.properties.ID_ == scope.utdata.dataCollection[i].areaCode) {
																		d.properties.utdata = scope.utdata.dataCollection[i];
																		return scope.utdata.dataCollection[i].cssClass;
																	} else {
																		if (d.properties) {
																			d.properties.utdata = null;
																		}
																	}
																}
															});

										}, true);
						scope.$watch(scope.getWindowDimensions, function(
								newValue, oldValue) {
							scope.svgHeight = (newValue.h - 190);
							scope.svgWidth = (newValue.w);
							scope.style = function() {
								return {
									'height' : (newValue.h - 190) + 'px',
									'width' : (newValue.w) + 'px'
								};
							};
							w = scope.getWindowDimensions();
							width = w.w, height = w.h;
							d3.select("#mapsvg").attr({
								width : width,
								height : height - 190
							});
							d3.select("#mapsvg").selectAll("rect").attr({
								width : width,
								height : height - 190
							});

						}, true);

						primary_url = "resources/geomaps/Bihar.json";

						// if ($window.location.search
						// && $window.location.search.trim("?").split("&")[4]
						// .substring($window.location.search
						// .trim("?").split("&")[4]
						// .indexOf("=") + 1)) {
						// primary_url = "resources/geomaps/"
						// + $window.location.search.trim("?").split(
						// "&")[4]
						// .substring($window.location.search
						// .trim("?").split("&")[4]
						// .indexOf("=") + 1)
						// + ".json";
						// }
						scope.mapSetup(primary_url);

					}
					return {
						link : link,
						restrict : "E"
					};
				});

app
		.directive(
				"samikshaLine",
				function($window) {
					function link(scope, el) {
						var el = el[0];

						// Render graph based on 'data'
						scope.renderDataSeries = function(data) {
							// remove all
							d3.select("#trendsvg").remove();
							var margin = {
								top : 20,
								right : 55,
								bottom : 30,
								left : 40
							}, width = $(document).outerWidth() * 38 / 100
									- margin.left - margin.right, height = 230
									- margin.top - margin.bottom;
							var x = d3.scale.ordinal().rangeRoundBands(
									[ 0, width ], .1);

							var y = d3.scale.linear().rangeRound([ height, 0 ]);

							var xAxis = d3.svg.axis().scale(x).orient("bottom");

							var yAxis = d3.svg.axis().scale(y).orient("left");

							var line = d3.svg.line().interpolate("cardinal").x(
									function(d) {
										return x(d.label) + x.rangeBand() / 2;
									}).y(function(d) {
								return y(d.value);
							});

							var color = d3.scale.ordinal().range(
									[ "#00CC33", "#001c9c", "#101b4d",
											"#475003", "#9c8305" ]);

							var svg = d3.select(el).append("svg").attr("id",
									"trendsvg").attr("width",
									width + margin.left + margin.right).attr(
									"height",
									height + margin.top + margin.bottom)
									.append("g").attr(
											"transform",
											"translate(" + margin.left + ","
													+ margin.top + ")");
							var labelVar = 'key';
							var varNames = [ 'value' ];
							color.domain(varNames);
							var seriesData = varNames.map(function(name) {
								return {
									name : name,
									values : data.map(function(d) {
										return {
											name : name,
											label : d[labelVar],
											value : +d[name]
										};
									})
								};
							});
							x.domain(data.map(function(d) {
								return d.key;
							}));
							y.domain([ d3.min(seriesData, function(c) {
								return d3.min(c.values, function(d) {
									return d.value;
								});
							}), d3.max(seriesData, function(c) {
								return d3.max(c.values, function(d) {
									return d.value;
								});
							}) ]);

							svg.append("g").attr("class", "x axis").attr(
									"transform", "translate(0," + height + ")")
									.call(xAxis);
							svg.append("g").attr("class", "y axis").call(yAxis)
									.append("text").attr("transform",
											"rotate(-90)").attr("y", 1).attr(
											"dy", ".71em").style("text-anchor",
											"end").text(
											scope.selectedIndicator.value);

							var series = svg.selectAll(".series").data(
									seriesData).enter().append("g").attr(
									"class", "series");
							series.append("path").attr("class", "line").attr(
									"d", function(d) {
										return line(d.values);
									}).style("stroke", function(d) {
								return color(d.name);
							}).style("stroke-width", "2px").style("fill",
									"none");

							series.selectAll(".point").data(function(d) {
								return d.values;
							}).enter().append("circle").attr("class", "point")
									.attr("cx", function(d) {
										return x(d.label) + x.rangeBand() / 2;
									}).attr("cy", function(d) {
										return y(d.value);
									}).attr("r", "3px").style("fill",
											function(d) {
												return color(d.name);
											}).style("stroke", "grey").style(
											"stroke-width", "2px").on(
											"mouseover", function(d) {
												showPopover.call(this, d);
											}).on("mouseout", function(d) {
										removePopovers();
									});

							var legend = svg.selectAll(".legend").data(
									varNames.slice().reverse()).enter().append(
									"g").attr("class", "legend").attr(
									"transform", function(d, i) {
										return "translate(55," + i * 20 + ")";
									});
							legend.append("rect").attr("x", width - 10).attr(
									"width", 10).attr("height", 10).style(
									"fill", color).style("stroke", "grey");
							legend.append("text").attr("x", width - 12).attr(
									"y", 6).attr("dy", ".35em").style(
									"text-anchor", "end").text(function(d) {
								return d;
							});
							function removePopovers() {
								$('.popover').each(function() {
									$(this).remove();
								});
							}
							function showPopover(d) {
								$(this).popover(
										{
											title : d.name,
											placement : 'auto top',
											container : 'body',
											trigger : 'manual',
											html : true,
											content : function() {
												return "Time: " + d.label
														+ "<br/>Value: "
														+ d.value;
											}
										});
								$(this).popover('show');
							}
						};

						scope
								.$watch(
										'selectedArea',
										function() {
											if (scope.selectedArea.properties
													&& scope.selectedArea.properties.utdata
													&& scope.selectedArea.properties.utdata.dataSeries) {
												scope
														.renderDataSeries(scope.selectedArea.properties.utdata.dataSeries);
											} else {
												d3.select("#trendsvg").remove();
											}
										}, true);
					}
					return {
						link : link,
						restrict : "E"
					};
				});

app
		.directive(
				"sdrcSpider",
				function($window) {
					function link(scope, el) {
						var el = el[0];

						var RadarChart = {
							draw : function(el, d, options) {
								var w = scope.getWindowDimensions();
								var width = (w.w * 30) / 100;
								var cfg = {
									radius : 5,
									w : width,
									h : width,
									factor : 1,
									factorLegend : .85,
									levels : 3,
									maxValue : 0,
									radians : 2 * Math.PI,
									opacityArea : 0.5,
									ToRight : 5,
									TranslateX : 80,
									TranslateY : 30,
									ExtraWidthX : 100,
									ExtraWidthY : 100,
									color : d3.scale.category10()
								};

								if ('undefined' !== typeof options) {
									for ( var i in options) {
										if ('undefined' !== typeof options[i]) {
											cfg[i] = options[i];
										}
									}
								}
								// cfg.maxValue = Math.max(cfg.maxValue,
								// d3.max(d,
								// function(i) {
								// return d3.max(i.map(function(o) {
								// return o.value;
								// }));
								// }));
								cfg.maxValue = 100;
								var allAxis = (d[0].map(function(i, j) {
									return i.axis;
								}));
								var mouseOutcolor = [ "#8FBBD9", "#FFBF87" ];
								var hoverColor = [ "#1F77B4", "#FF8C26" ];

								var total = allAxis.length;
								var radius = cfg.factor
										* Math.min(cfg.w / 2, cfg.h / 2);
								var Format = d3.format('%');
								d3.select(el).select("svg").remove();

								var g = d3.select(el).append("svg").attr(
										"width", cfg.w + cfg.ExtraWidthX).attr(
										"height", cfg.h + cfg.ExtraWidthY)
										.append("g").attr(
												"transform",
												"translate(" + cfg.TranslateX
														+ "," + cfg.TranslateY
														+ ")");
								;

								var tooltip;

								// Circular segments
								for (var j = 0; j <= cfg.levels - 1; j++) {
									var levelFactor = cfg.factor * radius
											* ((j + 1) / cfg.levels);
									g
											.selectAll(".levels")
											.data(allAxis)
											.enter()
											.append("svg:line")
											.attr(
													"x1",
													function(d, i) {
														return levelFactor
																* (1 - cfg.factor
																		* Math
																				.sin(i
																						* cfg.radians
																						/ total));
													})
											.attr(
													"y1",
													function(d, i) {
														return levelFactor
																* (1 - cfg.factor
																		* Math
																				.cos(i
																						* cfg.radians
																						/ total));
													})
											.attr(
													"x2",
													function(d, i) {
														return levelFactor
																* (1 - cfg.factor
																		* Math
																				.sin((i + 1)
																						* cfg.radians
																						/ total));
													})
											.attr(
													"y2",
													function(d, i) {
														return levelFactor
																* (1 - cfg.factor
																		* Math
																				.cos((i + 1)
																						* cfg.radians
																						/ total));
													})
											.attr("class", "line")
											.style("stroke",
													j == 7 ? "#8FBBD9" : "grey")
											.style("stroke-opacity", "0.75")
											.style("stroke-width",
													j == 7 ? "2px" : "0.3px")
											.style("stroke-dasharray",
													j == 7 ? 5 : 0)
											.attr(
													"transform",
													"translate("
															+ (cfg.w / 2 - levelFactor)
															+ ", "
															+ (cfg.h / 2 - levelFactor)
															+ ")");
								}

								// Text indicating at what % each level is
								for (var j = 0; j < cfg.levels; j++) {
									var levelFactor = cfg.factor * radius
											* ((j + 1) / cfg.levels);
									g
											.selectAll(".levels")
											.data([ 1 ])
											// dummy data
											.enter()
											.append("svg:text")
											.attr(
													"x",
													function(d) {
														return levelFactor
																* (1 - cfg.factor
																		* Math
																				.sin(0));
													})
											.attr(
													"y",
													function(d) {
														return levelFactor
																* (1 - cfg.factor
																		* Math
																				.cos(0));
													})
											.attr("class", "legend")
											.style("font-family", "sans-serif")
											.style("font-size", "10px")
											.attr(
													"transform",
													"translate("
															+ (cfg.w
																	/ 2
																	- levelFactor + cfg.ToRight)
															+ ", "
															+ (cfg.h / 2 - levelFactor)
															+ ")").attr("fill",
													"#737373").text(
													Math.round((j + 1) * 100
															/ cfg.levels));
								}
								series = 0;

								var axis = g.selectAll(".axis").data(allAxis)
										.enter().append("g").attr("class",
												"axis");

								axis
										.append("line")
										.attr("x1", cfg.w / 2)
										.attr("y1", cfg.h / 2)
										.attr(
												"x2",
												function(d, i) {
													return cfg.w
															/ 2
															* (1 - cfg.factor
																	* Math
																			.sin(i
																					* cfg.radians
																					/ total));
												})
										.attr(
												"y2",
												function(d, i) {
													return cfg.h
															/ 2
															* (1 - cfg.factor
																	* Math
																			.cos(i
																					* cfg.radians
																					/ total));
												}).attr("class", "line").style(
												"stroke", "grey").style(
												"stroke-width", "1px");

								axis
										.append("text")
										.attr("class", "legend")
										.text(function(d) {
											return d;
										})
										.style("font-family", "sans-serif")
										.style("font-size", "10px")
										.attr("text-anchor", "start")
										.attr("dy", "1.5em")
										.attr("transform", function(d, i) {
											return "translate(0, -10)";
										})

										.attr(
												"x",
												function(d, i) {
													return cfg.w
															/ 2
															* (1 - cfg.factorLegend
																	* Math
																			.sin(i
																					* cfg.radians
																					/ total))
															- 60
															* Math
																	.sin(i
																			* cfg.radians
																			/ total);
												})
										.attr(
												"y",
												function(d, i) {
													return cfg.h
															/ 2
															* (1 - Math
																	.cos(i
																			* cfg.radians
																			/ total))
															- 20
															* Math
																	.cos(i
																			* cfg.radians
																			/ total);
												});

								d
										.forEach(function(y, x) {
											dataValues = [];
											g
													.selectAll(".nodes")
													.data(
															y,
															function(j, i) {
																dataValues
																		.push([
																				cfg.w
																						/ 2
																						* (1 - (parseFloat(Math
																								.max(
																										j.value,
																										0)) / cfg.maxValue)
																								* cfg.factor
																								* Math
																										.sin(i
																												* cfg.radians
																												/ total)),
																				cfg.h
																						/ 2
																						* (1 - (parseFloat(Math
																								.max(
																										j.value,
																										0)) / cfg.maxValue)
																								* cfg.factor
																								* Math
																										.cos(i
																												* cfg.radians
																												/ total)) ]);
															});
											dataValues.push(dataValues[0]);
											g
													.selectAll(".area")
													.data([ dataValues ])
													.enter()
													.append("polygon")
													.attr(
															"class",
															"radar-chart-serie"
																	+ series)
													.style("stroke-width",
															"2px")
													.style("stroke",
															cfg.color(series))
													.attr(
															"points",
															function(d) {
																var str = "";
																for (var pti = 0; pti < d.length; pti++) {
																	str = str
																			+ d[pti][0]
																			+ ","
																			+ d[pti][1]
																			+ " ";
																}
																return str;
															})
													.style(
															"fill",
															function(j, i) {
																return cfg
																		.color(series);
															})
													.style("fill-opacity",
															cfg.opacityArea)
													.on(
															'mouseover',
															function(d) {
																z = "polygon."
																		+ d3
																				.select(
																						this)
																				.attr(
																						"class");
																g
																		.selectAll(
																				"polygon")
																		.transition(
																				200)
																		.style(
																				"fill-opacity",
																				0.1);
																g
																		.selectAll(
																				z)
																		.transition(
																				200)
																		.style(
																				"fill-opacity",
																				.7);
															})
													.on(
															'mouseout',
															function() {
																g
																		.selectAll(
																				"polygon")
																		.transition(
																				200)
																		.style(
																				"fill-opacity",
																				cfg.opacityArea);
															});
											series++;
										});
								series = 0;

								d
										.forEach(function(y, x) {
											g
													.selectAll(".nodes")
													.data(y)
													.enter()
													.append("svg:circle")
													.attr(
															"class",
															"radar-chart-serie"
																	+ series)
													.attr('r', cfg.radius)
													.attr(
															"alt",
															function(j) {
																return Math
																		.max(
																				j.value,
																				0);
															})
													.attr(
															"cx",
															function(j, i) {
																dataValues
																		.push([
																				cfg.w
																						/ 2
																						* (1 - (parseFloat(Math
																								.max(
																										j.value,
																										0)) / cfg.maxValue)
																								* cfg.factor
																								* Math
																										.sin(i
																												* cfg.radians
																												/ total)),
																				cfg.h
																						/ 2
																						* (1 - (parseFloat(Math
																								.max(
																										j.value,
																										0)) / cfg.maxValue)
																								* cfg.factor
																								* Math
																										.cos(i
																												* cfg.radians
																												/ total)) ]);
																return cfg.w
																		/ 2
																		* (1 - (Math
																				.max(
																						j.value,
																						0) / cfg.maxValue)
																				* cfg.factor
																				* Math
																						.sin(i
																								* cfg.radians
																								/ total));
															})
													.attr(
															"cy",
															function(j, i) {
																return cfg.h
																		/ 2
																		* (1 - (Math
																				.max(
																						j.value,
																						0) / cfg.maxValue)
																				* cfg.factor
																				* Math
																						.cos(i
																								* cfg.radians
																								/ total));
															})
													.attr("data-id",
															function(j) {
																return j.axis;
															})
													.style("fill",
															cfg.color(series))
													.style("fill-opacity", .9)
													.on(
															'mouseover',
															function(d) {
																showPopover
																		.call(
																				this,
																				d);
																d3
																		.select(
																				this)
																		.attr(
																				'fill',
																				function(
																						d,
																						i) {
																					return hoverColor[i];
																				});
															},
															function(d) {
																newX = parseFloat(d3
																		.select(
																				this)
																		.attr(
																				'cx')) - 10;
																newY = parseFloat(d3
																		.select(
																				this)
																		.attr(
																				'cy')) - 5;

																z = "polygon."
																		+ d3
																				.select(
																						this)
																				.attr(
																						"class");
																g
																		.selectAll(
																				"polygon")
																		.transition(
																				200)
																		.style(
																				"fill-opacity",
																				0.1);
																g
																		.selectAll(
																				z)
																		.transition(
																				200)
																		.style(
																				"fill-opacity",
																				.7);
															})
													.on(
															'mouseout',
															function(d) {
																removePopovers();
																d3
																		.select(
																				this)
																		.attr(
																				'fill',
																				function(
																						d,
																						i) {
																					return color[i];
																				});
															});

											series++;
										});

								function removePopovers() {
									$('.popover').each(function() {
										$(this).remove();
									});
								}
								function showPopover(d) {
									$(this).popover({
										title : '',
										placement : 'auto top',
										container : 'body',
										trigger : 'manual',
										html : true,
										content : function() {
											return "Score : " + d.value + "%";
										}
									});
									$(this).popover('show');
								}
							}
						};

						var w = scope.getWindowDimensions();
						var width = (w.w * 30) / 100;
						// Options for the Radar chart, other than default
						var mycfg = {
							w : width,
							h : width,
							maxValue : 0.6,
							levels : 10,
							ExtraWidthX : 300
						};

						scope.$watch('spiderdata', function() {
							// var cdata=[[{"axis":"E2 :
							// Temperature","value":"43.0"},{"axis":"E3 :
							// Storage Capacity","value":"50.0"},{"axis":"E4 :
							// Storage Capacity","value":"60.0"},{"axis":"E5 :
							// Storage Capacity","value":"70.0"},{"axis":"E6 :
							// Storage Capacity","value":"80.0"},{"axis":"E7 :
							// Storage Capacity","value":"90.0"}],[{"axis":"E2 :
							// Temperature","value":"70.0"},{"axis":"E3 :
							// Storage Capacity","value":"90.0"},{"axis":"E4 :
							// Storage Capacity","value":"70.0"},{"axis":"E5 :
							// Storage Capacity","value":"90.0"},{"axis":"E6 :
							// Storage Capacity","value":"97.0"},{"axis":"E7 :
							// Storage Capacity","value":"100.0"}]];
							// RadarChart.draw(el, cdata, mycfg);
							if (scope.spiderdata
									&& scope.spiderdata.dataCollection) {
								var d = scope.spiderdata.dataCollection;
								RadarChart.draw(el, d, mycfg);
							}
						}, true);

					}
					return {
						link : link,
						restrict : "E"
					};

				});

app.directive("sdrcBarChart",
		function($window) {
			function link(scope, el) {
				var el = el[0];
				draw = function(el, data) {

					d3.select("#columnbarChart").remove();
					var n = 2, // number of layers
					m = 10, // number of samples per layer
					stack = d3.layout.stack(), layers = stack(data),

					yGroupMax = d3.max(layers, function(layer) {
						return d3.max(layer, function(d) {

							return d.value;
						});
					}), yStackMax = d3.max(layers, function(layer) {
						return d3.max(layer, function(d) {
							return d.y0 + d.value;
						});
					});
					var w = scope.getWindowDimensions();
					var relativewidth = (w.w * 30) / 100;
					var margin = {
						top : 20,
						right : 55,
						bottom : 40, // // bottom height
						left : 40
					}, width = $(document).outerWidth() * 38 / 100
							- margin.left - margin.right, height = 400 // //
							// height
							- margin.top - margin.bottom;

					var x = d3.scale.ordinal().domain(data[0].map(function(d) {
						return d.axis;
					})).rangeRoundBands([ 0, width ], .1);
					;

					var y = d3.scale.linear().domain([ 0, 100 ]).range(
							[ height, 0 ]);

					// var color = d3.scale.linear().domain([ 0, n - 1 ])
					// .range([ "#8FBBD9", "#FFBF87" ]);
					// var color = [ "#8FBBD9", "#FFBF87","#FF8C26" ];
					var color = [ "#1a9641", "#fdae61", "#d7191c" ];

					// var hoverColor = d3.scale.linear().domain([ 0, n - 1 ])
					// .range([ "#1F77B4", "#FF8C26" ]);
					var hoverColor = [ "#017A27", "#fc8835", "#b7191c" ];

					var formatTick = function(d) {
						return d.split(":")[0];
					};
					var xAxis = d3.svg.axis().scale(x).orient("bottom")
							.tickFormat(formatTick);

					var svg = d3.select(el).append("svg").attr("id",
							"columnbarChart").attr("width",
							width + margin.left + margin.right).attr("height",
							height + margin.top + margin.bottom).append("g")
							.attr(
									"transform",
									"translate(" + margin.left + ","
											+ margin.top + ")");

					var layer = svg.selectAll(".layer").data(layers).enter()
							.append("g").attr("class", "layer").style("fill",
									function(d, i) {
										return color[i];
									}).attr("id", function(d, i) {
								return i;
							});
					// var layer = svg.selectAll(".layer").data(layers)
					// .enter().append("g").attr("class", "layer").style("fill",
					// function(d, i) {
					// if(80 <= d[i].value ){
					// return color[0];}
					// else if(61 <= d[i].value && d[i].value <= 79 ){
					// return color[1];}
					// else if(d[i].value <= 60 ){
					// return color[2];}
					// }).attr("id", function(d,i) {
					// return i;
					// });

					var rect = layer.selectAll("rect").data(function(d) {
						return d;
					}).enter().append("rect").attr("x", function(d) {
						return x(d.axis);
					}).attr("y", height).attr("width", x.rangeBand()).attr(
							"height", 0).style("fill", function(d, i) {
						if (80 <= d.value) {
							return color[0];
						} else if (61 <= d.value && d.value <= 79) {
							return color[1];
						} else if (d.value <= 60) {
							return color[2];
						}
					}).on("mouseover", function(d) {
						showPopover.call(this, d);
						// d3.select(this).attr('fill', function(d, i) {
						// return hoverColor[$(this).parent().attr('id')];
						// });
						d3.select(this).style('fill', function(d, i) {

							if (80 <= d.value) {
								return hoverColor[0];
							} else if (61 <= d.value && d.value <= 79) {
								return hoverColor[1];
							} else if (d.value <= 60) {
								return hoverColor[2];
							}
						});

					}).on("mouseout", function(d) {
						removePopovers();
						// d3.select(this).attr('fill', function(d, i) {
						// return color[$(this).parent().attr('id')];
						// });
						d3.select(this).style('fill', function(d, i) {
							if (80 <= d.value) {
								return color[0];
							} else if (61 <= d.value && d.value <= 79) {
								return color[1];
							} else if (d.value <= 60) {
								return color[2];
							}
						});
					});
					;

					svg.append("g").attr("class", "x axis").attr("transform",
							"translate(0," + height + ")").call(xAxis)
							.selectAll("text").style("text-anchor", "end")
							.attr("dx", "-.2em").attr("dy", ".70em");
					// start
					var xAxis = d3.svg.axis().scale(x).orient("bottom");

					var yAxis = d3.svg.axis().scale(y).orient("left");

					var lineData = [ {
						'x' : 0,
						'y' : 80
					}, {
						'x' : width,
						'y' : 80
					} ];
					var xRange = d3.scale.linear().range([ 0, width ]).domain(
							[ 0, d3.max(lineData, function(d) {
								return d.x;
							}) ]);
					var yRange = d3.scale.linear().range([ 0, 70 ]).domain(
							[ 0, d3.max(lineData, function(d) {
								return d.y;
							}) ]);
					var lineFunc = d3.svg.line().x(function(d) {
						return xRange(d.x);
					}).y(function(d) {
						return yRange(d.y);
					}).interpolate('linear');

					svg.append("g").attr("class", "x axis").attr("transform",
							"translate(0," + height + ")").call(xAxis).attr(
							"x", width / 2).attr("y", margin.bottom).attr("dx",
							"1em").style("text-anchor", "middle").text(
							"Time Period");
					// .style("fill", "#FFFFFF");

					svg.append("g").attr("class", "y axis").call(yAxis).append(
							"text").attr("transform", "rotate(-90)").attr("y",
							0 - margin.left).attr("x", 0 - (height / 2)).attr(
							"dy", "1em").style("text-anchor", "end").text(
							"Score");

					svg.append("g").attr("class", "y axis").call(yAxis).append(
							"svg:path").attr("d", lineFunc(lineData)).attr(
							"stroke", "#1F77B4")
							.attr("stroke-dasharray", "5,5").attr(
									"stroke-width", 2).attr("fill", "none");

					// END
					// legend

					// end of legend

					function transitionGrouped() {
						y.domain([ 0, 100 ]);

						rect.transition().duration(500).delay(function(d, i) {
							return i * 10;
						}).attr("x", function(d, i, j) {
							return x(d.axis) + x.rangeBand() / n * j; // function(d)
							// {return
							// x(d.axis);
							// //
							// for
							// Group
							// bar
							// chat
						}).attr("width", x.rangeBand() / n).transition().attr(
								"y", function(d) {
									return y(d.value);
								}).attr("height", function(d) {
							return height - y(d.value);
						});
					}

					transitionGrouped();
					function removePopovers() {
						$('.popover').each(function() {
							$(this).remove();
						});
					}
					function showPopover(d) {
						$(this).popover(
								{
									title : '',
									placement : 'auto top',
									container : 'body',
									trigger : 'manual',
									html : true,
									content : function() {
										return d.axis + "<br/>" + "Score : "
												+ d.value + "%";
									}
								});
						$(this).popover('show');
					}
				};
				scope.$watch('spiderdata', function() {
					// var cdata=[[{"axis":"E2 :
					// Temperature","value":"43.0"},{"axis":"E3 : Storage
					// Capacity","value":"50.0"},{"axis":"E4 : Storage
					// Capacity","value":"60.0"},{"axis":"E5 : Storage
					// Capacity","value":"70.0"},{"axis":"E6 : Storage
					// Capacity","value":"80.0"},{"axis":"E7 : Storage
					// Capacity","value":"90.0"}],[{"axis":"E2 :
					// Temperature","value":"70.0"},{"axis":"E3 : Storage
					// Capacity","value":"90.0"},{"axis":"E4 : Storage
					// Capacity","value":"70.0"},{"axis":"E5 : Storage
					// Capacity","value":"90.0"},{"axis":"E6 : Storage
					// Capacity","value":"97.0"},{"axis":"E7 : Storage
					// Capacity","value":"100.0"}]];
					// draw(el, cdata);
					if (scope.spiderdata && scope.spiderdata.dataCollection) {
						var d = scope.spiderdata.dataCollection;
						draw(el, d);
					}
				}, true);

			}
			return {
				link : link,
				restrict : "E"
			};

		});