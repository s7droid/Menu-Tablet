package com.s7design.menutablet.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.s7design.menutablet.R;

public class MenuTextView extends TextView {

	private boolean isBookFont;

	public MenuTextView(Context context) {
		super(context);
		init(context);
	}

	public MenuTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.text_font);
		isBookFont = ta.getBoolean(R.styleable.text_font_isBookFont, true);
		ta.recycle();

		int isBold = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", 0);

		setIncludeFontPadding(false);

		if (isBold == 1)
			isBookFont = false;

		init(context);
	}

	private void init(Context context) {

		if (isBookFont)
			setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GothamRounded-Book.otf"));
		else
			setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GothamRounded-Medium.otf"));
	}

}
