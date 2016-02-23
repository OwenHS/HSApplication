package lib.hs.com.hsapplication.config;

import com.hs.hshttplib.titan.Titans;
import com.hs.lib.processannotation.annotation.Named;
import com.hs.lib.processannotation.annotation.ObjectInject;

import lib.hs.com.hsapplication.HSExampleApplication;

/**
 * Created by owen on 16-2-4.
 */

public class AppApi {

    public final static String TAG = "AppApi";

    private static String TEST_SUFFIX = "Test";

    @ObjectInject
    public Boolean debug;

    @ObjectInject
    @Named(name = "Titans")
    public Titans titans;

    public static <T> T get(Class<T> service){
       return get(service,false);
    }

    public static <T> T get(Class<T> service,boolean debug){

        String clsName = service.getName() + TEST_SUFFIX;

        if(debug){
            try {
                return service.cast(Class.forName(clsName).newInstance());
            } catch (InstantiationException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }

        return HSExampleApplication.current.appApi.titans.create(service);
    }

}
