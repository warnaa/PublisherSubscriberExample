package pl.dtarnacki.publishersubscriberexample.api;

import java.util.List;

import pl.dtarnacki.publishersubscriberexample.model.Post;
import retrofit.Callback;
import retrofit.http.GET;

public interface BackendAPI {

    @GET("/posts")
    void loadPosts(Callback<List<Post>> result);
}
