package info.nivaldobondanca.asciiwarehouse.store.loader;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.Locale;

import info.nivaldobondanca.asciiwarehouse.model.StoreResponse;
import info.nivaldobondanca.asciiwarehouse.data.StoreProvider;
import info.nivaldobondanca.asciiwarehouse.store.adapter.StoreAdapter;
import info.nivaldobondanca.asciiwarehouse.store.viewmodel.StoreViewModel;
import info.nivaldobondanca.asciiwarehouse.util.Objects;

/**
 * @author Nivaldo Bondança
 */
public class StoreLoaderCallbacks implements LoaderManager.LoaderCallbacks<StoreResponse> {

	private static final String ARG_ONLY_IN_STOCK = "arg.ONLY_IN_STOCK";
	private static final String ARG_PAGE          = "arg.PAGE";
	private static final String ARG_QUERY         = "arg.QUERY";

	private final Context        context;
	private final StoreProvider  storeProvider;
	private final StoreAdapter   adapter;
	private final StoreViewModel viewModel;

	public StoreLoaderCallbacks(Context context, StoreProvider storeProvider,
	                            StoreAdapter adapter, StoreViewModel viewModel) {
		this.context = context;
		this.storeProvider = storeProvider;
		this.adapter = adapter;
		this.viewModel = viewModel;
	}

	@Override
	public Loader<StoreResponse> onCreateLoader(int id, Bundle bundle) {
		final String query = bundle.getString(ARG_QUERY);
		final int page = bundle.getInt(ARG_PAGE, 0);
		final boolean onlyInStock = bundle.getBoolean(ARG_ONLY_IN_STOCK, false);

		viewModel.setLoading(true);

		return new StoreLoader(context, storeProvider, query, page, onlyInStock);
	}

	@Override
	public void onLoadFinished(Loader<StoreResponse> loader, StoreResponse storeResponse) {
		adapter.updateData(storeResponse);
		viewModel.setLoading(false);
	}

	@Override
	public void onLoaderReset(Loader<StoreResponse> loader) {
	}


	///////////////////////////////
	/// Helper methods
	///////////////////////////////

	@CheckResult
	public static int generateId(String query, int page, boolean onlyInStock) {
		final String safeQuery = Objects.coalesce(query, "");
		final String stock = onlyInStock ? "" : "_all";

		final String id = String.format(Locale.ENGLISH, "%s%s-%d", safeQuery, stock, page);

		return java.util.Objects.hashCode(id);
	}

	@CheckResult
	public static Bundle generateArgs(String query, int page, boolean onlyInStock) {
		final Bundle args = new Bundle();
		args.putString(ARG_QUERY, query);
		args.putInt(ARG_PAGE, page);
		args.putBoolean(ARG_ONLY_IN_STOCK, onlyInStock);

		return args;
	}
}
