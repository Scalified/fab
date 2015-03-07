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
 * File created: 2015-03-07 10:14:24
 */

package com.software.shell.fab.move;

import android.content.Context;
import android.view.animation.Interpolator;
import com.software.shell.fab.MetricsConverter;

/**
 * Entity class, which holds parameters for move animation
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
public class MovementParameters {

	private static final long DEFAULT_ANIMATION_DURATION = 500;

	private float xAxisDelta;
	private float yAxisDelta;

	private final long animationDuration;
	private final Interpolator animationInterpolator;

	public MovementParameters(float xAxisDelta, float yAxisDelta, long animationDuration,
	                          Interpolator animationInterpolator) {
		this.xAxisDelta = xAxisDelta;
		this.yAxisDelta = yAxisDelta;
		this.animationDuration = animationDuration;
		this.animationInterpolator = animationInterpolator;
	}

	public MovementParameters(float xAxisDelta, float yAxisDelta) {
		this(xAxisDelta, yAxisDelta, DEFAULT_ANIMATION_DURATION, null);
	}

	public MovementParameters(float xAxisDelta, float yAxisDelta, long animationDuration) {
		this(xAxisDelta, yAxisDelta, animationDuration, null);
	}

	MovementParameters(MovementParameters parameters) {
		this(parameters.getXAxisDelta(), parameters.getYAxisDelta(), parameters.getAnimationDuration(),
				parameters.getAnimationInterpolator());
	}

	void setXAxisDelta(float xAxisDelta) {
		this.xAxisDelta = xAxisDelta;
	}

	void setYAxisDelta(float yAxisDelta) {
		this.yAxisDelta = yAxisDelta;
	}

	public long getAnimationDuration() {
		return animationDuration;
	}

	public float getXAxisDelta() {
		return xAxisDelta;
	}

	public float getYAxisDelta() {
		return yAxisDelta;
	}

	public Interpolator getAnimationInterpolator() {
		return animationInterpolator;
	}

	/**
	 * A wrapper class for {@link com.software.shell.fab.move.MovementParameters},
	 * which converts its density-dependent values into density-independent ones
	 */
	static class Converted extends MovementParameters {

		Converted(Context context, MovementParameters parameters) {
			super(MetricsConverter.dpToPx(context, parameters.getXAxisDelta()),
					MetricsConverter.dpToPx(context, parameters.getYAxisDelta()),
					parameters.getAnimationDuration(), parameters.getAnimationInterpolator());
		}

	}

}
