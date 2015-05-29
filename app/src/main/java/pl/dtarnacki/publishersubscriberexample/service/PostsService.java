package pl.dtarnacki.publishersubscriberexample.service;

import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import pl.dtarnacki.publishersubscriberexample.api.BackendAPI;
import pl.dtarnacki.publishersubscriberexample.event.LoadPostsEvent;
import pl.dtarnacki.publishersubscriberexample.event.PostsLoadedEvent;
import pl.dtarnacki.publishersubscriberexample.event.PostsNotLoadedEvent;
import pl.dtarnacki.publishersubscriberexample.model.Post;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostsService {

    private final BackendAPI api;
    private final Bus bus;

    public PostsService(BackendAPI api, Bus bus) {
        this.api = api;
        this.bus = bus;
    }

    @Subscribe
    public void onLoadPosts(LoadPostsEvent event) {
        Log.i("service", "load post event");

        api.loadPosts(new Callback<List<Post>>() {
            @Override
            public void success(List<Post> list, Response response) {
                bus.post(new PostsLoadedEvent(list));
            }

            @Override
            public void failure(RetrofitError error) {
                bus.post(new PostsNotLoadedEvent(error));
            }
        });
    }
}
