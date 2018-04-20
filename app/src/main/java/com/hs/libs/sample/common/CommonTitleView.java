package com.hs.libs.sample.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hs.lib.inject.HSInjectUtil;
import com.hs.lib.inject.InjectType;
import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;

/**
 * Created by huangshuo on 2018/1/19.
 */

@ViewInject(id = R.layout.layout_common_title)
public class CommonTitleView extends LinearLayout {

    /**
     * 标题
     */
    @ViewInject(id = R.id.common_tv_title)
    TextView common_tv_title;

    /**
     * 返回
     */
    @ViewInject(id = R.id.common_left)
    RelativeLayout common_left;

    /**
     * 右侧layout
     */
    @ViewInject(id = R.id.common_right)
    RelativeLayout common_right;

//    /**
//     * 右侧显示
//     */
//    @ViewInject(id = R.id.common_title_right_iv)
//    TextView common_title_right_iv;


    private HeadMode headMode = HeadMode.LEFT;

    public CommonTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        HSInjectUtil.inject(InjectType.VIEW, this, this, true);

    }

    public CommonTitleView setHeadMode(HeadMode headMode) {
        this.headMode = headMode;
        return this;
    }

    /**
     * 设置标题
     */
    public CommonTitleView setTitle(String title) {
        common_tv_title.setText(title);
        return this;
    }

    /**
     * 设置右侧名称
     */
    public CommonTitleView setRightText(String content) {
//        common_title_right_iv.setText(content);
        return this;
    }


    /**
     * 设置左侧点击事件
     */
    public CommonTitleView setOnLeftClick(View.OnClickListener listener) {
        if (listener != null) {
            common_left.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置右侧点击事件
     */
    public CommonTitleView setOnRightClick(View.OnClickListener listener) {
        if (listener != null) {
            common_right.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 头部模式
     */
    public enum HeadMode {
        LEFT, CENTER, RIGHT, SEARCH, RIGHT2
    }


}