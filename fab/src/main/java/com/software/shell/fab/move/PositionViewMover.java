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
 * File created: 2015-03-07 08:20:51
 */

package com.software.shell.fab.move;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * View mover class, which moves the view, based on its position
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
class PositionViewMover extends ViewMover {

	private static final String LOG_TAG = String.format("[FAB][%s]", PositionViewMover.class.getSimpleName());

	private int screenWidth;
	private int screenHeight;

	PositionViewMover(View view) {
		super(view);
		init();
	}

	private void init() {
		final DisplayMetrics displayMetrics = getView().getResources().getDisplayMetrics();
		screenHeight = displayMetrics.heightPixels;
		screenWidth = displayMetrics.widthPixels;
		Log.d(LOG_TAG, "screenHeight: " + screenHeight);
		Log.d(LOG_TAG, "screenWidth: " + screenWidth);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	void changeViewPosition(MovementParameters parameters) {
		final float endPointX = calculateEndPointX(parameters.getXAxisDelta());
		final float endPointY = calculateEndPointY(parameters.getYAxisDelta());
		getView().setX(endPointX);
		getView().setY(endPointY);
	}

	@Override
	boolean hasHorizontalSpaceToMove(float xAxisDelta) {
		final float endPointX = calculateEndPointX(xAxisDelta);
		return endPointX >= 0 && endPointX <= screenWidth;
	}

	@Override
	boolean hasVerticalSpaceToMove(float yAxisDelta) {
		final float endPointY = calculateEndPointY(yAxisDelta);
		return endPointY >= 0 && endPointY <= screenHeight;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private float calculateEndPointX(float xAxisDelta) {
		return getView().getX() + xAxisDelta;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private float calculateEndPointY(float yAxisDelta) {
		return getView().getY() + yAxisDelta;
	}

}
