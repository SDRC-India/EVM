# EVM
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
						<li>Vaccine arrival procedures</li>
						<li>Vaccine storage temperatures</li>
						<li>Cold storage capacity</li>
						<li>Buildings, cold chain equipment and transport</li>
						<li>Maintenance of cold chain equipment and transport</li>
						<li>Stock management</li>
						<li>Effective vaccine delivery</li>
						<li>Vaccine Management practices</li>
						<li>SOPs and Supportive Management Systems</li>
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
	<h4>TECHNICAL SETUP GUIDE:</h4>
	
	<p>The EVM system enables data collection using android devices using the WHO-UNICEF designed Global Effective Vaccine Management (EVM) tool. Data is pushed to a central repository and passed through an aggregation engine to generate key statistics.
	</p>
	
	<p>The key indicators are displayed on a dashboard that presents the geo referenced health facilities which are color coded based on their performance. The dashboard was developed using a combination of technologies like Angular.js as the web MVC framework, Bootstrap and Jquery for the user interface developments, Java Spring framework for the service layer and google’s oauth authentication and D3 for the charts components. A customized DI7 web adaptation was used to enable custom query, analysis, and visualization.
	</p>
	
	<h5>Database Setup :
	</h5>
	<p>EVM uses two sets of MSSQL based databases, evm_devinfo for the DevInfo 7 based customised adaptation and evm_web for the dashboard components.
	</p>
	
	<p>Step1: MSSQL Server should be installed before moving to the next steps.</p>
	<p>Step2: Create two empty databases evm_devinfo and evm_web.</p>
	<p>Step3: Update the properties hibernate.hbm2ddl.auto from none to update  in the app.properties file (resources/spring 		folder), this will ensure that the required tables are created by Spring Data JPA in the corresponding databases 			when the application executes.</p>
	<p>Step4: Update the jdbc.url, jdbc.username, jdbc.password, jdbc.devinfo.url, jdbc.devinfo.username jdbc.devinfo.password 	properties for evm_web and evm_devinfo databases respectively, in the app.properties file (resources/spring folder), based on the development machine properties.</p>
	
	
	<h5>ODK Aggregate Server Setup :</h5>
	<p>The ODK server can be setup by following the link mentioned below.<br> https://opendatakit.org/use/aggregate/</p>
	<p>Once the aggregate server is ready, update the odk.aggregate.server, odk.aggregate.username, odk.aggregate.password in 	the app.properties file.</p>


	
				</div>
