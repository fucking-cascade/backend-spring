package org.latheild.common.utils;

import static org.latheild.common.constant.BaseURI.BASE_URI;

public class CombineURI {
    public static String combineURI(String serviceURI, String type) {
        return BASE_URI + serviceURI + type;
    }
}
