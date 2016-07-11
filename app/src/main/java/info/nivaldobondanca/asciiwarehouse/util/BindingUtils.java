package info.nivaldobondanca.asciiwarehouse.util;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * @author Nivaldo Bondança
 */
public class BindingUtils {

	@BindingAdapter("refreshing")
	public static void setRefreshing(final SwipeRefreshLayout view, final boolean refreshing)
	{
		// This is a hack to fix a support library bug, still present on version 24.0.0.
		// BUG: If setRefreshing is called directly, it will not show the refreshing view if the screen
		// was not fully visible when the method was called.
		view.post(new Runnable()
		{
			@Override
			public void run()
			{
				view.setRefreshing(refreshing);
			}
		});
	}
}
