package info.nivaldobondanca.asciiwarehouse.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Nivaldo Bondança
 */
public class AsciiItemTest {
	final String type = "face";
	final String id = "0-4itmqrfkz6e9izfr";
	final int size = 24;
	final int price = 379;
	final String face = "( .-. )";
	final int stock = 7;
	final String[] tags = {"flat", "bored"};

	AsciiItem item1;
	AsciiItem item2;

	@Before
	public void setUp() throws Exception {
		final JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("id", id);
		json.put("size", size);
		json.put("price", price);
		json.put("face", face);
		json.put("stock", stock);
		json.put("tags", new JSONArray(tags));

		item1 = AsciiItem.create("{\"type\":\"face\",\"id\":\"0-4itmqrfkz6e9izfr\",\"size\":24,\"price\":379,\"face\":\"( .-. )\",\"stock\":7,\"tags\":[\"flat\",\"bored\"]}");
		item2 = AsciiItem.create(json);
	}

	@Test
	public void testItem1Creation() throws Exception {
		assertNotNull(item1);
	}

	@Test
	public void testItemEquals() throws Exception {
		assertEquals(item1, item2);
	}

	@Test
	public void testItemType() throws Exception {
		assertEquals(type, item1.type());
		assertEquals(type, item2.type());
	}

	@Test
	public void testItemId() throws Exception {
		assertEquals(id, item1.id());
		assertEquals(id, item2.id());
	}

	@Test
	public void testItemSize() throws Exception {
		assertEquals(size, item1.size());
		assertEquals(size, item2.size());
	}

	@Test
	public void testItemPrice() throws Exception {
		assertEquals(price, item1.price());
		assertEquals(price, item2.price());
	}

	@Test
	public void testItemFace() throws Exception {
		assertEquals(face, item1.face());
		assertEquals(face, item2.face());
	}

	@Test
	public void testItemStock() throws Exception {
		assertEquals(stock, item1.stock());
		assertEquals(stock, item2.stock());
	}

	@Test
	public void testItemTags() throws Exception {
		assertArrayEquals(tags, item1.tags());
		assertArrayEquals(tags, item2.tags());
	}
}
