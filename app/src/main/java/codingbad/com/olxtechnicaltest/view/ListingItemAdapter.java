package codingbad.com.olxtechnicaltest.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class ListingItemAdapter extends RecyclerView.Adapter<ListingItemAdapter.ViewHolder> {

    private final RecyclerViewListener recyclerViewListener;

    private List<String> images;

    private Context context;

    private boolean isShowingList;

    public ListingItemAdapter(Context context, RecyclerViewListener recyclerViewListener) {
        this.recyclerViewListener = recyclerViewListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListingView image = new ListingView(parent.getContext());

        return new ViewHolder(image);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = images.get(position);

        holder.view.setModel(url, isShowingList);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addItemList(List<String> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public void isShowingList(boolean showingList) {
        this.isShowingList = showingList;
        notifyDataSetChanged();
    }

    public interface RecyclerViewListener {

        void onItemClickListener(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListingView view;

        public ViewHolder(ListingView itemView) {
            super(itemView);
            this.view = itemView;
            this.view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewListener.onItemClickListener(v, getAdapterPosition());
        }
    }
}
