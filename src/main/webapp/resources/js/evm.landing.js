// Angular app creation
var app = angular.module("evmmapp",
		[ 'chieffancypants.loadingBar', 'ngAnimate' ]);

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

	// To be deleted
	// return "http://" + location.hostname
	// + (location.port ? ":" + location.port : "")
	return rootURL + "/dashboard?" + "st=" + scope.selectedTimeperiod.key
			+ "%26ss=" + scope.selectedSector.key + "%26si="
			+ scope.selectedIndicator.key + "%26sg="
			+ scope.selectedGranularity.key + "%26sgn="
			+ scope.selectedGranularity.value.replace(' ', '_') + "%26sl="
			+ scope.areaLevel + "%26sd=" + scope.shouldDrilldown;
};

app.currentStateMailUrl = function(scope) {
	var scope = angular.element($("#containerId")).scope();

	// To be deleted
	// return "http://" + location.hostname
	// + (location.port ? ":" + location.port : "")
	return rootURL + "/dashboard?" + "st=" + scope.selectedTimeperiod.key
			+ "&ss=" + scope.selectedSector.key + "&si="
			+ scope.selectedIndicator.key + "&sg="
			+ scope.selectedGranularity.key + "&sgn="
			+ scope.selectedGranularity.value.replace(' ', '_') + "&sl="
			+ scope.areaLevel + "&sd=" + scope.shouldDrilldown;
};
app.directive("samikshaMap", function($window, cfpLoadingBar) {
	cfpLoadingBar.start();
	function link(scope, el) {

		var el = el[0];
		var DELAY = 300, clicks = 0, timer = null;

		function onmousemove(d) {
			d3.select(".map_popover").style("display", "block").style("left",
					(d3.event.pageX) - 90 + "px")// TODO:
			.style("top", (d3.event.pageY - 70) + "px").style("opacity", "1");

		}
		function onmouseout() {
			d3.select(".map_popover").style("display", "none");
		}
		function onover(d) {
			var rank = d.properties.NAME1_;
			if(d.properties && d.properties.NAME1_ == "Bihar" || d.properties && d.properties.NAME1_ == "Assam" ){
			d3.select(".map_popover_content").html(
					"<strong>Click to view <br>the "+scope.feature+" for </strong><span style='color:red'>" 
							+ d.properties.NAME1_ + "</span>");}
			else{
//				d3.select(".map_popover_content").html("<span style='color:red'>" + ""+d.properties.NAME1_+"" + "</span>" + 
//						"<strong>has not<br> covered</strong>"+ ""  );
				d3.select(".map_popover_content").html( "<span style='color:red'>"
				+rank+ "</span>" +
						"<strong>: Coming soon.</strong>");
			}

//			if (d.properties.utdata && d.properties.utdata.rank) {
//
//				rank = d.properties.utdata.rank;
//
//			} else {
//				rank = "Not Available";
//			}

//			d3.select(".map_popover_close").html(
//					"<strong>Value :</strong> <span style='color:red'>" + rank
//							+ "</span>");

			d3.select(this.parentNode.appendChild(this)).classed("activehover",
					true);
		}
		function onout(d) {
			d3.select(this.parentNode.appendChild(this)).classed("activehover",
					false);
		}

		scope.mapSetup = function(url, success) {
			d3.select(el).selectAll("*").remove();
			var w = scope.getWindowDimensions();
			var feature = "";
			
			var width = w.w, height = w.h;
			var rectWidth=width/3+50;
			var projection = d3.geo.mercator().scale(1);
			var path = d3.geo.path().projection(projection);

			var svg = d3.select(el).append("svg").attr("id", "mapsvg").attr(
					"width", width).attr("height", height);

//			svg.append("rect").attr("class", "background").attr("width", rectWidth).attr("x", width/3-20)
//					.attr("height", height);
////					.style("fill","	#FAF8F5");

			var g = svg.append("g").attr("id", "mapg");

			d3.json(url, function(error, us) {
				if (error)
					return;

				feature = topojson.feature(us, us.objects.layer1);

				var b = path.bounds(feature), s = 0.95 / Math.max(
						(b[1][0] - b[0][0]) / width, (b[1][1] - b[0][1])
								/ height);
				projection.scale(s);
				b = d3.geo.bounds(feature);
				projection.center([ (b[1][0] + b[0][0]) / 2,
						(b[1][1] + b[0][1]) / 2 ]);
				projection.translate([ width / 2, height / 2 ]);

				g.append("g")

				.attr("class", "states").selectAll("path").data(
						topojson.feature(us, us.objects.layer1).features)
						.enter().append("path").style("fill", function(d) {
							if (d.properties.NAME1_ == "Bihar" || d.properties.NAME1_ == "Assam"  ){
								return "#4db7b7";
							}
							else{
								return "#009999";
							}
								
							}).style(
								"stroke", "#005050").attr("d", path).on(
								"mousedown", clickHandler).on("mouseover",
								onover).on("mouseout");

				g.append("path")

				.datum(topojson.mesh(us, us.objects.layer1, function(a, b) {
					return a !== b;
				})).attr("id", "states-borders").attr("d", path);
				g.on("mousemove", onmousemove).on("mouseout", onmouseout);
				
				
				
				
				g
				.selectAll("text")
				.data(
						topojson
								.feature(
										us,
										us.objects.layer1).features)
				.enter()
				.append("svg:text")
				.text(
						function(d) {
							if (d.properties.NAME1_ == "Bihar"){
							return d.properties.NAME1_ == "Bihar" ? d.properties.NAME1_ // only
									// for
									// Bihar
									: "";
						}
							else if(d.properties.NAME1_ == "Assam"){
								return d.properties.NAME1_ == "Assam" ? d.properties.NAME1_ // only
										// for
										// Assam
										: "";
								
							}	
						}
						
				)
				.attr(
						"x",
						function(d) {
							return path
									.centroid(d)[0];
						})
				.attr(
						"y",
						function(d) {
							return path
									.centroid(d)[1];
						})
				.attr(
						"text-anchor",
						"middle")
				.attr('font-size',
						'9pt')
						.attr('fill',
						'white')
				.on("mousedown", clickHandler);


				if (success)
					success();
			});

			function clickHandler(d) {
				clicks++; // count clicks

				if (clicks === 1) {

					timer = setTimeout(function() {

						clicked(d); // perform
						// single-click
						// action
						clicks = 0; // after action performed,
						// reset counter
					}, DELAY);

				} else {
					clearTimeout(timer); // prevent
					// single-click
					// action
					// drilldown(d); // perform
					// double-click
					// action
					clicks = 0; // after action performed, reset
					// counter
				}
			}
			function clicked(d) {
				// var page = $("[name='page']").val();
				var area_name = d.properties.NAME1_;
				var area_id = d.properties.ID_;
				if (d.properties && d.properties.NAME1_ == "Bihar"){
				// console.log(d)
				// alert(page+"<>"+area_name);
				// if (page=='dashboard'){
				// window.location.replace('dashboard');
				// }
				// else{
				// window.location.replace('factsheets');
				// }
				$("#lAreaName").val(area_name);
				$("#lAreaCode").val(area_id);
				$("#landingForm").submit();
				// scope.selectDistrictByMap(area_id, area_name);
			}
				else if(d.properties && d.properties.NAME1_ == "Assam")
					{
					$("#lAreaName").val(area_name);
					$("#lAreaCode").val(area_id);
					$("#landingForm").submit();
					}
					
			}
				
		};
		// scope
		// .$watch(
		// "utdata",
		// function() {
		// d3
		// .select("#mapsvg")
		// .selectAll("path")
		//													
		// .attr(
		// "class",
		// function(d) {
		// if (!(scope.utdata && scope.utdata.dataCollection)) {
		// if (d.properties) {
		// d.properties.utdata = null;
		// }
		// return;
		// }
		// for (var i = 0; i < scope.utdata.dataCollection.length; i++) {
		// if (d.properties
		// && d.properties.ID_ == scope.utdata.dataCollection[i].areaCode) {
		// d.properties.utdata = scope.utdata.dataCollection[i];
		// return scope.utdata.dataCollection[i].cssClass;
		// } else {
		// if (d.properties) {
		// d.properties.utdata = null;
		// }
		// }
		// }
		// });
		//
		// }, true);
		scope.$watch(scope.getWindowDimensions, function(newValue, oldValue) {
			scope.svgHeight = (newValue.h);
			scope.svgWidth = (newValue.w);
			scope.style = function() {
				return {
					'height' : (newValue.h + 40) + 'px',
					'width' : (newValue.w) + 'px'
				};
			};
			w = scope.getWindowDimensions();
			width = w.w, height = w.h;
			d3.select("#mapsvg").attr({
				width : width,
				height : height
			});
			d3.select("#mapsvg").selectAll("rect").attr({
				width : width,
				height : height
			});

		}, true);

		scope.mapSetup(scope.primary_url, function() {
			scope.selectedGranularity = new ValueObject("IND", "India");
			//scope.start();
			scope.complete();
		});

	}
	return {
		link : link,
		restrict : "E"
	};
});
