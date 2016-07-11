package info.nivaldobondanca.asciiwarehouse.util;

/**
 * @author Nivaldo Bondança
 */
public class Objects {

	private Objects() {}

	public static <T> T coalesce(T a, T b) {
		return a != null ? a : b;
	}
}
