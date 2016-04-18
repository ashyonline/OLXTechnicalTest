package codingbad.com.olxtechnicaltest.service;

import codingbad.com.olxtechnicaltest.dto.ResponseDto;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Service API to access custom search created for this app
 */
public interface ISearchServiceAPI {

    @GET("/customsearch/v1")
    Observable<ResponseDto> search(@Query("q") String word, @Query("cx") String cx,
            @Query("key") String customSearchCredential, @Query("searchType") String searchType,
            @Query("imageSize") String imageSize, @Query("num") int resultSize);
}
