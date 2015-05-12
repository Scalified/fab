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
 * File created: 2015-04-13 21:16:33
 */

package com.software.shell.fab;

import android.util.Log;
import android.view.View;

/**
 * Used to invalidate the view
 * <p>
 * Contains invalidation options, which can be configured and then
 * used for view invalidation
 *
 * @author shell
 * @version 1.1.0
 * @since 1.1.0
 */
class ViewInvalidator {

	/**
	 * Logging tag
	 */
	private static final String LOG_TAG = String.format("[FAB][%s]", ViewInvalidator.class.getSimpleName());

	/**
	 * Indicates whether invalidation required
	 */
	private boolean invalidationRequired;

	/**
	 * Indicates whether delayed invalidation required
	 */
	private boolean invalidationDelayedRequired;

	/**
	 * Delay time in ms used for delayed invalidation
	 */
	private long invalidationDelay;

	/**
	 * A view to invalidate
	 */
	private final View view;

	/**
	 * Creates the {@link ViewInvalidator} instance
	 *
	 * @param view view to be invalidate
	 */
	ViewInvalidator(View view) {
		this.view = view;
	}

	/**
	 * Returns whether invalidation required
	 *
	 * @return true if invalidation required, otherwise false
	 */
	boolean isInvalidationRequired() {
		return invalidationRequired;
	}

	/**
	 * Sets that invalidation is required
	 *
	 */
	void requireInvalidation() {
		this.invalidationRequired = true;
		Log.v(LOG_TAG, "Invalidation required set");
	}

	/**
	 * Returns whether delayed invalidation required
	 *
	 * @return true if delayed invalidation required, otherwise false
	 */
	boolean isInvalidationDelayedRequired() {
		return invalidationDelayedRequired;
	}

	/**
	 * Sets that delayed invalidation required
	 */
	void requireDelayedInvalidation() {
		this.invalidationDelayedRequired = true;
		Log.v(LOG_TAG, "Delayed invalidation required set");
	}

	/**
	 * Returns the invalidation delay time in ms used for delayed invalidation
	 *
	 * @return invalidation delay time in ms used for delayed invalidation
	 */
	long getInvalidationDelay() {
		return invalidationDelay;
	}

	/**
	 * Sets the invalidation delay time used for delayed invalidation
	 *
	 * @param invalidationDelay invalidation delay time in ms
	 */
	void setInvalidationDelay(long invalidationDelay) {
		this.invalidationDelay = invalidationDelay;
	}

	/**
	 * Invalidates the view based on the current invalidator configuration
	 */
	void invalidate() {
		if (isInvalidationRequired()) {
			view.postInvalidate();
			Log.v(LOG_TAG, "View invalidation called");
		}
		if (isInvalidationDelayedRequired()) {
			view.postInvalidateDelayed(getInvalidationDelay());
			Log.v(LOG_TAG, "View delay invalidation called. Delay time is: " + getInvalidationDelay());
		}
		reset();
	}

	/**
	 * Resets the current invalidator configuration
	 */
	private void reset() {
		invalidationRequired = false;
		invalidationDelayedRequired = false;
		setInvalidationDelay(0L);
		Log.v(LOG_TAG, "View invalidator configuration reset");
	}

}
