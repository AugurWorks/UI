UI
==
# Object Definitions #
This section describes the standardized form of request and result objects. These objects are passed around quite a bit, so it's nice to have the definitions somewhere.
## Request Object ##
This is the standard request object sent from graph or analysis pages to get data. It is a JavaScript object formulated from inputs on each page in the construction area.

    req = {0: { agg: 'Day Value type' String,
				custom: 'Custom function' String,
				dataType: 'Data type category' String,
				endDate: 'Data set end date' String,
				longName: 'Data set full name' String,
				name: 'Data set short name' String,
				startDate: 'Data set start date' String,
				page: 'Page name' String,
				reqId: 'Request ID, -1 if new' int},
		   1: { Another data set's data },
		   .
		   .
		   .
		   analysis[OPTIONAL]: { serviceName: 'Analysis service name',
								 Additional data}}

The analysis part of the request object is only added for analysis requests. It is stripped off of the request object before the request object is passed to the dataController to process each data set.

## Data Object ##

This is the data object that is returned from the dataController based on the request object. It is returned as a Groovy map. The optional analysis part is added after analysis is performed on the data sets and is interpreted by the analysis visualization pages.

    data = [root: [0: [dates: [2013-08-26: 37.96,
							   2013-08-27: 38.87,
							   .
							   date: Value double,
							   .
							   2014-02-20: 36.88],
					   metadata: [errors: 'Any thrown errors' Object,
								  req: 'Request object passed in for this dataset, see above',
								  unit: 'Data set units' String,
								  valid: 'Success' boolean]]
				   1: [Another data set's data]
				   .
				   .
				   .
				   analysis[OPTIONAL]: [Analysis data]
				   ]]

The data object only contains data and is turned into JSON for the graph page, but is blindly input into an analysis service for analysis. After analysis the map is converted to JSON and rendered on an analysis visualization page.