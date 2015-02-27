package com.software.shell.fab;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * The class is responsible for changing the view's position relatively to its parent container
 * <p>
 * Uses layout margins to perform the move
 *
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
class ViewMarginMover {
	
	private static final String LOG_TAG = String.format("[FAB][%s]", ViewMarginMover.class.getSimpleName());
	
	private static final int DEFAULT_ANIMATION_DURATION_MS = 500;
	
	private final View view;

	ViewMarginMover(View view) {
		this.view = view;
	}
	
	ViewGroup.MarginLayoutParams calculateLayoutParams(float xAxisDelta, float yAxisDelta) {
//		final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
		final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
		if (layoutParams.rightMargin == 0 && layoutParams.leftMargin != 0) {
			Log.v(LOG_TAG, "Right margin not set, left margin will be changed");
			layoutParams.setMargins(layoutParams.leftMargin + (int) xAxisDelta, layoutParams.topMargin, 
					layoutParams.rightMargin, layoutParams.bottomMargin);
//			layoutParams.leftMargin += xAxisDelta;
		} else {
			Log.v(LOG_TAG, "Right margin set, right margin will be changed");
			layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, 
					layoutParams.rightMargin - (int) xAxisDelta, layoutParams.bottomMargin);
//			layoutParams.rightMargin -= xAxisDelta;
		}
		if (layoutParams.bottomMargin == 0 && layoutParams.topMargin != 0) {
			Log.v(LOG_TAG, "Bottom margin not set, top margin will be changed");
			layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + (int) yAxisDelta, 
					layoutParams.rightMargin, layoutParams.bottomMargin);
//			layoutParams.topMargin += yAxisDelta;
		} else {
			Log.v(LOG_TAG, "Bottom margin set, bottom margin will be changed");
			layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, 
					layoutParams.rightMargin, layoutParams.bottomMargin - (int) yAxisDelta);
//			layoutParams.bottomMargin -= yAxisDelta;
		}
		return layoutParams;
	}
	
	View getParentView() {
		return (View) view.getParent();
	}
	
	void move(float xAxisDelta, float yAxisDelta) {
		move(xAxisDelta, yAxisDelta, DEFAULT_ANIMATION_DURATION_MS, null);
	}
	
	void move(float xAxisDelta, float yAxisDelta, long duration) {
		move(xAxisDelta, yAxisDelta, duration, null);
	}
	
	void move(float xAxisDelta, float yAxisDelta, long duration, Interpolator interpolator) {
		final Animation currentAnimation = view.getAnimation();
		if (currentAnimation != null && !currentAnimation.hasEnded()) {
			Log.w(LOG_TAG, "Unable to move the view. View is being currently moving");
			return;
		}
		if (xAxisDelta != 0 || yAxisDelta != 0) {
			final float mXAxisDelta = hasHorizontalSpace(xAxisDelta) ? xAxisDelta : 0.0f;
			final float mYAxisDelta = hasVerticalSpace(yAxisDelta) ? yAxisDelta : 0.0f;
			Log.v(LOG_TAG, String.format("View is about to be moved at: %s x-axis, %s y-axis", mXAxisDelta, 
					mYAxisDelta));
			final Animation moveAnimation = new TranslateAnimation(0, mXAxisDelta, 0, mYAxisDelta);
			moveAnimation.setFillEnabled(true);
			moveAnimation.setFillBefore(false);
			moveAnimation.setDuration(duration);
			if (interpolator != null) {
				moveAnimation.setInterpolator(interpolator);
			}
			final ViewGroup.LayoutParams layoutParams = calculateLayoutParams(mXAxisDelta, mYAxisDelta);
			moveAnimation.setAnimationListener(new MoveAnimationListener(layoutParams));
			view.startAnimation(moveAnimation);
		}
	}

	boolean hasVerticalSpace(float yAxisDelta) {
		boolean verticalSpaceAvailable;
		if (yAxisDelta >= 0) {
			verticalSpaceAvailable = view.getBottom() + yAxisDelta <= getParentView().getHeight();
		} else {
			verticalSpaceAvailable =  view.getTop() + yAxisDelta >= 0;
		}
		if (!verticalSpaceAvailable) {
			Log.w(LOG_TAG, "No vertical space left to move the button");
		}
		return verticalSpaceAvailable;
	}
	
	boolean hasHorizontalSpace(float xAxisDelta) {
		boolean horizontalSpaceAvailable;
		if (xAxisDelta >= 0) {
			horizontalSpaceAvailable = view.getRight() + xAxisDelta <= getParentView().getWidth();
		} else {
			horizontalSpaceAvailable = view.getLeft() + xAxisDelta >= 0;
		}
		if (!horizontalSpaceAvailable) {
			Log.w(LOG_TAG, "No horizontal space left to move the button");
		}
		return horizontalSpaceAvailable;
	}

	class MoveAnimationListener implements Animation.AnimationListener {
		
		private ViewGroup.LayoutParams layoutParams;

		public MoveAnimationListener(ViewGroup.LayoutParams layoutParams) {
			this.layoutParams = layoutParams;
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
			Log.v(LOG_TAG, "View has been moved");
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
		
	}
	
}
