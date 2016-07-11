package info.nivaldobondanca.asciiwarehouse.store;

/**
 * @author Nivaldo Bondança
 */
public interface PageLoader {
	void loadPage(String query, int page, boolean onlyInStock);

	void cancelLoads(String oldQuery, int pageCount, boolean onlyInStock);
}
