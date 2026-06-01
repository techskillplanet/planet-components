package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 打字机文本组件。
 *
 * <p>参考 animal-island-ui Typewriter 的逐字出现效果，适合 AI 生成结果、
 * 引导语和技趣星球文章式提示。组件只使用原生 TextView 和 Handler。</p>
 */
public class BasicTypewriterView extends TextView {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private CharSequence fullText = "";
    private int index;
    private long intervalMs = 36L;
    private boolean running;

    private final Runnable tick = new Runnable() {
        @Override
        public void run() {
            if (!running) {
                return;
            }
            index++;
            int end = Math.min(index, fullText.length());
            setText(fullText.subSequence(0, end));
            if (end < fullText.length()) {
                handler.postDelayed(this, intervalMs);
            } else {
                running = false;
            }
        }
    };

    public BasicTypewriterView(Context context) {
        this(context, null);
    }

    public BasicTypewriterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTypewriterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        refreshTheme();
    }

    /** 设置需要打字展示的文本并重新开始动画。 */
    public void setBasicText(CharSequence text) {
        fullText = text == null ? "" : text;
        start();
    }

    /** 设置打字间隔。 */
    public void setIntervalMs(long intervalMs) {
        this.intervalMs = Math.max(12L, intervalMs);
    }

    /** 当前不使用变体。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** selected=true 时重新播放。 */
    public void setSelectedState(boolean selected) {
        if (selected) {
            start();
        }
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 启动逐字动画。 */
    public void start() {
        handler.removeCallbacks(tick);
        index = 0;
        running = true;
        setText("");
        handler.post(tick);
    }

    /** 停止逐字动画并显示完整文本。 */
    public void finishImmediately() {
        handler.removeCallbacks(tick);
        running = false;
        setText(fullText);
    }

    /** 刷新文字主题。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        setTextColor(isEnabled() ? colors.textPrimary : colors.textDisabled);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        setLineSpacing(0f, 1.35f);
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacks(tick);
        running = false;
        super.onDetachedFromWindow();
    }
}
