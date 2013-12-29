function formatAllData(dataSet, cutNum, linkNum) {
	var exclude = ['marketwatch', 'terms of service', 'terms of use', 'six financial information', 'real time']
	var obj = {'nodes': [], 'links': []}
	var tempObj = new Object()
	var names = []
	var vals = []

	for (i in dataSet) {
		if (dataSet[i].entities != undefined) {
			for (var j = 0; j < dataSet[i].entities.length; j++) {
				if (names.indexOf(dataSet[i].entities[j].actual_name) == -1 && exclude.indexOf(dataSet[i].entities[j].actual_name.toLowerCase()) == -1) {
					vals.push(dataSet[i].entities[j].averageFreq)
					names.push(dataSet[i].entities[j].actual_name)
				}
			}
		}
	}
	var cut = (!cutNum || cutNum >= vals.length) ? 0 : vals.sort().reverse()[cutNum]
	names = []
	
	for (i in dataSet) {
		if (dataSet[i].entities != undefined) {
			for (var j = 0; j < dataSet[i].entities.length; j++) {
				var name = $.map(dataSet[i].entities[j].actual_name.split(' '), function(d) { return d.substring(0, 1).toUpperCase() + d.substring(1); }).join(' ')
				if (((dataSet[i].entities[j].averageFreq >= cut && (!cutNum || obj['nodes'].length < cutNum)) || names.indexOf(name) != -1) && exclude.indexOf(name.toLowerCase()) == -1) {
					var nodes = obj['nodes']
					var src = names.indexOf(name)
					if (src == -1) {
						src = nodes.length
						nodes.push({'name': name, 'group': dataSet[i].entities[j].type})
						names.push(name)
					}
					for (var k = 0; k < j; k++) {
						var name2 = $.map(dataSet[i].entities[k].actual_name.split(' '), function(d) { return d.substring(0, 1).toUpperCase() + d.substring(1); }).join(' ')
						if (exclude.indexOf(name2.toLowerCase()) == -1) {
							var tar = names.indexOf(name2)
							if (tar != -1) {
								var index = src.toString() + '-' + tar.toString()
								var num = tempObj[index]
								if (num == undefined) {
									tempObj[index] = 1
								} else {
									tempObj[index] = num + 1
								}
							}
						}
					}
				}
			}
		}
	}
	var temp = []
	for (i in tempObj) {
		temp.push(tempObj[i])
	}
	var linkCut = (!linkNum || linkNum >= temp.length) ? 0 : temp.sort().reverse()[linkNum]
	temp = []
	var used = []
	for (i in tempObj) {
		if (tempObj[i] >= linkCut && (!linkNum || temp.length < linkNum)) {
			temp.push({'source': parseInt(i.split('-')[0]), 'target': parseInt(i.split('-')[1]), 'value': tempObj[i]})
		}
	}
	obj['links'] = temp
	return obj
}