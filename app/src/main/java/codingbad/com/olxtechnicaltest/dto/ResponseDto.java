package codingbad.com.olxtechnicaltest.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class ResponseDto implements Serializable {

    @SerializedName("items")
    @Expose
    private List<ImageSearchResult> imageSearchResultList;

    public List<ImageSearchResult> getResult() {
        return imageSearchResultList;
    }
}
