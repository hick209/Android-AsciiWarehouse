package info.nivaldobondanca.asciiwarehouse.store.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.nivaldobondanca.asciiwarehouse.model.AsciiItem;
import info.nivaldobondanca.asciiwarehouse.databinding.WarehouseAsciiItemBinding;

/**
 * @author Nivaldo Bondança
 */
public class StoreItemViewHolder extends RecyclerView.ViewHolder {
	private final WarehouseAsciiItemBinding binding;

	public StoreItemViewHolder(WarehouseAsciiItemBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
	}

	public StoreItemViewHolder(View view) {
		super(view);
		binding = null;
	}

	public void setData(AsciiItem item) {
		binding.getViewModel().setData(item);
	}
}
