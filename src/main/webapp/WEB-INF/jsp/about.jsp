<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<html lang="en">

<jsp:include page="fragments/headTag.jsp" />

<body>

	<div id="wrapper">
		<jsp:include page="fragments/bodyHeader.jsp" />
		<div class="content">
			<div class="container-fluid">
				<h2 class="page-header evm-font-blue">About</h2>
				<div class="col-md-8">
					<h4 class="evm-font-blue">EVM - setting a standard for the
						vaccine supply chain</h4>
					<p>With the rising cost of vaccines and the greater storage
						capacity now required at every level of the cold chain, countries
						must maintain lower stock levels, reduce wastage, accurately
						forecast vaccine requirements, and prevent equipment break-downs.</p>
					<p>This requires a consistently high standard of supply chain
						management, which can only be achieved if all the links in the
						supply chain comply with current standards for storage and
						distribution.</p>
					<p>The EVM initiative provides materials and tools needed to
						monitor and assess vaccine supply chains and help countries to
						improve their supply chain performance.</p>
					<p>WHO-UNICEF have designed the Global Effective Vaccine
						Management (EVM) initiative to help countries to improve the
						quality of their vaccine and cold chain management from the time
						the vaccine arrives in their country down to the service delivery
						point.</p>
					<p class="mar-top-10 mar-bot-10">It is based on nine basic
						indicators listed below:</p>
					<ol class="list-unstyled">
						<li>1. Vaccine arrival procedures</li>
						<li>2. Vaccine storage temperatures</li>
						<li>3. Cold storage capacity</li>
						<li>4. Buildings, cold chain equipment and transport</li>
						<li>5. Maintenance of cold chain equipment and transport</li>
						<li>6. Stock management</li>
						<li>7. Effective vaccine delivery</li>
						<li>8. Vaccine Management practices</li>
						<li>9. SOPs and Supportive Management Systems</li>
					</ol>
					<p>It consists of a series of focused questions, which are
						numerically scored based on the observed practices and records of
						the past 12 months, against recommended standards.</p>
					<p>The questions under the 9 indicators can be divided into 7
						management implementation categories: Building, Storage Capacity,
						Equipment, Management issues, Repair and Maintenance, Training and
						Vehicles.</p>
					<p>UNICEF has been supporting EVM assessments in many states,
						covering state, regional, district vaccine stores and health
						facilities.</p>
					<p>Based on facts collected from the field and recommendations
						developed by assessment team members, improvements plans are
						prepared, to address gaps in the system.</p>
				</div>
				<div class="col-md-4">
					<img  class="img-responsive" alt="layeredvaccine"
						src="resources/images/evm_layersvaccine.jpg"
						title="layeredvaccine" class="imagezoom" />
				</div>
			</div>
		</div>
		<div class="clearfooter"></div>
	</div>
	<jsp:include page="fragments/footer.jsp" />
</body>

</html>
