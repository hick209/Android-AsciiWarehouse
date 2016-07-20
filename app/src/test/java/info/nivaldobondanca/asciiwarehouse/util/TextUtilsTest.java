package info.nivaldobondanca.asciiwarehouse.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Nivaldo Bondança
 */
public class TextUtilsTest {

	@Test
	public void testIsEmpty() throws Exception {
		assertTrue(TextUtils.isEmpty(""));
		assertTrue(TextUtils.isEmpty(null));
		assertFalse(TextUtils.isEmpty(" "));
		assertFalse(TextUtils.isEmpty("a"));
		assertFalse(TextUtils.isEmpty("1"));
		assertFalse(TextUtils.isEmpty(" 123 "));
		assertFalse(TextUtils.isEmpty("\n"));
	}

	@Test
	public void testIsNotEmpty() throws Exception {
		assertFalse(TextUtils.isNotEmpty(""));
		assertFalse(TextUtils.isNotEmpty(null));
		assertTrue(TextUtils.isNotEmpty(" "));
		assertTrue(TextUtils.isNotEmpty("a"));
		assertTrue(TextUtils.isNotEmpty("1"));
		assertTrue(TextUtils.isNotEmpty(" 123 "));
		assertTrue(TextUtils.isNotEmpty("\n"));
	}
}
