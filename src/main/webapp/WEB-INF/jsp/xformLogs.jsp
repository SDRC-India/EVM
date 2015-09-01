<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<div>
	<datatables:table id="" data="${formDetails}" row="detail"
		theme="bootstrap2" cssClass="table table-striped" export="pdf"
		paginate="true">
		<datatables:column title="No.">${detail.count}
		</datatables:column>
		<datatables:column title="FORM ID">
			${detail.formID}
		</datatables:column>
		<datatables:column title="State">
			${detail.areaName}
		</datatables:column>
		<datatables:column title="District">
			${detail.district}
		</datatables:column>
		<datatables:column title="Facility">
			${detail.facility}
		</datatables:column>
		<datatables:column title="SUBMISSION DATE">
			${detail.submission_date}
		</datatables:column>
		<datatables:column title="LOGS">
			<button type="button" data-toggle="modal" data-target="#myModal"
				onclick="openXlsFormTable('${formID}','${detail.id}','${detail.level}')">view
				form</button>
		</datatables:column>
	</datatables:table>
</div>


<div class="xform modal" id="myModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>

				<h4 class="modal-title" id="">Submission details</h4>
			</div>
			<div class="modal-body">

				<div id="submittedtable"></div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<!--         <button type="button" class="btn btn-primary">Save changes</button> -->
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
