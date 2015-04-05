/*
 * Copyright 2015 Shell Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File created: 2015-04-01 09:29:02
 */

package com.software.shell.fab;

import android.util.Log;

/**
 * Entity class, which contains the information about X- and Y-axis
 * coordinates of the touch point
 *
 * @author shell
 * @version 1.1.0
 * @since 1.1.0
 */
public final class TouchPoint {

	/**
	 * Logging tag
	 */
	private static final String LOG_TAG = String.format("[FAB][%s]", TouchPoint.class.getSimpleName());

	/**
	 * Touch point X-axis coordinate
	 */
	private float x;

	/**
	 * Touch point Y-axis coordinate
	 */
	private float y;

	/**
	 * The last touch point X-axis coordinate, which was saved before reset
	 */
	private float lastX;

	/**
	 * The last touch point Y-axis coordinate, which was saved before reset
	 */
	private float lastY;

	/**
	 * Creates the {@link TouchPoint} instance
	 *
	 * @param x touch point X-axis coordinate
	 * @param y touch point Y-axis coordinate
	 */
	TouchPoint(float x, float y) {
		setX(x);
		setY(y);
	}

	/**
	 * Returns the touch point X-axis coordinate
	 *
	 * @return touch point X-axis coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the touch point X-axis coordinate and calls
	 * {@link #setLastX(float)}
	 *
	 * @param x touch point X-axis coordinate
	 */
	final void setX(float x) {
		this.x = x;
		Log.v(LOG_TAG, "Touch point X-axis coordinate set to: " + getX());
		setLastX(x);
	}

	/**
	 * Returns the touch point Y-axis coordinate
	 *
	 * @return touch point Y-axis coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the touch point Y-axis coordinate and calls
	 * {@link #setLastY(float)}
	 *
	 * @param y touch point Y-axis coordinate
	 */
	final void setY(float y) {
		this.y = y;
		Log.v(LOG_TAG, "Touch point Y-axis coordinate set to: " + getY());
		setLastY(y);
	}

	/**
	 * Returns the last touch point X-axis coordinate, which was saved before reset
	 *
	 * @return last touch point X-axis coordinate, which was saved before reset
	 */
	public float getLastX() {
		return lastX;
	}

	/**
	 * Sets the last touch point X-axis coordinate if it is greater than zero
	 *
	 * @param x last touch point X-axis coordinate
	 */
	final void setLastX(float x) {
		if (x > 0) {
			this.lastX = x;
			Log.v(LOG_TAG, "Touch point last X-axis coordinate set to: " + getLastX());
		}
	}

	/**
	 * Returns the last touch point Y-axis coordinate, which was saved before reset
	 *
	 * @return last touch point Y-axis coordinate, which was saved before reset
	 */
	public float getLastY() {
		return lastY;
	}

	/**
	 * Sets the last touch point Y-axis coordinate if it is greater than zero
	 *
	 * @param y last touch point Y-axis coordinate
	 */
	final void setLastY(float y) {
		if (y > 0) {
			this.lastY = y;
			Log.v(LOG_TAG, "Touch point last Y-axis coordinate set to: " + getLastY());
		}
	}

	/**
	 * Resets the touch point X- and Y-axis coordinates to zero
	 */
	void reset() {
		setX(0.0f);
		setY(0.0f);
		Log.v(LOG_TAG, "Touch point reset");
	}

	/**
	 * Checks whether the touch point is inside the circle or not
	 *
	 * @param centerPointX circle X-axis center coordinate
	 * @param centerPointY circle Y-axis center coordinate
	 * @param radius circle radius
	 *
	 * @return true if touch point is inside the circle, otherwise false
	 */
	boolean isInsideCircle(float centerPointX, float centerPointY, float radius) {
		double xValue = Math.pow((getX() - centerPointX), 2);
		double yValue = Math.pow((getY() - centerPointY), 2);
		double radiusValue = Math.pow(radius, 2);
		boolean touchPointInsideCircle = xValue + yValue <= radiusValue;
		Log.v(LOG_TAG, String.format("Point IS %s circle", touchPointInsideCircle ? "INSIDE" : "NOT INSIDE"));
		return touchPointInsideCircle;
	}

}
