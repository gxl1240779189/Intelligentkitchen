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
	 * ����ʶ����
	 */
	private GestureDetector gestureDetector;

	/**
	 * ����ʱ���ֵİ�ť
	 */
	private View btnDelete;

	/**
	 * listview��ÿһ��item�Ĳ���
	 */
	private ViewGroup viewGroup;
	/**
	 * ѡ�е���
	 */
	private int selectedItem;

	/**
	 * �Ƿ��Ѿ���ʾɾ����ť
	 */
	private boolean isDeleteShow;

	/**
	 * ���ɾ����ťʱɾ��ÿһ�е��¼�������
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
		// �õ���ǰ��������Ŀ
		selectedItem = pointToPosition((int) event.getX(), (int) event.getY());
		// ���ɾ����ť�Ѿ���ʾ����ô���ذ�ť���쳣��ť�ڵ�ǰλ�õĻ���
		if (isDeleteShow) {
			btnHide(btnDelete);
			viewGroup.removeView(btnDelete);
			btnDelete = null;
			isDeleteShow = false;
			return false;
		} else {
			// �����ťû��ʾ���򴥷������¼�
			// �ɴ�ȥ����GestureDetector���¼������Բ鿴��Դ���֪��onTouchEvent�н����������жϣ�����onFling
			return gestureDetector.onTouchEvent(event);
		}
	}

	public interface OnItemDeleteListener {
		public void onItemDelete(int selectedItem);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		// �õ���ǰ��������Ŀ
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
		// ���ɾ����ťû����ʾ���������ƻ����������ǵ�����
		// �˴����Ը�����Ҫ�������ƻ������жϣ��������󻬻����һ��������������һ�������
		if (!isDeleteShow && Math.abs(velocityX) > Math.abs(velocityY)) {
			// �ڵ�ǰ�����ϣ���̬������ǵ�ɾ����ť�����ð�ť�ĸ��ֲ������¼�����ť�ĵ���¼���Ӧ���ǵ�ɾ���������
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
