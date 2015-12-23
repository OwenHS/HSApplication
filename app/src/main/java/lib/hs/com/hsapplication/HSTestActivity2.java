package lib.hs.com.hsapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hs.lib.inject.annotation.ViewInject;

import lib.hs.com.hsbaselib.HSBaseActivity;

@ViewInject(id = R.layout.content_main)
public class HSTestActivity2 extends HSBaseActivity {

    @ViewInject(id = R.id.bt_start, onClick = true)
    Button bt1;

    @ViewInject(id = R.id.bt_finish, onClick = true)
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWidgetClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                showActivity(this, HSTestActivity.class);
                break;
            case R.id.bt_finish:
                finish();
                break;
        }

    }
}
