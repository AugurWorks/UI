package com.augurworks.web.data;

import com.augurworks.decisiontree.BinaryNode;
import com.augurworks.decisiontree.impl.CopyableDouble;
import com.augurworks.decisiontree.impl.CopyableString;
import com.augurworks.decisiontree.impl.TreeWithStats;
import com.google.gson.JsonObject;

public class DecisionTreeUtils {
    public static String toJsonString(TreeWithStats<CopyableString, CopyableDouble, CopyableString> tree) {
        JsonObject jsonObj = new JsonObject();
        recurse(tree.getTree(), jsonObj);
        jsonObj.addProperty("correctness", roundDecimal(tree.getCorrectPercent()));
        return jsonObj.toString();
    }

    private static double roundDecimal(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    private static void recurse(BinaryNode<CopyableString, CopyableDouble, CopyableString> node, JsonObject obj) {
        obj.addProperty("cutoffValue", node.getLimit().getValue());
        obj.addProperty("cutoffName", node.getOperator().toString());
        obj.addProperty("cutoffType", node.getOperatorType().getString());
        JsonObject results = new JsonObject();
        JsonObject trueSide = new JsonObject();
        if (node.getLeftHandChild() != null) {
            recurse(node.getLeftHandChild(), trueSide);
        } else {
            trueSide.addProperty("results", node.getDefaultLeft().getString());
        }
        results.add("isTrue", trueSide);

        JsonObject falseSide = new JsonObject();
        if (node.getRightHandChild() != null) {
            recurse(node.getRightHandChild(), falseSide);
        } else {
            falseSide.addProperty("results", node.getDefaultRight().getString());
        }
        results.add("isFalse", falseSide);

        obj.add("results", results);
    }
}
