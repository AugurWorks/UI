
<%@ page import="com.augurworks.web.Stocks" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'stocks.label', default: 'Stocks')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-stocks" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-stocks" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list stocks">
			
				<g:if test="${stocksInstance?.adjusted_close}">
				<li class="fieldcontain">
					<span id="adjusted_close-label" class="property-label"><g:message code="stocks.adjusted_close.label" default="Adjustedclose" /></span>
					
						<span class="property-value" aria-labelledby="adjusted_close-label"><g:fieldValue bean="${stocksInstance}" field="adjusted_close"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.daily_high}">
				<li class="fieldcontain">
					<span id="daily_high-label" class="property-label"><g:message code="stocks.daily_high.label" default="Dailyhigh" /></span>
					
						<span class="property-value" aria-labelledby="daily_high-label"><g:fieldValue bean="${stocksInstance}" field="daily_high"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.daily_low}">
				<li class="fieldcontain">
					<span id="daily_low-label" class="property-label"><g:message code="stocks.daily_low.label" default="Dailylow" /></span>
					
						<span class="property-value" aria-labelledby="daily_low-label"><g:fieldValue bean="${stocksInstance}" field="daily_low"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.date}">
				<li class="fieldcontain">
					<span id="date-label" class="property-label"><g:message code="stocks.date.label" default="Date" /></span>
					
						<span class="property-value" aria-labelledby="date-label"><g:formatDate date="${stocksInstance?.date}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.day_change}">
				<li class="fieldcontain">
					<span id="day_change-label" class="property-label"><g:message code="stocks.day_change.label" default="Daychange" /></span>
					
						<span class="property-value" aria-labelledby="day_change-label"><g:fieldValue bean="${stocksInstance}" field="day_change"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.open}">
				<li class="fieldcontain">
					<span id="open-label" class="property-label"><g:message code="stocks.open.label" default="Open" /></span>
					
						<span class="property-value" aria-labelledby="open-label"><g:fieldValue bean="${stocksInstance}" field="open"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.price}">
				<li class="fieldcontain">
					<span id="price-label" class="property-label"><g:message code="stocks.price.label" default="Price" /></span>
					
						<span class="property-value" aria-labelledby="price-label"><g:fieldValue bean="${stocksInstance}" field="price"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.ticker}">
				<li class="fieldcontain">
					<span id="ticker-label" class="property-label"><g:message code="stocks.ticker.label" default="Ticker" /></span>
					
						<span class="property-value" aria-labelledby="ticker-label"><g:fieldValue bean="${stocksInstance}" field="ticker"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.time}">
				<li class="fieldcontain">
					<span id="time-label" class="property-label"><g:message code="stocks.time.label" default="Time" /></span>
					
						<span class="property-value" aria-labelledby="time-label"><g:fieldValue bean="${stocksInstance}" field="time"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stocksInstance?.volume}">
				<li class="fieldcontain">
					<span id="volume-label" class="property-label"><g:message code="stocks.volume.label" default="Volume" /></span>
					
						<span class="property-value" aria-labelledby="volume-label"><g:fieldValue bean="${stocksInstance}" field="volume"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:stocksInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${stocksInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
