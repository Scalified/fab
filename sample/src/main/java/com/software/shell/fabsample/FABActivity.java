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
 * File created: 2015-02-13 21:35:17
 */

package com.software.shell.fabsample;

import com.software.shell.fab.ActionButton;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import com.software.shell.uitools.convert.DensityConverter;
import com.software.shell.uitools.resutils.id.IdGenerator;
import com.software.shell.viewmover.configuration.MovingParams;

import java.util.Set;

/**
 * Contains a sample activity with 
 * <a href="https://github.com/shell-software/fab">Floating Action Button Library</a>
 *
 * @author Vladislav
 * @version 1.0.0
 * @since 1.0.0
 */
public class FABActivity extends Activity {
	
	private static final int SEEKBAR_PROGRESS_MULTIPLIER = 10;
	private static final int ACTION_BUTTON_POST_DELAY_MS = 3000;
	private static final float MOVE_DISTANCE = 100.0f;

	private ActionButton actionButton;
	
	private RadioGroup buttonTypeRadioGroup;
	private SeekBar shadowRadiusSeekBar;
	private SeekBar shadowXOffsetSeekBar;
	private SeekBar shadowYOffsetSeekBar;
	private CheckBox defaultIconPlusCheckBox;
	private CheckBox rippleEffectEnabledCheckBox;
	private CheckBox shadowResponsiveEffectEnabledCheckBox;
	private RadioGroup buttonColorsRadioGroup;
	private RadioGroup strokeColorRadioGroup;
	private SeekBar strokeWidthSeekBar;
	private RadioGroup buttonBehaviorRadioGroup;
	private RadioGroup animationsRadioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fab_activity_layout);

		initActionButton();
		initButtonTypeRadioGroup();
		initShadowRadiusSeekBar();
		initShadowXOffsetSeekBar();
		initShadowYOffsetSeekBar();
		initDefaultIconPlusCheckBox();
		initRippleEffectEnabledCheckBox();
		initShadowResponsiveEffectEnabledCheckBox();
		initButtonBehaviorRadioGroup();
		initButtonColorsRadioGroup();
		initStrokeColorRadioGroup();
		initStrokeWidthSeekBar();
		initAnimationsRadioGroup();
	}
	
	private void initActionButton() {
		actionButton = (ActionButton) findViewById(R.id.fab_activity_action_button);
	}
	
	private void initButtonTypeRadioGroup() {
		buttonTypeRadioGroup = (RadioGroup) findViewById(R.id.fab_activity_radiogroup_button_type);
		switch (actionButton.getType()) {
			case DEFAULT:
				buttonTypeRadioGroup.check(R.id.fab_activity_radiobutton_default);
				break;
			case MINI:
				buttonTypeRadioGroup.check(R.id.fab_activity_radiobutton_mini);
				break;
			case BIG:
                buttonTypeRadioGroup.check(R.id.fab_activity_radiobutton_big);
                break;
		}
		buttonTypeRadioGroup.setOnCheckedChangeListener(new ButtonTypeChangeListener());
	}
	
	private void initShadowRadiusSeekBar() {
		shadowRadiusSeekBar = (SeekBar) findViewById(R.id.fab_activity_seekbar_shadow_radius);
		final int progress = (int) (pxToDp(actionButton.getShadowRadius())
				* SEEKBAR_PROGRESS_MULTIPLIER);
		shadowRadiusSeekBar.setProgress(progress);
		shadowRadiusSeekBar.setOnSeekBarChangeListener(new ShadowRadiusChangeListener());
	}
	
	private void initShadowXOffsetSeekBar() {
		shadowXOffsetSeekBar = (SeekBar) findViewById(R.id.fab_activity_seekbar_shadow_x_offset);
		final int progress = (int) (pxToDp(actionButton.getShadowXOffset())
				* SEEKBAR_PROGRESS_MULTIPLIER + shadowXOffsetSeekBar.getMax() / 2);
		shadowXOffsetSeekBar.setProgress(progress);
		shadowXOffsetSeekBar.setOnSeekBarChangeListener(new ShadowOffsetChangeListener());
	}
	
	private void initShadowYOffsetSeekBar() {
		shadowYOffsetSeekBar = (SeekBar) findViewById(R.id.fab_activity_seekbar_shadow_y_offset);
		final int progress = (int) (pxToDp(actionButton.getShadowYOffset())
				* SEEKBAR_PROGRESS_MULTIPLIER + shadowXOffsetSeekBar.getMax() / 2);
		shadowYOffsetSeekBar.setProgress(progress);
		shadowYOffsetSeekBar.setOnSeekBarChangeListener(new ShadowOffsetChangeListener());
	}
	
	private void initDefaultIconPlusCheckBox() {
		defaultIconPlusCheckBox = (CheckBox) findViewById(R.id.fab_activity_checkbox_default_icon_plus);
		defaultIconPlusCheckBox.setChecked(actionButton.hasImage());
		defaultIconPlusCheckBox.setOnCheckedChangeListener(new DefaultIconPlusChangeListener());
	}

	private void initRippleEffectEnabledCheckBox() {
		rippleEffectEnabledCheckBox = (CheckBox) findViewById(R.id.fab_activity_checkbox_ripple_effect_enabled);
		rippleEffectEnabledCheckBox.setChecked(actionButton.isRippleEffectEnabled());
		rippleEffectEnabledCheckBox.setOnCheckedChangeListener(new RippleEffectEnabledChangeListener());
	}

	private void initShadowResponsiveEffectEnabledCheckBox() {
		shadowResponsiveEffectEnabledCheckBox =
				(CheckBox) findViewById(R.id.fab_activity_checkbox_shadow_responsive_effect_enabled);
		shadowResponsiveEffectEnabledCheckBox.setChecked(actionButton.isShadowResponsiveEffectEnabled());
		shadowResponsiveEffectEnabledCheckBox
				.setOnCheckedChangeListener(new ShadowResponsiveEffectEnabledChangeListener());
	}
	
	private void initButtonBehaviorRadioGroup() {
		buttonBehaviorRadioGroup = (RadioGroup) findViewById(R.id.fab_activity_button_behavior_radiogroup);
		buttonBehaviorRadioGroup.setOnCheckedChangeListener(new ButtonBehaviorChangeListener());
		buttonBehaviorRadioGroup.check(R.id.fab_activity_radiobutton_none_on_click_radiobutton);
	}
	
	private void initButtonColorsRadioGroup() {
		buttonColorsRadioGroup = (RadioGroup) findViewById(R.id.fab_activity_radiogroup_colors);
		populateColorsRadioGroup(buttonColorsRadioGroup, RadioButtons.COLORS);
		buttonColorsRadioGroup.setOnCheckedChangeListener(new ColorChangeListener());
		buttonColorsRadioGroup.check(buttonColorsRadioGroup.getChildAt(0).getId());
	}
	
	private void initStrokeColorRadioGroup() {
		strokeColorRadioGroup = (RadioGroup) findViewById(R.id.fab_activity_radiogroup_stroke);
		populateColorsRadioGroup(strokeColorRadioGroup, RadioButtons.STROKE_COLORS);
		strokeColorRadioGroup.setOnCheckedChangeListener(new ColorChangeListener());
		strokeColorRadioGroup.check(strokeColorRadioGroup.getChildAt(0).getId());
	}
	
	private void populateColorsRadioGroup(RadioGroup group, Set<RadioButtons.ColorsInfo> colorsInfos) {
		for (RadioButtons.ColorsInfo colorsInfo : colorsInfos) {
			final RadioButton button = new RadioButton(this);
			final String text = getResources().getString(colorsInfo.colorTextResId);
			final int color = getResources().getColor(colorsInfo.primaryColorResId);
			final int textColor = color == getResources().getColor(R.color.fab_material_white) ?
					getResources().getColor(R.color.fab_material_black) : color;
			button.setId(IdGenerator.next());
			button.setText(text);
			button.setTextColor(textColor);
			button.setTag(colorsInfo);
			group.addView(button);
		}
	}
	
	private void initStrokeWidthSeekBar() {
		strokeWidthSeekBar = (SeekBar) findViewById(R.id.fab_activity_seekbar_stroke_width);
		final int progress = (int) (pxToDp(actionButton.getStrokeWidth())
				* SEEKBAR_PROGRESS_MULTIPLIER);
		strokeWidthSeekBar.setProgress(progress);
		strokeWidthSeekBar.setOnSeekBarChangeListener(new StrokeWidthChangeListener());
	}
	
	private void initAnimationsRadioGroup() {
		animationsRadioGroup = (RadioGroup) findViewById(R.id.fab_activity_radiogroup_animations);
		populateAnimationsRadioGroup(animationsRadioGroup, RadioButtons.ANIMATIONS);
		animationsRadioGroup.setOnCheckedChangeListener(new AnimationsChangeListener());
		animationsRadioGroup.check(animationsRadioGroup.getChildAt(0).getId());
		animationsRadioGroup.setEnabled(false);
	}
	
	private void populateAnimationsRadioGroup(RadioGroup group, Set<RadioButtons.AnimationInfo> animationInfos) {
		for (RadioButtons.AnimationInfo animationInfo : animationInfos) {
			final RadioButton button = new RadioButton(this);
			final String text = getResources().getString(animationInfo.animationTextResId);
			button.setId(IdGenerator.next());
			button.setText(text);
			button.setTag(animationInfo);
			button.setEnabled(buttonBehaviorRadioGroup.getCheckedRadioButtonId() ==
					R.id.fab_activity_radiobutton_hide_and_show_on_click_radiobutton);
			group.addView(button);
		}
	}

	public void onActionButtonClick(View v) {
		final int checkedId = buttonBehaviorRadioGroup.getCheckedRadioButtonId();
		switch (checkedId) {
			case R.id.fab_activity_radiobutton_hide_and_show_on_click_radiobutton:
				actionButton.hide();
				new Handler().postDelayed(getShowRunnable(), ACTION_BUTTON_POST_DELAY_MS);
				break;
			case R.id.fab_activity_radiobutton_move_up_and_down_on_click_radiobutton:
				actionButton.moveUp(MOVE_DISTANCE);
				new Handler().postDelayed(getMoveDownRunnable(), ACTION_BUTTON_POST_DELAY_MS);
				break;
			case R.id.fab_activity_radiobutton_move_left_and_right_on_click_radiobutton:
				actionButton.moveLeft(MOVE_DISTANCE);
				new Handler().postDelayed(getMoveRightRunnable(), ACTION_BUTTON_POST_DELAY_MS);
			default:
				break;
		}
	}
	
	private Runnable getShowRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				actionButton.show();
			}
		};
	}
	
	private Runnable getMoveDownRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				actionButton.move(new MovingParams(FABActivity.this, 0, MOVE_DISTANCE));
			}
		};
	}
	
	private Runnable getMoveRightRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				actionButton.move(new MovingParams(FABActivity.this, MOVE_DISTANCE, 0));
			}
		};
	}

	private float pxToDp(float px) {
		return DensityConverter.pxToDp(this, px);
	}
	
	protected static float getSeekBarRealProgress(int progress) {
		return (float) progress / SEEKBAR_PROGRESS_MULTIPLIER;		
	}
	
	class ButtonTypeChangeListener implements RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (buttonTypeRadioGroup.equals(group)) {
				switch (checkedId) {
					case R.id.fab_activity_radiobutton_default:
						actionButton.setType(ActionButton.Type.DEFAULT);
						break;
					case R.id.fab_activity_radiobutton_mini:
						actionButton.setType(ActionButton.Type.MINI);
						break;
					case R.id.fab_activity_radiobutton_big:
                        actionButton.setType(ActionButton.Type.BIG);
                        break;
				}
			}
		}
		
	}
	
	class ShadowRadiusChangeListener implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (shadowRadiusSeekBar.equals(seekBar)) {
				actionButton.setShadowRadius(getSeekBarRealProgress(progress));
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (shadowRadiusSeekBar.equals(seekBar)) {
				final String toastText = "Shadow radius = " + getSeekBarRealProgress(seekBar.getProgress());
				Toast.makeText(FABActivity.this, toastText, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	class ShadowOffsetChangeListener implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (shadowXOffsetSeekBar.equals(seekBar)) {
				actionButton.setShadowXOffset(getRealProgress(progress, seekBar.getMax()));
			} else if (shadowYOffsetSeekBar.equals(seekBar)) {
				actionButton.setShadowYOffset(getRealProgress(progress, seekBar.getMax()));
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			final StringBuilder toastTextBuilder = new StringBuilder("Shadow ");
			if (shadowXOffsetSeekBar.equals(seekBar)) {
				toastTextBuilder.append("X Offset = ")
						.append(getRealProgress(seekBar));
			} else if (shadowYOffsetSeekBar.equals(seekBar)) {
				toastTextBuilder.append("Y Offset = ")
						.append(getRealProgress(seekBar));
			}
			Toast.makeText(FABActivity.this, toastTextBuilder.toString(), Toast.LENGTH_SHORT).show();
		}
		
		private float getRealProgress(SeekBar seekBar) {
			return getRealProgress(seekBar.getProgress(), seekBar.getMax());			
		}
		
		private float getRealProgress(int progress, int max) {
			return (float) (progress - max / 2) / SEEKBAR_PROGRESS_MULTIPLIER;
		}
		
	}
	
	class DefaultIconPlusChangeListener implements CheckBox.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (defaultIconPlusCheckBox.equals(buttonView)) {
				if (isChecked) {
					actionButton.setImageResource(R.drawable.fab_plus_icon);
				} else {
					actionButton.removeImage();
				}
			}
		}
		
	}

	class RippleEffectEnabledChangeListener implements CheckBox.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (rippleEffectEnabledCheckBox.equals(buttonView)) {
				actionButton.setRippleEffectEnabled(isChecked);
			}
		}

	}

	class ShadowResponsiveEffectEnabledChangeListener implements CheckBox.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (shadowResponsiveEffectEnabledCheckBox.equals(buttonView)) {
				actionButton.setShadowResponsiveEffectEnabled(isChecked);
			}
		}

	}

	abstract class RadioGroupChangeListener implements RadioGroup.OnCheckedChangeListener {
		
		RadioButton getCheckedRadioButton(RadioGroup group) {
			return (RadioButton) group.findViewById(group.getCheckedRadioButtonId());
		}

		Object extractTag(RadioGroup group) {
			return getCheckedRadioButton(group).getTag();
		}
		
	}
	
	class ColorChangeListener extends RadioGroupChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (buttonColorsRadioGroup.equals(group)) {
				RadioButtons.ColorsInfo colorsInfo = (RadioButtons.ColorsInfo) extractTag(group);
				final int buttonColor = getResources().getColor(colorsInfo.primaryColorResId);
				final int buttonColorPressed = getResources().getColor(colorsInfo.secondaryColorResId);
				actionButton.setButtonColor(buttonColor);
				actionButton.setButtonColorPressed(buttonColorPressed);
			} else if (strokeColorRadioGroup.equals(group)) {
				RadioButtons.ColorsInfo colorsInfo = (RadioButtons.ColorsInfo) extractTag(group);
				final int strokeColor = getResources().getColor(colorsInfo.primaryColorResId);
				actionButton.setStrokeColor(strokeColor);
			}
		}

	}

	class ButtonBehaviorChangeListener extends RadioGroupChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (buttonBehaviorRadioGroup.equals(group)) {
				for (int i = 0; i < animationsRadioGroup.getChildCount(); i++) {
					RadioButton button = (RadioButton) animationsRadioGroup.getChildAt(i);
					button.setEnabled(checkedId == R.id.fab_activity_radiobutton_hide_and_show_on_click_radiobutton);
				}
 			}
		}

	}
	
	class StrokeWidthChangeListener implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (strokeWidthSeekBar.equals(seekBar)) {
				actionButton.setStrokeWidth(getSeekBarRealProgress(progress));
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (strokeWidthSeekBar.equals(seekBar)) {
				final String toastText = "Stroke width = " + getSeekBarRealProgress(seekBar.getProgress());
				Toast.makeText(FABActivity.this, toastText, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	class AnimationsChangeListener extends RadioGroupChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (animationsRadioGroup.equals(group)) {
				final RadioButtons.AnimationInfo animationInfo =
						(RadioButtons.AnimationInfo) extractTag(group);
				actionButton.setShowAnimation(animationInfo.animationOnShow);
				actionButton.setHideAnimation(animationInfo.animationOnHide);
			}
		}
		
	}
	
}
