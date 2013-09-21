<%@ page import="com.augurworks.web.Stocks" %>



<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'adjusted_close', 'error')} required">
	<label for="adjusted_close">
		<g:message code="stocks.adjusted_close.label" default="Adjustedclose" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="adjusted_close" value="${fieldValue(bean: stocksInstance, field: 'adjusted_close')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'daily_high', 'error')} required">
	<label for="daily_high">
		<g:message code="stocks.daily_high.label" default="Dailyhigh" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="daily_high" value="${fieldValue(bean: stocksInstance, field: 'daily_high')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'daily_low', 'error')} required">
	<label for="daily_low">
		<g:message code="stocks.daily_low.label" default="Dailylow" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="daily_low" value="${fieldValue(bean: stocksInstance, field: 'daily_low')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="stocks.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${stocksInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'day_change', 'error')} required">
	<label for="day_change">
		<g:message code="stocks.day_change.label" default="Daychange" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="day_change" value="${fieldValue(bean: stocksInstance, field: 'day_change')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'open', 'error')} required">
	<label for="open">
		<g:message code="stocks.open.label" default="Open" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="open" value="${fieldValue(bean: stocksInstance, field: 'open')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="stocks.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="price" value="${fieldValue(bean: stocksInstance, field: 'price')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'ticker', 'error')} ">
	<label for="ticker">
		<g:message code="stocks.ticker.label" default="Ticker" />
		
	</label>
	<g:textField name="ticker" value="${stocksInstance?.ticker}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'time', 'error')} ">
	<label for="time">
		<g:message code="stocks.time.label" default="Time" />
		
	</label>
	<g:textField name="time" value="${stocksInstance?.time}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stocksInstance, field: 'volume', 'error')} required">
	<label for="volume">
		<g:message code="stocks.volume.label" default="Volume" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="volume" type="number" value="${stocksInstance.volume}" required=""/>
</div>

