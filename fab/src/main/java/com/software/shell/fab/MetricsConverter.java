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
 * File created: 2015-01-30 22:55:44
 */

package com.software.shell.fab;

import android.content.Context;

/**
 * Contains utility methods for metrics conversion
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MetricsConverter {

	/**
	 * Prevents from creating {@link com.software.shell.fab.MetricsConverter} instances 
	 */
	private MetricsConverter() {
	}

	/**
	 * Converts the density-independent value into real pixel value based on current display metrics
	 *
	 * @param context application context
	 * @param dp density-independent value
	 * @return converted real pixel value
	 */
	public static float dpToPx(Context context, float dp) {
		return dp * calculateScale(context);
	}

	/**
	 * Converts the real pixel value into density-independent value based on current display metrics
	 *
	 * @param context application context
	 * @param px real pixel value
	 * @return density-independent value
	 */
	public static float pxToDp(Context context, float px) {
		return px / calculateScale(context);
	}

	/**
	 * Calculates the density scale factor for the current device
	 * <p>
	 * Used when converting density-independent pixels to real ones and vice versa
	 *  
	 * @param context application context
	 * @return density scale factor for the current device
	 */
	private static float calculateScale(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}
	
}
