package com.example.ui;

import com.example.intelligentkitchen.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AbsListView.LayoutParams;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

public class mylistview extends ListView implements OnTouchListener,
		OnGestureListener {
	/**
	 * 手势识别类
	 */
	private GestureDetector gestureDetector;

	/**
	 * 滑动时出现的按钮
	 */
	private View btnDelete;

	/**
	 * listview的每一个item的布局
	 */
	private ViewGroup viewGroup;
	/**
	 * 选中的项
	 */
	private int selectedItem;

	/**
	 * 是否已经显示删除按钮
	 */
	private boolean isDeleteShow;

	/**
	 * 点击删除按钮时删除每一行的事件监听器
	 */
	private OnItemDeleteListener onItemDeleteListener;

	public mylistview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public mylistview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public mylistview(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector = new GestureDetector(getContext(), this);
		setOnTouchListener(this);
		// TODO Auto-generated constructor stub
	}

	public void setOnItemDeleteListener(
			OnItemDeleteListener onItemDeleteListener) {
		this.onItemDeleteListener = onItemDeleteListener;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// 得到当前触摸的条目
		selectedItem = pointToPosition((int) event.getX(), (int) event.getY());
		// 如果删除按钮已经显示，那么隐藏按钮，异常按钮在当前位置的绘制
		if (isDeleteShow) {
			btnHide(btnDelete);
			viewGroup.removeView(btnDelete);
			btnDelete = null;
			isDeleteShow = false;
			return false;
		} else {
			// 如果按钮没显示，则触发手势事件
			// 由此去触发GestureDetector的事件，可以查看其源码得知，onTouchEvent中进行了手势判断，调用onFling
			return gestureDetector.onTouchEvent(event);
		}
	}

	public interface OnItemDeleteListener {
		public void onItemDelete(int selectedItem);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		// 得到当前触摸的条目
		if (!isDeleteShow) {
			selectedItem = pointToPosition((int) e.getX(), (int) e.getY());
		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		// 如果删除按钮没有显示，并且手势滑动符合我们的条件
		// 此处可以根据需要进行手势滑动的判断，如限制左滑还是右滑，我这里是左滑右滑都可以
		if (!isDeleteShow && Math.abs(velocityX) > Math.abs(velocityY)) {
			// 在当前布局上，动态添加我们的删除按钮，设置按钮的各种参数、事件，按钮的点击事件响应我们的删除项监听器
			btnDelete = LayoutInflater.from(getContext()).inflate(
					R.layout.layout_button, null);
			btnDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// btnHide(btnDelete);
					viewGroup.removeView(btnDelete);
					btnDelete = null;
					isDeleteShow = false;
					onItemDeleteListener.onItemDelete(selectedItem);
				}
			});
			viewGroup = (ViewGroup) getChildAt(selectedItem
					- getFirstVisiblePosition());
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					300, LayoutParams.MATCH_PARENT);
		    layoutParams.gravity=Gravity.RIGHT;
			btnDelete.setLayoutParams(layoutParams);
			viewGroup.addView(btnDelete);
			btnShow(btnDelete);
			isDeleteShow = true;
		} else {
			setOnTouchListener(this);
		}

		return false;
	}

	private void btnShow(View v) {
		v.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.btn_show));
	}

	private void btnHide(View v) {
		v.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.btn_hide));
	}

}
