package info.nivaldobondanca.asciiwarehouse.store.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import info.nivaldobondanca.asciiwarehouse.model.StoreResponse;
import info.nivaldobondanca.asciiwarehouse.data.StoreProvider;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Nivaldo Bondança
 */
public class StoreLoader extends Loader<StoreResponse> {

	private Subscription subscription;
	private boolean      loaded = false;

	private Observable<StoreResponse> loadCall;


	public StoreLoader(Context context, StoreProvider provider,
	                   String query, int page, boolean onlyInStock) {
		super(context);
		loadCall = provider.getItems(query, page, onlyInStock)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onForceLoad() {
		subscription = loadCall.subscribe(new Subscriber<StoreResponse>() {
			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
				subscription = null;
				loaded = false;
			}

			@Override
			public void onNext(StoreResponse response) {
				if (!loaded) {
					commitContentChanged();
					loaded = true;
				}
				deliverResult(response);
			}

			@Override
			public void onCompleted() {
				subscription = null;
			}
		});
	}

	@Override
	protected boolean onCancelLoad() {
		if (subscription == null) return false;

		subscription.unsubscribe();
		subscription = null;

		deliverCancellation();

		return true;
	}

	@Override
	protected void onReset() {
		// Ensure the loader is stopped
		onStopLoading();
	}
}
