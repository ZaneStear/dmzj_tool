package com.mirroring.units;

import org.apache.commons.lang.StringEscapeUtils;

public class StringUnits {
    public static String replaceHtmlTags(String text) {
        text = text.replaceAll("<br />", "\n");
        text = text.replaceAll("<br/>", "\n");
        text = StringEscapeUtils.unescapeHtml(text);
        return text;
    }
}
