package com.techskillplanet.basiccontrols.theme;

/**
 * Basic Controls 的运行时颜色集合。
 *
 * <p>这个类只保存已经解析完成的 Android int 颜色值，不保存 token 字符串。
 * 组件层不应该直接读取 JSON，也不应该硬编码十六进制颜色；所有颜色都从这里取。</p>
 */
public final class BasicColors {
    /** 品牌主色，用于主按钮、选中态、焦点边框等高优先级视觉。 */
    public final int brandPrimary;
    /** 品牌悬停/高亮色，用于 Switch 开启态、Loading 条纹等轻量高亮。 */
    public final int brandPrimaryHover;
    /** 品牌按下色，用于 pressed/active 状态。 */
    public final int brandPrimaryActive;
    /** 品牌浅底色，用于选项选中背景、弱提示背景。 */
    public final int brandPrimarySubtle;

    /** 主文字颜色。 */
    public final int textPrimary;
    /** 次级文字颜色。 */
    public final int textSecondary;
    /** 三级文字颜色，常用于占位文案或辅助说明。 */
    public final int textTertiary;
    /** 反色文字，通常用于主按钮上的白色文字。 */
    public final int textInverse;
    /** 禁用文字颜色。 */
    public final int textDisabled;

    /** 页面背景起始色。 */
    public final int backgroundPage;
    /** 页面背景渐变结束色。 */
    public final int backgroundPageGradientEnd;
    /** 普通内容面板背景。 */
    public final int backgroundSurface;
    /** 抬高面板背景，例如卡片、弹窗。 */
    public final int backgroundSurfaceRaised;
    /** 弱面板背景。 */
    public final int backgroundSurfaceSubtle;
    /** 禁用态背景。 */
    public final int backgroundSurfaceDisabled;
    /** 菜单/下拉面板背景。 */
    public final int backgroundMenu;
    /** 弹窗遮罩颜色。 */
    public final int backgroundModalScrim;

    /** 默认边框颜色。 */
    public final int borderDefault;
    /** 浅边框颜色。 */
    public final int borderLight;
    /** 控件边框颜色。 */
    public final int borderControl;
    /** 焦点边框颜色。 */
    public final int borderFocus;
    /** 警告边框/状态颜色。 */
    public final int borderWarning;
    /** 危险边框/状态颜色。 */
    public final int borderDanger;

    /** 成功状态色。 */
    public final int statusSuccess;
    /** 警告状态色。 */
    public final int statusWarning;
    /** 危险状态色。 */
    public final int statusDanger;

    /** 默认按钮背景。 */
    public final int buttonDefaultBackground;
    /** 默认按钮文字。 */
    public final int buttonDefaultText;
    /** 主按钮背景。 */
    public final int buttonPrimaryBackground;
    /** 主按钮文字。 */
    public final int buttonPrimaryText;
    /** 岛屿风格按钮底部托起阴影颜色。 */
    public final int buttonRaisedShadow;

    /** Checkbox 默认背景。 */
    public final int checkboxBackground;
    /** Checkbox 选中背景。 */
    public final int checkboxCheckedBackground;
    /** Radio 选中圆点颜色。 */
    public final int radioCheckedDot;
    /** Switch 关闭背景。 */
    public final int switchOffBackground;
    /** Switch 关闭边框。 */
    public final int switchOffBorder;
    /** Switch 关闭态内文案。 */
    public final int switchOffText;
    /** Switch 开启背景。 */
    public final int switchOnBackground;
    /** Switch 开启边框。 */
    public final int switchOnBorder;
    /** Switch 开启态内文案。 */
    public final int switchOnText;
    /** Switch 圆形手柄背景。 */
    public final int switchHandleBackground;
    /** Switch 圆形手柄关闭态边框。 */
    public final int switchHandleBorder;
    /** Switch 圆形手柄开启态边框。 */
    public final int switchHandleCheckedBorder;
    /** Switch 加载 spinner 颜色。 */
    public final int switchLoadingSpinner;
    /** Select 已选项背景。 */
    public final int selectSelectedOptionBackground;
    /** Loading 主条纹颜色。 */
    public final int loadingStripePrimary;
    /** Loading 辅助条纹颜色。 */
    public final int loadingStripeSecondary;
    /** Loading 边框颜色。 */
    public final int loadingBorder;

