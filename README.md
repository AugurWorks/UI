UI
==
# Object Definitions #
This section describes the standardized form of request and result objects. These objects are passed around quite a bit, so it's nice to have the definitions somewhere.
## Request Object ##
This is the standard request object sent from graph or analysis pages to get data. It is a JavaScript object formulated from inputs on each page in the construction area.

    req = {0: { agg: 'Day Value type',
				custom: 'Custom function',
				dataType: 'Data type category',
				endDate: 'Data set end date',
				longName: 'Data set full name',
				name: 'Data set short name',
				startDate: 'Data set start date'},
		   1: { Another data set's data },
		   .
		   .
		   .
		   analysis[OPTIONAL]: { Analysis specific data }}

The analysis part of the request object is only added for analysis requests. It is stripped off of the request object before the request object is passed to the dataController to process each data set.

## Data Object ##

This is the data object that is returned from the dataController based on the request object. It is returned as a Groovy map.

    data = [root: [0: [dates: [2013-08-26: 37.96,
							   2013-08-27: 38.87,
							   .
							   .
							   .
							   2014-02-20: 36.88],
					   metadata: [errors: 'Any thrown errors',
								  req: 'Request object passed in for this dataset, see above',
								  unit: 'Data set units',
								  valid: 'Success boolean']]
				   1: [Another data set's data]
				   .
				   .
				   .
				   ]]

The data object only contains data and is turned into JSON for the graph page, but is blindly input into an analysis service for analysis.

## Analysis Object ##

The analysis object is the Groovy map returned by an analysis service function. This result is blindly converted to JSON and sent to an analysis rendering page.

	TBD