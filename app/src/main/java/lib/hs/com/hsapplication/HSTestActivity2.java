package lib.hs.com.hsapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HSTestActivity2 extends Activity implements View.OnClickListener{

    Button bt1;

    Button bt2;

    Button bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        bt1 = (Button)findViewById(R.id.bt_start);
        bt2 = (Button)findViewById(R.id.bt_finish);
        bt3 = (Button)findViewById(R.id.bt_kill);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_start:
                Intent intent = new Intent();
                intent.setClass(this,HSTestActivity.class);
                this.startActivity(intent);
                break;
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bt_kill:
                break;
        }
    }
}
