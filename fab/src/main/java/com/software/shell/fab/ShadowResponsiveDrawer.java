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
 * File created: 2015-05-04 16:15:28
 */

package com.software.shell.fab;

import android.graphics.Canvas;
import android.util.Log;

/**
 * A class responsible for drawing the <b>Action Button</b>
 * Shadow Responsive Effect
 *
 * @author shell
 * @version 1.1.0
 * @since 1.1.0
 */
class ShadowResponsiveDrawer extends EffectDrawer {

	/**
	 * Logging tag
	 */
	private static final String LOG_TAG = String.format("[FAB][%s]", ShadowResponsiveDrawer.class.getSimpleName());

	/**
	 * The default factor, which is used as multiplier for determining
	 * the enlargement limits of the shadow
	 */
	private static final float SHADOW_RESPONSE_FACTOR = 1.75f;

	/**
	 * The default step, which is used for incrementing the shadow
	 * radius while drawing the Shadow Responsive Effect
	 */
	private static final float SHADOW_DRAWING_STEP = 0.5f;

	/**
	 * Current shadow radius
	 */
	private float currentShadowRadius;

	/**
	 * Creates the {@link ShadowResponsiveDrawer} instance
	 *
	 * @param actionButton <b>Action Button</b> instance
	 */
	ShadowResponsiveDrawer(ActionButton actionButton) {
		super(actionButton);
		init();
	}

	/**
	 * Draws the Shadow Responsive Effect
	 *
	 * @param canvas canvas, on which the drawing is to be performed
	 */
	@Override
	void draw(Canvas canvas) {
		updateRadius();
		getActionButton().getPaint().setShadowLayer(currentShadowRadius, getActionButton().getShadowXOffset(),
				getActionButton().getShadowYOffset(), getActionButton().getShadowColor());
		Log.v(LOG_TAG, "Shadow responsive step drawn");
	}

	/**
	 * Initializes the {@link ShadowResponsiveDrawer} instance
	 */
	private void init() {
		currentShadowRadius = getActionButton().getShadowRadius();
	}

	/**
	 * Updates the {@link #currentShadowRadius} depending on the current state
	 */
	void updateRadius() {
		if (isPressed() && currentShadowRadius < getMaxShadowRadius()) {
			currentShadowRadius += SHADOW_DRAWING_STEP;
			getActionButton().getInvalidator().requireInvalidation();
		} else if (!isPressed() && currentShadowRadius > getMinShadowRadius()) {
			currentShadowRadius -= SHADOW_DRAWING_STEP;
			getActionButton().getInvalidator().requireInvalidation();
		} else if (!isPressed()){
			currentShadowRadius = getActionButton().getShadowRadius();
		}
		Log.v(LOG_TAG, "Shadow responsive current radius updated to: " + currentShadowRadius);
	}

	/**
	 * Sets the current shadow radius
	 *
	 * @param currentShadowRadius current shadow radius
	 */
	void setCurrentShadowRadius(float currentShadowRadius) {
		this.currentShadowRadius = currentShadowRadius;
	}

	/**
	 * Returns the minimum value of the shadow radius
	 * <p>
	 * Shadow has the minimum radius while in
	 * {@link com.software.shell.fab.ActionButton.State#NORMAL} state
	 *
	 * @return minimum shadow radius
	 */
	float getMinShadowRadius() {
		return getActionButton().getShadowRadius();
	}

	/**
	 * Returns the maximum value of the shadow radius
	 * <p>
	 * Shadow has the maximum radius while in
	 * {@link com.software.shell.fab.ActionButton.State#PRESSED} state
	 * and the Shadow Responsive Effect is fully drawn
	 *
	 * @return maximum value of the shadow radius
	 */
	float getMaxShadowRadius() {
		return getMinShadowRadius() * SHADOW_RESPONSE_FACTOR;
	}

}
