package info.nivaldobondanca.asciiwarehouse.data;

import java.util.List;
import java.util.concurrent.TimeUnit;

import info.nivaldobondanca.asciiwarehouse.model.AsciiItem;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.LifeCache;
import rx.Observable;

/**
 * @author Nivaldo Bondança
 */
public interface WarehouseCache {

	@LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
	Observable<List<AsciiItem>> getAsciiItems(Observable<List<AsciiItem>> apiCall, DynamicKeyGroup filterPage);
}
