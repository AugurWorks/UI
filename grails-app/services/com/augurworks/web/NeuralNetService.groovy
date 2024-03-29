package com.augurworks.web

import com.augurworks.web.NeuralNetResult
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class NeuralNetService {

    def grailsApplication

    def performAnalysis(parameters, inputData, removed, recordedReq) {
        writeFile(parameters.analysis, inputData, recordedReq, parameters.user)
        return [success: true]
    }

    def writeFile(param, data, recordedReq, user) {
		def result = new NeuralNetResult(created: new Date(), user: User.get(user), request: recordedReq).save();
        def name = result.created.format('MM-dd-yy-HH-mm-ss-SSS') + '.augtrain';
        def f = grailsApplication.mainContext.getResource('neuralnet/' + name).file;
        f << 'net ' + (data.size() - 1) + ',' + param.depth + '\n'
        f << 'train 1,' + param.rounds + ',' + param.learningConstant + ',' + param.rounds + ',' + param.cutoff + '\n';
		def vals = data.values().sort { a, b -> param.dependent.indexOf(b.metadata.req.name) - param.dependent.indexOf(a.metadata.req.name) }
        def titles = vals.collect { it.metadata.req.name }.join(',')
        titles = titles.substring(titles.indexOf(",") + 1)
        f << 'TITLES ' + titles + '\n'
        def dates = vals.collect { it.dates.values() };
        def start = new Date().parse('yyyy-MM-dd', vals.collect { it.dates.keySet() }[0][0]);
        (0..(dates[1].size() - 1)).each { i ->
            def values = start.plus(i).format('yyyy-MM-dd') + ' ' + (dates[0][i] ? dates[0][i] : 'NULL') + ' ' + dates[1..(dates.size() - 1)].collect { it[i] }.join(',') + '\n';
            f << values;
        }
    }

    def readResult(name) {
		def f = grailsApplication.mainContext.getResource('data/' + name).file
		println 'Reading ' + f.path;
		def date = Date.parse('MM-dd-yy-HH-mm-ss-SSS', f.name.split('\\.')[0]);
		def result = NeuralNetResult.findByCreated(date);
		result.dataLocation = f.path;
		result.save()
    }
}
