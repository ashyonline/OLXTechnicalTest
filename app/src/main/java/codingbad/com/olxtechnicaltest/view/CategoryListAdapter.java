package codingbad.com.olxtechnicaltest.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * <p/>
 * Adapter for the Categories showed to the user with different sizes depending on the
 * amount of clicks.
 */
public class CategoryListAdapter extends ArrayAdapter<Category> {


    private final CategoryEventListener categoriesListener;

    private ViewHolder viewHolder;

    public CategoryListAdapter(Context context, int resource, List<Category> categories,
                               CategoryEventListener listener) {
        super(context, resource, categories);

        this.categoriesListener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new CategoryView(this.getContext());

            viewHolder = new ViewHolder(convertView, categoriesListener);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Category category = getItem(position);
        if (category != null) {
            viewHolder.setCategory(category);
        }

        return convertView;
    }

    private class ViewHolder implements CategoryView.CategoryListener {

        private final CategoryView categoryView;

        private final CategoryEventListener categoryListener;

        private Category category;

        public ViewHolder(View categoryView, CategoryEventListener categoriesListener) {
            this.categoryView = (CategoryView) categoryView;
            this.categoryView.setCategoryClickListener(this);
            this.categoryListener = categoriesListener;
        }

        public void setCategory(Category category) {
            this.category = category;
            categoryView.setModel(category);
        }

        @Override
        public void onClick() {
            this.categoryListener.onItemClickListener(category);
        }
    }
}
