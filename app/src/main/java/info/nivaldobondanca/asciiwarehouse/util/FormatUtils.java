package info.nivaldobondanca.asciiwarehouse.util;

import java.text.NumberFormat;

/**
 * @author Nivaldo Bondança
 */
public class FormatUtils {
	private FormatUtils() {}

	public static String formatCurrency(Number number)
	{
		return NumberFormat.getCurrencyInstance().format(number);
	}
}
