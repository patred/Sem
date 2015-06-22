<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript" src="${it.staticpath}/js/common.js"></script>
<div id="Theader"></div>
<div id="Tbody">
	<c:if test="${not empty(it.message)}">
		<div>
			<div>${it.message}</div>
		</div>
	</c:if>

	<div class="details">
		<div class="detail">
			<div class="riga">
				<div class="colonna top-border bottom-border left-border head">GIORNO</div>
				<div class="colonna top-border bottom-border left-border head">ENTRATA</div>
				<div class="colonna top-border bottom-border left-border head">USCITA</div>
				<div class="colonna top-border bottom-border left-border head">ORE</div>
				<div class="colonna top-border bottom-border left-border head">PICAP</div>
				<div class="colonna top-border bottom-border left-border head">REP.</div>
				<div class="colonna top-border bottom-border left-border head">NOTE</div>
				<div class="colonna top-border bottom-border left-border head">TRASFERTA</div>
				<div class="colonna top-border bottom-border left-border head">COD</div>
				<div class="colonna top-border bottom-border left-border right-border head">COMMESSA</div>
			</div>
		</div>
		<div id="detailForm" class="detail">
			<c:forEach var="tDetails" items="${it.tDetails}" varStatus="loop">
				<div
					class="riga ${loop.index % 2 == 0 ? 'even' : 'odd'} ${loop.last ? 'bottom-border' : ''}">
					<div class="colonna top-border bottom-border left-border">${tDetails.day}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.ongoin}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.ongoout}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.hours}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.picap}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.availability}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.note}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.transfer}</div>
					<div class="colonna top-border bottom-border left-border">${tDetails.code}</div>
					<div class="colonna top-border bottom-border left-border right-border">${tDetails.orderId}</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div id="Tfooter">
	<input id="addDetail" type="submit" value="Salva" onclick="addDetail();" style="display: none;">
	<input id="addDetailForm" type="submit" value="Aggiungi" onclick="addDetailForm();">
	<input disabled="disabled" id="confirmDetail" type="submit" value="Conferma" onclick="confirmDetail();" style="display: none;">
	<input disabled="disabled" id="cancel" type="submit" value="Annulla" onclick="cancel();" style="display: none;">
</div>