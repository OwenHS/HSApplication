package lib.hs.com.hsapplication;

import com.hs.hshttplib.HSHttp;
import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.titan.Titans;
import com.hs.lib.processannotation.ObjectGraph;

import java.util.List;

import lib.hs.com.hsapplication.config.AppApi;
import lib.hs.com.hsapplication.config.AppConfig;
import lib.hs.com.hsapplication.httpcenter.ILoginable;
import lib.hs.com.hsbaselib.HSApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by owen on 15-12-8.
 */
public class HSExampleApplication extends HSApplication {

    public static final String API_URL = "https://api.github.com/repos/{owner}/{repo}/contributors/";

    HSHttp hs = new HSHttp();

    @Override
    public void onCreate() {
        super.onCreate();


        AppApi appApi = new AppApi();
        ObjectGraph graph = ObjectGraph.create(new AppConfig());
        graph.inject(appApi);

        Titans titans = appApi.titans;
        int count = 0;
        while(count ++ < 10){
            titans.create(ILoginable.class).login("owen","123");
        }
//        titans.create(ILoginable.class).login("owen","123");

        HSHttp hsClient = new HSHttp();
        hsClient.get("http://www.baidu.com", new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }
        });


        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        //使用动态代理处理业务　比如 github接口中执行了某一个方法，那么执行的过程其实在动态代理中去执行了。
        GitHub github = retrofit.create(GitHub.class);

        //通过动态代理获得一个请求的对象
        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                for (Contributor contributor : response.body()) {
                    System.out.println(contributor.login + " (" + contributor.contributions + ")");
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }
}
