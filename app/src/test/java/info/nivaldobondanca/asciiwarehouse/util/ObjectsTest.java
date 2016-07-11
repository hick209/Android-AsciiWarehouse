package info.nivaldobondanca.asciiwarehouse.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Nivaldo Bondança
 */
public class ObjectsTest {

	@Test
	public void testCoalesce() throws Exception {
		assertNull(Objects.coalesce(null, null));

		assertEquals("Hello", Objects.coalesce("Hello", "World"));
		assertEquals("Hello", Objects.coalesce("Hello", null));
		assertEquals("fallback", Objects.coalesce(null, "fallback"));

		assertEquals(1, Objects.coalesce(null, 1).intValue());
		assertEquals(2, Objects.coalesce(2, 1).intValue());

		assertFalse(Objects.coalesce(null, false));
		assertTrue(Objects.coalesce(true, false));
	}
}
