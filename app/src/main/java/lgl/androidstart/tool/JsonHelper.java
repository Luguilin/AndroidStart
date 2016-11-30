package lgl.androidstart.tool;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 
 * @author LGL
 *
 */
public class JsonHelper {

	/**
	 * 轮胎接口特有方法
	 * 
	 * @return
	 */
	public static <T> String processJson(String result) {
		String josn = null;
		try {
			JSONObject object = new JSONObject(result);
			int state = object.getInt("state");
			if (state == 0) {
				josn = object.get("info").toString();
			}
		} catch (JSONException e) {
		}
		return josn;
	}

	/**
	 * 加入了预处理（可直接传入结果）
	 * 
	 * @return
	 */
	public static <T> T getModel4Json2(String result, java.lang.reflect.Type type) {
		String josn = null;
		try {
			JSONObject object = new JSONObject(result);
			int state = object.getInt("state");
			if (state != 0) {
				return null;
			}
			josn = object.get("info").toString();
		} catch (JSONException e) {
		}
		if (StringHelper.isEmpty(josn)) {
			return null;
		}
		return getModel4Json(josn, type);
	}

	public static <T> T getModel4Json3(String result, String info, java.lang.reflect.Type type) {
		String josn = null;
		try {
			JSONObject object = new JSONObject(result);
			int state = object.getInt("state");
			if (state != 0) {
				return null;
			}
			josn = object.get(info).toString();
		} catch (JSONException e) {
		}
		if (StringHelper.isEmpty(josn)) {
			return null;
		}
		return getModel4Json(josn, type);
	}

	public static <T> T getModel4Json(String json, java.lang.reflect.Type type) {
		Gson gson = new Gson();
		try {
			T t = gson.fromJson(json, type);
			return t;
		} catch (JsonSyntaxException e) {
		}
		return null;
	}

	public static java.lang.reflect.Type getType(List<T> object) {
		return new TypeToken<List<T>>() {
		}.getType();
	}
}
