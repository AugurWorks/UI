package aw_web;

import grails.test.mixin.*

import org.apache.commons.io.FileUtils

import com.augurworks.web.DecisionTreeService
import com.augurworks.web.data.AnalysisParamType
import com.augurworks.web.data.DataTransferObject
import com.augurworks.web.data.DataTransferObjects
import com.augurworks.web.data.DtreeAnalysisParam
import com.augurworks.web.data.DtreeResult

@TestFor(DecisionTreeService)
public class DtreeAnalysisServiceTest {
    DecisionTreeService service = new DecisionTreeService();

    private String readFile() throws IOException {
        return FileUtils.readFileToString(new File("test/resources/sample_data.json"));
    }

    void test1() {
        DataTransferObject dataObject = DataTransferObjects.fromJsonString(readFile());
        DtreeAnalysisParam param = dataObject.getAnalysis().get(AnalysisParamType.DTREE);
        def rows = service.getRowGroupFromData(dataObject);
        def result = service.getTree(rows, "BUY", "SELL", param.getTreeDepth());
        println DtreeResult.fromTree(result).toJson();
    }
}
