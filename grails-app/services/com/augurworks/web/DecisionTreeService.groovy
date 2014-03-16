package com.augurworks.web;

import grails.converters.JSON

import com.augurworks.decisiontree.BinaryNode
import com.augurworks.decisiontree.BinaryOperator
import com.augurworks.decisiontree.BinaryOperatorSet
import com.augurworks.decisiontree.Provider
import com.augurworks.decisiontree.Row
import com.augurworks.decisiontree.RowGroup
import com.augurworks.decisiontree.impl.BinaryNodeImpl
import com.augurworks.decisiontree.impl.BinaryOperatorDoubleImpl
import com.augurworks.decisiontree.impl.CopyableDouble
import com.augurworks.decisiontree.impl.CopyableString
import com.augurworks.decisiontree.impl.DecisionTrees
import com.augurworks.decisiontree.impl.RowGroupImpl
import com.augurworks.decisiontree.impl.RowImpl
import com.augurworks.decisiontree.impl.TreeWithStats
import com.augurworks.web.data.AnalysisParamType
import com.augurworks.web.data.DataTransferObject
import com.augurworks.web.data.DataTransferObjects
import com.augurworks.web.data.DtreeAnalysisParam
import com.augurworks.web.data.DtreeResult;
import com.google.common.collect.Lists

public class DecisionTreeService {
    DataController dataController = new DataController();

    def performAnalysis(parameters) {
        def inputData = dataController.getData(parameters);
        DataTransferObject dataObject = DataTransferObjects.fromJsonString((inputData as JSON).toString());
        DtreeAnalysisParam param = dataObject.getAnalysis().get(AnalysisParamType.DTREE);
        def rows = getRowGroupFromData(dataObject);
        def result = getTree(rows, "BUY", "SELL", param.getTreeDepth());
        return DtreeResult.fromTree(result).toJson();
    }

    private static final BinaryOperatorSet<CopyableDouble> OPERATORS = new BinaryOperatorSet<CopyableDouble>() {
        @Override
        public Collection<BinaryOperator<CopyableDouble>> operators() {
            Set<BinaryOperator<CopyableDouble>> output = new HashSet<BinaryOperator<CopyableDouble>>();
            for (BinaryOperator<CopyableDouble> op : BinaryOperatorDoubleImpl.values()) {
                output.add(op);
            }
            return output;
        }
    };

    private static final Provider<CopyableString> COPYABLE_STRING_PROVIDER = new Provider<CopyableString>() {
        @Override
        public CopyableString fromString(String s) {
            return CopyableString.from(s);
        }
    };

    private static final Provider<CopyableDouble> COPYABLE_DOUBLE_PROVIDER = new Provider<CopyableDouble>() {
        @Override
        public CopyableDouble fromString(String s) {
            return CopyableDouble.valueOf(s);
        }
    };

    public static CopyableString evaluateRow(TreeWithStats<CopyableString, CopyableDouble, CopyableString> tree,
        Row<CopyableString, CopyableDouble, CopyableString> row) {
        return tree.evaluateRow(row);
    }

    public static String evaluateRow(TreeWithStats<CopyableString, CopyableDouble, CopyableString> tree,
        List<String> titles, List<String> values) {
        return evaluateRow(tree, getRowNoOutput(titles, values)).getString();
    }

    private static BinaryNode<CopyableString, CopyableDouble, CopyableString> getGenericRoot(String defaultLeft, String defaultRight) {
        return new BinaryNodeImpl<CopyableString, CopyableDouble, CopyableString>(
                CopyableString.from(defaultLeft), CopyableString.from(defaultRight), null);
    }

    public static TreeWithStats<CopyableString, CopyableDouble, CopyableString> getTree(RowGroup<CopyableString, CopyableDouble, CopyableString> rows, String defaultLeft, String defaultRight, int depth) {
        BinaryNode<CopyableString, CopyableDouble, CopyableString> root = getGenericRoot(defaultLeft, defaultRight);
        root = DecisionTrees.train(root, rows, OPERATORS, depth);
        return TreeWithStats.of(root, rows);
    }

