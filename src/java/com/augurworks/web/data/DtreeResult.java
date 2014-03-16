package com.augurworks.web.data;

import com.augurworks.decisiontree.BinaryNode;
import com.augurworks.decisiontree.impl.CopyableDouble;
import com.augurworks.decisiontree.impl.CopyableString;
import com.augurworks.decisiontree.impl.TreeWithStats;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DtreeResult {
    public Node root;

    private DtreeResult() {
        //prevents instantiation
    }

    public static DtreeResult fromTree(TreeWithStats<CopyableString, CopyableDouble, CopyableString> tree) {
        DtreeResult result = new DtreeResult();
        Node node = Node.fromBinaryNode(tree.getTree());
        result.root = node;
        return result;
    }

    public JsonElement toJson() {
        return root.toJson();
    }

    @Override
    public String toString() {
        return "DtreeResult [root=" + root + "]";
    }

    public static class Node {
        private Double cutoffValue;
        private String cutoffName;
        private String cutoffType;
        private Node left;
        private Node right;
        private String defaultLeft;
        private String defaultRight;

        private Node() {
            //prevents instantiation
        }

        public static Node fromBinaryNode(BinaryNode<CopyableString, CopyableDouble, CopyableString> binaryNode) {
            Node node = new Node();
            node.cutoffName = binaryNode.getOperator().toString();
            node.cutoffValue = binaryNode.getLimit().getValue();
            node.cutoffType = binaryNode.getOperatorType().getString();
            node.defaultLeft = binaryNode.getDefaultLeft().getString();
            node.defaultRight = binaryNode.getDefaultRight().getString();
            if (binaryNode.getLeftHandChild() != null) {
                node.left = fromBinaryNode(binaryNode.getLeftHandChild());
            }
            if (binaryNode.getRightHandChild() != null) {
                node.right = fromBinaryNode(binaryNode.getRightHandChild());
            }
            return node;
        }

        public JsonElement toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("cutoffValue", cutoffValue);
            json.addProperty("cutoffName", cutoffName);
            json.addProperty("cutoffType", cutoffType);
            if (left != null) {
                json.add("left", left.toJson());
            } else {
                json.addProperty("left", defaultLeft);
            }
            if (right != null) {
                json.add("right", right.toJson());
            } else {
                json.addProperty("right", defaultRight);
            }
            return json;
        }

        @Override
        public String toString() {
            return "Node [cutoffValue=" + cutoffValue + ", cutoffName="
                    + cutoffName + ", cutoffType=" + cutoffType + ", left="
                    + left + ", right=" + right + ", defaultLeft="
                    + defaultLeft + ", defaultRight=" + defaultRight + "]";
        }
    }
}
