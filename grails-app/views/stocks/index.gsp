
<%@ page import="com.augurworks.web.Stocks" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'stocks.label', default: 'Stocks')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-stocks" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-stocks" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="adjusted_close" title="${message(code: 'stocks.adjusted_close.label', default: 'Adjustedclose')}" />
					
						<g:sortableColumn property="daily_high" title="${message(code: 'stocks.daily_high.label', default: 'Dailyhigh')}" />
					
						<g:sortableColumn property="daily_low" title="${message(code: 'stocks.daily_low.label', default: 'Dailylow')}" />
					
						<g:sortableColumn property="date" title="${message(code: 'stocks.date.label', default: 'Date')}" />
					
						<g:sortableColumn property="day_change" title="${message(code: 'stocks.day_change.label', default: 'Daychange')}" />
					
						<g:sortableColumn property="open" title="${message(code: 'stocks.open.label', default: 'Open')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${stocksInstanceList}" status="i" var="stocksInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${stocksInstance.id}">${fieldValue(bean: stocksInstance, field: "adjusted_close")}</g:link></td>
					
						<td>${fieldValue(bean: stocksInstance, field: "daily_high")}</td>
					
						<td>${fieldValue(bean: stocksInstance, field: "daily_low")}</td>
					
						<td><g:formatDate date="${stocksInstance.date}" /></td>
					
						<td>${fieldValue(bean: stocksInstance, field: "day_change")}</td>
					
						<td>${fieldValue(bean: stocksInstance, field: "open")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${stocksInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