    /**
     * Creates a rowgroup out of string inputs.
     *
     * titles list:                A   B   C   D   E
     * list for each data row:     1   2   1   0  1.2   BUY
     *                             0   1   2   3   4    SELL
     *                             7   1   2   4   0    BUY
     *                             ....
     *                                     outputs list ^^
     */
    public static RowGroup<CopyableString, CopyableDouble, CopyableString> getRowGroup(List<String> titles, List<List<String>> valuesLists, List<String> outputs) {
        validateRowGroupInputs(titles, valuesLists, outputs);
        RowGroup<CopyableString, CopyableDouble, CopyableString> rows = new RowGroupImpl<CopyableString, CopyableDouble, CopyableString>();
        for (int i = 0; i < outputs.size(); i++) {
            rows.addRow(getRow(titles, valuesLists.get(i), outputs.get(i)));
        }
        return rows;
    }

    public static RowGroup<CopyableString, CopyableDouble, CopyableString> getRowGroupFromData(DataTransferObject data) {
        DtreeAnalysisParam param = data.getAnalysis().get(AnalysisParamType.DTREE);
        List<String> titles = DataTransferObjects.getTitlesFromData(data);
        Set<Date> allDates = data.getAllDates();
        RowGroup<CopyableString, CopyableDouble, CopyableString> rows = new RowGroupImpl<CopyableString, CopyableDouble, CopyableString>();
        for (Date d : allDates) {
            List<String> rowValues = Lists.newArrayList();
            Map<String, Double> values = data.getValuesOnDate(d);
            for (String title : titles) {
                rowValues.add("" + values.get(title).doubleValue());
            }
            Date dayBefore = addToDate(d, -1);
            String recommendation = "";
            Double yesterdayPrice = data.getValueOnDate(param.getNameToPredict(), dayBefore);
            if (yesterdayPrice == null) {
                recommendation = "HOLD";
            } else {
                Double todayPrice = data.getValueOnDate(param.getNameToPredict(), d);
                if (todayPrice - yesterdayPrice > param.getCutoff()) {
                    recommendation = "BUY";
                } else if (yesterdayPrice - todayPrice > param.getCutoff()) {
                    recommendation = "SELL";
                } else {
                    recommendation = "HOLD";
                }
            }
            rows.addRow(getRow(titles, rowValues, recommendation));
        }
        return rows;
    }

    private static Date addToDate(Date d, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    private static Row<CopyableString, CopyableDouble, CopyableString> getRow(List<String> titles, List<String> values, String output) {
        validateRowInputs(titles, values, output);
        Row<CopyableString, CopyableDouble, CopyableString> row = new RowImpl<CopyableString, CopyableDouble, CopyableString>();
        for (int i = 0; i < titles.size(); i++) {
            row.put(COPYABLE_STRING_PROVIDER.fromString(titles.get(i)), COPYABLE_DOUBLE_PROVIDER.fromString(values.get(i)));
        }
        row.setResult(COPYABLE_STRING_PROVIDER.fromString(output));
        return row;
    }

    public static Row<CopyableString, CopyableDouble, CopyableString> getRowNoOutput(List<String> titles, List<String> values) {
        validateRowInputs(titles, values, "");
        Row<CopyableString, CopyableDouble, CopyableString> row = new RowImpl<CopyableString, CopyableDouble, CopyableString>();
        for (int i = 0; i < titles.size(); i++) {
            row.put(COPYABLE_STRING_PROVIDER.fromString(titles.get(i)), COPYABLE_DOUBLE_PROVIDER.fromString(values.get(i)));
        }
        return row;
    }

    private static void validateRowGroupInputs(List<String> titles, List<List<String>> valuesLists, List<String> outputs) {
        if (titles == null) {
            throw new IllegalArgumentException("titles list cannot be null");
        }
        if (valuesLists == null) {
            throw new IllegalArgumentException("valuesLists cannot be null");
        }
        if (outputs == null) {
            throw new IllegalArgumentException("outputs cannot be null");
        }
        if (outputs.size() != valuesLists.size()) {
            throw new IllegalArgumentException("valuesLists has a different number of rows than outputs. valuesLists is " + valuesLists + " and outputs is " + outputs);
        }
    }

    private static void validateRowInputs(List<String> titles, List<String> values, String output) {
        if (titles == null) {
            throw new IllegalArgumentException("titles list cannot be null");
        }
        if (values == null) {
            throw new IllegalArgumentException("values list cannot be null");
        }
        if (titles.size() != values.size()) {
            throw new IllegalArgumentException("values and titles lists must be of equal length. titles was " + titles + " and values was " + values);
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null");
        }
    }
}
