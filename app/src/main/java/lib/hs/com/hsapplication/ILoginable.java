package lib.hs.com.hsapplication;

import com.hs.hshttplib.annotations.Get;
import com.hs.hshttplib.annotations.Path;

/**
 * Created by owen on 16-2-3.
 */
public interface ILoginable {
    @Get
    @Path("/login")
    public LoginResponse login(String username,String password);
}
