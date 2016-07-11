package info.nivaldobondanca.asciiwarehouse.store.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import info.nivaldobondanca.asciiwarehouse.R;
import info.nivaldobondanca.asciiwarehouse.model.AsciiItem;
import info.nivaldobondanca.asciiwarehouse.model.StoreResponse;
import info.nivaldobondanca.asciiwarehouse.databinding.WarehouseAsciiItemBinding;
import info.nivaldobondanca.asciiwarehouse.store.PageLoader;
import info.nivaldobondanca.asciiwarehouse.store.viewmodel.AsciiViewModel;

/**
 * @author Nivaldo Bondança
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreItemViewHolder> {

	private PageLoader pageLoader;

	@IntDef({ TYPE_ITEM, TYPE_LOADING })
	@Retention(RetentionPolicy.SOURCE)
	private @interface ViewType {}

	private static final int TYPE_ITEM    = 0;
	private static final int TYPE_LOADING = 1;

	private final DisplayMetrics displayMetrics;
	private final LayoutInflater inflater;

	private final Handler pageLoadingHandler;

	private String  query;
	private boolean onlyInStock;
	private int     lastPage = -1;
	private boolean hasNext = true;

	private List<AsciiItem> storeItems = new LinkedList<>();

	public StoreAdapter(Context context, PageLoader pageLoader, DisplayMetrics displayMetrics) {
		inflater = LayoutInflater.from(context);
		this.pageLoader = pageLoader;
		this.displayMetrics = displayMetrics;

		pageLoadingHandler = new Handler(Looper.getMainLooper());
	}

	@Nullable
	public String getQuery() {
		return query;
	}

	public void updateData(StoreResponse response) {
		if (!Objects.equals(response.query(), query) || response.onlyInStock() != onlyInStock) {
			// New data!
			updateSearch(response.query(), response.onlyInStock());
		}

		hasNext = !response.isLastPage();

		lastPage++;
		if (lastPage != response.page()) {
			throw new IllegalStateException("Page loading order was broken! Loaded=" + response.page() + ", expected=" + lastPage);
		}

		// Add the new items to the list
		final int startPosition = storeItems.size();
		final List<AsciiItem> newItems = response.items();

		storeItems.addAll(newItems);
		// Mutate the loading item
		notifyItemChanged(startPosition);
		// Add new items
		notifyItemRangeInserted(startPosition+1, newItems.size() + (hasNext ? 1 : 0));
	}

	public void updateSearch(String query, boolean onlyInStock) {
		if (Objects.equals(this.query, query) && this.onlyInStock == onlyInStock) {
			return;
		}

		pageLoader.cancelLoads(this.query, lastPage+1, this.onlyInStock);

		this.query = query;
		this.onlyInStock = onlyInStock;
		lastPage = -1;
		hasNext = true;

		storeItems.clear();
		notifyDataSetChanged();
	}

	@ViewType
	@Override
	public int getItemViewType(int position) {
		return position < storeItems.size() ? TYPE_ITEM : TYPE_LOADING;
	}

	@Override
	public StoreItemViewHolder onCreateViewHolder(ViewGroup parent, @ViewType int viewType) {
		switch (viewType) {
			case TYPE_ITEM: {
				final WarehouseAsciiItemBinding binding =
						WarehouseAsciiItemBinding.inflate(inflater, parent, false);
				final AsciiViewModel viewModel = new AsciiViewModel(displayMetrics.scaledDensity);
				binding.setViewModel(viewModel);

				return new StoreItemViewHolder(binding);
			}

			case TYPE_LOADING: {
				final View view = inflater.inflate(R.layout.warehouse_loading_item, parent, false);
				return new StoreItemViewHolder(view);
			}

			default:
				throw new IllegalArgumentException("Bad view type, viewType=" + viewType);
		}
	}

	@Override
	public void onBindViewHolder(StoreItemViewHolder holder, int position) {
		switch (getItemViewType(position)) {
			case TYPE_ITEM:
				holder.setData(storeItems.get(position));
				break;

			case TYPE_LOADING:
				pageLoadingHandler.post(new Runnable() {
					@Override
					public void run() {
						pageLoader.loadPage(query, lastPage+1, onlyInStock);
					}
				});
				break;
		}
	}

	@Override
	public int getItemCount() {
		return storeItems.size() + (hasNext ? 1 : 0);
	}

	public GridLayoutManager.SpanSizeLookup getSpanLookup(final GridLayoutManager layoutManager) {
		return new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				switch (getItemViewType(position)) {
					case TYPE_LOADING:
						return layoutManager.getSpanCount();

					case TYPE_ITEM:
					default:
						return 1;
				}
			}
		};
	}
}
