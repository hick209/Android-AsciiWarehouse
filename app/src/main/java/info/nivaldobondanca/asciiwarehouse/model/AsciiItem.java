package info.nivaldobondanca.asciiwarehouse.model;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Nivaldo Bondança
 */
public abstract class AsciiItem {

	@Nullable
	public static AsciiItem create(String value) {
		try {
			return create(new JSONObject(value));
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static AsciiItem create(JSONObject json) throws JSONException {
		final String id = json.getString("id");
		final String type = json.getString("type");
		final int size = json.getInt("size");
		final int price = json.getInt("price");
		final int stock = json.getInt("stock");
		final String face = json.getString("face");

		final JSONArray array = json.getJSONArray("tags");
		final String[] tags = new String[array.length()];

		for (int i = 0; i < tags.length; i++) {
			tags[i] = array.getString(i);
		}

		return new AsciiItemImpl(id, type, size, price, stock, face, tags);
	}

	public abstract String   id();
	public abstract String   type();
	public abstract int      size();
	public abstract int      price();
	public abstract int      stock();
	public abstract String   face();
	public abstract String[] tags();
}

class AsciiItemImpl extends AsciiItem {
	private final String   id;
	private final String   type;
	private final int      size;
	private final int      price;
	private final int      stock;
	private final String   face;
	private final String[] tags;

	public AsciiItemImpl(String id, String type, int size, int price, int stock, String face, String[] tags) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.price = price;
		this.stock = stock;
		this.face = face;
		this.tags = tags;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String type() {
		return type;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int price() {
		return price;
	}

	@Override
	public int stock() {
		return stock;
	}

	@Override
	public String face() {
		return face;
	}

	@Override
	public String[] tags() {
		return tags;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AsciiItemImpl asciiItem = (AsciiItemImpl) o;
		return size == asciiItem.size &&
				price == asciiItem.price &&
				stock == asciiItem.stock &&
				Objects.equals(id, asciiItem.id) &&
				Objects.equals(type, asciiItem.type) &&
				Objects.equals(face, asciiItem.face) &&
				Arrays.equals(tags, asciiItem.tags);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, size, price, stock, face, tags);
	}

	@Override
	public String toString() {
		return "AsciiItemImpl{" +
				"id='" + id + '\'' +
				", type='" + type + '\'' +
				", size=" + size +
				", price=" + price +
				", stock=" + stock +
				", face='" + face + '\'' +
				", tags=" + Arrays.toString(tags) +
				'}';
	}
}
