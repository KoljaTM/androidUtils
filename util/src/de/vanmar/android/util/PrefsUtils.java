package de.vanmar.android.util;

import com.googlecode.androidannotations.api.sharedpreferences.StringPrefField;

public class PrefsUtils {

	public static boolean isSet(final StringPrefField field) {
		return field.exists() && field.get().length() != 0;
	}
}
