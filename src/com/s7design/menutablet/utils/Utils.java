package com.s7design.menutablet.utils;

import java.lang.reflect.Type;
import java.util.Date;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.s7design.menutablet.R;

public class Utils {

	public static Gson getGson() {
		return new GsonBuilder()
				.registerTypeAdapter(Date.class, new GsonDateSerializer())
				.registerTypeAdapter(Date.class, new GsonDateDeserializer())
				.disableHtmlEscaping().create();
	}

	static public class GsonDateSerializer implements JsonSerializer<Date> {
		public JsonElement serialize(Date src, Type typeOfSrc,
				JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.getTime());
		}
	}

	static public class GsonDateDeserializer implements JsonDeserializer<Date> {
		public Date deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return json == null ? null : new Date(json.getAsLong());
		}
	}

	public static boolean isBluetoothEnabled() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			return false;
		} else {
			if (bluetoothAdapter.isEnabled()) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static boolean isLocationEnabled(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static float convertDpToPixel(int dp, Context context) {

		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				r.getDisplayMetrics());
		return px;
	}

	/**
	 * Method for getting screen width
	 * 
	 * @return screen width
	 */
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.x;

	}

	/**
	 * Method for getting screen height
	 * 
	 * @return screen height
	 */
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.y;
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceID = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");

		if (resourceID > 0) {
			result = context.getResources().getDimensionPixelSize(resourceID);
		}
		return result;
	}

	public static void handleDismissDialog(final View view1, Context context) {
		int dismissDialog1Id = R.id.linearLayoutCommentsContainer1;
		int dismissDialog2Id = R.id.linearLayoutCommentsContainer2;
		int dismissDialog3Id = R.id.linearLayoutCommentsContainer3;
		
		if (!(view1 instanceof RelativeLayout)) {
			view1.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (view1.getVisibility() == View.VISIBLE)
						view1.setVisibility(View.GONE);
					return false;
				}

			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view1 instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view1).getChildCount(); i++) {

				View innerView = ((ViewGroup) view1).getChildAt(i);

				handleDismissDialog(innerView, context);
			}
		}

	}

}
