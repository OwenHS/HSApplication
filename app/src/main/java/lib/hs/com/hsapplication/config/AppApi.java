package lib.hs.com.hsapplication.config;

import com.hs.hshttplib.titan.Titans;
import com.hs.lib.processannotation.annotation.Named;
import com.hs.lib.processannotation.annotation.ObjectInject;

/**
 * Created by owen on 16-2-4.
 */

public class AppApi {

    @ObjectInject
    @Named(name = "Titans")
    public Titans titans;

}
