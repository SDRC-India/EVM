var sdrc_export = new function() {
	"use strict"
	this.init = function(rootUri) {
		this.root = rootUri;
		console.log("in init");
	};

	// Please give container Id and export pdf btn ids
	this.export_pdf = function(mapContainerId,chartContainerId,exportPdfbtn) {
		$("#" + exportPdfbtn)
				.on(
						"click",
						function(event) {
							event.preventDefault();
							d3
									.selectAll("svg")
									.attr("version", 1.1)
									.attr("xmlns", "http://www.w3.org/2000/svg");
							var spiderSvg = $("sdrc-spider").html();
							var columnSvg = $("sdrc-bar-chart").html();
							
							
							$.ajax({
								url:"exportImage",
								method:'POST',
								data : {spider:spiderSvg,column:columnSvg},
//								data : JSON.parse(data),
								success:function(data){
							    console.log(data);
							    $("#imagePath").val(data);

							var mapContainerClone = $("#" + mapContainerId);
//							var chartContainerClone = $("#" + chartContainerId);
							var mapImage;
							html2canvas(
									mapContainerClone,
									{
										useCORS : true,
										allowTaint : false,
										taintTest : false,
										logging : true,
										onrendered : function(canvas) {
											
											mapImage =	canvas.toDataURL("image/png");
															var serverUrl = sdrc_export.root
																	+ "/exportToPdf";
//															
															$("#imageBase64")
																	.val(mapImage);
//
															$("#exportForm").attr("action",
																	serverUrl).submit();
										}}); 
								}
							
						});});
	};

	this.export_excel = function() {
		alert("excel exported");
	};

};


