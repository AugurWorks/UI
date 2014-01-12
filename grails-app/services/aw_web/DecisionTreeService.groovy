package aw_web;

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

public class DecisionTreeService {
		
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
	
	private static BinaryNode<CopyableString, CopyableDouble, CopyableString> getGenericRoot(String defaultLeft, String defaultRight) {
		return new BinaryNodeImpl<CopyableString, CopyableDouble, CopyableString>(
				CopyableString.from(defaultLeft), CopyableString.from(defaultRight), null);
	}
	
	public static TreeWithStats<CopyableString, CopyableDouble, CopyableString> getTree(RowGroup<CopyableString, CopyableDouble, CopyableString> rows, String defaultLeft, String defaultRight, int depth) {
		BinaryNode<CopyableString, CopyableDouble, CopyableString> root = getGenericRoot(defaultLeft, defaultRight);
		root = DecisionTrees.train(root, rows, OPERATORS, depth);
		return TreeWithStats.of(root, rows);
	}
	
	public static RowGroup<CopyableString, CopyableDouble, CopyableString> getRowGroup(List<String> titles, List<List<String>> valuesLists, List<String> outputs) {
		validateRowGroupInputs(titles, valuesLists, outputs);
		RowGroup<CopyableString, CopyableDouble, CopyableString> rows = new RowGroupImpl<CopyableString, CopyableDouble, CopyableString>();
		for (int i = 0; i < outputs.size(); i++) {
			rows.addRow(getRow(titles, valuesLists.get(i), outputs.get(i)));
		}
		return rows;
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
