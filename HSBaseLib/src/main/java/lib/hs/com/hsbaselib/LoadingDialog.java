package lib.hs.com.hsbaselib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hs.tools.common.utils.DeviceUtils;

/**
 * Author：KUN
 * Time: 2016/3/7 14:11
 * Email：zhangkun@haiye.com
 */
public class LoadingDialog extends Dialog {

    TextView mMessageTextView;

    public LoadingDialog(Context context) {
        super(context);
        init(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.loading_dialog_layout, null);
        setContentView(viewGroup);

        mMessageTextView = (TextView) viewGroup.findViewById(R.id.m_message_text_view);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = (int) (DeviceUtils.getScreenWidth() * 0.7f);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }

    public void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            mMessageTextView.setText(message);
        }
    }
}
