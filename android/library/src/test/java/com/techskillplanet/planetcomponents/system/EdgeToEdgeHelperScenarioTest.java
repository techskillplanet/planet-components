package com.techskillplanet.planetcomponents.system;

import android.app.Activity;
import android.graphics.Color;

import com.techskillplanet.planetcomponents.PlanetRobolectricTest;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 场景：edge-to-edge 窗口 — 状态栏透明、图标明暗随页面亮度。
 */
public class EdgeToEdgeHelperScenarioTest extends PlanetRobolectricTest {

    @Test
    @Config(sdk = 34)
    public void applyWindowSetsTransparentSystemBars() {
        Activity activity = Robolectric.buildActivity(Activity.class).setup().get();
        BasicEdgeToEdgeHelper.applyWindow(activity, true);
        assertEquals(Color.TRANSPARENT, activity.getWindow().getStatusBarColor());
        assertEquals(Color.TRANSPARENT, activity.getWindow().getNavigationBarColor());
    }

    @Test
    public void lightIconsForBrightPageTheme() {
        BasicThemeManager.init(context, "sky_planet_day", "island_raised");
        assertTrue(BasicEdgeToEdgeHelper.shouldUseLightStatusBarIcons(BasicThemeManager.colors()));
    }

    @Test
    public void darkIconsForNightPageTheme() {
        BasicThemeManager.init(context, "star_planet_night", "island_raised");
        assertFalse(BasicEdgeToEdgeHelper.shouldUseLightStatusBarIcons(BasicThemeManager.colors()));
    }
}
