package codingbad.com.olxtechnicaltest.model;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * This class saves some "business" decisions.
 * The user will see 6 Categories. Each of them has a search criteria which will be used to search
 * for the given images. The name of the category will be the once showed to the user and the
 * initials are showed on the categories images.
 */
@Singleton
public final class Categories {

    // don't like this here but...
    public static int INITIAL_CATEGORIES_COUNT = 6;

    protected final List<InitialCategory> initialCategories;

    public Categories() {
        initialCategories = new ArrayList<>();
        initialCategories.add(new InitialCategory("KITTEN", "cute kitten", "K"));
        initialCategories.add(new InitialCategory("PUPPY", "cute puppy", "P"));
        initialCategories.add(new InitialCategory("BABY", "cute baby", "C"));
        initialCategories.add(new InitialCategory("PIG", "cute teacup pig", "T"));
        initialCategories.add(new InitialCategory("SEAL", "cute baby seal", "S"));
        initialCategories.add(new InitialCategory("BUNNY", "cute little bunny", "B"));
    }

    public List<InitialCategory> getInitialCategories() {
        return initialCategories;
    }

    public class InitialCategory {

        protected String name;

        protected String searchCriteria;

        protected String initial;

        public InitialCategory(String categoryName, String searchCriteria, String initial) {
            this.name = categoryName;
            this.searchCriteria = searchCriteria;
            this.initial = initial;
        }

        public String getName() {
            return this.name;
        }

        public String getInitials() {
            return this.initial;
        }

        public String getSearchCriteria() {
            return this.searchCriteria;
        }
    }
}
