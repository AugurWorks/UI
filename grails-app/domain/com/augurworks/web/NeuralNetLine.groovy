package com.augurworks.web

class NeuralNetLine {
	
	Date date
	double val
	
	static belongsTo = [neuralNet: NeuralNetResult]

    static constraints = {
		
    }
}
