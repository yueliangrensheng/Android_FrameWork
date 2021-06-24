package com.yazao.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.view.ViewCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SystemBarTintManager {
    private static String sNavBarOverride;
    private final SystemBarConfig mConfig;
    private boolean mNavBarAvailable;
    private boolean mNavBarTintEnabled;
    private View mNavBarTintView;
    private boolean mStatusBarAvailable;
    private boolean mStatusBarTintEnabled;
    private View mStatusBarTintView;

    static {
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
                declaredMethod.setAccessible(true);
                sNavBarOverride = (String) declaredMethod.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable unused) {
                sNavBarOverride = null;
            }
        }
    }

    public SystemBarTintManager(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        if (Build.VERSION.SDK_INT >= 21) {
            window.getDecorView().setSystemUiVisibility(1280);
            window.clearFlags(67108864);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
            this.mStatusBarAvailable = true;
            this.mNavBarAvailable = false;
        } else if (Build.VERSION.SDK_INT >= 19) {
            TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(new int[]{16843759, 16843760});
            try {
                this.mStatusBarAvailable = obtainStyledAttributes.getBoolean(0, false);
                this.mNavBarAvailable = obtainStyledAttributes.getBoolean(1, false);
                obtainStyledAttributes.recycle();
                WindowManager.LayoutParams attributes = window.getAttributes();
                if ((attributes.flags & 67108864) != 0) {
                    this.mStatusBarAvailable = true;
                }
                if ((attributes.flags & 134217728) != 0) {
                    this.mNavBarAvailable = true;
                }
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        }
        this.mConfig = new SystemBarConfig(activity, this.mStatusBarAvailable, this.mNavBarAvailable);
        if (!this.mConfig.hasNavigtionBar()) {
            this.mNavBarAvailable = false;
        }
        if (this.mStatusBarAvailable) {
            setupStatusBarView(activity, viewGroup);
        }
        if (this.mNavBarAvailable) {
            setupNavBarView(activity, viewGroup);
        }
    }

    public void setStatusBarTintEnabled(boolean z) {
        this.mStatusBarTintEnabled = z;
        if (this.mStatusBarAvailable) {

            this.mStatusBarTintView.setVisibility(z ? View.VISIBLE : View.GONE);
        }
    }

    public void setNavigationBarTintEnabled(boolean z) {
        this.mNavBarTintEnabled = z;
        if (this.mNavBarAvailable) {
            this.mNavBarTintView.setVisibility(z ? View.VISIBLE : View.GONE);
        }
    }

    public void setTintColor(int i) {
        setStatusBarTintColor(i);
        setNavigationBarTintColor(i);
    }

    public void setTintResource(int i) {
        setStatusBarTintResource(i);
        setNavigationBarTintResource(i);
    }

    public void setTintDrawable(Drawable drawable) {
        setStatusBarTintDrawable(drawable);
        setNavigationBarTintDrawable(drawable);
    }

    public void setTintAlpha(float f) {
        setStatusBarAlpha(f);
        setNavigationBarAlpha(f);
    }

    public void setStatusBarTintColor(@ColorInt int color) {
        if (this.mStatusBarAvailable) {
            this.mStatusBarTintView.setBackgroundColor(color);
        }
    }

    public void setStatusBarTintResource(@DrawableRes int resid) {
        if (this.mStatusBarAvailable) {
            this.mStatusBarTintView.setBackgroundResource(resid);
        }
    }

    public void setStatusBarColorAuto(Activity activity) {
        Window window = activity.getWindow();
        if (setFlymeStatusBarLightMode(window, true) || setMiUIStatusBarLightMode(window, true) || setStatusBarLightMode(window, true)) {
            setStatusBarTintColor(-1);
        } else {
            setStatusBarTintColor(ViewCompat.MEASURED_STATE_MASK);
        }
    }

    public void setStatusFontWhite(Activity activity) {
        Window window = activity.getWindow();
        if (!setFlymeStatusBarLightMode(window, false) && !setMiUIStatusBarLightMode(window, false)) {
            setStatusBarLightMode(window, false);
        }
    }

    public void setStatusFontBlack(Activity activity) {
        Window window = activity.getWindow();
        if (!setFlymeStatusBarLightMode(window, true) && !setMiUIStatusBarLightMode(window, true)) {
            setStatusBarLightMode(window, true);
        }
    }

    private static boolean setStatusBarLightMode(Window window, boolean z) {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        if (z) {
            window.getDecorView().setSystemUiVisibility(9216);
            return true;
        }
        window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() & -8193);
        return true;
    }

    private static boolean setMiUIStatusBarLightMode(Window window, boolean z) {
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                if (z) {
                    method.invoke(window, Integer.valueOf(i), Integer.valueOf(i));
                } else {
                    method.invoke(window, 0, Integer.valueOf(i));
                }
                setStatusBarLightMode(window, z);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private static boolean setFlymeStatusBarLightMode(Window window, boolean z) {
        if (window != null) {
            try {
                WindowManager.LayoutParams attributes = window.getAttributes();
                Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                int i = declaredField.getInt(null);
                int i2 = declaredField2.getInt(attributes);
                declaredField2.setInt(attributes, z ? i | i2 : (~i) & i2);
                window.setAttributes(attributes);
                setStatusBarLightMode(window, z);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public void setStatusBarTintDrawable(Drawable drawable) {
        if (this.mStatusBarAvailable) {
            this.mStatusBarTintView.setBackgroundDrawable(drawable);
        }
    }

    public void setStatusBarAlpha(float f) {
        if (this.mStatusBarAvailable && Build.VERSION.SDK_INT >= 11) {
            this.mStatusBarTintView.setAlpha(f);
        }
    }

    public void setNavigationBarTintColor(int i) {
        if (this.mNavBarAvailable) {
            this.mNavBarTintView.setBackgroundColor(i);
        }
    }

    public void setNavigationBarTintResource(int i) {
        if (this.mNavBarAvailable) {
            this.mNavBarTintView.setBackgroundResource(i);
        }
    }

    public void setNavigationBarTintDrawable(Drawable drawable) {
        if (this.mNavBarAvailable) {
            this.mNavBarTintView.setBackgroundDrawable(drawable);
        }
    }

    public void setNavigationBarAlpha(float f) {
        if (this.mNavBarAvailable && Build.VERSION.SDK_INT >= 11) {
            this.mNavBarTintView.setAlpha(f);
        }
    }

    public SystemBarConfig getConfig() {
        return this.mConfig;
    }

    public boolean isStatusBarTintEnabled() {
        return this.mStatusBarTintEnabled;
    }

    public boolean isNavBarTintEnabled() {
        return this.mNavBarTintEnabled;
    }

    private void setupStatusBarView(Context context, ViewGroup viewGroup) {
        this.mStatusBarTintView = new View(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, this.mConfig.getStatusBarHeight());
        layoutParams.gravity = 48;
        if (this.mNavBarAvailable && !this.mConfig.isNavigationAtBottom()) {
            layoutParams.rightMargin = this.mConfig.getNavigationBarWidth();
        }
        this.mStatusBarTintView.setLayoutParams(layoutParams);
        this.mStatusBarTintView.setBackgroundColor(-1728053248);
        this.mStatusBarTintView.setVisibility(View.GONE);
        viewGroup.addView(this.mStatusBarTintView);
    }

    private void setupNavBarView(Context context, ViewGroup viewGroup) {
        FrameLayout.LayoutParams layoutParams;
        this.mNavBarTintView = new View(context);
        if (this.mConfig.isNavigationAtBottom()) {
            layoutParams = new FrameLayout.LayoutParams(-1, this.mConfig.getNavigationBarHeight());
            layoutParams.gravity = 80;
        } else {
            layoutParams = new FrameLayout.LayoutParams(this.mConfig.getNavigationBarWidth(), -1);
            layoutParams.gravity = 5;
        }
        this.mNavBarTintView.setLayoutParams(layoutParams);
        this.mNavBarTintView.setBackgroundColor(-1728053248);
        this.mNavBarTintView.setVisibility(View.GONE);
        viewGroup.addView(this.mNavBarTintView);
    }

    public static class SystemBarConfig {
        private final int mActionBarHeight;
        private final boolean mHasNavigationBar;
        private final boolean mInPortrait;
        private final int mNavigationBarHeight;
        private final int mNavigationBarWidth;
        private final float mSmallestWidthDp;
        private final int mStatusBarHeight;
        private final boolean mTranslucentNavBar;
        private final boolean mTranslucentStatusBar;

        private SystemBarConfig(Activity activity, boolean z, boolean z2) {
            Resources resources = activity.getResources();
            boolean z3 = false;
            this.mInPortrait = resources.getConfiguration().orientation == 1;
            this.mSmallestWidthDp = getSmallestWidthDp(activity);
            this.mStatusBarHeight = getInternalDimensionSize(resources, "status_bar_height");
            this.mActionBarHeight = getActionBarHeight(activity);
            this.mNavigationBarHeight = getNavigationBarHeight(activity);
            this.mNavigationBarWidth = getNavigationBarWidth(activity);
            this.mHasNavigationBar = this.mNavigationBarHeight > 0 ? true : z3;
            this.mTranslucentStatusBar = z;
            this.mTranslucentNavBar = z2;
        }

        private int getActionBarHeight(Context context) {
            if (Build.VERSION.SDK_INT < 14) {
                return 0;
            }
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16843499, typedValue, true);
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }

        private int getNavigationBarHeight(Context context) {
            Resources resources = context.getResources();
            if (Build.VERSION.SDK_INT < 14 || !hasNavBar(context)) {
                return 0;
            }
            return getInternalDimensionSize(resources, this.mInPortrait ? "navigation_bar_height" : "navigation_bar_height_landscape");
        }

        private int getNavigationBarWidth(Context context) {
            Resources resources = context.getResources();
            if (Build.VERSION.SDK_INT < 14 || !hasNavBar(context)) {
                return 0;
            }
            return getInternalDimensionSize(resources, "navigation_bar_width");
        }

        private boolean hasNavBar(Context context) {
            Resources resources = context.getResources();
            int identifier = resources.getIdentifier("config_showNavigationBar", "bool", "android");
            if (identifier == 0) {
                return !ViewConfiguration.get(context).hasPermanentMenuKey();
            }
            boolean z = resources.getBoolean(identifier);
            if ("1".equals(SystemBarTintManager.sNavBarOverride)) {
                return false;
            }
            if ("0".equals(SystemBarTintManager.sNavBarOverride)) {
                return true;
            }
            return z;
        }

        public static int getInternalDimensionSize(Resources resources, String str) {
            int identifier = resources.getIdentifier(str, "dimen", "android");
            if (identifier > 0) {
                return resources.getDimensionPixelSize(identifier);
            }
            try {
                Class<?> cls = Class.forName("com.android.internal.R$dimen");
                int i = toInt(cls.getField("status_bar_height").get(cls.newInstance()).toString(), 0);
                if (i > 0) {
                    return resources.getDimensionPixelSize(i);
                }
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        private static int toInt(String str, int i) {
            if (!(str == null || str.length() == 0 || str.equals("null"))) {
                try {
//                    return Integer.parseInt(str.trim().replace(",", com.tencent.imsdk.BuildConfig.FLAVOR));
                    return Integer.parseInt(str.trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return i;
        }


        private float getSmallestWidthDp(Activity activity) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= 16) {
                activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            } else {
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            }
            return Math.min(((float) displayMetrics.widthPixels) / displayMetrics.density, ((float) displayMetrics.heightPixels) / displayMetrics.density);
        }

        public boolean isNavigationAtBottom() {
            return this.mSmallestWidthDp >= 600.0f || this.mInPortrait;
        }

        public int getStatusBarHeight() {
            return this.mStatusBarHeight;
        }

        public int getActionBarHeight() {
            return this.mActionBarHeight;
        }

        public boolean hasNavigtionBar() {
            return this.mHasNavigationBar;
        }

        public int getNavigationBarHeight() {
            return this.mNavigationBarHeight;
        }

        public int getNavigationBarWidth() {
            return this.mNavigationBarWidth;
        }

        public int getPixelInsetTop(boolean z) {
            int i = 0;
            int i2 = this.mTranslucentStatusBar ? this.mStatusBarHeight : 0;
            if (z) {
                i = this.mActionBarHeight;
            }
            return i2 + i;
        }

        public int getPixelInsetBottom() {
            if (!this.mTranslucentNavBar || !isNavigationAtBottom()) {
                return 0;
            }
            return this.mNavigationBarHeight;
        }

        public int getPixelInsetRight() {
            if (!this.mTranslucentNavBar || isNavigationAtBottom()) {
                return 0;
            }
            return this.mNavigationBarWidth;
        }
    }
}
