package com.techskillplanet.planetcomponents.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.techskillplanet.planetcomponents.PlanetRobolectricTest;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 场景：沉浸式顶栏 — 状态栏带透明，内容带使用 surface，不整块铺死状态栏。
 */
public class TopBarImmersiveScenarioTest extends PlanetRobolectricTest {

    @Test
    public void statusBandDefaultsToTransparentAndViewBackgroundIsClear() {
        BasicTopBarView topBar = new BasicTopBarView(context);
        topBar.setTitle("Planet");
        topBar.refreshTheme();

        assertTrue("immersive status band should be transparent by default",
                topBar.isTransparentStatusBand());

        Drawable bg = topBar.getBackground();
        if (bg instanceof ColorDrawable) {
            assertEquals(Color.TRANSPARENT, ((ColorDrawable) bg).getColor());
        } else {
            assertTrue("TopBar background should be null or transparent ColorDrawable", bg == null);
        }

        assertEquals(
                BasicThemeManager.colors().backgroundSurfaceRaised,
                topBar.getContentBandColor()
        );
    }

    @Test
    public void opaqueStatusBandCanBeForcedForLegacyLayouts() {
        BasicTopBarView topBar = new BasicTopBarView(context);
        topBar.setTransparentStatusBand(false);
        assertEquals(false, topBar.isTransparentStatusBand());
        assertEquals(
                BasicThemeManager.colors().backgroundSurfaceRaised,
                topBar.getContentBandColor()
        );
    }

    @Test
    public void layoutMeasuresPositiveHeightWithoutInset() {
        BasicTopBarView topBar = new BasicTopBarView(context);
        topBar.setTitle("Inset");
        topBar.measure(
                View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        assertTrue("TopBar should measure a positive content height", topBar.getMeasuredHeight() > 0);
        assertEquals(0, topBar.getStatusBarInsetPx());
    }
}
