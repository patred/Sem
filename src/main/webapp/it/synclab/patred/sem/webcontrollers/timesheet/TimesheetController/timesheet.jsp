<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript" src="${it.staticpath}/js/common.js"></script>
<div id="Theader"></div>
<div id="Tbody">
	<c:if test="${not empty(it.message)}">
		<div>
			<div>${it.message}</div>
		</div>
	</c:if>
	<c:if test="${! empty (it.timesheets)}">
		<div class="timesheets">
			<div class="timesheet">
				<div class="riga top-border right-border bottom-border">
					<div class="colonna left-border head" style="width: 20px">+</div>
					<div class="colonna left-border head">ID</div>
					<div class="colonna left-border head">STATO</div>
					<div class="colonna left-border head">MESE/ANNO</div>
					<div class="colonna left-border head">NOTE</div>
					<div class="colonna left-border head">DIPENDENTE</div>
				</div>
			</div>
			<c:forEach var="timesheet" items="${it.timesheets}" varStatus="loop">
				<div class="timesheet">
					<div class="riga right-border ${loop.index % 2 == 0 ? 'even' : 'odd'} ${loop.last ? 'bottom-border' : ''}">
						<div class="colonna left-border" style="width: 20px"><input type="radio" name="timesheet-id" value="${timesheet.id}"/></div>
						<div class="colonna left-border">${timesheet.id}</div>
						<div class="colonna left-border">${timesheet.status}</div>
						<div class="colonna left-border">${timesheet.month}/${timesheet.year}</div>
						<c:choose>
							<c:when test="${fn:length(timesheet.note) eq 0}">
								<div class="colonna left-border">-</div>
							</c:when>
							<c:when test="${fn:length(timesheet.note) ge 22}">
								<div class="colonna left-border">${fn:substring(timesheet.note, 0, 17)}&hellip;</div>
							</c:when>
							<c:otherwise>
								<div class="colonna left-border">${timesheet.note}</div>
							</c:otherwise>
						</c:choose>
						<div class="colonna left-border">${fn:substring(timesheet.employee.name, 0, 1)}. ${timesheet.employee.surname}</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>
</div>
<div id="Tfooter">
	<input id="addTimesheet" type="submit" value="Salva" onclick="addTimesheet();" style="display: none;">
	<input id="getAddTimesheetForm" type="submit" value="Aggiungi" onclick="addTimesheetForm();">
	<input id="editTimesheet" type="submit" value="Salva" onclick="editTimesheet();" style="display: none;">
	<input id="getEditTimesheetForm" type="submit" value="Modifica" onclick="editTimesheetForm();">
	<input id="delTimesheet" type="submit" value="Elimina" onclick="delTimesheet();">
	<input id="cancel" type="submit" value="Annulla" onclick="cancel();" style="display: none;">
</div>