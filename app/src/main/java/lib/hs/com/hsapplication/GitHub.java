package lib.hs.com.hsapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by owen on 16-1-21.
 *
 * 这个接口中有很多方法，而且会不停的添加，如果使用动态代理，就不需要给每个实现类都实现一遍添加的方法
 */
public interface GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
}
