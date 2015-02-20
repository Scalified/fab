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
 * File created: 2015-02-15 13:05:38
 */

package com.software.shell.fabsample;

import com.software.shell.fab.ActionButton;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Contains button colors
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
public class RadioButtons {
	
	public static final Set<ColorsInfo> COLORS = new LinkedHashSet<>();
	public static final Set<ColorsInfo> STROKE_COLORS = new LinkedHashSet<>();
	public static final Set<AnimationInfo> ANIMATIONS = new LinkedHashSet<>();

	static {
		
		COLORS.add(new ColorsInfo(R.string.fab_default_color_text, R.color.fab_light_grey_color,
				R.color.fab_dark_grey_color));
		COLORS.add(new ColorsInfo(R.string.fab_red_color_text, R.color.fab_material_red_500,
				R.color.fab_material_red_900));
		COLORS.add(new ColorsInfo(R.string.fab_pink_color_text, R.color.fab_material_pink_500,
				R.color.fab_material_pink_900));
		COLORS.add(new ColorsInfo(R.string.fab_purple_color_text, R.color.fab_material_purple_500,
				R.color.fab_material_purple_900));
		COLORS.add(new ColorsInfo(R.string.fab_deep_purple_color_text, R.color.fab_material_deep_purple_500,
				R.color.fab_material_deep_purple_900));
		COLORS.add(new ColorsInfo(R.string.fab_indigo_color_text, R.color.fab_material_indigo_500,
				R.color.fab_material_indigo_900));
		COLORS.add(new ColorsInfo(R.string.fab_blue_color_text, R.color.fab_material_blue_500,
				R.color.fab_material_blue_900));
		COLORS.add(new ColorsInfo(R.string.fab_light_blue_color_text, R.color.fab_material_light_blue_500,
				R.color.fab_material_light_blue_900));
		COLORS.add(new ColorsInfo(R.string.fab_cyan_color_text, R.color.fab_material_cyan_500,
				R.color.fab_material_cyan_900));
		COLORS.add(new ColorsInfo(R.string.fab_teal_color_text, R.color.fab_material_teal_500,
				R.color.fab_material_teal_900));
		COLORS.add(new ColorsInfo(R.string.fab_green_color_text, R.color.fab_material_green_500,
				R.color.fab_material_green_900));
		COLORS.add(new ColorsInfo(R.string.fab_light_green_color_text, R.color.fab_material_light_green_500,
				R.color.fab_material_light_green_900));
		COLORS.add(new ColorsInfo(R.string.fab_lime_color_text, R.color.fab_material_lime_500,
				R.color.fab_material_lime_900));
		COLORS.add(new ColorsInfo(R.string.fab_yellow_color_text, R.color.fab_material_yellow_500,
				R.color.fab_material_yellow_900));
		COLORS.add(new ColorsInfo(R.string.fab_amber_color_text, R.color.fab_material_amber_500,
				R.color.fab_material_amber_900));
		COLORS.add(new ColorsInfo(R.string.fab_orange_color_text, R.color.fab_material_orange_500,
				R.color.fab_material_orange_900));
		COLORS.add(new ColorsInfo(R.string.fab_deep_orange_color_text, R.color.fab_material_deep_orange_500,
				R.color.fab_material_deep_orange_900));
		COLORS.add(new ColorsInfo(R.string.fab_brown_color_text, R.color.fab_material_brown_500,
				R.color.fab_material_brown_900));
		COLORS.add(new ColorsInfo(R.string.fab_grey_color_text, R.color.fab_material_grey_500,
				R.color.fab_material_grey_900));
		COLORS.add(new ColorsInfo(R.string.fab_blue_grey_color_text, R.color.fab_material_blue_grey_500,
				R.color.fab_material_blue_grey_900));
		COLORS.add(new ColorsInfo(R.string.fab_black_and_white_color_text, R.color.fab_material_white,
				R.color.fab_material_black));
		STROKE_COLORS.add(new ColorsInfo(R.string.fab_black_stroke_color_text, R.color.fab_material_black));
		STROKE_COLORS.add(new ColorsInfo(R.string.fab_grey_stroke_color_text, R.color.fab_material_grey_500));
		STROKE_COLORS.add(new ColorsInfo(R.string.fab_yellow_stroke_color_text, R.color.fab_material_yellow_900));
		ANIMATIONS.add(new AnimationInfo(R.string.fab_animation_none_text,
				ActionButton.Animations.NONE, ActionButton.Animations.NONE));
		ANIMATIONS.add(new AnimationInfo(R.string.fab_animation_fade_in_fade_out_text, 
				ActionButton.Animations.FADE_IN, ActionButton.Animations.FADE_OUT));
		ANIMATIONS.add(new AnimationInfo(R.string.fab_animation_scale_up_scale_down_text,
				ActionButton.Animations.SCALE_UP, ActionButton.Animations.SCALE_DOWN));
		ANIMATIONS.add(new AnimationInfo(R.string.fab_animation_roll_from_right_roll_to_right_text,
				ActionButton.Animations.ROLL_FROM_RIGHT, ActionButton.Animations.ROLL_TO_RIGHT));
		ANIMATIONS.add(new AnimationInfo(R.string.fab_animation_roll_from_down_roll_to_down_text,
				ActionButton.Animations.ROLL_FROM_DOWN, ActionButton.Animations.ROLL_TO_DOWN));
		ANIMATIONS.add(new AnimationInfo(R.string.fab_animation_jump_from_right_jump_to_right_text,
				ActionButton.Animations.JUMP_FROM_RIGHT, ActionButton.Animations.JUMP_TO_RIGHT));
		ANIMATIONS.add(new AnimationInfo(R.string.fab_animation_jump_from_down_jump_to_down_text,
				ActionButton.Animations.JUMP_FROM_DOWN, ActionButton.Animations.JUMP_TO_DOWN));

	}
	
	static class ColorsInfo {

		int colorTextResId;
		int primaryColorResId;
		int secondaryColorResId;

		ColorsInfo(int colorTextResId, int primaryColorResId, int secondaryColorResId) {
			this.colorTextResId = colorTextResId;
			this.primaryColorResId = primaryColorResId;
			this.secondaryColorResId = secondaryColorResId;
		}

		ColorsInfo(int colorTextResId, int primaryColorResId) {
			this.colorTextResId = colorTextResId;
			this.primaryColorResId = primaryColorResId;
		}
		
	}

	static class AnimationInfo {
		
		int animationTextResId;
		ActionButton.Animations animationOnShow;
		ActionButton.Animations animationOnHide;

		AnimationInfo(int animationTextResId, ActionButton.Animations animationOnShow,
		              ActionButton.Animations animationOnHide) {
			this.animationTextResId = animationTextResId;
			this.animationOnShow = animationOnShow;
			this.animationOnHide = animationOnHide;
		}
		
	}
	
	
}
