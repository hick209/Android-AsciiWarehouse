package info.nivaldobondanca.asciiwarehouse.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.functions.Func1;

/**
 * @author Nivaldo Bondança
 */
public class NdJsonParser<T> {

	public static NdJsonParser<JSONObject> parseJson(String ndJsonString) throws JSONException {
		final String[] lines = ndJsonString.split("\\r?\\n");
		final List<JSONObject> values = new ArrayList<>(lines.length);

		for (String line : lines) {
			if (TextUtils.isEmpty(line)) continue;

			values.add(new JSONObject(line));
		}

		return new NdJsonParser<>(values);
	}

	public static NdJsonParser<String> parseString(String ndJsonString) throws JSONException {
		return parseJson(ndJsonString).map(new Func1<JSONObject, String>() {
			@Override
			public String call(JSONObject jsonObject) {
				return jsonObject.toString();
			}
		});
	}


	private final List<T> values;

	private NdJsonParser(Collection<T> items) {
		values = new ArrayList<>(items);
	}


	public <E> NdJsonParser<E> map(Func1<T, E> func) {
		final Collection<E> result = new ArrayList<>(values.size());
		for (T value : values) {
			result.add(func.call(value));
		}

		return new NdJsonParser<>(result);
	}

	public JSONArray toJsonArray() throws JSONException {
		return new JSONArray(values);
	}

	public List<T> toList() {
		return new ArrayList<>(values);
	}
}
