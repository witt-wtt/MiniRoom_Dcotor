package com.witt.doctor_miniroom.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * @author Witt Create by 2018/9/28 14:34
 * @e-mail wittvip@163.com
 * @copyright 版权归家有一生咨询服务有限公司所有
 * 自定义对话框
 */
public class CenterDialog extends Dialog {
    public static String flag;

    // 默认高度
    public CenterDialog(Context context, int width, int height, int layout, int style) {
        this(context, flag, width, height, layout, style);
    }

    public CenterDialog(Context context, String flag, int width, int height, int layout, int style) {
        super(context, style);
        setContentView(layout);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = height;
        params.width = width;
        if (flag.equals("Bottom")) {
            params.gravity = Gravity.BOTTOM;
        } else if (flag.equals("Center")) {
            params.gravity = Gravity.CENTER;
        }
        getWindow().setAttributes(params);
    }

}
