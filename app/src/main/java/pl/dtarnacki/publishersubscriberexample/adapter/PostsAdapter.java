package pl.dtarnacki.publishersubscriberexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.dtarnacki.publishersubscriberexample.R;
import pl.dtarnacki.publishersubscriberexample.model.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> items;

    public PostsAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_post, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = items.get(position);

        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
        holder.user.setText("User: " + post.getUserId());

        holder.setClickListener(new ViewHolder.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Click on " + post.getId(), Toast.LENGTH_SHORT).show();

                // Or start a new activity for details about post
                // Intent intent = new Intent(context, SomeActivity.class);
                // intent.putExtra("id", post.getId());
                // context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Post> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected LinearLayout container;
        protected TextView title;
        protected TextView body;
        protected TextView user;

        private ClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            this.container = (LinearLayout) itemView.findViewById(R.id.card_post);
            this.title = (TextView) itemView.findViewById(R.id.card_post_title);
            this.body = (TextView) itemView.findViewById(R.id.card_post_body);
            this.user = (TextView) itemView.findViewById(R.id.card_post_user);

            this.container.setOnClickListener(this);
        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public interface ClickListener {
            public void onClick(View view, int position);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
