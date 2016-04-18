package codingbad.com.olxtechnicaltest.manager;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import codingbad.com.olxtechnicaltest.model.Categories;
import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Saves data about all the categories in the system.
 */
@Singleton
public class DataManager {

    // bad name. This is the number of different width a category can have and make no sense
    // having in a constant since the rest of the algorithm is hardcoded
    private static final int NUMBER_OF_RATIOS = 3;

    private List<Category> categories = new ArrayList<>();

    private Category mainCategory;

    public void addCategory(Category category) {
        this.categories.add(category);

        if (this.categories.size() == Categories.INITIAL_CATEGORIES_COUNT) {
            calculateCategories();
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public boolean isEmpty() {
        return categories.isEmpty();
    }

    // ugly
    public void calculateCategories() {
        int maxClicks = 1;
        int minClicks = 1;

        int current;

        for (Category category : categories) {
            current = category.clicks();
            if (current > maxClicks) {
                maxClicks = current;
                this.mainCategory = category;
            }

            if (current < minClicks) {
                minClicks = current;
            }
        }

        // once I have min and max I can define the category
        float r = (float) (maxClicks - minClicks) / NUMBER_OF_RATIOS;
        for (Category category : categories) {
            current = category.clicks();
            // first category, width 1: min - min+r
            if (minClicks <= current && current <= minClicks + r) {
                category.setRatio(1);
            }
            // second category, width 2: min+r - min+r*2
            if (minClicks + r < current && current < minClicks + (r * 2)) {
                category.setRatio(2);
            }
            // third category, width 3: min+r*2 - min+r*3
            if (minClicks + (r * 2) <= current && current <= minClicks + (r * 3)) {
                category.setRatio(3);
            }
        }
    }

    public Category getMainCategory() {
        return mainCategory;
    }
}
