package com.techskillplanet.basiccontrols.i18n;

import android.content.Context;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic Controls 的轻量 JSON 国际化入口。
 *
 * <p>JSON 结构与业务侧 local.json 保持一致：顶层是语言码，语言码下是文案 key-value。
 * 组件只读取这里的当前语言缓存，不使用 Android resources 国际化。</p>
 */
public final class BasicI18nManager {
    private static final String DEFAULT_LANGUAGE = "zh-CN";

    private static JSONObject root;
    private static String currentLanguage = DEFAULT_LANGUAGE;

    private BasicI18nManager() {
        // 工具类不允许实例化。
    }

    public static void init(Context context, String assetPath, String language) {
        try {
            root = new JSONObject(readAsset(context.getApplicationContext(), assetPath));
            setLanguage(language);
        } catch (Exception error) {
            throw new IllegalStateException("BasicControls 国际化初始化失败", error);
        }
    }

    public static void setLanguage(String language) {
        if (root == null) {
            currentLanguage = language == null ? DEFAULT_LANGUAGE : language;
            return;
        }
        if (language != null && root.has(language)) {
            currentLanguage = language;
            return;
        }
        currentLanguage = root.has(DEFAULT_LANGUAGE) ? DEFAULT_LANGUAGE : firstLanguage();
    }

    public static String language() {
        return currentLanguage;
    }

    public static List<String> languages() {
        List<String> values = new ArrayList<>();
        if (root == null) {
            values.add(DEFAULT_LANGUAGE);
            return values;
        }
        java.util.Iterator<String> keys = root.keys();
        while (keys.hasNext()) {
            values.add(keys.next());
        }
        return values;
    }

    public static String text(String key) {
        return text(key, key);
    }

    public static String text(String key, String fallback) {
        if (key == null || key.length() == 0) {
            return fallback == null ? "" : fallback;
        }
        if (root == null) {
            return fallback == null ? key : fallback;
        }
        String value = lookup(currentLanguage, key);
        if (value != null) {
            return value;
        }
        value = lookup(DEFAULT_LANGUAGE, key);
        if (value != null) {
            return value;
        }
        return fallback == null ? key : fallback;
    }

    private static String lookup(String language, String key) {
        if (language == null || !root.has(language)) {
            return null;
        }
        JSONObject table = root.optJSONObject(language);
        if (table == null || !table.has(key)) {
            return null;
        }
        return table.optString(key, null);
    }

    private static String firstLanguage() {
        java.util.Iterator<String> keys = root.keys();
        return keys.hasNext() ? keys.next() : DEFAULT_LANGUAGE;
    }

    private static String readAsset(Context context, String path) throws IOException {
        try (InputStream input = context.getAssets().open(path);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        }
    }
}
