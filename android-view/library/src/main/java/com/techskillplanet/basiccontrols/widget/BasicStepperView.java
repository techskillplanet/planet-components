package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN Stepper 的静态进度实现，适合 3-5 步流程。
 */
public class BasicStepperView extends LinearLayout {
    private int stepCount = 3;
    private int currentStep = 1;

    public BasicStepperView(Context context) {
        this(context, null);
    }

    public BasicStepperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicStepperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        refreshTheme();
    }

    public void setSteps(int stepCount, int currentStep) {
        this.stepCount = Math.max(3, Math.min(5, stepCount));
        this.currentStep = Math.max(1, Math.min(this.stepCount, currentStep));
        refreshTheme();
    }

    public void nextStep() {
        setSteps(stepCount, Math.min(stepCount, currentStep + 1));
    }

    public void backStep() {
        setSteps(stepCount, Math.max(1, currentStep - 1));
    }

    public void refreshTheme() {
        removeAllViews();
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int nodeSize = Math.round(style.controlHeightSm);
        int lineHeight = Math.max(2, Math.round(style.borderDefault));
        for (int index = 1; index <= stepCount; index++) {
            boolean done = index < currentStep;
            boolean active = index == currentStep;
            TextView node = new TextView(getContext());
            node.setGravity(Gravity.CENTER);
            node.setText(done ? "✓" : String.valueOf(index));
            node.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            node.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
            int fill = done ? colors.statusSuccess : active ? colors.brandPrimary : colors.backgroundSurfaceRaised;
            int text = (done || active) ? colors.textInverse : colors.textTertiary;
            int stroke = active ? colors.brandPrimary : colors.borderDefault;
            node.setTextColor(text);
            node.setBackground(BasicDrawableFactory.ovalFillStroke(fill, stroke, style.borderDefault));
            addView(node, new LayoutParams(nodeSize, nodeSize));
            if (index < stepCount) {
                View line = new View(getContext());
                line.setBackgroundColor(index < currentStep ? colors.statusSuccess : colors.borderDefault);
                LayoutParams lp = new LayoutParams(0, lineHeight, 1f);
                addView(line, lp);
            }
        }
    }
}
