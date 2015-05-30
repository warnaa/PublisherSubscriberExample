package pl.dtarnacki.publishersubscriberexample;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import pl.dtarnacki.publishersubscriberexample.adapter.PostsAdapter;
import pl.dtarnacki.publishersubscriberexample.event.BusProvider;
import pl.dtarnacki.publishersubscriberexample.event.LoadPostsEvent;
import pl.dtarnacki.publishersubscriberexample.event.PostsLoadedEvent;
import pl.dtarnacki.publishersubscriberexample.event.PostsNotLoadedEvent;

public class MainActivity extends AppCompatActivity {

    private Bus bus;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView posts;
    private PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setSubtitle(R.string.actionbar_subtitle);

        // RecyclerView
        // see: https://developer.android.com/training/material/lists-cards.html

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new PostsAdapter(this);

        posts = (RecyclerView) findViewById(R.id.main_posts);
        posts.setLayoutManager(layoutManager);
        posts.setAdapter(adapter);

        // Pull to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_posts_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary, R.color.primary_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBus().post(new LoadPostsEvent());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register this activity as Subscriber whenever activity is on top.
        // It will only listen to events handled by methods with @Subscribe annotation.
        getBus().register(this);
    }

    /**
     * Method that will be fired on success of fetching posts
     *
     * @param event Event object that carries data
     */
    @Subscribe
    public void onPostsLoaded(PostsLoadedEvent event) {
        // Update list with new items
        adapter.updateItems(event.getList());

        // Disable refreshing animation
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Method that will be fired on failure of fetching posts
     *
     * @param event
     */
    @Subscribe
    public void onPostsNotLoaded(PostsNotLoadedEvent event) {
        // Show a toast with error, log, etc.
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();

        // Disable refreshing animation
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister when activity goes to background
        getBus().unregister(this);
    }

    private Bus getBus() {
        if (bus == null) {
            bus = BusProvider.getInstance();
        }

        return bus;
    }
}
