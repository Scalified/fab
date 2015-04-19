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

import android.view.View;

/**
 * <class description>
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
class Invalidator {

	private boolean invalidationRequired;
	private long invalidationDelay;
	private boolean invalidationDelayedRequired;

	private final View view;

	Invalidator(View view) {
		this.view = view;
	}

	public boolean isInvalidationRequired() {
		return invalidationRequired;
	}

	public void setInvalidationRequired(boolean invalidationRequired) {
		this.invalidationRequired = invalidationRequired;
	}

	public long getInvalidationDelay() {
		return invalidationDelay;
	}

	public void setInvalidationDelay(long invalidationDelay) {
		this.invalidationDelay = invalidationDelay;
	}

	public boolean isInvalidationDelayedRequired() {
		return invalidationDelayedRequired;
	}

	public void setInvalidationDelayedRequired(boolean invalidationDelayedRequired) {
		this.invalidationDelayedRequired = invalidationDelayedRequired;
	}

	public void invalidate() {
		if (isInvalidationRequired()) {
//			view.postInvalidate();
		}
		if (isInvalidationDelayedRequired()) {
			view.postInvalidateDelayed(getInvalidationDelay());
		}
		reset();
	}

	private void reset() {
		setInvalidationRequired(false);
		setInvalidationDelayedRequired(false);
		setInvalidationDelay(0L);
	}

}
