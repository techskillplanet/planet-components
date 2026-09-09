package com.techskillplanet.planetcomponents.samples;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.widget.BasicButton;
import com.techskillplanet.planetcomponents.widget.BasicCardView;

import java.util.Locale;

/**
 * 偏好设置 Sample Tab Page：主题与语言切换（对齐 RN {@code SettingsPage}）。
 */
public final class SettingsSamplePage {
    private static final String[][] LANGUAGE_OPTIONS = {
            {"sample/lang/zh-CN", "zh-CN"},
            {"sample/lang/en", "en"},
            {"sample/lang/ja", "ja"},
    };

/** 内置主题预设：colorToken × styleProfile，与 RN {@code builtInThemePresets} 对齐。 */
    public static final ThemePreset[] THEME_PRESETS = {
            new ThemePreset("sample/theme/sky_raised", "sky_planet_day", "island_raised"),
            new ThemePreset("sample/theme/sky_flat", "sky_planet_day", "island_flat"),
            new ThemePreset("sample/theme/star_raised", "star_planet_night", "island_raised"),
            new ThemePreset("sample/theme/star_flat", "star_planet_night", "island_flat"),
            new ThemePreset("sample/theme/mint_raised", "mint_planet_day", "island_raised"),
            new ThemePreset("sample/theme/mint_flat", "mint_planet_day", "island_flat"),
            new ThemePreset("sample/theme/sunrise_raised", "sunrise_planet_day", "island_raised"),
            new ThemePreset("sample/theme/sunrise_flat", "sunrise_planet_day", "island_flat"),
    };

    private SettingsSamplePage() {
    }

    /** 入口：由 {@link MainActivity#renderCurrentPage()} 在 route=settings 时调用。 */
    public static void render(SamplePageHost host) {
        addThemeSection(host);
        addLanguageSection(host);
    }

    private static void addThemeSection(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/settings/theme/title"));
        BasicCardView card = new BasicCardView(host.getActivity());
        LinearLayout inner = new LinearLayout(host.getActivity());
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.addView(host.text(
                host.t("sample/settings/theme/current", host.currentThemeLabel()),
                host.style().textMd,
                host.colors().textPrimary,
                true
        ), host.fullWidth());
        inner.addView(host.text(
                host.t("sample/settings/theme/hint"),
                host.style().textSm,
                host.colors().textSecondary,
                false
        ), host.withTopMargin(4));
        inner.addView(host.text(
                host.t("sample/settings/theme/hint_token"),
                host.style().textSm,
                host.colors().textSecondary,
                false
        ), host.withTopMargin(4));
        inner.addView(host.button(
                host.t("sample/settings/theme/preview_button"),
                BasicButton.VARIANT_PRIMARY,
                false
        ), host.withTopMargin(12));

        LinearLayout actions = new LinearLayout(host.getActivity());
        actions.setOrientation(LinearLayout.VERTICAL);
        for (ThemePreset preset : THEME_PRESETS) {
            actions.addView(host.themeButton(preset), host.withTopMargin(10));
        }
        inner.addView(actions, host.fullWidth());
        card.addView(inner, host.fullWidth());
        host.content().addView(card, host.withTopMargin(10));
    }

    private static void addLanguageSection(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/settings/language/title"));
        BasicCardView card = new BasicCardView(host.getActivity());
        LinearLayout inner = new LinearLayout(host.getActivity());
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.addView(host.text(
                host.t("sample/settings/language/current", host.currentLanguage()),
                host.style().textMd,
                host.colors().textPrimary,
                true
        ), host.fullWidth());
        inner.addView(host.text(
                host.t("sample/settings/language/hint"),
                host.style().textSm,
                host.colors().textSecondary,
                false
        ), host.withTopMargin(4));
        for (String[] option : LANGUAGE_OPTIONS) {
            inner.addView(host.languageButton(host.t(option[0]), option[1]), host.withTopMargin(10));
        }
        card.addView(inner, host.fullWidth());
        host.content().addView(card, host.withTopMargin(10));
    }

    /** 内置主题预设。 */
    public static final class ThemePreset {
        public final String labelKey;
        public final String colorTheme;
        public final String styleProfile;

        public ThemePreset(String labelKey, String colorTheme, String styleProfile) {
            this.labelKey = labelKey;
            this.colorTheme = colorTheme;
            this.styleProfile = styleProfile;
        }

        public String label(SamplePageHost host) {
            return host.t(labelKey);
        }
    }
}