    public BasicColors(
            int brandPrimary,
            int brandPrimaryHover,
            int brandPrimaryActive,
            int brandPrimarySubtle,
            int textPrimary,
            int textSecondary,
            int textTertiary,
            int textInverse,
            int textDisabled,
            int backgroundPage,
            int backgroundPageGradientEnd,
            int backgroundSurface,
            int backgroundSurfaceRaised,
            int backgroundSurfaceSubtle,
            int backgroundSurfaceDisabled,
            int backgroundMenu,
            int backgroundModalScrim,
            int borderDefault,
            int borderLight,
            int borderControl,
            int borderFocus,
            int borderWarning,
            int borderDanger,
            int statusSuccess,
            int statusWarning,
            int statusDanger,
            int buttonDefaultBackground,
            int buttonDefaultText,
            int buttonPrimaryBackground,
            int buttonPrimaryText,
            int buttonRaisedShadow,
            int checkboxBackground,
            int checkboxCheckedBackground,
            int radioCheckedDot,
            int switchOffBackground,
            int switchOffBorder,
            int switchOffText,
            int switchOnBackground,
            int switchOnBorder,
            int switchOnText,
            int switchHandleBackground,
            int switchHandleBorder,
            int switchHandleCheckedBorder,
            int switchLoadingSpinner,
            int selectSelectedOptionBackground,
            int loadingStripePrimary,
            int loadingStripeSecondary,
            int loadingBorder
    ) {
        this.brandPrimary = brandPrimary;
        this.brandPrimaryHover = brandPrimaryHover;
        this.brandPrimaryActive = brandPrimaryActive;
        this.brandPrimarySubtle = brandPrimarySubtle;
        this.textPrimary = textPrimary;
        this.textSecondary = textSecondary;
        this.textTertiary = textTertiary;
        this.textInverse = textInverse;
        this.textDisabled = textDisabled;
        this.backgroundPage = backgroundPage;
        this.backgroundPageGradientEnd = backgroundPageGradientEnd;
        this.backgroundSurface = backgroundSurface;
        this.backgroundSurfaceRaised = backgroundSurfaceRaised;
        this.backgroundSurfaceSubtle = backgroundSurfaceSubtle;
        this.backgroundSurfaceDisabled = backgroundSurfaceDisabled;
        this.backgroundMenu = backgroundMenu;
        this.backgroundModalScrim = backgroundModalScrim;
        this.borderDefault = borderDefault;
        this.borderLight = borderLight;
        this.borderControl = borderControl;
        this.borderFocus = borderFocus;
        this.borderWarning = borderWarning;
        this.borderDanger = borderDanger;
        this.statusSuccess = statusSuccess;
        this.statusWarning = statusWarning;
        this.statusDanger = statusDanger;
        this.buttonDefaultBackground = buttonDefaultBackground;
        this.buttonDefaultText = buttonDefaultText;
        this.buttonPrimaryBackground = buttonPrimaryBackground;
        this.buttonPrimaryText = buttonPrimaryText;
        this.buttonRaisedShadow = buttonRaisedShadow;
        this.checkboxBackground = checkboxBackground;
        this.checkboxCheckedBackground = checkboxCheckedBackground;
        this.radioCheckedDot = radioCheckedDot;
        this.switchOffBackground = switchOffBackground;
        this.switchOffBorder = switchOffBorder;
        this.switchOffText = switchOffText;
        this.switchOnBackground = switchOnBackground;
        this.switchOnBorder = switchOnBorder;
        this.switchOnText = switchOnText;
        this.switchHandleBackground = switchHandleBackground;
        this.switchHandleBorder = switchHandleBorder;
        this.switchHandleCheckedBorder = switchHandleCheckedBorder;
        this.switchLoadingSpinner = switchLoadingSpinner;
        this.selectSelectedOptionBackground = selectSelectedOptionBackground;
        this.loadingStripePrimary = loadingStripePrimary;
        this.loadingStripeSecondary = loadingStripeSecondary;
        this.loadingBorder = loadingBorder;
    }
}
