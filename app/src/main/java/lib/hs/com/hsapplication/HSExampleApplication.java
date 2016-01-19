package lib.hs.com.hsapplication;

import android.widget.Toast;

import com.hs.lib.processannotation.ObjectGraph;

import lib.hs.com.hsbaselib.HSApplication;

/**
 * Created by owen on 15-12-8.
 */
public class HSExampleApplication extends HSApplication {

    ApiA apiA ;
    ApiB apiB ;



    @Override
    public void onCreate() {
        super.onCreate();

        apiA = new ApiA();
        apiB = new ApiB();

        ObjectGraph og = ObjectGraph.create(new ModuleA());

        og.inject(apiA);
        og.inject(apiB);
        Toast.makeText(HSExampleApplication.this, "sss = "+apiA.classA.name+"  "+apiB.classB.name, Toast.LENGTH_SHORT).show();
    }
}
