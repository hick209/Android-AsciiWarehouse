package info.nivaldobondanca.asciiwarehouse.util;

/**
 * @author Nivaldo Bondança
 */
public class TextUtils {

	public static boolean isEmpty(CharSequence value) {
		return value == null || value.length() == 0;
	}

	public static boolean isNotEmpty(CharSequence value) {
		return value != null && value.length() > 0;
	}
}
