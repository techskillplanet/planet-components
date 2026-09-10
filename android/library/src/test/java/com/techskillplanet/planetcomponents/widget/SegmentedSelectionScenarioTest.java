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
 * 场景：分段 / Tabs / ChildSwitcher 选中态 — 白拇指 + 品牌深色字、无硬编码紫、仅变更时回调。
 */
public class SegmentedSelectionScenarioTest extends PlanetRobolectricTest {

    private static final int LEGACY_PURPLE = 0xFF9B6DFF;

    @Test
    public void selectedSegmentUsesRaisedThumbAndBrandTextNotPurple() {
        BasicSegmentedControl control = new BasicSegmentedControl(context);
        control.setOptions(Arrays.asList("日", "周", "月"), Arrays.asList("d", "w", "m"));
        control.setSelectedIndex(1, false);

        assertEquals(1, control.getSelectedIndex());
        assertEquals("w", control.getSelectedValue());

        TextView selected = (TextView) control.getChildAt(1);
        assertTrue(selected.getBackground() instanceof GradientDrawable);
        GradientDrawable thumb = (GradientDrawable) selected.getBackground();
        // Soft-track design: selected thumb is raised surface fill (not solid brand paint).
        int expectedFill = BasicThemeManager.colors().backgroundSurfaceRaised != 0
                ? BasicThemeManager.colors().backgroundSurfaceRaised
                : BasicThemeManager.colors().backgroundSurface;
        assertEquals(expectedFill, thumb.getColor().getDefaultColor());
        assertFalse("must not use legacy purple fill", thumb.getColor().getDefaultColor() == LEGACY_PURPLE);

        int expectedText = BasicThemeManager.colors().brandDark != 0
                ? BasicThemeManager.colors().brandDark
                : BasicThemeManager.colors().brandPrimary;
        assertEquals(expectedText, selected.getCurrentTextColor());
        assertFalse("selected text must not be legacy purple", selected.getCurrentTextColor() == LEGACY_PURPLE);

        TextView unselected = (TextView) control.getChildAt(0);
        assertEquals(BasicThemeManager.colors().textSecondary, unselected.getCurrentTextColor());
        assertTrue(unselected.getBackground() == null);
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

        assertTrue(switcher.getChildCount() >= 1);
        ViewGroup row = findOptionRow(switcher);
        assertNotNull(row);
        assertEquals(2, row.getChildCount());
        int w0 = row.getChildAt(0).getMeasuredWidth();
        int w1 = row.getChildAt(1).getMeasuredWidth();
        assertTrue(Math.abs(w0 - w1) <= 2);
    }

    private static ViewGroup findOptionRow(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            if (group.getChildCount() == 2 && group.getChildAt(0) instanceof TextView) {
                return group;
            }
            for (int i = 0; i < group.getChildCount(); i++) {
                ViewGroup hit = findOptionRow(group.getChildAt(i));
                if (hit != null) {
                    return hit;
                }
            }
        }
        return null;
    }
}
