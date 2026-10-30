package com.techskillplanet.phonics.controls

/**
 * 控件尺寸阶梯（专业库惯例 Sm/Md/Lg）。
 * Button 面高默认对齐 [com.techskillplanet.phonics.theme.PhonicsStyleProfile.buttonFaceHeight]（Md=46）。
 * Input 高度对齐 Web `.bc-input`（Md=48）。
 */
enum class TspControlSize {
    Sm,
    Md,
    Lg,
}

fun TspControlSize.buttonFaceHeightDp(mdDefault: Float = 46f): Float = when (this) {
    TspControlSize.Sm -> 36f
    TspControlSize.Md -> mdDefault
    TspControlSize.Lg -> 52f
}

fun TspControlSize.inputHeightDp(): Float = when (this) {
    TspControlSize.Sm -> 40f
    TspControlSize.Md -> 48f
    TspControlSize.Lg -> 56f
}

fun TspControlSize.inputFontSp(): Float = when (this) {
    TspControlSize.Sm -> 14f
    TspControlSize.Md -> 16f
    TspControlSize.Lg -> 17f
}

/** 勾选盒边长：协议行常用 Sm(14)；列表 Md(20)；大触控 Lg(22)。 */
fun TspControlSize.checkboxBoxDp(): Float = when (this) {
    TspControlSize.Sm -> 14f
    TspControlSize.Md -> 20f
    TspControlSize.Lg -> 22f
}
