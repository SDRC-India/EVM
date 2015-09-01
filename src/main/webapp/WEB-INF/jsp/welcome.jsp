<!DOCTYPE html>
<%@page import="org.sdrc.evm.model.User"%>
<%@page import="org.sdrc.evm.model.UserRoleSchemeMapping"%>
<%@page import="java.util.List"%>
<%@page import="org.sdrc.evm.util.Constants"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">

<jsp:include page="fragments/headTag.jsp" />
<body>
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
	<div id="wrapper">
		<jsp:include page="fragments/bodyHeader.jsp" />

		<div class="content">
			<section id="banner">
				<div id="carousel-banner-slide" class="carousel slide"
					data-ride="carousel">

					<div class="carousel-inner">
						<div class="item active">
							<img alt="evm_bg" src="resources/images/bihar_banner4_400.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/assam_banner1.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/bihar_banner6_400.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/assam_banner2.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/bihar_banner1_400.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/assam_banner3.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/bihar_banner2_400.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/assam_banner4.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/bihar_banner3_400.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/assam_banner5.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/bihar_banner5_400.jpg"
								class="img-responsive" width="100%">
						</div>
						<div class="item">
							<img alt="evm_bg" src="resources/images/assam_banner6.jpg"
								class="img-responsive" width="100%">
						</div>
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-banner-slide"
						data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left"></span>
					</a> <a class="right carousel-control" href="#carousel-banner-slide"
						data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right"></span>
					</a>
				</div>
			</section>
			<div class="content">
				<section id="EVM_site_menu" >
					<div class="container-fluid">
						<h3>
							<i class="fa fa-arrow-circle-o-right fa-lg"></i>EVM Site Menu
						</h3>
						<div class="row">
							<div class="col-md-3 text-center">
								<a href="about">
									<div class="site_menu">
										<i class="fa fa-info-circle fa-3x"></i>
									</div>
								</a> <a href="about"><h4>About EVM</h4></a>
							</div>
							<div class="col-md-3 text-center">
								<a href="resource">
									<div class="site_menu">
										<i class="fa fa-files-o fa-3x"></i>
									</div>
								</a><a href="resource">


									<h4>Resources</h4>
								</a>

							</div>
							<div class="col-md-3 text-center">
								<a href="sops">
									<div class="site_menu">
										<i class="fa fa-bars fa-3x "></i>
									</div>
								</a> <a href="sops">
									<h4>SOPs</h4>
								</a>
							</div>
							<div class="col-md-3 text-center">
								<a href="tools">
									<div class="site_menu">
										<i class="fa fa-gears fa-3x"></i>
									</div>
								</a> <a href="tools">
									<h4>Tools</h4>
								</a>
							</div>
						</div>
					</div>
				</section>
				<section id="devinfo_section">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-4 text-center">
								<h4>
									<i class="fa fa-file-text fa-2x evm-font-blue"></i>FACTSHEETS
								</h4>
								<a href="landing?feature=Factsheet"><img alt="factsheets" class="img-rounded"
									src="resources/images/img1_a.png"></a>
							</div>
							<div class="col-md-4 text-center">
								<h4>
									<i class="fa fa-hand-o-up fa-2x evm-font-blue"></i>DASHBOARD
								</h4>
								<a href="landing?feature=Dashboard"><img alt="dashboard" class="img-rounded"
									src="resources/images/img2.jpg"></a>
							</div>
							<div class="col-md-4 text-center">
								<h4>
									<i class="fa fa-align-left fa-2x evm-font-blue"></i>DEVINFO
								</h4>
								<a
									href="http://evmindia.org.in:8080/evminfo/libraries/aspx/Home.aspx"
									target="_blank" title="www.devinfo.org"><img alt="di" class="img-rounded"
									src="resources/images/img3.png"></a>
							</div>
						</div>
					</div>
				</section>
				<section id="blogforum">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-6 mar-top-5 ">
								<h3 class="text-center">GALLERY</h3>
								<div id="carousel-example-generic"
									class="carousel slide min-height250" data-ride="carousel">

									<div class="carousel-inner">
										<div class="item active">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/10.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/11.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/12.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/13.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/14.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/15.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/16.jpg" class="img-responsive"></a>
										</div>

										
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/1411726656252.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/1419315165765.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DSCN2493.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DSCN2494.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DSCN2520.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DSCN2522.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DSCN2545.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DSCN2585.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DSCN2591.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/DVS Darrang.jpg" class="img-responsive"></a>
										</div>
										<div class="item">
											<a href="gallery"><img alt="evm-gallry-image"
												src="resources/images/SVS Guwahati.jpg" class="img-responsive"></a>
										</div>
									</div>

									<!-- Controls -->
									<a class="left carousel-control"
										href="#carousel-example-generic" data-slide="prev"> <span
										class="glyphicon glyphicon-chevron-left"></span>
									</a> <a class="right carousel-control"
										href="#carousel-example-generic" data-slide="next"> <span
										class="glyphicon glyphicon-chevron-right"></span>
									</a>
								</div>
								<div class="text-center">
									<button type="button" class="btn btn-default button"
										onclick="location.href='gallery';">
										MORE <i class="fa fa-angle-double-right"></i>
									</button>
								</div>
							</div>
							<div class="col-md-6">
								<h3 class="text-center">FAQ's</h3>
								<div class="panel-group min-height250" id="accordion">
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<a class="accordion-toggle" data-toggle="collapse"
													data-parent="#accordion" href="#collapseOne"> <span
													class="glyphicon glyphicon-minus"></span>I am trying to
													install the SDRC Collect but am unable to do so. What could be
													the reason?
												</a>
											</h4>
										</div>
										<div id="collapseOne" class="panel-collapse collapse in">
											<div class="panel-body">
												<p>Check for any instances of SDRC Collect that are
													installed on the device. If SDRC Collect is already
													installed, then you will not be able to install SDRC Collect.</p>
											</div>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<a class="accordion-toggle" data-toggle="collapse"
													data-parent="#accordion" href="#collapseTwo"> <span
													class="glyphicon glyphicon-plus"></span>I have SDRC Collect
													installed on my device. Can i use it for data collection?
												</a>
											</h4>
										</div>
										<div id="collapseTwo" class="panel-collapse collapse">
											<div class="panel-body">
												<p>Yes. You may use the generic SDRC Collect application.
													You need to provide the server configuration details to
													enable downloading the forms and pushing data to the
													server. Follow instructions in the user manual.</p>
											</div>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<a class="accordion-toggle" data-toggle="collapse"
													data-parent="#accordion" href="#collapseThree"> <span
													class="glyphicon glyphicon-plus"></span>I have manually
													copied the xml data entry forms onto the device and
													completed data entry. I am getting error message while
													submitting data.
												</a>
											</h4>
										</div>
										<div id="collapseThree" class="panel-collapse collapse">
											<div class="panel-body">
												<p>Please check if you have provided the correct
													credentials (server name, user name, password, etc).</p>
											</div>
										</div>
									</div>
								</div>
								<div class="text-center   ">
									<button type="button" class="btn btn-default button"
										onclick="location.href='faqs';">
										MORE <i class="fa fa-angle-double-right"></i>
									</button>
								</div>
							</div>

						</div>
					</div>
				</section>
			</div>
		</div>
	</div>
	<jsp:include page="fragments/footer.jsp" />
</body>

</html>
