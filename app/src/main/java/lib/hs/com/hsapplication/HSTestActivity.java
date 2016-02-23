package lib.hs.com.hsapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hs.hshttplib.titan.GaiaCommonCallback;
import com.hs.lib.inject.annotation.ViewInject;

import lib.hs.com.hsapplication.config.AppApi;
import lib.hs.com.hsapplication.httpcenter.ILoginable;
import lib.hs.com.hsbaselib.HSBaseActivity;

@ViewInject(id = R.layout.content_main)
public class HSTestActivity extends HSBaseActivity {

    @ViewInject(id = R.id.bt_start, onClick = true)
    Button bt1;

    @ViewInject(id = R.id.bt_finish, onClick = true)
    Button bt2;

    @ViewInject(id = R.id.bt_kill, onClick = true)
    Button bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWidgetClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_start:
                showActivity(this,HSTestActivity2.class);
                break;
            case R.id.bt_finish:
                requestData();
                break;
            case R.id.bt_kill:
              break;
        }
    }

    private void requestData() {

        AppApi.get(ILoginable.class).login("11.0", "11.0", "2", new GaiaCommonCallback() {
            @Override
            public void onSuccess(int code, String t) {
                Log.d("owen", "response = " + t);
            }
        });
    }
}
