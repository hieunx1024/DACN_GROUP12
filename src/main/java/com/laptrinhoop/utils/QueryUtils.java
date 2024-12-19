package com.laptrinhoop.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@UtilityClass
public class QueryUtils {
    public static String generateQuery(Map<String, String> params) throws UnsupportedEncodingException {
        Map<String, String> treeMap = new TreeMap<>(params);
        Iterator<Map.Entry<String, String>> iterator = treeMap
                .entrySet()
                .stream()
                .filter(item -> StringUtils.isNotEmpty(item.getValue()))
                .iterator();
        StringBuilder query = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            query
                    .append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString()))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()))
                    .append(iterator.hasNext() ? "&" : "");
        }
        return query.toString();
    }
}
