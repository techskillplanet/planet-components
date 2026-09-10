package com.techskillplanet.planetcomponents.theme;

import com.techskillplanet.planetcomponents.PlanetRobolectricTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * 场景：token 加载与岛型 profile — 保证测试与运行时同色。
 */
public class ThemeTokenScenarioTest extends PlanetRobolectricTest {

    @Test
    public void skyThemeExposesBrandAndPageTokens() {
        BasicColors colors = BasicThemeManager.colors();
        assertEquals(0xFF31A8FF, colors.brandPrimary);
        assertEquals(0xFFDDF4FF, colors.backgroundPage);
        assertNotEquals(colors.brandPrimary, colors.statusSuccess);
        assertEquals(colors.brandPrimarySubtle, colors.selectedFill);
    }

    @Test
    public void islandRaisedAndFlatDifferOnLift() {
        BasicThemeManager.init(context, "sky_planet_day", "island_raised");
        float raisedLift = BasicThemeManager.style().shadowControlIslandLiftY;
        BasicThemeManager.init(context, "sky_planet_day", "island_flat");
        float flatLift = BasicThemeManager.style().shadowControlIslandLiftY;
        assertTrue("raised lift should be > 0", raisedLift > 0f);
        assertEquals(0f, flatLift, 0.01f);
    }
}
