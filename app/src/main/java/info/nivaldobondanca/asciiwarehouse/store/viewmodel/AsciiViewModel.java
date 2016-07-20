package info.nivaldobondanca.asciiwarehouse.store.viewmodel;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import info.nivaldobondanca.asciiwarehouse.BR;
import info.nivaldobondanca.asciiwarehouse.R;
import info.nivaldobondanca.asciiwarehouse.model.AsciiItem;
import info.nivaldobondanca.asciiwarehouse.util.FormatUtils;

/**
 * @author Nivaldo Bondança
 */
public class AsciiViewModel extends BaseObservable {

	private final float scaledDensity;

	@Nullable
	private AsciiItem item;

	public AsciiViewModel(float scaledDensity) {
		this.scaledDensity = scaledDensity;
	}

	public void setData(AsciiItem item) {
		this.item = item;
		notifyPropertyChanged(BR.face);
		notifyPropertyChanged(BR.size);
		notifyPropertyChanged(BR.price);
		notifyPropertyChanged(BR.buyEnabled);
		notifyPropertyChanged(BR.lastItemVisibility);
		notifyPropertyChanged(BR.soldOutVisibility);
	}


	///////////////////////////////
	/// Properties
	///////////////////////////////

	@Bindable
	public CharSequence getFace() {
		return (item != null) ? item.face() : null;
	}

	@Bindable
	public float getSize() {
		// This (maxSize) is here to make sure the biggest item fits the item tile
		final int maxSize = 18;
		final int sizeSp = (item != null) ? Math.min(maxSize, item.size()) : 16;

		return scaledDensity * sizeSp;
	}

	@Bindable
	public CharSequence getPrice() {
		final float price = (item != null) ? item.price() * .01f : 0;
		return FormatUtils.formatCurrency(price);
	}

	@Bindable
	public int getLastItemVisibility() {
		return item != null && item.stock() == 1 ? View.VISIBLE : View.GONE;
	}

	@Bindable
	public int getSoldOutVisibility() {
		return item != null && item.stock() == 0 ? View.VISIBLE : View.GONE;
	}

	@Bindable
	public boolean isBuyEnabled() {
		return item != null && item.stock() > 0;
	}

	public View.OnClickListener getClickListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (item != null) {
					final Context context = view.getContext();
					final ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
					clipboard.setPrimaryClip(ClipData.newPlainText("ascii face", item.face()));

					Toast.makeText(context, context.getString(R.string.warehouse_asciiBought, item.face()), Toast.LENGTH_SHORT).show();
				}
			}
		};
	}
}
