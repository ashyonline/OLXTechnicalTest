package codingbad.com.olxtechnicaltest.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class ImageSearchResult implements Serializable {

    @SerializedName("link")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }
}
