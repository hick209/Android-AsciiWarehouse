package info.nivaldobondanca.asciiwarehouse.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import info.nivaldobondanca.asciiwarehouse.model.AsciiItem;
import info.nivaldobondanca.asciiwarehouse.model.StoreResponse;
import info.nivaldobondanca.asciiwarehouse.util.Objects;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Nivaldo Bondança
 */
public class StoreProvider {
	private static final String API_BASE_URL = "http://74.50.59.155:5000/";

	private static final int DEFAULT_PAGE_COUNT = 10;

	private final WarehouseApi   api;
	private final WarehouseCache cache;

	private int pageCount = DEFAULT_PAGE_COUNT;

	private Func1<String, List<AsciiItem>> responseMapper;


	public StoreProvider(Context context) {
		final Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.build();

		final File cacheDir = context.getCacheDir();

		api = retrofit.create(WarehouseApi.class);
		cache = new RxCache.Builder()
				.persistence(cacheDir, new GsonSpeaker())
				.using(WarehouseCache.class);
	}


	public Observable<StoreResponse> getItems(@Nullable final String query, int page, boolean onlyInStock) {
		final int limit = pageCount;
		final int skip = page * pageCount;

		final Observable<List<AsciiItem>> apiCall = api.getStoreItems(query, onlyInStock ? 1 : 0, limit, skip)
				.map(responseMapper);

		final String safeQuery = Objects.coalesce(query, "");
		final String groupKey = onlyInStock ? safeQuery : "all_" + safeQuery;
		final DynamicKeyGroup key = new DynamicKeyGroup(page, groupKey);

		return cache.getAsciiItems(apiCall, key)
				.map(createStoreResponseMapper(query, page, onlyInStock));
	}


	@NonNull
	private Func1<List<AsciiItem>, StoreResponse> createStoreResponseMapper(
			@Nullable final String query, final int page, final boolean onlyInStock) {
		return new Func1<List<AsciiItem>, StoreResponse>() {
			@Override
			public StoreResponse call(List<AsciiItem> items) {
				final boolean isLastPage = items.size() != pageCount;
				return StoreResponse.create(query, page, onlyInStock, items, isLastPage);
			}
		};
	}

	{
		responseMapper = new Func1<String, List<AsciiItem>>() {
			@Override
			public List<AsciiItem> call(String response) {
				final List<AsciiItem> list = new ArrayList<>(pageCount);

				final String[] lines = response.split("\\r?\\n");
				for (String line : lines) {
					if (TextUtils.isEmpty(line)) break;

					final AsciiItem item = AsciiItem.create(line);
					if (item != null) {
						list.add(item);
					} else {
						throw new RuntimeException("Failed to parse JSON response. Response = " + line);
					}
				}

				return list;
			}
		};
	}
}
