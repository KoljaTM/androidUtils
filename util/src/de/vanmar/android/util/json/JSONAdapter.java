package de.vanmar.android.util.json;

import android.widget.BaseAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONAdapter extends BaseAdapter {

	private JSONArray array;

	@Override
	public int getCount() {
		return array == null ? 0 : array.length();
	}

	@Override
	public Object getItem(final int position) {
		if (array == null | array.length() < position) {
			return null;
		}
		try {
			return array.get(position);
		} catch (final JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	public JSONObject getObject(final int position) {
		return (JSONObject) getItem(position);
	}

	public void setData(final JSONArray data) {
		array = data;
		notifyDataSetChanged();
	}
}
