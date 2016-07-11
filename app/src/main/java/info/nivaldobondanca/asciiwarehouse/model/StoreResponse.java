package info.nivaldobondanca.asciiwarehouse.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * @author Nivaldo Bondança
 */
public abstract class StoreResponse {
	@NonNull
	public static StoreResponse create(
			String query, int page, boolean onlyInStock,
			List<AsciiItem> items, boolean isLastPage) {
		return new StoreResponseImpl(query, page, onlyInStock, items, isLastPage);
	}

	@Nullable
	public abstract String          query();
	public abstract int             page();
	public abstract boolean         onlyInStock();
	public abstract List<AsciiItem> items();
	public abstract boolean         isLastPage();
}

class StoreResponseImpl extends StoreResponse {

	@Nullable
	private final String          query;
	private final int             page;
	private final boolean         onlyInStock;
	private final List<AsciiItem> items;
	private final boolean         isLastPage;


	public StoreResponseImpl(@Nullable String query,
	                         int page,
	                         boolean onlyInStock,
	                         List<AsciiItem> items,
	                         boolean isLastPage) {
		this.query = query;
		this.page = page;
		this.onlyInStock = onlyInStock;
		this.items = items;
		this.isLastPage = isLastPage;
	}


	@Nullable
	@Override
	public final String query() {
		return query;
	}

	@Override
	public final int page() {
		return page;
	}

	@Override
	public final boolean onlyInStock() {
		return onlyInStock;
	}

	@Override
	public final List<AsciiItem> items() {
		return items;
	}

	@Override
	public boolean isLastPage() {
		return isLastPage;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StoreResponseImpl that = (StoreResponseImpl) o;
		return page == that.page &&
				onlyInStock == that.onlyInStock &&
				isLastPage == that.isLastPage &&
				Objects.equals(query, that.query) &&
				Objects.equals(items, that.items);
	}

	@Override
	public int hashCode() {
		return Objects.hash(query, page, onlyInStock, items, isLastPage);
	}

	@Override
	public String toString() {
		return "StoreResponseImpl{" +
				"query='" + query + '\'' +
				", page=" + page +
				", onlyInStock=" + onlyInStock +
				", items=" + items +
				", isLastPage=" + isLastPage +
				'}';
	}
}
