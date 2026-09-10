package com.techskillplanet.planetcomponents;

import android.content.Context;

import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Robolectric 场景测试基类：加载 library assets 中的 Sky Planet token。
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public abstract class PlanetRobolectricTest {
    protected Context context;

    @Before
    public void setUpPlanetTheme() {
        context = RuntimeEnvironment.getApplication();
        BasicThemeManager.init(context, "sky_planet_day", "island_raised");
    }
}
