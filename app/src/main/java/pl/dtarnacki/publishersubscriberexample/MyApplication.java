package pl.dtarnacki.publishersubscriberexample;

import android.app.Application;

import com.squareup.otto.Bus;

import pl.dtarnacki.publishersubscriberexample.api.BackendAPI;
import pl.dtarnacki.publishersubscriberexample.event.BusProvider;
import pl.dtarnacki.publishersubscriberexample.service.PostsService;
import retrofit.RestAdapter;

public class MyApplication extends Application {

    /**
     * URL to our API that will handle requests from services
     */
    private static final String DUMMY_API_URL = "http://jsonplaceholder.typicode.com";

    @Override
    public void onCreate() {
        super.onCreate();

        BackendAPI api = buildApi();
        Bus bus = BusProvider.getInstance();

        // Services that subscribe to Fetch-Data-Events
        PostsService postsService = new PostsService(api, bus);
        bus.register(postsService);

    }

    private BackendAPI buildApi() {
        return new RestAdapter.Builder()
                .setEndpoint(DUMMY_API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(BackendAPI.class);
    }
}
