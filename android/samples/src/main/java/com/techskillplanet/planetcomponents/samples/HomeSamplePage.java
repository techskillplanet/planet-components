package com.techskillplanet.planetcomponents.samples;

import com.techskillplanet.planetcomponents.widget.BasicListItemView;

/**
 * Sample 学习 Tab Page：应用说明与组件分组列表。
 * <p>
 * 设置内容在 {@link SettingsSamplePage}，通过 Shell 底部 Tab 切换（对齐 RN {@code HomePage}）。
 */
public final class HomeSamplePage {
    private HomeSamplePage() {
    }

    public static void render(SamplePageHost host) {
        addPageIntro(host);
        addComponentList(host);
    }

    private static void addPageIntro(SamplePageHost host) {
        host.content().addView(
                host.text(host.t("sample/app/title"), host.style().textTitle, host.colors().textPrimary, true),
                host.fullWidth()
        );
        host.content().addView(
                host.text(host.t("sample/app/subtitle"), host.style().textMd, host.colors().textSecondary, false),
                host.withTopMargin(6)
        );
    }

    private static void addComponentList(SamplePageHost host) {
        addComponentGroup(host, "sample/group/actions", "Button", "Chip", "IconButton", "TextLink", "Tag", "Fab");
        addComponentGroup(host, "sample/group/surfaces", "Card", "ListItem", "Empty", "Avatar", "Drawer", "Collapse", "Divider", "RefreshLayout");
        addComponentGroup(host, "sample/group/feedback", "Alert", "Badge", "Progress", "Notification", "Toast", "Modal", "LoadingDialog", "Skeleton", "Tooltip");
        addComponentGroup(
                host,
                "sample/group/inputs",
                "Input",
                "Select",
                "OptionSheet",
                "Switch",
                "Checkbox",
                "Radio",
                "SearchBar",
                "SegmentedControl",
                "StarRating",
                "PinInput",
                "Slider",
                "TextArea",
                "InputNumber",
                "TimePicker",
                "Upload"
        );
        addComponentGroup(host, "sample/group/navigation", "TopBar", "BottomTab", "Tabs", "StickyFooter", "Swiper");
        addComponentGroup(host, "sample/group/data", "Amount", "KeyValueLabel", "Stepper", "Table", "Tree", "Cascader");
        addComponentGroup(
                host,
                "sample/group/domain",
                "DatePicker",
                "ChildSwitcher",
                "ScoreRuleGrid",
                "RedeemCardGrid",
                "CalendarHeatmap",
                "PrintSheet",
                "BalanceHero",
                "CheckInStreakCard"
        );
    }

    private static void addComponentGroup(SamplePageHost host, String groupKey, String... componentIds) {
        host.addSectionTitle(host.t(groupKey));
        for (String componentId : componentIds) {
            BasicListItemView item = host.listItem(
                    "Tsp" + componentId,
                    host.t(componentDescKey(componentId)),
                    host.t("sample/nav/chevron"),
                    false,
                    false
            );
            item.setOnClickListener(view -> host.navigateComponent(componentId));
            host.content().addView(item, host.withTopMargin(10));
        }
    }

    private static String componentDescKey(String componentId) {
        return "sample/component/" + componentId.toLowerCase() + "/desc";
    }
}
