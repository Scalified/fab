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
 * File created: 2015-03-07 11:23:04
 */

package com.software.shell.fab.move;

import android.os.Build;
import android.view.View;

/**
 * Helper class, which serves for creating a view mover instances
 * depending on {@code BUILD VERSION}
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class ViewMoverFactory {

	public static ViewMover createInstance(View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return new PositionViewMover(view);
		} else {
			return null;
		}
	}

}
