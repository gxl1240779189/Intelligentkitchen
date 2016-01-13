package com.example.paintcanvas;





import com.example.intelligentkitchenn.R;

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
     * 该容器内child item的默认尺寸 
     */  
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;  
    /** 
     * 菜单的中心child的默认尺寸 
     */  
    private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;  
    /** 
     * 该容器的内边距,无视padding属性，如需边距请用该变量 
     */  
    private static final float RADIO_PADDING_LAYOUT = 1 / 12f;  
  
    /** 
     * 当每秒移动角度达到该值时，认为是快速移动 
     */  
    private static final int FLINGABLE_VALUE = 300;  
  
    /** 
     * 如果移动角度达到该值，则屏蔽点击 
     */  
    private static final int NOCLICK_VALUE = 3;  
  
    /** 
     * 当每秒移动角度达到该值时，认为是快速移动 
     */  
    private int mFlingableValue = FLINGABLE_VALUE;  
    /** 
     * 该容器的内边距,无视padding属性，如需边距请用该变量 
     */  
    private float mPadding;  
  
    /** 
     * 布局时的开始角度 
     */  
    private double mStartAngle = 0;  
    
    private OnMenuItemClickListener Menulistener;
    /**
	 * 检测按下到抬起时旋转的角度
	 */
	private float mTmpAngle;
	/**
	 * 检测按下到抬起时使用的时间
	 */
	private long mDownTime;

	/**
	 * 判断是否正在自动滚动
	 */
	private boolean isFling;
   

	public roundmenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setPadding(0, 0, 0, 0);
	}
	
	public void setOnMenuItemClickListener(OnMenuItemClickListener Menulistener)
	{
	    this.Menulistener=Menulistener;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		 int layoutRadius = mRadius;  
		  
		    // Laying out the child views  
		    final int childCount = getChildCount();  
		  
		    int left, top;  
		    // menu item 的尺寸  
		    int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);  
		  
		    // 根据menu item的个数，计算角度  
		    float angleDelay = 360 / (getChildCount() - 1);  
		  
		    // 遍历去设置menuitem的位置  
		    for (int i = 0; i < childCount; i++)  
		    {  
		        final View child = getChildAt(i);  
		        if (child.getId() == R.id.id_circle_menu_item_center)  
		            continue;  
		  
		        if (child.getVisibility() == GONE)  
		        {  
		            continue;  
		        }  

		        
		        mStartAngle %= 360;  
		  
		        // 计算，中心点到menu item中心的距离  
		        float tmp = layoutRadius / 2f - cWidth / 2 - mPadding;  
		  
		        // tmp cosa 即menu item中心点的横坐标  
		        left = layoutRadius  
		                / 2  
		                + (int) Math.round(tmp  
		                        * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f  
		                        * cWidth);  
		        // tmp sina 即menu item的纵坐标  
		        top = layoutRadius  
		                / 2  
		                + (int) Math.round(tmp  
		                        * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f  
		                        * cWidth);  
		  
		        child.layout(left, top, left + cWidth, top + cWidth);  
		        // 叠加尺寸  
		        mStartAngle += angleDelay;  
		    }  
		  
		    // 找到中心的view，如果存在设置onclick事件  
		    View cView = findViewById(R.id.id_circle_menu_item_center);  
		    if (cView != null)  
		    {  
		     
		    cView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Menulistener.onmenucentre(v);
				}
			});
		    // 设置center item位置  
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
		int widthsize=MeasureSpec.getSize(widthMeasureSpec);
		int widthmode=MeasureSpec.getMode(widthMeasureSpec);
		
		int heightsize=MeasureSpec.getSize(heightMeasureSpec);
		int heightmode=MeasureSpec.getMode(heightMeasureSpec);
		
		if (widthmode != MeasureSpec.EXACTLY  
                || heightmode != MeasureSpec.EXACTLY)  
        {  
            // 主要设置为背景图的高度  
            resWidth = getSuggestedMinimumWidth();  
            // 如果未设置背景图片，则设置为屏幕宽高的默认值  
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;  
  
            resHeight = getSuggestedMinimumHeight();  
            // 如果未设置背景图片，则设置为屏幕宽高的默认值  
            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;  
        } else  
        {  
            // 如果都设置为精确值，则直接取小值；  
            resWidth = resHeight = Math.min(widthsize, heightsize);  
        } 
		
		setMeasuredDimension(resWidth, resHeight);
		
		 // 获得半径  
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());  
  
        // menu item数量  
        final int count = getChildCount();  
        // menu item尺寸  
        int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);  
        // menu item测量模式  
        int childMode = MeasureSpec.EXACTLY;  
  
        // 迭代测量  
        for (int i = 0; i < count; i++)  
        {  
            final View child = getChildAt(i);  
  
            if (child.getVisibility() == GONE)  
            {  
                continue;  
            }  
  
            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量  
            int makeMeasureSpec = -1;  
  
            if (child.getId() == R.id.id_circle_menu_item_center)  
            {  
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(  
                        (int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),  
                        childMode);  
            } else  
            {  
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,  
                        childMode);  
            }  
            child.measure(makeMeasureSpec, makeMeasureSpec);  
        }  
  
            mPadding = RADIO_PADDING_LAYOUT * mRadius;  			
	}
	
	

	private int getDefaultWidth() {
		// TODO Auto-generated method stub
		WindowManager wm=(WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics=new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
	}
	
	public void setmenuitem_text_icon(String[] text,int[] images)
	{
		menu_texts=text;
		menu_images=images;
		int textcount=text.length;
		int imagecount=images.length;
		menuitem_count=textcount>imagecount?textcount:imagecount;
		addmenuitems();
	}

	private void addmenuitems() {
		LayoutInflater layoutinflater=LayoutInflater.from(getContext());
		for(int i=0;i<menuitem_count;i++)
		{
			final int j=i;
			View view=layoutinflater.inflate(R.layout.circle_menu_item, this, false);
			ImageView imageview=(ImageView) view.findViewById(R.id.id_circle_menu_item_image);
			TextView textview=(TextView) view.findViewById(R.id.id_circle_menu_item_text);
			if(imageview!=null)
			{
				imageview.setVisibility(View.VISIBLE);
				imageview.setImageResource(menu_images[i]);
			}
			if(textview!=null)
			{
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
	 * 记录上一次的x，y坐标
	 */
	private float mLastX;
	private float mLastY;

	/**
	 * 自动滚动的Runnable
	 */
	private AutoFlingRunnable mFlingRunnable;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();

		// Log.e("TAG", "x = " + x + " , y = " + y);

		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:

			mLastX = x;
			mLastY = y;
			mDownTime = System.currentTimeMillis();
			mTmpAngle = 0;

			// 如果当前已经在快速滚动
			if (isFling)
			{
				// 移除快速滚动的回调
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}

			break;
		case MotionEvent.ACTION_MOVE:

			/**
			 * 获得开始的角度
			 */
			float start = getAngle(mLastX, mLastY);
			/**
			 * 获得当前的角度
			 */
			float end = getAngle(x, y);

			// Log.e("TAG", "start = " + start + " , end =" + end);
			// 如果是一、四象限，则直接end-start，角度值都是正值
			if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4)
			{
				mStartAngle += end - start;
				mTmpAngle += end - start;
			} else
			// 二、三象限，色角度值是付值
			{
				mStartAngle += start - end;
				mTmpAngle += start - end;
			}
			// 重新布局
			requestLayout();

			mLastX = x;
			mLastY = y;

			break;
		case MotionEvent.ACTION_UP:

			// 计算，每秒移动的角度
			float anglePerSecond = mTmpAngle * 1000
					/ (System.currentTimeMillis() - mDownTime);

			// Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
			// mTmpAngle);

			// 如果达到该值认为是快速移动
			if (Math.abs(anglePerSecond) > mFlingableValue && !isFling)
			{
				// post一个任务，去自动滚动
				post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

				return true;
			}

			// 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
			if (Math.abs(mTmpAngle) > NOCLICK_VALUE)
			{
				return true;
			}

			break;
		}
		return super.dispatchTouchEvent(event);
	}

	/**
	 * 主要为了action_down时，返回true
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}

	/**
	 * 根据触摸的位置，计算角度
	 * 
	 * @param xTouch
	 * @param yTouch
	 * @return
	 */
	private float getAngle(float xTouch, float yTouch)
	{
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	/**
	 * 根据当前位置计算象限
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int getQuadrant(float x, float y)
	{
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0)
		{
			return tmpY >= 0 ? 4 : 1;
		} else
		{
			return tmpY >= 0 ? 3 : 2;
		}

	}
	
	/**
	 * 自动滚动的任务
	 * 
	 * @author zhy
	 * 
	 */
	private class AutoFlingRunnable implements Runnable
	{

		private float angelPerSecond;

		public AutoFlingRunnable(float velocity)
		{
			this.angelPerSecond = velocity;
		}

		public void run()
		{
			// 如果小于20,则停止
			if ((int) Math.abs(angelPerSecond) < 20)
			{
				isFling = false;
				return;
			}
			isFling = true;
			// 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
			mStartAngle += (angelPerSecond / 30);
			// 逐渐减小这个值
			angelPerSecond /= 1.0666F;
			postDelayed(this, 30);
			// 重新布局
			requestLayout();
		}
	}

}
