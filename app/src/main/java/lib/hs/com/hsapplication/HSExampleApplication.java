package lib.hs.com.hsapplication;

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

        ObjectGraph og = ObjectGraph.create(new ModuleA());

        og.inject(apiA);
        apiA.classA.name++;
        int a1 = apiA.classA.name;
        og.inject(apiA);
        apiA.classA.name++;
        int a2 = apiA.classA.name;
    }
}
