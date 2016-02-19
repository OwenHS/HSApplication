package lib.hs.com.hsapplication.config;

import com.hs.hshttplib.titan.Titans;
import com.hs.lib.processannotation.annotation.Module;
import com.hs.lib.processannotation.annotation.Named;
import com.hs.lib.processannotation.annotation.ObjectProvider;

@Module(objectInjects = AppApi.class)
public class AppConfig {

    @ObjectProvider
    @Named(name = "Titans")
    public Titans getTitans() {
        return new Titans.Builder().setBaseUrl("http://fucheng.hkgb08.16data.com/aixue/index.php/home/index").build();
    }


}