function ValueObject(key, value) {
	this.key = key;
	this.value = value;
}
function EvmDashboardController($scope, $http, $window, $timeout, cfpLoadingBar,
		$filter) {

	$scope.feature = angFeature;
	var w = angular.element($window);
	$scope.getWindowDimensions = function() {
		return {
			"h" : 500,
			"w" : (w.width() * 90) / 100
		};
	};
	// this is to make sure that scope gets changes as window get resized.
	w.on("resize", function() {
		$scope.$apply();
	});
	// Scope properties
	// $scope.indicators = [ new ValueObject("1", "Select Indicator") ];---
	$scope.ishideTabularView = true;
	$scope.hidetable = function() {
		$scope.ishideTabularView = true;
	};
	$scope.showtable = function() {
		$scope.ishideTabularView = false;
		$scope.getTabularview(function() {
			$('html, body').animate({
				scrollTop : '630px',
			}, 1000);
		});
	};
//	$scope.areaLevel = 1;
//	$scope.indicators = [];
//	$scope.timeformats = [];
//	$scope.sectors = [];// [ new ValueObject("1", "Basic Info") ];
//	$scope.utdata = [];
//	$scope.legends = [];
//	$scope.topPerformers = [];
//	$scope.bottomPerformers = [];
//	$scope.tableData = [];
	
	
	

	// select the first user of the list
//	$scope.selectedTimeperiod = $scope.timeformats[0];
//	$scope.selectedSector = $scope.sectors[0];
//	$scope.selectedIndicator = $scope.indicators[0];
	$scope.selectedGranularity = new ValueObject("IND", "India");
//	$scope.isTrendVisible = false;
//	$scope.selectedArea = [];
//	$scope.show = false;
//	$scope.shouldDrilldown = true;
//	$scope.isfactsheet=true;
//	$scope.isdashboard=false;
	$scope.primary_url = "resources/geomaps/India.json";
	// Scope methods
	

	$scope.start = function() {
		cfpLoadingBar.start();

		// Http calls

	};

	
	

	$scope.complete = function() {
		cfpLoadingBar.complete();
	};

	// fake the initial load so first time users can see the bar right away:
	$scope.start();
	$scope.fakeIntro = true;
//	$timeout(function() {
//		$scope.complete();
//
//		$scope.fakeIntro = false;
//	}, 1000);
	$scope.style = function() {

	};
}

Array.prototype.indexOffield = function(field) {
	for (var i = 0; i < this.length; i++)
		if (this[i].field === field)
			return i;
	return -1;
};
