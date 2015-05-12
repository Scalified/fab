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
 * File created: 2015-05-04 19:25:07
 */

package com.software.shell.fab;

import android.graphics.Canvas;

/**
 * Abstract class, responsible for drawing the <b>Action Button</b> effects
 *
 * @author Vladislav
 * @version 1.1.0
 * @since 1.1.0
 */
abstract class EffectDrawer {

	/**
	 * <b>Action Button</b> instance
	 */
	private ActionButton actionButton;

	/**
	 * Called to draw a specific effect
	 *
	 * @param canvas canvas, on which the drawing is to be performed
	 */
	abstract void draw(Canvas canvas);

	/**
	 * Overrides default constructor
	 *
	 * @param actionButton <b>Action Button</b> instance
	 */
	EffectDrawer(ActionButton actionButton) {
		this.actionButton = actionButton;
	}

	/**
	 * Returns the <b>Action Button</b> instance
	 *
	 * @return <b>Action Button</b> instance
	 */
	ActionButton getActionButton() {
		return actionButton;
	}

	/**
	 * Checks whether <b>Action Button</b> is in
	 * {@link com.software.shell.fab.ActionButton.State#PRESSED} state
	 *
	 * @return true if <b>Action Button</b> is in
	 *         {@link com.software.shell.fab.ActionButton.State#PRESSED} state,
	 *         otherwise false
	 */
	boolean isPressed() {
		return actionButton.getState() == ActionButton.State.PRESSED;
	}

}
