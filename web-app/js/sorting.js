function sortByName(a, b) {
	var aName = a.disambiguated_name.toLowerCase();
	var bName = b.disambiguated_name.toLowerCase(); 
	return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
}

function sortByFrequency(a, b) {
	var aName = a.frequency;
	var bName = b.frequency;
	return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
}

function sortByType(a, b) {
	var aName = a.type.toLowerCase();
	var bName = b.type.toLowerCase(); 
	return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
}

function sortBySentiment(a, b) {
	var aName = a.sentiment;
	var bName = b.sentiment; 
	return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
}

function sortBySignificance(a, b) {
	var aName = a.significance;
	var bName = b.significance; 
	return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
}