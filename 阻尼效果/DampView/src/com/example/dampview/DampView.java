package com.example.dampview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * 阻尼效果的scrollview
 */
public class DampView extends ScrollView {
	/** 该属性具体参数 怎么控制 未解！！！！ */
	private static final int LEN = 0xc8;
	/** 回弹时所用的时间 */
	private static final int DURATION = 200;
	// private static final int MAX_DY = 200;
	/** 最大Y坐标 其值一般设定为Scroller对应控件的高度 */
	private static final int MAX_DY = 200;
	private Scroller mScroller;
	TouchTool tool;
	int left, top;
	float startX, startY, currentX, currentY;
	int imageViewH;
	int rootW, rootH;
	ImageView imageView;
	boolean scrollerType;

	public DampView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public DampView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
	}

	public DampView(Context context) {
		super(context);

	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	float curY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (!mScroller.isFinished()) {
			return super.onTouchEvent(event);
		}
		currentX = event.getX();
		currentY = event.getY();
		imageView.getTop();
		switch (action) {
		case MotionEvent.ACTION_DOWN:// 变量赋初始值
			left = imageView.getLeft();
			top = imageView.getBottom();
			rootW = getWidth();
			rootH = getHeight();

			imageViewH = imageView.getHeight();
			startX = currentX;
			startY = currentY;
			tool = new TouchTool(imageView.getLeft(), imageView.getBottom(),
					imageView.getLeft(), imageView.getBottom() + LEN);
			break;
		case MotionEvent.ACTION_MOVE:
			if (imageView.isShown() && imageView.getTop() >= 0) {
				if (tool != null) {
					int t = tool.getScrollY(currentY - startY);
					if (t >= top && t <= imageView.getBottom() + LEN) {
						android.view.ViewGroup.LayoutParams params = imageView
								.getLayoutParams();
						params.height = t;// 改变高度
						imageView.setLayoutParams(params);
					}

				}
				scrollerType = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			scrollerType = true;

			// 松手后 回弹
			// 开始一个动画控制，由(startX , startY)在duration时间内前进(dx,dy)个单位
			// ，即到达坐标为(startX+dx , startY+dy)处
			mScroller.startScroll(imageView.getLeft(), imageView.getBottom(),
					0 - imageView.getLeft(),
					imageViewH - imageView.getBottom(), DURATION);
			invalidate();
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	// //该mScroller针对于imageView的变化
	// 被父视图调用，用于必要时候对其子视图的值（mScrollX和mScrollY）
	// 进行更新。典型的情况如：父视图中某个子视图使用一个Scroller对象来实现滚动操作，会使得此方法被调用。
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			int x = mScroller.getCurrX();
			int y = mScroller.getCurrY();// ImageView的当前Y坐标
			imageView.layout(0, 0, x + imageView.getWidth(), y);// 使imageView本身做相应变化
			invalidate();

			// 滑动还未完成时，手指抬起时，当前y坐标大于其实imageView的高度时
			// 设定imageView的布局参数 作用：使除imageView之外的控件做相应变化
			if (!mScroller.isFinished() && scrollerType && y > MAX_DY) {
				android.view.ViewGroup.LayoutParams params = imageView
						.getLayoutParams();
				params.height = y;
				imageView.setLayoutParams(params);
			}
			// invalidate();
		}
	}

	public class TouchTool {

		private int startX, startY;

		public TouchTool(int startX, int startY, int endX, int endY) {
			super();
			this.startX = startX;
			this.startY = startY;
		}

		public int getScrollX(float dx) {
			int xx = (int) (startX + dx / 2.5F);
			return xx;
		}

		public int getScrollY(float dy) {
			int yy = (int) (startY + dy / 2.5F);// 手势滑动距离/2.5 才是屏幕滑动的距离
												// 此内部类主要做此用
			return yy;
		}
	}
}
