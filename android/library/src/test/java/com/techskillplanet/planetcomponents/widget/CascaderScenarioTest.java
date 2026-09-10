package com.techskillplanet.planetcomponents.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.PlanetRobolectricTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 场景：Cascader 展开分支 → 选叶子 → value / listener。
 */
public class CascaderScenarioTest extends PlanetRobolectricTest {

    @Test
    public void pickEuropeThenFranceUpdatesValueAndNotifies() {
        BasicCascaderView cascader = new BasicCascaderView(context);
        cascader.setOptions(Arrays.asList(
                new BasicCascaderView.Option("asia", "亚洲", Arrays.asList(
                        new BasicCascaderView.Option("cn", "中国", Collections.emptyList()),
                        new BasicCascaderView.Option("jp", "日本", Collections.emptyList())
                )),
                new BasicCascaderView.Option("eu", "欧洲", Collections.singletonList(
                        new BasicCascaderView.Option("fr", "法国", Collections.emptyList())
                ))
        ));

        AtomicReference<List<String>> values = new AtomicReference<>();
        AtomicReference<List<String>> labels = new AtomicReference<>();
        cascader.setOnChangeListener((value, labs) -> {
            values.set(new ArrayList<>(value));
            labels.set(new ArrayList<>(labs));
        });

        TextView trigger = (TextView) cascader.getChildAt(0);
        trigger.performClick();

        ViewGroup panelScroll = (ViewGroup) cascader.getChildAt(1);
        assertEquals(View.VISIBLE, panelScroll.getVisibility());

        TextView europe = findTextView(cascader, "欧洲");
        assertNotNull(europe);
        europe.performClick();

        TextView france = findTextView(cascader, "法国");
        assertNotNull("panel should include 法国 after picking 欧洲", france);
        france.performClick();

        assertEquals(Arrays.asList("eu", "fr"), cascader.getValue());
        assertEquals(Arrays.asList("eu", "fr"), values.get());
        assertEquals(Arrays.asList("欧洲", "法国"), labels.get());
        assertEquals(View.GONE, panelScroll.getVisibility());
    }

    @Test
    public void openShowsAsiaAndEurope() {
        BasicCascaderView cascader = new BasicCascaderView(context);
        cascader.setOptions(Arrays.asList(
                new BasicCascaderView.Option("asia", "亚洲", Collections.singletonList(
                        new BasicCascaderView.Option("cn", "中国", Collections.emptyList())
                )),
                new BasicCascaderView.Option("eu", "欧洲", Collections.singletonList(
                        new BasicCascaderView.Option("fr", "法国", Collections.emptyList())
                ))
        ));

        ((TextView) cascader.getChildAt(0)).performClick();
        assertNotNull(findTextView(cascader, "亚洲"));
        assertNotNull(findTextView(cascader, "欧洲"));
        assertTrue(((ViewGroup) cascader.getChildAt(1)).getVisibility() == View.VISIBLE);
    }

    private static TextView findTextView(View root, String text) {
        if (root instanceof TextView) {
            CharSequence content = ((TextView) root).getText();
            if (content != null && text.equals(content.toString())) {
                return (TextView) root;
            }
        }
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                TextView hit = findTextView(group.getChildAt(i), text);
                if (hit != null) {
                    return hit;
                }
            }
        }
        return null;
    }
}
