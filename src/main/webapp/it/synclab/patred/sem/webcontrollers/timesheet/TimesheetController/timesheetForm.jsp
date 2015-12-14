<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="hero-unit">
		<input type="hidden" id="timesheetID" value="${it.timesheet.id ne null ? it.timesheet.id : '0'}">
		<c:set var="date" value="${it.timesheet.month}/${it.timesheet.year}"/>
		<div>
			<select id="status">
				<option ${it.timesheet.status eq 'Draft' ? 'selected' : ''} value="Draft">Bozza</option>
				<option ${it.timesheet.status eq 'ToConfirm' ? 'selected' : ''} value="ToConfirm" >Da Confermare</option>
				<option ${it.timesheet.status eq 'Confirmed' ? 'selected' : ''} value="Confirmed">Confermato</option>
				<option ${it.timesheet.status eq 'Rejected' ? 'selected' : ''} value="Rejected">Rigettato</option>
			</select>
		</div>
		<div>
			<input type="text" placeholder="click per sceglire mese e anno!"
				id="datetimepicker" value="${it.timesheet.id ne null ? date : ''}">
		</div>
		<div>
			<textarea id="note" name="note" rows="5" cols="200"
				style="width: 100%; height: 100%">${it.timesheet.id ne null ? it.timesheet.note : ''}</textarea>
		</div>
		<div>
			<input id="timesheetDetails" type="submit" value="Dettagli" onclick="timesheetDetails();">
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {

		$('#datetimepicker').datepicker({
			viewMode : 'years',
			format : "mm/yyyy"
		});
	});
</script>
