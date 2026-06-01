package com.techskillplanet.basiccontrols.theme;

/**
 * Basic Controls 的运行时尺寸、圆角、字号、阴影和动效集合。
 *
 * <p>JSON 里的 dp/sp 会在解析阶段转换成 px，因此组件可以直接使用这里的值。
 * 这样组件内部不需要重复做单位转换，也更容易保持一致。</p>
 */
public final class BasicStyle {
    /** 小圆角 px。 */
    public final float radiusSm;
    /** 中圆角 px。 */
    public final float radiusMd;
    /** 大圆角 px。 */
    public final float radiusLg;
    /** 超大圆角 px。 */
    public final float radiusXl;
    /** 岛屿风格卡片有机圆角 px。 */
    public final float radiusCardOrganic;
    /** 岛屿风格弹窗有机圆角 px。 */
    public final float radiusDialogOrganic;
    /** 岛屿风格控件圆角 px。 */
    public final float radiusControlIsland;
    /** 胶囊圆角，通常取一个极大值。 */
    public final float radiusPill;

    /** 默认边框宽度 px。 */
    public final float borderDefault;
    /** 细边框宽度 px。 */
    public final float borderHairline;
    /** 焦点边框宽度 px。 */
    public final float borderFocus;
    /** Switch 专属边框宽度 px。 */
    public final float borderSwitch;

    /** 小控件高度 px。 */
    public final float controlHeightSm;
    /** 中控件高度 px。 */
    public final float controlHeightMd;
    /** 中号按钮高度 px。 */
    public final float controlHeightButtonMedium;
    /** 大控件高度 px。 */
    public final float controlHeightLg;

    /** 小间距 px。 */
    public final float spaceSm;
    /** 中间距 px。 */
    public final float spaceMd;
    /** 大间距 px。 */
    public final float spaceLg;
    /** 超大间距 px。 */
    public final float spaceXl;

    /** 小字号 px。 */
    public final float textSm;
    /** 正文字号 px。 */
    public final float textMd;
    /** 大字号 px。 */
    public final float textLg;
    /** 标题字号 px。 */
    public final float textTitle;
    /** 弹窗标题字号 px。 */
    public final float textDialogTitle;

    /** Badge 高度 px。 */
    public final float badgeHeight;
    /** Chip 高度 px。 */
    public final float chipHeight;
    /** Toast 最小高度 px。 */
    public final float toastMinHeight;
    /** Tab 高度 px。 */
    public final float tabHeight;
    /** 表格行高 px。 */
    public final float tableRowHeight;
    /** 小号 Switch 宽度 px。 */
    public final float switchSmWidth;
    /** 小号 Switch 高度 px。 */
    public final float switchSmHeight;
    /** 小号 Switch 手柄尺寸 px。 */
    public final float switchSmHandle;
    /** 小号 Switch 轨道内字号 px。 */
    public final float switchSmInnerText;
    /** 默认 Switch 宽度 px。 */
    public final float switchMdWidth;
    /** 默认 Switch 高度 px。 */
    public final float switchMdHeight;
    /** 默认 Switch 手柄尺寸 px。 */
    public final float switchMdHandle;
    /** 默认 Switch 轨道内字号 px。 */
    public final float switchMdInnerText;

    /** 岛屿风格控件静止时的底部托起距离 px。 */
    public final float shadowControlIslandLiftY;
    /** 岛屿风格控件按下时的底部托起距离 px。 */
    public final float shadowControlPressedY;
    /** 交互按下时内容向下移动的距离 px。 */
    public final float pressedDropY;
    /** Switch 启用态透明度。 */
    public final float switchOpacityEnabled;
    /** Switch loading 态透明度。 */
    public final float switchOpacityLoading;
    /** Switch 禁用态透明度。 */
    public final float switchOpacityDisabled;
    /** Switch handle 滑动动画时长 ms。 */
    public final long switchMotionDuration;

    public BasicStyle(
            float radiusSm,
            float radiusMd,
            float radiusLg,
            float radiusXl,
            float radiusCardOrganic,
            float radiusDialogOrganic,
            float radiusControlIsland,
            float radiusPill,
            float borderDefault,
            float borderHairline,
            float borderFocus,
            float borderSwitch,
            float controlHeightSm,
            float controlHeightMd,
            float controlHeightButtonMedium,
            float controlHeightLg,
            float spaceSm,
            float spaceMd,
            float spaceLg,
            float spaceXl,
            float textSm,
            float textMd,
            float textLg,
            float textTitle,
            float textDialogTitle,
            float badgeHeight,
            float chipHeight,
            float toastMinHeight,
            float tabHeight,
            float tableRowHeight,
            float switchSmWidth,
            float switchSmHeight,
            float switchSmHandle,
            float switchSmInnerText,
            float switchMdWidth,
            float switchMdHeight,
            float switchMdHandle,
            float switchMdInnerText,
            float shadowControlIslandLiftY,
            float shadowControlPressedY,
            float pressedDropY,
            float switchOpacityEnabled,
            float switchOpacityLoading,
            float switchOpacityDisabled,
            long switchMotionDuration
    ) {
        this.radiusSm = radiusSm;
        this.radiusMd = radiusMd;
        this.radiusLg = radiusLg;
        this.radiusXl = radiusXl;
        this.radiusCardOrganic = radiusCardOrganic;
        this.radiusDialogOrganic = radiusDialogOrganic;
        this.radiusControlIsland = radiusControlIsland;
        this.radiusPill = radiusPill;
        this.borderDefault = borderDefault;
        this.borderHairline = borderHairline;
        this.borderFocus = borderFocus;
        this.borderSwitch = borderSwitch;
        this.controlHeightSm = controlHeightSm;
        this.controlHeightMd = controlHeightMd;
        this.controlHeightButtonMedium = controlHeightButtonMedium;
        this.controlHeightLg = controlHeightLg;
        this.spaceSm = spaceSm;
        this.spaceMd = spaceMd;
        this.spaceLg = spaceLg;
        this.spaceXl = spaceXl;
        this.textSm = textSm;
        this.textMd = textMd;
        this.textLg = textLg;
        this.textTitle = textTitle;
        this.textDialogTitle = textDialogTitle;
        this.badgeHeight = badgeHeight;
        this.chipHeight = chipHeight;
        this.toastMinHeight = toastMinHeight;
        this.tabHeight = tabHeight;
        this.tableRowHeight = tableRowHeight;
        this.switchSmWidth = switchSmWidth;
        this.switchSmHeight = switchSmHeight;
        this.switchSmHandle = switchSmHandle;
        this.switchSmInnerText = switchSmInnerText;
        this.switchMdWidth = switchMdWidth;
        this.switchMdHeight = switchMdHeight;
        this.switchMdHandle = switchMdHandle;
        this.switchMdInnerText = switchMdInnerText;
        this.shadowControlIslandLiftY = shadowControlIslandLiftY;
        this.shadowControlPressedY = shadowControlPressedY;
        this.pressedDropY = pressedDropY;
        this.switchOpacityEnabled = switchOpacityEnabled;
        this.switchOpacityLoading = switchOpacityLoading;
        this.switchOpacityDisabled = switchOpacityDisabled;
        this.switchMotionDuration = switchMotionDuration;
    }
}
