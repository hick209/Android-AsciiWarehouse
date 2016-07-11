package info.nivaldobondanca.asciiwarehouse.data;

import android.support.annotation.Nullable;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Nivaldo Bondança
 */
public interface WarehouseApi {

	@GET("api/search")
	Observable<String> getStoreItems(
			@Query("q") @Nullable String query,
			@Query("onlyInStock") int onlyInStock, // XXX ideally, this should be a boolean value
			@Query("limit") int limit,
			@Query("skip") int skip);
}
