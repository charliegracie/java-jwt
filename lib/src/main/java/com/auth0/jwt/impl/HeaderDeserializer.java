package com.auth0.jwt.impl;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;

class HeaderDeserializer extends StdDeserializer<BasicHeader> {

    HeaderDeserializer() {
        this(null);
    }

    private HeaderDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BasicHeader deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, JsonNode> tree = DeserializerHelper.readMap(p);
        if (tree == null) {
            throw new JWTDecodeException("Parsing the Header's JSON resulted on a Null map");
        }

        String algorithm = getString(tree, PublicClaims.ALGORITHM);
        String type = getString(tree, PublicClaims.TYPE);
        String contentType = getString(tree, PublicClaims.CONTENT_TYPE);
        String keyId = getString(tree, PublicClaims.KEY_ID);
        return new BasicHeader(algorithm, type, contentType, keyId, tree);
    }

    String getString(Map<String, JsonNode> tree, String claimName) {
        JsonNode node = tree.get(claimName);
        if (node == null || node.isNull()) {
            return null;
        }
        return node.asText(null);
    }
}
