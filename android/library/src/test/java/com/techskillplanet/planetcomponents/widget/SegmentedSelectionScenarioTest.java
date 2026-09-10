package com.techskillplanet.planetcomponents.widget;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.PlanetRobolectricTest;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 场景：分段 / Tabs / ChildSwitcher 选中态 — 品牌色高亮、无硬编码紫、仅变更时回调。
 */
public class SegmentedSelectionScenarioTest extends PlanetRobolectricTest {

    private static final int LEGACY_PURPLE = 0xFF9B6DFF;

    @Test
    public void selectedSegmentUsesBrandGradientNotPurple() {
        BasicSegmentedControl control = new BasicSegmentedControl(context);
        control.setOptions(Arrays.asList("日", "周", "月"), Arrays.asList("d", "w", "m"));
        control.setSelectedIndex(1, false);

        assertEquals(1, control.getSelectedIndex());
        assertEquals("w", control.getSelectedValue());

        TextView selected = (TextView) control.getChildAt(1);
        assertTrue(selected.getBackground() instanceof GradientDrawable);
        GradientDrawable gradient = (GradientDrawable) selected.getBackground();
        int[] colors = gradient.getColors();
        assertNotNull("selected gradient must expose colors", colors);
        assertTrue(colors.length >= 2);
        assertEquals(BasicThemeManager.colors().brandPrimary, colors[0]);
        assertEquals(BasicThemeManager.colors().brandPrimaryHover, colors[1]);
        assertFalse("must not use legacy purple end stop", colors[1] == LEGACY_PURPLE);

        TextView unselected = (TextView) control.getChildAt(0);
        assertEquals(BasicThemeManager.colors().textSecondary, unselected.getCurrentTextColor());
        assertEquals(BasicThemeManager.colors().textInverse, selected.getCurrentTextColor());
    }

    @Test
    public void selectingSameIndexDoesNotRenotify() {
        BasicSegmentedControl control = new BasicSegmentedControl(context);
        control.setOptions(Arrays.asList("A", "B"), Arrays.asList("a", "b"));
        AtomicInteger calls = new AtomicInteger();
        control.setOnOptionSelectedListener((index, label, value) -> calls.incrementAndGet());

        control.setSelectedIndex(0, true);
        assertEquals(0, calls.get());

        control.setSelectedIndex(1, true);
        assertEquals(1, calls.get());

        control.setSelectedIndex(1, true);
        assertEquals(1, calls.get());
    }

    @Test
    public void tabsChildSwitcherMarksSelectionAndEqualWidths() {
        BasicChildSwitcherView switcher = new BasicChildSwitcherView(context);
        switcher.setVariant("tabs");
        switcher.setItems(Arrays.asList(
                new BasicChildSwitcherView.Item("1", "悦悦"),
                new BasicChildSwitcherView.Item("2", "佑佑")
        ));
        switcher.setSelectedId("2");

        int width = 720;
        switcher.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        switcher.layout(0, 0, width, switcher.getMeasuredHeight());

        ViewGroup row = (ViewGroup) switcher.getChildAt(0);
        assertEquals(2, row.getChildCount());
        TextView first = (TextView) row.getChildAt(0);
        TextView second = (TextView) row.getChildAt(1);

        assertEquals(BasicThemeManager.colors().textSecondary, first.getCurrentTextColor());
        assertEquals(BasicThemeManager.colors().textInverse, second.getCurrentTextColor());

        int delta = Math.abs(first.getWidth() - second.getWidth());
        assertTrue("tabs segments should share width, delta=" + delta, delta <= 24);
        assertTrue(first.getWidth() > 0);
        assertTrue(second.getWidth() > 0);
    }

    @Test
    public void selectingSameChildDoesNotRenotify() {
        BasicChildSwitcherView switcher = new BasicChildSwitcherView(context);
        switcher.setItems(Arrays.asList(
                new BasicChildSwitcherView.Item("1", "悦悦"),
                new BasicChildSwitcherView.Item("2", "佑佑")
        ));
        switcher.setSelectedId("1");
        AtomicInteger calls = new AtomicInteger();
        switcher.setOnChildChangeListener(id -> calls.incrementAndGet());

        // Simulate click path: same id should early-return without notify.
        ViewGroup row = (ViewGroup) switcher.getChildAt(0);
        row.getChildAt(0).performClick();
        assertEquals(0, calls.get());

        row.getChildAt(1).performClick();
        assertEquals(1, calls.get());
        assertEquals("2", switcherSelectedId(switcher));
    }

    @Test
    public void basicTabsOnlyNotifiesOnChange() {
        BasicTabsView tabs = new BasicTabsView(context);
        tabs.setTabs(Arrays.asList("概览", "组件", "主题"));
        AtomicInteger calls = new AtomicInteger();
        tabs.setOnTabSelectedListener((index, title) -> calls.incrementAndGet());

        tabs.setSelectedIndex(0, true);
        assertEquals(0, calls.get());

        tabs.setSelectedIndex(2, true);
        assertEquals(1, calls.get());

        tabs.setSelectedIndex(2, true);
        assertEquals(1, calls.get());

        ViewGroup row = (ViewGroup) tabs.getChildAt(0);
        TextView selected = (TextView) row.getChildAt(2);
        assertEquals(BasicThemeManager.colors().textInverse, selected.getCurrentTextColor());
    }

    private static String switcherSelectedId(BasicChildSwitcherView switcher) {
        // selectedId is private — re-read via visual state (selected text color).
        ViewGroup row = (ViewGroup) switcher.getChildAt(0);
        TextView first = (TextView) row.getChildAt(0);
        TextView second = (TextView) row.getChildAt(1);
        if (first.getCurrentTextColor() == BasicThemeManager.colors().textInverse) {
            return "1";
        }
        if (second.getCurrentTextColor() == BasicThemeManager.colors().textInverse) {
            return "2";
        }
        return "";
    }
}
