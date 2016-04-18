package codingbad.com.olxtechnicaltest.model;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

import android.os.Parcel;

import java.util.List;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Represents a category.
 * Also, it implements AsymmetricItem to be used in an AsymmentricGridView
 */
public class Category implements AsymmetricItem {

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    // name of the category, mostly to be showed to the user
    private final String categoryName;

    // url of the image (chosen randomly between the first 10 ones) representing the category
    private final String imageUrl;

    // initials to show on the image
    private final String initials;

    // saves the 30 urls of the images that will be shown in the listing of this category
    private final List<String> urls;

    // the number of times the user chose this category
    private int numberOfClicks;

    // number calculated between all the clicks of all the categories to know how big this
    // category should be shown to the user
    private int categoryRatio = 1;

    public Category(String category, String url, String initials, int clicks, List<String> urls) {
        this.categoryName = category;
        this.imageUrl = url;
        this.initials = initials;
        this.numberOfClicks = clicks;
        this.urls = urls;
    }

    protected Category(Parcel in) {
        this.categoryName = in.readString();
        this.imageUrl = in.readString();
        this.initials = in.readString();
        this.urls = in.createStringArrayList();
        this.numberOfClicks = in.readInt();
        this.categoryRatio = in.readInt();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategoryFirstLetter() {
        return this.initials;
    }

    public int clicks() {
        return numberOfClicks;
    }

    @Override
    public int getColumnSpan() {
        return categoryRatio;
    }

    @Override
    public int getRowSpan() {
        return 1;
    }

    public void addClick() {
        this.numberOfClicks++;
    }

    public String getName() {
        return categoryName;
    }

    public int widthRatio() {
        return this.categoryRatio;
    }

    public void setRatio(int ratio) {
        categoryRatio = ratio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.categoryName);
        dest.writeString(this.imageUrl);
        dest.writeString(this.initials);
        dest.writeStringList(this.urls);
        dest.writeInt(this.numberOfClicks);
        dest.writeInt(this.categoryRatio);
    }

    public List<String> getUrls() {
        return urls;
    }
}
