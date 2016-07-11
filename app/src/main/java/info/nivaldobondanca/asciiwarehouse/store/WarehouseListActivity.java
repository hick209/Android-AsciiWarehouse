package info.nivaldobondanca.asciiwarehouse.store;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import info.nivaldobondanca.asciiwarehouse.R;
import info.nivaldobondanca.asciiwarehouse.databinding.WarehouseActivityListBinding;
import info.nivaldobondanca.asciiwarehouse.data.StoreProvider;
import info.nivaldobondanca.asciiwarehouse.store.adapter.StoreAdapter;
import info.nivaldobondanca.asciiwarehouse.store.loader.StoreLoaderCallbacks;
import info.nivaldobondanca.asciiwarehouse.store.viewmodel.StoreViewModel;

/**
 * @author Nivaldo Bondança
 */
public class WarehouseListActivity extends AppCompatActivity
		implements PageLoader {

	private StoreAdapter         adapter;
	private StoreLoaderCallbacks loaderCallbacks;
	private StoreViewModel       viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final WarehouseActivityListBinding binding =
				DataBindingUtil.setContentView(this, R.layout.warehouse_activity_list);

		final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		final float widthDpi = displayMetrics.widthPixels / displayMetrics.density;

		adapter = new StoreAdapter(this, this, displayMetrics);

		final int spanCount = (int) (widthDpi / 160);
		final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
		layoutManager.setSpanSizeLookup(adapter.getSpanLookup(layoutManager));

		setSupportActionBar(binding.toolbar);
		setTitle(null);

		binding.recyclerView.setAdapter(adapter);
		binding.recyclerView.setLayoutManager(layoutManager);

		viewModel = new StoreViewModel(adapter);
		binding.setViewModel(viewModel);

		final StoreProvider storeProvider =  new StoreProvider(this);
		loaderCallbacks = new StoreLoaderCallbacks(this, storeProvider, adapter, viewModel);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		viewModel.tearDown();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.warehouse_activity_list, menu);
		final MenuItem onlyInStock = menu.findItem(R.id.warehouse_stockOnly);

		// Associate searchable configuration with the SearchView
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.warehouse_search));
		searchView.setQueryHint(getText(R.string.warehouse_hint_search));
		searchView.setOnQueryTextListener(new WarehouseQueryTextListener(adapter, onlyInStock));

		final CharSequence queryText = searchView.getQuery();
		adapter.updateSearch(queryText != null ? queryText.toString() : null, onlyInStock.isChecked());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.warehouse_stockOnly:
				item.setChecked(!item.isChecked());
				adapter.updateSearch(adapter.getQuery(), item.isChecked());
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


	///////////////////////////////
	/// PageLoader
	///////////////////////////////

	@Override
	public void loadPage(String query, int page, boolean onlyInStock) {
		final int id = StoreLoaderCallbacks.generateId(query, page, onlyInStock);
		final Bundle args = StoreLoaderCallbacks.generateArgs(query, page, onlyInStock);

		getSupportLoaderManager().initLoader(id, args, loaderCallbacks);
	}

	@Override
	public void cancelLoads(String oldQuery, int pageCount, boolean onlyInStock) {
		for (int page = 0; page < pageCount; page++) {
			final int id = StoreLoaderCallbacks.generateId(oldQuery, page, onlyInStock);
			getSupportLoaderManager().destroyLoader(id);
		}
	}

}
