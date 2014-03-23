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
        return jsonObj.toString();
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
            trueSide.addProperty("results", node.getDefaultRight().getString());
        }
        results.add("isFalse", trueSide);

        obj.add("results", results);
    }
}
