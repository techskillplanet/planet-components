package com.techskillplanet.planetcomponents.widget;

import com.techskillplanet.planetcomponents.PlanetRobolectricTest;
import com.techskillplanet.planetcomponents.icon.PlanetIcons;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 场景：Planet 图标目录 — glyph 与 catalog 对齐。
 */
public class IconCatalogScenarioTest extends PlanetRobolectricTest {

    @Test
    public void catalogExposesCoreNames() {
        assertTrue(PlanetIcons.names().contains("back"));
        assertTrue(PlanetIcons.names().contains("check"));
        assertTrue(PlanetIcons.names().contains("close"));
        assertTrue(PlanetIcons.names().contains("chevron"));
        assertTrue(PlanetIcons.names().contains("warning"));
    }

    @Test
    public void basicIconViewUsesGlyph() {
        BasicIconView icon = new BasicIconView(context);
        icon.setIconName("check");
        assertEquals("check", icon.getIconName());
        assertEquals(PlanetIcons.get("check").glyph, icon.getText().toString());

        icon.setIconName("close");
        assertEquals(PlanetIcons.get("close").glyph, icon.getText().toString());
    }
}
