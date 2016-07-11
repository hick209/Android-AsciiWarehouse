package info.nivaldobondanca.asciiwarehouse.store.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.nivaldobondanca.asciiwarehouse.BR;

/**
 * @author Nivaldo Bondança
 */
public class StoreViewModel extends BaseObservable {

	private RecyclerView.Adapter             adapter;
	private RecyclerView.AdapterDataObserver adapterObserver;

	private boolean loading;

	public StoreViewModel(RecyclerView.Adapter adapter) {
		this.adapter = adapter;

		// Since loading state change will be always called once the data gets updated,
		// it is not necessary to register an observer on the adapter.
		// It is still done, because it is a good practice.
		adapter.registerAdapterDataObserver(adapterObserver);
	}

	public void tearDown() {
		adapter.unregisterAdapterDataObserver(adapterObserver);
	}


	public void setLoading(boolean loading) {
		this.loading = loading;
		notifyPropertyChanged(BR.loading);
		notifyPropertyChanged(BR.emptyViewVisibility);
	}

	///////////////////////////////
	/// Properties
	///////////////////////////////

	@Bindable
	public boolean isLoading() {
		return loading;
	}

	@Bindable
	public int getEmptyViewVisibility() {
		return loading || adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE;
	}


	///////////////////////////////
	/// AdapterDataObserver
	///////////////////////////////

	{
		adapterObserver = new RecyclerView.AdapterDataObserver() {
			@Override
			public void onChanged() {
				notifyPropertyChanged(BR.emptyViewVisibility);
			}
		};
	}
}
