package com.example.ui;

import com.example.intelligentkitchen.R;
import com.example.myinterface.OnMenuItemClickListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class roundmenu extends ViewGroup {

	private int menuitem_count;
	private String[] menu_texts;
	private int[] menu_images;
	private int mRadius;
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷child item锟斤拷默锟较尺达拷
	 */
	private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
	/**
	 * 锟剿碉拷锟斤拷锟斤拷锟斤拷child锟斤拷默锟较尺达拷
	 */
	private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟节边撅拷,锟斤拷锟斤拷padding锟斤拷锟皆ｏ拷锟斤拷锟斤拷呔锟斤拷锟斤拷酶帽锟斤拷锟�
	 */
	private static final float RADIO_PADDING_LAYOUT = 1 / 12f;

	/**
	 * 锟斤拷每锟斤拷锟狡讹拷锟角度达到锟斤拷值时锟斤拷锟斤拷为锟角匡拷锟斤拷锟狡讹拷
	 */
	private static final int FLINGABLE_VALUE = 300;

	/**
	 * 锟斤拷锟斤拷贫锟斤拷嵌却锏斤拷锟街碉拷锟斤拷锟斤拷锟斤拷蔚锟斤拷
	 */
	private static final int NOCLICK_VALUE = 3;

	/**
	 * 锟斤拷每锟斤拷锟狡讹拷锟角度达到锟斤拷值时锟斤拷锟斤拷为锟角匡拷锟斤拷锟狡讹拷
	 */
	private int mFlingableValue = FLINGABLE_VALUE;
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟节边撅拷,锟斤拷锟斤拷padding锟斤拷锟皆ｏ拷锟斤拷锟斤拷呔锟斤拷锟斤拷酶帽锟斤拷锟�
	 */
	private float mPadding;

	/**
	 * 锟斤拷锟斤拷时锟侥匡拷始锟角讹拷
	 */
	private double mStartAngle = 0;

	private OnMenuItemClickListener Menulistener;
	/**
	 * 锟斤拷獍达拷碌锟教э拷锟绞憋拷锟阶拷慕嵌锟�
	 */
	private float mTmpAngle;
	/**
	 * 锟斤拷獍达拷碌锟教э拷锟绞笔癸拷玫锟绞憋拷锟�
	 */
	private long mDownTime;

	/**
	 * 锟叫讹拷锟角凤拷锟斤拷锟斤拷锟皆讹拷锟斤拷锟斤拷
	 */
	private boolean isFling;

	public roundmenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setPadding(0, 0, 0, 0);
	}

	public void setOnMenuItemClickListener(OnMenuItemClickListener Menulistener) {
		this.Menulistener = Menulistener;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int layoutRadius = mRadius;

		// Laying out the child views
		final int childCount = getChildCount();

		int left, top;
		// menu item 锟侥尺达拷
		int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);

		// 锟斤拷锟絤enu item锟侥革拷锟斤拷锟斤拷锟角讹拷
		float angleDelay = 360 / (getChildCount() - 1);

		// 锟斤拷锟斤拷去锟斤拷锟斤拷menuitem锟斤拷位锟斤拷
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if (child.getId() == R.id.id_circle_menu_item_center)
				continue;

			if (child.getVisibility() == GONE) {
				continue;
			}

			mStartAngle %= 360;

			// 锟斤拷锟姐，锟斤拷锟侥点到menu item锟斤拷锟侥的撅拷锟斤拷
			float tmp = layoutRadius / 2f - cWidth / 2 - mPadding;

			// tmp cosa 锟斤拷menu item锟斤拷锟侥碉拷暮锟斤拷锟斤拷
			left = layoutRadius
					/ 2
					+ (int) Math.round(tmp
							* Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);
			// tmp sina 锟斤拷menu item锟斤拷锟斤拷锟斤拷锟�
			top = layoutRadius
					/ 2
					+ (int) Math.round(tmp
							* Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);

			child.layout(left, top, left + cWidth, top + cWidth);
			// 锟斤拷锟接尺达拷
			mStartAngle += angleDelay;
		}

		// 锟揭碉拷锟斤拷锟侥碉拷view锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷onclick锟铰硷拷
		View cView = findViewById(R.id.id_circle_menu_item_center);
		if (cView != null) {

			cView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Menulistener.onmenucentre(v);
				}
			});
			// 锟斤拷锟斤拷center item位锟斤拷
			int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
			int cr = cl + cView.getMeasuredWidth();
			cView.layout(cl, cl, cr, cr);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int resWidth;
		int resHeight;
		int widthsize = MeasureSpec.getSize(widthMeasureSpec);
		int widthmode = MeasureSpec.getMode(widthMeasureSpec);

		int heightsize = MeasureSpec.getSize(heightMeasureSpec);
		int heightmode = MeasureSpec.getMode(heightMeasureSpec);

		if (widthmode != MeasureSpec.EXACTLY
				|| heightmode != MeasureSpec.EXACTLY) {
		
			resWidth = getSuggestedMinimumWidth();
			// 锟斤拷锟轿达拷锟斤拷帽锟斤拷锟酵计拷锟斤拷锟斤拷锟斤拷锟轿拷锟侥伙拷锟竭碉拷默锟斤拷值
			resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

			resHeight = getSuggestedMinimumHeight();
			// 锟斤拷锟轿达拷锟斤拷帽锟斤拷锟酵计拷锟斤拷锟斤拷锟斤拷锟轿拷锟侥伙拷锟竭碉拷默锟斤拷值
			resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
		} else {
			// 锟斤拷锟斤拷锟斤拷锟轿拷锟饺分碉拷锟斤拷锟街憋拷锟饺⌒≈碉拷锟�
			resWidth = resHeight = Math.min(widthsize, heightsize);
		}

		setMeasuredDimension(resWidth, resHeight);

		// 锟斤拷冒刖�
		mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());

		// menu item锟斤拷锟斤拷
		final int count = getChildCount();
		// menu item锟竭达拷
		int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// menu item锟斤拷锟斤拷模式
		int childMode = MeasureSpec.EXACTLY;

		// 锟斤拷锟斤拷锟斤拷
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child.getVisibility() == GONE) {
				continue;
			}

			// 锟斤拷锟斤拷menu item锟侥尺寸；锟皆硷拷锟斤拷锟斤拷锟矫好碉拷模式锟斤拷去锟斤拷item锟斤拷锟叫诧拷锟斤拷
			int makeMeasureSpec = -1;

			if (child.getId() == R.id.id_circle_menu_item_center) {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
						childMode);
			} else {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
						childMode);
			}
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}

		mPadding = RADIO_PADDING_LAYOUT * mRadius;
	}

	private int getDefaultWidth() {
		// TODO Auto-generated method stub
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
	}

	public void setmenuitem_text_icon(String[] text, int[] images) {
		menu_texts = text;
		menu_images = images;
		int textcount = text.length;
		int imagecount = images.length;
		menuitem_count = textcount > imagecount ? textcount : imagecount;
		addmenuitems();
	}

	private void addmenuitems() {
		LayoutInflater layoutinflater = LayoutInflater.from(getContext());
		for (int i = 0; i < menuitem_count; i++) {
			final int j = i;
			View view = layoutinflater.inflate(R.layout.circle_menu_item, this,
					false);
			ImageView imageview = (ImageView) view
					.findViewById(R.id.id_circle_menu_item_image);
			TextView textview = (TextView) view
					.findViewById(R.id.id_circle_menu_item_text);
			if (imageview != null) {
				imageview.setVisibility(View.VISIBLE);
				imageview.setImageResource(menu_images[i]);
			}
			if (textview != null) {
				textview.setVisibility(View.VISIBLE);
				textview.setText(menu_texts[i]);
			}
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Menulistener.onmenuitem(v, j);
				}
			});
			addView(view);
		}

	}

	/**
	 * 锟斤拷录锟斤拷一锟轿碉拷x锟斤拷y锟斤拷锟�
	 */
	private float mLastX;
	private float mLastY;

	/**
	 * 锟皆讹拷锟斤拷锟斤拷锟斤拷Runnable
	 */
	private AutoFlingRunnable mFlingRunnable;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		// Log.e("TAG", "x = " + x + " , y = " + y);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			mLastX = x;
			mLastY = y;
			mDownTime = System.currentTimeMillis();
			mTmpAngle = 0;

			// 锟斤拷锟角帮拷丫锟斤拷诳锟斤拷俟锟斤拷锟�
			if (isFling) {
				// 锟狡筹拷锟斤拷俟锟斤拷锟斤拷幕氐锟�
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}

			break;
		case MotionEvent.ACTION_MOVE:

			/**
			 * 锟斤拷每锟绞硷拷慕嵌锟�
			 */
			float start = getAngle(mLastX, mLastY);
			/**
			 * 锟斤拷玫锟角帮拷慕嵌锟�
			 */
			float end = getAngle(x, y);

			// Log.e("TAG", "start = " + start + " , end =" + end);
			// 锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷蓿锟斤拷锟街憋拷锟絜nd-start锟斤拷锟角讹拷值锟斤拷锟斤拷锟斤拷值
			if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
				mStartAngle += end - start;
				mTmpAngle += end - start;
			} else
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟睫ｏ拷色锟角讹拷值锟角革拷值
			{
				mStartAngle += start - end;
				mTmpAngle += start - end;
			}
			// 锟斤拷锟铰诧拷锟斤拷
			requestLayout();

			mLastX = x;
			mLastY = y;

			break;
		case MotionEvent.ACTION_UP:

			// 锟斤拷锟姐，每锟斤拷锟狡讹拷锟侥角讹拷
			float anglePerSecond = mTmpAngle * 1000
					/ (System.currentTimeMillis() - mDownTime);

			// Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
			// mTmpAngle);

			// 锟斤拷锟斤到锟斤拷值锟斤拷为锟角匡拷锟斤拷锟狡讹拷
			if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
				// post一锟斤拷锟斤拷锟斤拷去锟皆讹拷锟斤拷锟斤拷
				post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

				return true;
			}

			// 锟斤拷锟角帮拷锟阶拷嵌瘸锟斤拷锟絅OCLICK_VALUE锟斤拷锟轿碉拷锟�
			if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
				return true;
			}

			break;
		}
		return super.dispatchTouchEvent(event);
	}

	/**
	 * 锟斤拷要为锟斤拷action_down时锟斤拷锟斤拷锟斤拷true
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	/**
	 * 锟斤拷荽锟斤拷锟斤拷锟轿伙拷茫锟斤拷锟斤拷锟角讹拷
	 * 
	 * @param xTouch
	 * @param yTouch
	 * @return
	 */
	private float getAngle(float xTouch, float yTouch) {
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	/**
	 * 锟斤拷莸锟角拔伙拷眉锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int getQuadrant(float x, float y) {
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0) {
			return tmpY >= 0 ? 4 : 1;
		} else {
			return tmpY >= 0 ? 3 : 2;
		}

	}

	/**
	 * 锟皆讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @author zhy
	 * 
	 */
	private class AutoFlingRunnable implements Runnable {

		private float angelPerSecond;

		public AutoFlingRunnable(float velocity) {
			this.angelPerSecond = velocity;
		}

		public void run() {
			// 锟斤拷锟叫★拷锟�0,锟斤拷停止
			if ((int) Math.abs(angelPerSecond) < 20) {
				isFling = false;
				return;
			}
			isFling = true;
			// 锟斤拷锟较改憋拷mStartAngle锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�30为锟剿憋拷锟斤拷锟斤拷锟教拷锟�
			mStartAngle += (angelPerSecond / 30);
			// 锟金渐硷拷小锟斤拷锟街�
			angelPerSecond /= 1.0666F;
			postDelayed(this, 30);
			// 锟斤拷锟铰诧拷锟斤拷
			requestLayout();
		}
	}

}
