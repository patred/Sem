<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="riga bottom-border">
	<div class="colonna left-border">
		<input class="inDetail" type="text" id="detail-day">
	</div>
	<div class="colonna left-border">
		<input class="inDetail" type="text" id="detail-ongoin">
	</div>
	<div class="colonna left-border">
		<input class="inDetail" type="text" id="detail-ongoout">
	</div>
	<div class="colonna left-border">
		<input class="inDetail" type="text" id="detail-hours">
	</div>
	<div class="colonna left-border">
		<input class="inDetail" type="text" id="detail-picap">
	</div>
	<div class="colonna left-border">
	<select class="inDetail" id="detail-availability">
			<option value=false>No</option>
			<option value="true">Si</option>
		</select>
	</div>
	<div class="colonna left-border">
		<input class="inDetail" type="text" id="detail-note">
	</div>
	<div class="colonna left-border">
		<input class="inDetail" type="text" id="detail-transfer">
	</div>
	<div class="colonna left-border">
		<select class="inDetail" id="detail-code">
			<option value=NONE></option>
			<option value="TR">Trasferta</option>
			<option value="SD">Sede Disagiata</option>
		</select>
	</div>
	<div class="colonna left-border right-border">
		<input class="inDetail" type="text" id="detail-orderId">
	</div>
</div>