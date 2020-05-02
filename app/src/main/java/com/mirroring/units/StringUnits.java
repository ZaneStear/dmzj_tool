package com.mirroring.units;

import android.service.autofill.FieldClassification;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUnits {
    public static String replaceHtmlTags(String text) {
        text = text.replaceAll("<br />", "\n");
        text = text.replaceAll("<br/>", "\n");
        text = StringEscapeUtils.unescapeHtml(text);
        return text;
    }

    public static boolean isCorrectFileName(String fileName) {
        Pattern pattern = Pattern.compile("(\\d+)_(\\d+)_(\\d+).txt");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
