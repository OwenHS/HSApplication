package lib.hs.com.hsapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hs.lib.http.processannotation.annotation.Module;
import com.hs.lib.http.processannotation.annotation.ObjectProvider;
import com.hs.lib.inject.annotation.ViewInject;

import lib.hs.com.hsbaselib.HSBaseActivity;

@ViewInject(id = R.layout.content_main)
public class HSTestActivity extends HSBaseActivity {

    @ViewInject(id = R.id.bt_start, onClick = true)
    Button bt1;

    @ViewInject(id = R.id.bt_finish, onClick = true)
    Button bt2;

    @ViewInject(id = R.id.bt_kill, onClick = true)
    Button bt3;

    @ObjectProvider()
    @Module()
    TextView ttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bt1.setText("llll");
    }

    @Override
    public void onWidgetClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_start:
                showActivity(this,HSTestActivity.class);
                break;
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bt_kill:
              break;

        }

    }


}
