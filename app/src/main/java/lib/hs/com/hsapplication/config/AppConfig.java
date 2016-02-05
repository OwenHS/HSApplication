package lib.hs.com.hsapplication.config;

import com.hs.hshttplib.titan.Titans;
import com.hs.lib.processannotation.annotation.Module;
import com.hs.lib.processannotation.annotation.Named;
import com.hs.lib.processannotation.annotation.ObjectProvider;
import com.hs.lib.processannotation.annotation.Singleton;

/**
 * Created by owen on 16-2-4.
 */

@Module(objectInjects = AppApi.class)
public class AppConfig {

    @ObjectProvider
    @Named(name = "Titans")
    @Singleton(isSingle = true)
    public Titans getTitans() {
        return new Titans.Builder().build();
    }
}
