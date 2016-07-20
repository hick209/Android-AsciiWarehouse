package info.nivaldobondanca.asciiwarehouse.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Nivaldo Bondança
 */
public class NdJsonParserTest {

	private final String obj1 = "{\"hello\":\"world\"}";
	private final String obj2 = "{\"foo\":\"bar\"}";

	@Test
	public void testParseJson() throws JSONException {
		NdJsonParser.parseJson("{\"hello\":\"world\"}\n{\"foo\":\"bar\"}\n");
		NdJsonParser.parseJson("{\"hello\":\"world\"}\n{\"foo\":\"bar\"}");
		NdJsonParser.parseJson("");
	}

	@Test
	public void testParseString() throws Exception {
		NdJsonParser.parseString("{\"hello\":\"world\"}\n{\"foo\":\"bar\"}\n");
		NdJsonParser.parseString("{\"hello\":\"world\"}\n{\"foo\":\"bar\"}");
		NdJsonParser.parseString("");
	}

	@Test
	public void testToJsonArray() throws Exception {
		final JSONArray array1 = NdJsonParser.parseString(String.format("%s\n%s", obj1, obj2)).toJsonArray();
		final JSONArray array2 = new JSONArray(Arrays.asList(obj1, obj2));

		assertEquals(array1.get(0), array2.get(0));
		assertEquals(array1.get(1), array2.get(1));
	}

	@Test
	public void testToList() throws Exception {
		final List<String> list1 = NdJsonParser.parseString(String.format("%s\n%s", obj1, obj2)).toList();
		final List<String> list2 = Arrays.asList(obj1, obj2);

		assertEquals(list1, list2);
	}
}
