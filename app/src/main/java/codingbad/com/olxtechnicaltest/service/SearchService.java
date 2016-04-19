package codingbad.com.olxtechnicaltest.service;

import com.google.inject.Singleton;

import java.util.concurrent.TimeUnit;

import codingbad.com.olxtechnicaltest.BuildConfig;
import codingbad.com.olxtechnicaltest.OlxTechnicalTestApplication;
import codingbad.com.olxtechnicaltest.Secret;
import codingbad.com.olxtechnicaltest.dto.ResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * <p/>
 * Implementation of the Custom Search service.
 */
@Singleton
public class SearchService {

    private static final String IMAGE_SIZE = "medium";

    private static final String SEARCH_TYPE = "image";

    private static final int TIMEOUT_MILLIS = 30000;

    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

    private ISearchServiceAPI searchService;

    private Retrofit retrofit;

    public SearchService() {
        OlxTechnicalTestApplication.injectMembers(this);
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .client(createDefaultOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        searchService = retrofit.create(ISearchServiceAPI.class);
    }

    private OkHttpClient createDefaultOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
                .readTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
                .writeTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT);

        // logging interception
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder.interceptors().add(logging);

        return okHttpClientBuilder.build();
    }

    public Observable<ResponseDto> search(String word) {
        return searchService
                .search(word, Secret.CX, Secret.CUSTOM_SEARCH_CREDENTIAL, SEARCH_TYPE, IMAGE_SIZE,
                        OlxTechnicalTestApplication.RESULT_SIZE);
    }
}
