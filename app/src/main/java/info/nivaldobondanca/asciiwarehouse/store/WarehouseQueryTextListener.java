package info.nivaldobondanca.asciiwarehouse.store;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import info.nivaldobondanca.asciiwarehouse.store.adapter.StoreAdapter;

/**
 * @author Nivaldo Bondança
 */
public class WarehouseQueryTextListener implements SearchView.OnQueryTextListener {
	private final StoreAdapter adapter;
	private final MenuItem onlyInStock;

	private final Handler handler;
	private Runnable task;

	public WarehouseQueryTextListener(StoreAdapter adapter, MenuItem onlyInStock) {
		this.adapter = adapter;
		this.onlyInStock = onlyInStock;
		handler = new Handler(Looper.getMainLooper());
		task = null;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		adapter.updateSearch(query, onlyInStock.isChecked());
		return false;
	}

	@Override
	public boolean onQueryTextChange(final String newText) {
		handler.removeCallbacks(task);
		task = new Runnable() {
			@Override
			public void run() {
				onQueryTextSubmit(newText);
			}
		};
		handler.postDelayed(task, 1000);
		return false;
	}
}
