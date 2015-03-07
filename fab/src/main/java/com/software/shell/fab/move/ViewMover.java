/*
 * Copyright 2015 Shell Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File created: 2015-03-07 08:04:52
 */

package com.software.shell.fab.move;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

/**
 * Abstract class, which is used by view mover subclasses
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class ViewMover {

	private static final String LOG_TAG = String.format("[FAB][%s]", ViewMover.class.getSimpleName());

	private final View view;

	ViewMover(View view) {
		this.view = view;
	}

	abstract void changeViewPosition(MovementParameters parameters);
	abstract boolean hasHorizontalSpaceToMove(float xAxisDelta);
	abstract boolean hasVerticalSpaceToMove(float yAxisDelta);

	protected View getView() {
		return view;
	}

	public void move(MovementParameters parameters) {
		if (isPreviousAnimationCompleted()) {
			final MovementParameters mParameters = createCorrectMovementParameters(parameters);
			if (isMovementNonZero(mParameters)) {
				final Animation moveAnimation = createAnimation(mParameters);
				view.startAnimation(moveAnimation);
			}
		}
	}

	private boolean isPreviousAnimationCompleted() {
		final Animation previousAnimation = view.getAnimation();
		final boolean previousAnimationCompleted = previousAnimation == null || previousAnimation.hasEnded();
		if (!previousAnimationCompleted) {
			Log.w(LOG_TAG, "Unable to move the view. View is being currently moving");
		}
		return previousAnimationCompleted;
	}

	private boolean isMovementNonZero(MovementParameters parameters) {
		final boolean movementCorrect = parameters.getXAxisDelta() != 0.0f || parameters.getYAxisDelta() != 0.0f;
		if (!movementCorrect) {
			Log.w(LOG_TAG, "Zero movement detected. No movement will be performed");
		}
		return movementCorrect;
	}

	private MovementParameters createCorrectMovementParameters(MovementParameters parameters) {
		final MovementParameters mParameters = new MovementParameters.Converted(view.getContext(), parameters);
		final float xAxisDelta = calculateXAxisDelta(mParameters);
		final float yAxisDelta = calculateYAxisDelta(mParameters);
		mParameters.setXAxisDelta(xAxisDelta);
		mParameters.setYAxisDelta(yAxisDelta);
		return mParameters;
	}

	private float calculateXAxisDelta(MovementParameters parameters) {
		final float xAxisDelta = parameters.getXAxisDelta();
		if (hasHorizontalSpaceToMove(xAxisDelta)) {
			return xAxisDelta;
		} else {
			Log.w(LOG_TAG, "Unable to move the view horizontally. No horizontal space left to move");
			return 0.0f;
		}
	}

	private float calculateYAxisDelta(MovementParameters parameters) {
		final float yAxisDelta = parameters.getYAxisDelta();
		if (hasVerticalSpaceToMove(yAxisDelta)) {
			return yAxisDelta;
		} else {
			Log.w(LOG_TAG, "Unable to move the view vertically. No vertical space left to move");
			return 0.0f;
		}
	}

	protected Animation createAnimation(MovementParameters parameters) {
		final Animation animation = new TranslateAnimation(0, parameters.getXAxisDelta(), 0,
				parameters.getYAxisDelta());
		animation.setFillEnabled(true);
		animation.setFillBefore(false);
		animation.setDuration(parameters.getAnimationDuration());
		final Interpolator interpolator = parameters.getAnimationInterpolator();
		if (interpolator != null) {
			animation.setInterpolator(interpolator);
		}
		animation.setAnimationListener(new MoveAnimationListener(parameters));
		return animation;
	}

	class MoveAnimationListener implements Animation.AnimationListener {

		private final MovementParameters parameters;

		MoveAnimationListener(MovementParameters parameters) {
			this.parameters = parameters;
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			changeViewPosition(parameters);
		}

	}

}
