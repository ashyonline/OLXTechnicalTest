package codingbad.com.olxtechnicaltest.view;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import codingbad.com.olxtechnicaltest.R;

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
        ImageView image = new ImageView(parent.getContext());
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(image);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = images.get(position);

        // now sure this should be here
        int height;
        int width;
        if (isShowingList) {
            height = (int) context.getResources().getDimension(R.dimen.item_size);
            width = height * 4;
        } else {
            height = (int) context.getResources().getDimension(R.dimen.item_size_grid);
            width = height;
        }

        Picasso.with(context)
                .load(url)
                .centerCrop()
                .resize(width, height)
                .placeholder(R.drawable.placeholder)
                .into(holder.view);
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

        private final ImageView view;

        public ViewHolder(ImageView itemView) {
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
