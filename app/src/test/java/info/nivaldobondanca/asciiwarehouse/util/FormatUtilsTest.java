package info.nivaldobondanca.asciiwarehouse.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * @author Nivaldo Bondança
 */
public class FormatUtilsTest {

	@Before
	public void setUp() throws Exception {
		Locale.setDefault(Locale.US);
	}

	@Test
	public void testFormatCurrency() throws Exception {
		assertEquals("$1.00", FormatUtils.formatCurrency(1));
		assertEquals("$1.00", FormatUtils.formatCurrency(1L));
		assertEquals("$1.00", FormatUtils.formatCurrency(1.00));
		assertEquals("$1.00", FormatUtils.formatCurrency(1.f));

		assertEquals("$2.00", FormatUtils.formatCurrency(1.9999));
		assertEquals("$1.01", FormatUtils.formatCurrency(1.0099));
		assertEquals("$1.00", FormatUtils.formatCurrency(1.0019));

		assertEquals("$999.00", FormatUtils.formatCurrency(999));
		assertEquals("$1,000.00", FormatUtils.formatCurrency(1_000));
		assertEquals("$1,999,999.00", FormatUtils.formatCurrency(1_999_999));
	}
}
