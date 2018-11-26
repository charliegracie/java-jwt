package com.auth0.jwt.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.*;

class DeserializerHelper {

    public static Map<String, JsonNode> readMap(JsonParser p) throws IOException {
        Map<String, JsonNode> tree = null;
        JsonNode root = p.getCodec().readTree(p);
        if (root != null && root.isObject()) {
            tree = new HashMap<String, JsonNode>(root.size());
            for (Iterator<String> iterator = root.fieldNames(); iterator.hasNext();) {
                String fieldName = (String) iterator.next();
                tree.put(fieldName, root.get(fieldName));
            }
        }
        return tree;
    }
}
