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
 * File created: 2015-01-17 10:39:13
 */

package com.software.shell.fab;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.view.animation.*;

import com.software.shell.uitools.convert.DensityConverter;
import com.software.shell.uitools.resutils.color.ColorModifier;
import com.software.shell.viewmover.configuration.MovingDetails;
import com.software.shell.viewmover.movers.ViewMover;
import com.software.shell.viewmover.movers.ViewMoverFactory;

/**
 * This class represents a <b>Action Button</b>, which is used in 
 * <a href="http://www.google.com.ua/design/spec/components/buttons.html">Material Design</a>
 *
 * @author Vladislav
 * @version 1.1.0
 * @since 1.0.0
 */
public class ActionButton extends View {

	/**
	 * Logging tag
	 */
	private static final String LOG_TAG = String.format("[FAB][%s]", ActionButton.class.getSimpleName());

	/**
	 * <b>Action Button</b> type
	 */
	private Type type = Type.DEFAULT;

	/**
	 * <b>Action Button</b> size in actual pixels
	 */
	private float size = dpToPx(type.getSize());

	/**
	 * <b>Action Button</b> state
	 */
	private State state = State.NORMAL;

	/**
	 * <b>Action Button</b> color for the {@link State#NORMAL} state
	 */
	private int buttonColor = Color.parseColor("#FF9B9B9B");

	/**
	 * <b>Action Button</b> color for the {@link State#PRESSED} state
	 */
	private int buttonColorPressed = Color.parseColor("#FF696969");

	/**
	 * Determines whether <b>Action Button</b> ripple effect enabled or not
	 */
	private boolean rippleEffectEnabled;

	/**
	 * <b>Action Button</b> ripple effect color
	 */
	private int buttonColorRipple = darkenButtonColorPressed();

	/**
	 * Shadow radius expressed in actual pixels
	 */
	private float shadowRadius = dpToPx(2.0f);

	/**
	 * Shadow X-axis offset expressed in actual pixels
	 */
	private float shadowXOffset = dpToPx(1.0f);

	/**
	 * Shadow Y-axis offset expressed in actual pixels 
	 */
	private float shadowYOffset = dpToPx(1.5f);

	/**
	 * Shadow color 
	 */
	private int shadowColor = Color.parseColor("#757575");

	/**
	 * Stroke width 
	 */
	private float strokeWidth = 0.0f;

	/**
	 * Stroke color 
	 */
	private int strokeColor = Color.BLACK;

	/**
	 * <b>Action Button</b> image drawable centered inside the view  
	 */
	private Drawable image;

	/**
	 * Size of the <b>Action Button</b> image inside the view
	 */
	private float imageSize = dpToPx(24.0f);

	/**
	 * Animation, which is used while showing <b>Action Button</b>
	 */
	private Animation showAnimation;

	/**
	 * Animation, which is used while hiding or dismissing <b>Action Button</b> 
	 */
	private Animation hideAnimation;

	/**
	 * <b>Action Button</b> touch point
	 * <p>
	 * {@link TouchPoint} contains information about X- and
	 * Y-axis touch points within the <b>Action Button</b>
	 */
	private TouchPoint touchPoint = new TouchPoint(0.0f, 0.0f);

	/**
	 * {@link android.graphics.Paint}, which is used for drawing the elements of
	 * <b>Action Button</b>
	 */
	protected final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * A drawer, which is used for drawing the <b>Action Button</b> ripple effect
	 */
	protected final RippleDrawer rippleDrawer = new RippleDrawer(this);

	/**
	 * A view mover, which is used to move the <b>Action Button</b>
	 */
	protected final ViewMover mover = ViewMoverFactory.createInstance(this);

	/**
	 * Creates an instance of the <b>Action Button</b>
	 * <p> 
	 * Used when instantiating <b>Action Button</b> programmatically
	 *  
	 * @param context context the view is running in
	 */
	public ActionButton(Context context) {
		super(context);
		initActionButton();
	}

	/**
	 * Creates an instance of the <b>Action Button</b>
	 * <p> 
	 * Used when inflating the declared <b>Action Button</b> 
	 * within XML resource
	 *
	 * @param context context the view is running in
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	public ActionButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initActionButtonAttrs(context, attrs, 0, 0);
	}

	/**
	 * Creates an instance of the <b>Action Button</b>
	 * <p> 
	 * Used when inflating the declared <b>Action Button</b> 
	 * within XML resource
	 *
	 * @param context context the view is running in
	 * @param attrs attributes of the XML tag that is inflating the view
	 * @param defStyleAttr attribute in the current theme that contains a
	 *        reference to a style resource that supplies default values for
	 *        the view. Can be 0 to not look for defaults
	 */
	public ActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initActionButtonAttrs(context, attrs, defStyleAttr, 0);
	}

	/**
	 * Creates an instance of the <b>Action Button</b>
	 * <p>
	 * Used when inflating the declared <b>Action Button</b> 
	 * within XML resource
	 * <p>
	 * Might be called if target API is LOLLIPOP (21) and higher
	 *  
	 * @param context context the view is running in
	 * @param attrs attributes of the XML tag that is inflating the view
	 * @param defStyleAttr attribute in the current theme that contains a
	 *        reference to a style resource that supplies default values for
	 *        the view. Can be 0 to not look for defaults
	 * @param defStyleRes resource identifier of a style resource that
	 *        supplies default values for the view, used only if
	 *        defStyleAttr is 0 or can not be found in the theme. Can be 0
	 *        to not look for defaults
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public ActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initActionButtonAttrs(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * Initializes the <b>Action Button</b>, which is created programmatically 
	 */
	private void initActionButton() {
		initLayerType();
		Log.v(LOG_TAG, "Action Button initialized");
	}

	/**
	 * Initializes the <b>Action Button</b>, which is declared within XML resource
	 * <p>
	 * Makes calls to different initialization methods for parameters initialization.
	 * For those parameters, which are not declared in the XML resource, 
	 * the default value will be used 
	 *
	 * @param context context the view is running in
	 * @param attrs attributes of the XML tag that is inflating the view
	 * @param defStyleAttr attribute in the current theme that contains a
	 *        reference to a style resource that supplies default values for
	 *        the view. Can be 0 to not look for defaults
	 * @param defStyleRes resource identifier of a style resource that
	 *        supplies default values for the view, used only if
	 *        defStyleAttr is 0 or can not be found in the theme. Can be 0
	 *        to not look for defaults
	 */
	private void initActionButtonAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		initActionButton();
		final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ActionButton,
				defStyleAttr, defStyleRes);
		try {
			initType(attributes);
			initSize(attributes);
			initButtonColor(attributes);
			initButtonColorPressed(attributes);
			initRippleEffectEnabled(attributes);
			initButtonColorRipple(attributes);
			initShadowRadius(attributes);
			initShadowXOffset(attributes);
			initShadowYOffset(attributes);
			initShadowColor(attributes);
			initStrokeWidth(attributes);
			initStrokeColor(attributes);
			initImage(attributes);
			initImageSize(attributes);
			initShowAnimation(attributes);
			initHideAnimation(attributes);
		} catch (Exception e) {
			Log.e(LOG_TAG, "Unable to read attr", e);
		} finally {
			attributes.recycle();
		}
		Log.v(LOG_TAG, "Action Button attributes initialized");
	}

	/**
	 * Initializes the layer type needed for shadows drawing
	 * <p>
	 * Might be called if target API is HONEYCOMB (11) and higher
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initLayerType() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(LAYER_TYPE_SOFTWARE, paint);
			Log.v(LOG_TAG, "Layer type initialized");
		}
	}

	/**
	 * Initializes the {@link Type} of <b>Action Button</b>
	 * <p>
	 * Must be called before {@link #initSize(TypedArray)} for the proper
	 * <b>Action Button</b> {@link #size} initialization
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initType(TypedArray attrs) {
		int index = R.styleable.ActionButton_type;
		if (attrs.hasValue(index)) {
			int id = attrs.getInteger(index, type.getId());
			type = Type.forId(id);
			Log.v(LOG_TAG, "Initialized type: " + getType());
		}
	}

	/**
	 * Initializes the {@link #size} of <b>Action Button</b>
	 * <p>
	 * Must be called after {@link #initType(TypedArray)} for the proper
	 * <b>Action Button</b> {@link #size} initialization
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initSize(TypedArray attrs) {
		int index = R.styleable.ActionButton_size;
		if (attrs.hasValue(index)) {
			this.size = attrs.getDimension(index, size);
		} else {
			this.size = dpToPx(type.getSize());
		}
		Log.v(LOG_TAG, "Initialized size: " + getSize());
	}

	/**
	 * Initializes the <b>Action Button</b> color for the {@link State#NORMAL}
	 * {@link #state}
	 *  
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initButtonColor(TypedArray attrs) {
		int index = R.styleable.ActionButton_button_color;
		if (attrs.hasValue(index)) {
			buttonColor = attrs.getColor(index, buttonColor);
			Log.v(LOG_TAG, "Initialized button color: " + getButtonColor());
		}
	}

	/**
	 * Initializes the <b>Action Button</b> color for the {@link State#PRESSED}
	 * {@link #state}
	 * <p>
	 * Initialized the <b>Action Button</b> ripple effect color
	 * <p>
	 * Must be called before {@link #initButtonColorRipple(TypedArray)} for proper
	 * {@link #buttonColorRipple} initialization
	 * 
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initButtonColorPressed(TypedArray attrs) {
		int index = R.styleable.ActionButton_button_colorPressed;
		if (attrs.hasValue(index)) {
			buttonColorPressed = attrs.getColor(index, buttonColorPressed);
			buttonColorRipple = darkenButtonColorPressed();
			Log.v(LOG_TAG, "Initialized button color pressed: " + getButtonColorPressed());
		}
	}

	/**
	 * Initializes the <b>Action Button</b> ripple effect state
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initRippleEffectEnabled(TypedArray attrs) {
		int index = R.styleable.ActionButton_rippleEffect_enabled;
		if (attrs.hasValue(index)) {
			rippleEffectEnabled = attrs.getBoolean(index, rippleEffectEnabled);
			Log.v(LOG_TAG, "Initialized ripple effect enabled: " + hasRipple());
		}
	}

	/**
	 * Initializes the <b>Action Button</b> ripple effect color
	 * <p>
	 * Must be called after {@link #initButtonColorPressed(TypedArray)} for proper
	 * {@link #buttonColorRipple} initialization
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initButtonColorRipple(TypedArray attrs) {
		int index = R.styleable.ActionButton_button_colorRipple;
		if (attrs.hasValue(index)) {
			buttonColorRipple = attrs.getColor(index, buttonColorRipple);
			Log.v(LOG_TAG, "Initialized button color ripple: " + getButtonColorRipple());
		}
	}

	/**
	 * Initializes the shadow radius
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initShadowRadius(TypedArray attrs) {
		int index = R.styleable.ActionButton_shadow_radius;
		if (attrs.hasValue(index)) {
			shadowRadius = attrs.getDimension(index, shadowRadius);
			Log.v(LOG_TAG, "Initialized shadow radius: " + getShadowRadius());
		}
	}

	/**
	 * Initializes the shadow X-axis offset
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initShadowXOffset(TypedArray attrs) {
		int index = R.styleable.ActionButton_shadow_xOffset;
		if (attrs.hasValue(index)) {
			shadowXOffset = attrs.getDimension(index, shadowXOffset);
			Log.v(LOG_TAG, "Initialized shadow X-axis offset: " + getShadowXOffset());
		}
	}

	/**
	 * Initializes the shadow Y-axis offset
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initShadowYOffset(TypedArray attrs) {
		int index = R.styleable.ActionButton_shadow_yOffset;
		if (attrs.hasValue(index)) {
			shadowYOffset = attrs.getDimension(index, shadowYOffset);
			Log.v(LOG_TAG, "Initialized shadow Y-axis offset: " + getShadowYOffset());
		}
	}

	/**
	 * Initializes the shadow color
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initShadowColor(TypedArray attrs) {
		int index = R.styleable.ActionButton_shadow_color;
		if (attrs.hasValue(index)) {
			shadowColor = attrs.getColor(index, shadowColor);
			Log.v(LOG_TAG, "Initialized shadow color: " + getShadowColor());
		}
	}

	/**
	 * Initializes the stroke width
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initStrokeWidth(TypedArray attrs) {
		int index = R.styleable.ActionButton_stroke_width;
		if (attrs.hasValue(index)) {
			strokeWidth = attrs.getDimension(index, strokeWidth);
			Log.v(LOG_TAG, "Initialized stroke width: " + getStrokeWidth());
		}
	}

	/**
	 * Initializes the stroke color
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initStrokeColor(TypedArray attrs) {
		int index = R.styleable.ActionButton_stroke_color;
		if (attrs.hasValue(index)) {
			strokeColor = attrs.getColor(index, strokeColor);
			Log.v(LOG_TAG, "Initialized stroke color: " + getStrokeColor());
		}
	}

	/**
	 * Initializes the animation, which is used while showing 
	 * <b>Action Button</b>
	 *  
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initShowAnimation(TypedArray attrs) {
		int index = R.styleable.ActionButton_show_animation;
		if (attrs.hasValue(index)) {
			int animResId = attrs.getResourceId(index, Animations.NONE.animResId);
			showAnimation = Animations.load(getContext(), animResId);
			Log.v(LOG_TAG, "Initialized animation on show");
		}
	}

	/**
	 * Initializes the animation, which is used while hiding or dismissing
	 * <b>Action Button</b>
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initHideAnimation(TypedArray attrs) {
		int index = R.styleable.ActionButton_hide_animation;
		if (attrs.hasValue(index)) {
			int animResId = attrs.getResourceId(index, Animations.NONE.animResId);
			hideAnimation = Animations.load(getContext(), animResId);
			Log.v(LOG_TAG, "Initialized animation on hide");
		}
	}

	/**
	 * Initializes the image inside <b>Action Button</b>
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initImage(TypedArray attrs) {
		int index = R.styleable.ActionButton_image;
		if (attrs.hasValue(index)) {
			image = attrs.getDrawable(index);
			Log.v(LOG_TAG, "Initialized image");
		}
	}

	/**
	 * Initializes the image size inside <b>Action Button</b>
	 * <p>
	 * Changing the default size of the image breaks the rules of 
	 * <a href="http://www.google.com/design/spec/components/buttons.html">Material Design</a>
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initImageSize(TypedArray attrs) {
		int index = R.styleable.ActionButton_image_size;
		if (attrs.hasValue(index)) {
			imageSize = attrs.getDimension(index, imageSize);
			Log.v(LOG_TAG, "Initialized image size: " + getImageSize());
		}
	}

	/**
	 * Plays the {@link #showAnimation} if set
	 */
	public void playShowAnimation() {
		startAnimation(getShowAnimation());
	}

	/**
	 * Plays the {@link #hideAnimation} if set
	 */
	public void playHideAnimation() {
		startAnimation(getHideAnimation());
	}

	/**
	 * Makes the <b>Action Button</b> to appear if it is hidden and
	 * sets its visibility to {@link #VISIBLE}
	 * <p>
	 * {@link #showAnimation} is played if set
	 */
	public void show() {
		if (isHidden()) {
			playShowAnimation();
			setVisibility(VISIBLE);
			Log.v(LOG_TAG, "Action Button shown");
		}
	}

	/**
	 * Makes the <b>Action Button</b> to disappear if it is showing and
	 * sets its visibility to {@link #INVISIBLE}
	 * <p>
	 * {@link #hideAnimation} is played if set
	 */
	public void hide() {
		if (!isHidden() && !isDismissed()) {
			playHideAnimation();
			setVisibility(INVISIBLE);
			Log.v(LOG_TAG, "Action Button hidden");
		}
	}

	/**
	 * Completely dismisses the <b>Action Button</b>,
	 * sets its visibility to {@link #GONE} and removes it from the parent view
	 * <p>
	 * After calling this method any calls to {@link #show()} won't result in showing
	 * the <b>Action Button</b> so far as it is removed from the parent View
	 * <p> 
	 * {@link #hideAnimation} is played if set
	 */
	public void dismiss() {
		if (!isDismissed()) {
			if (!isHidden()) {
				playHideAnimation();
			}
			setVisibility(GONE);
			ViewGroup parent = (ViewGroup) getParent();
			parent.removeView(this);
			Log.v(LOG_TAG, "Action Button dismissed");
		}
	}

	/**
	 * Checks whether <b>Action Button</b> is hidden
	 *  
	 * @return true if <b>Action Button</b> is hidden, otherwise false
	 */
	public boolean isHidden() {
		return getVisibility() == INVISIBLE;
	}

	/**
	 * Checks whether <b>Action Button</b> is dismissed
	 *
	 * @return true if <b>Action Button</b> is dismissed, otherwise false
	 */
	public boolean isDismissed() {
		return getParent() == null;
	}

	/**
	 * Moves the <b>Action Button</b> to a specified position defined in moving
	 * details object
	 *
	 * @param details moving details, which contain the desired position to move
	 */
	public void move(MovingDetails details) {
		Log.v(LOG_TAG, String.format("View is about to move at: X-axis delta = %s Y-axis delta = %s",
				details.getXAxisDelta(), details.getYAxisDelta()));
		mover.move(details);
	}

	/**
	 * Moves the <b>Action Button</b> right to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels to move
	 *                 the <b>Action Button</b> right
	 */
	public void moveRight(float distance) {
		final MovingDetails details = new MovingDetails(getContext(), distance, 0);
		move(details);
	}

	/**
	 * Moves the <b>Action Button</b> down to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels
	 *                 to move the <b>Action Button</b> down
	 */
	public void moveDown(float distance) {
		final MovingDetails details = new MovingDetails(getContext(), 0, distance);
		move(details);
	}

	/**
	 * Moves the <b>Action Button</b> left to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels
	 *                 to move the <b>Action Button</b> left
	 */
	public void moveLeft(float distance) {
		final MovingDetails details = new MovingDetails(getContext(), -distance, 0);
		move(details);
	}

	/**
	 * Moves the <b>Action Button</b> up to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels
	 *                 to move the <b>Action Button</b> up
	 */
	public void moveUp(float distance) {
		final MovingDetails details = new MovingDetails(getContext(), 0, -distance);
		move(details);
	}

	/**
	 * Returns the type of the <b>Action Button</b>
	 *  
	 * @return type of the <b>Action Button</b>
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the <b>Action Button</b> {@link com.software.shell.fab.ActionButton.Type}
	 * and calls {@link #setSize(float)} to set the size depending on {@link #type} set
	 *
	 * @param type type of the <b>Action Button</b>
	 */
	public void setType(Type type) {
		this.type = type;
		Log.v(LOG_TAG, "Type changed to: " + getType());
		setSize(getType().getSize());
	}

	/**
	 * Returns the size of the <b>Action Button</b> in actual pixels (px).
	 * Size of the <b>Action Button</b> is the diameter of the main circle
	 *
	 * @deprecated since version <b>1.1.0</b>. Please use {@link #getSize()} instead
	 *
	 * @return size of the <b>Action Button</b> in actual pixels (px)
	 */
	@Deprecated
	public int getButtonSize() {
		return (int) getSize();
	}

	/**
	 * Returns the size of the <b>Action Button</b> in actual pixels (px).
	 * Size of the <b>Action Button</b> is the diameter of the main circle
	 *
	 * @return size of the <b>Action Button</b> in actual pixels (px)
	 */
	public float getSize() {
		return size;
	}

	/**
	 * Sets the <b>Action Button</b> size and invalidates the layout of the view
	 * <p>
	 * Changing the default size of the button breaks the rules of 
	 * <a href="http://www.google.com/design/spec/components/buttons.html">Material Design</a>* 
	 * <p>
	 * Setting the button size explicitly means, that button types with its default sizes are
	 * completely ignored. Do not use this method, unless you know what you are doing
	 * <p> 
	 * Must be specified in density-independent (dp) pixels, which are
	 * then converted into actual pixels (px)
	 *  
	 * @param size size of the button specified in density-independent 
	 *                   (dp) pixels
	 */
	public void setSize(float size) {
		this.size = dpToPx(size);
		requestLayout();
		Log.v(LOG_TAG, "Button size set to: " + getSize());
	}

	/**
	 * Returns the current state of the <b>Action Button</b> 
	 *  
	 * @return current state of the <b>Action Button</b>
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the current state of the <b>Action Button</b> and 
	 * invalidates the view
	 *  
	 * @param state new state of the <b>Action Button</b>
	 */
	public void setState(State state) {
		this.state = state;
		invalidate();
		Log.v(LOG_TAG, "State changed to: " + getState());
	}

	/**
	 * Returns the <b>Action Button</b> color when in 
	 * {@link State#NORMAL} state
	 * 
	 * @return <b>Action Button</b> color when in 
	 * {@link State#NORMAL} state 
	 */
	public int getButtonColor() {
		return buttonColor;
	}

	/**
	 * Sets the <b>Action Button</b> color when in 
	 * {@link State#NORMAL} state and invalidates the view
	 *  
	 * @param buttonColor <b>Action Button</b> color 
	 *                    when in {@link State#NORMAL} state
	 */
	public void setButtonColor(int buttonColor) {
		this.buttonColor = buttonColor;
		invalidate();
		Log.v(LOG_TAG, "Button color changed to: " + getButtonColor());
	}

	/**
	 * Returns the <b>Action Button</b> color when in
	 * {@link State#PRESSED} state
	 *  
	 * @return <b>Action Button</b> color when in
	 * {@link State#PRESSED} state 
	 */
	public int getButtonColorPressed() {
		return buttonColorPressed;
	}

	/**
	 * Sets the <b>Action Button</b> color when in
	 * {@link State#PRESSED} state and invalidates the view
	 *
	 * @param buttonColorPressed <b>Action Button</b> color
	 *                           when in {@link State#PRESSED} state
	 */
	public void setButtonColorPressed(int buttonColorPressed) {
		this.buttonColorPressed = buttonColorPressed;
		setButtonColorRipple(darkenButtonColorPressed());
		Log.v(LOG_TAG, "Button color pressed changed to: " + getButtonColorPressed());
	}

	/**
	 * Darkens the {@link #buttonColorPressed} using the darkening factor
	 *
	 * @return darker color variant of {@link #buttonColorPressed}
	 */
	private int darkenButtonColorPressed() {
		final float darkenFactor = 0.8f;
		return ColorModifier.modifyExposure(getButtonColorPressed(), darkenFactor);
	}

	/**
	 * Checks whether <b>Action Button</b> ripple effect enabled
	 *
	 * @return true, if ripple effect enabled, otherwise false
	 */
	public boolean hasRipple() {
		return rippleEffectEnabled;
	}

	/**
	 * Toggles the ripple effect state
	 *
	 * @param enabled true if ripple effect needs to be enabled, otherwise false
	 */
	public void setRippleEffectEnabled(boolean enabled) {
		this.rippleEffectEnabled = enabled;
		Log.v(LOG_TAG, "Ripple effect " + (this.rippleEffectEnabled ? "ENABLED" : "DISABLED"));
	}

	/**
	 * Returns the <b>Action Button</b> ripple effect color
	 *
	 * @return <b>Action Button</b> ripple effect color
	 */
	public int getButtonColorRipple() {
		return buttonColorRipple;
	}

	/**
	 * Sets the <b>Action Button</b> ripple effect color
	 *
	 * @param buttonColorRipple <b>Action Button</b> ripple effect color
	 */
	public void setButtonColorRipple(int buttonColorRipple) {
		this.buttonColorRipple = buttonColorRipple;
		Log.v(LOG_TAG, "Button ripple effect color changed to: " + getButtonColorRipple());
	}

	/**
	 * Checks whether <b>Action Button</b> has shadow by determining shadow radius
	 * <p>
	 * Shadow is disabled if elevation is set API level is {@code 21 Lollipop} and higher     
	 *  
	 * @return true if <b>Action Button</b> has radius, otherwise false
	 */
	public boolean hasShadow() {
		return !hasElevation() && getShadowRadius() > 0.0f;
	}

	/**
	 * Returns the <b>Action Button</b> shadow radius in actual 
	 * pixels (px)
	 *  
	 * @return <b>Action Button</b> shadow radius in actual pixels (px)
	 */
	public float getShadowRadius() {
		return shadowRadius;
	}

	/**
	 * Sets the <b>Action Button</b> shadow radius and 
	 * invalidates the layout of the view
	 * <p>
	 * Must be specified in density-independent (dp) pixels, which are
	 * then converted into actual pixels (px). If shadow radius is set to 0, 
	 * shadow is removed
	 *
	 * @param shadowRadius shadow radius specified in density-independent 
	 *                     (dp) pixels
	 */
	public void setShadowRadius(float shadowRadius) {
		this.shadowRadius = dpToPx(shadowRadius);
		requestLayout();
		Log.v(LOG_TAG, "Shadow radius changed to:" + getShadowRadius());
	}

	/**
	 * Removes the <b>Action Button</b> shadow by setting its radius to 0
	 */
	public void removeShadow() {
		if (hasShadow()) {
			setShadowRadius(0.0f);
		}
	}

	/**
	 * Returns the <b>Action Button</b> shadow X-axis offset 
	 * in actual pixels (px)
	 * <p>
	 * If X-axis offset is greater than 0 shadow is shifted right. 
	 * If X-axis offset is lesser than 0 shadow is shifted left.
	 * 0 X-axis offset means that shadow is not X-axis shifted at all
	 *  
	 * @return <b>Action Button</b> shadow X-axis offset 
	 * in actual pixels (px)
	 */
	public float getShadowXOffset() {
		return shadowXOffset;
	}

	/**
	 * Sets the <b>Action Button</b> shadow X-axis offset and 
	 * invalidates the layout of the view
	 * <p>
	 * If X-axis offset is greater than 0 shadow is shifted right. 
	 * If X-axis offset is lesser than 0 shadow is shifted left.
	 * 0 X-axis offset means that shadow is not shifted at all
	 * <p>
	 * Must be specified in density-independent (dp) pixels, which are
	 * then converted into actual pixels (px)
	 *      
	 * @param shadowXOffset shadow X-axis offset specified in density-independent
	 *                      (dp) pixels                         
	 */
	public void setShadowXOffset(float shadowXOffset) {
		this.shadowXOffset = dpToPx(shadowXOffset);
		requestLayout();
		Log.v(LOG_TAG, "Shadow X offset changed to: " + getShadowXOffset());
	}

	/**
	 * Returns the <b>Action Button</b> shadow Y-axis offset 
	 * in actual pixels (px)
	 * <p>
	 * If Y-axis offset is greater than 0 shadow is shifted down.
	 * If Y-axis offset is lesser than 0 shadow is shifted up.
	 * 0 Y-axis offset means that shadow is not Y-axis shifted at all
	 *  
	 * @return <b>Action Button</b> shadow Y-axis offset 
	 * in actual pixels (px)
	 */
	public float getShadowYOffset() {
		return shadowYOffset;
	}

	/**
	 * Sets the <b>Action Button</b> shadow Y-axis offset and
	 * invalidates the layout of the view
	 * <p>
	 * If Y-axis offset is greater than 0 shadow is shifted down.
	 * If Y-axis offset is lesser than 0 shadow is shifted up.
	 * 0 Y-axis offset means that shadow is not Y-axis shifted at all
	 * <p>
	 * Must be specified in density-independent (dp) pixels, which are
	 * then converted into actual pixels (px)
	 *  
	 * @param shadowYOffset shadow Y-axis offset specified in density-independent
	 *                      (dp) pixels                         
	 */
	public void setShadowYOffset(float shadowYOffset) {
		this.shadowYOffset = dpToPx(shadowYOffset);
		requestLayout();
		Log.v(LOG_TAG, "Shadow Y offset changed to:" + getShadowYOffset());
	}

	/**
	 * Returns <b>Action Button</b> shadow color
	 *  
	 * @return <b>Action Button</b> shadow color
	 */
	public int getShadowColor() {
		return shadowColor;
	}

	/**
	 * Sets the <b>Action Button</b> shadow color and
	 * invalidates the view
	 *  
	 * @param shadowColor <b>Action Button</b> color
	 */
	public void setShadowColor(int shadowColor) {
		this.shadowColor = shadowColor;
		invalidate();
		Log.v(LOG_TAG, "Shadow color changed to: " + getShadowColor());
	}

	/**
	 * Returns the <b>Action Button</b> stroke width in actual 
	 * pixels (px)
	 *  
	 * @return <b>Action Button</b> stroke width in actual 
	 * pixels (px)
	 */
	public float getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * Checks whether <b>Action Button</b> has stroke by checking 
	 * stroke width
	 *  
	 * @return true if <b>Action Button</b> has stroke, otherwise false
	 */
	public boolean hasStroke() {
		return getStrokeWidth() > 0.0f;		
	}

	/**
	 * Sets the <b>Action Button</b> stroke width and
	 * invalidates the layout of the view
	 * <p>
	 * Stroke width value must be greater than 0. If stroke width is 
	 * set to 0 stroke is removed     
	 * <p>
	 * Must be specified in density-independent (dp) pixels, which are
	 * then converted into actual pixels (px)
	 *  
	 * @param strokeWidth stroke width specified in density-independent
	 *                    (dp) pixels                       
	 */
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = dpToPx(strokeWidth);
		requestLayout();
		Log.v(LOG_TAG, "Stroke width changed to: " + getStrokeWidth());
	}

	/**
	 * Removes the <b>Action Button</b> stroke by setting its width to 0 
	 */
	public void removeStroke() {
		if (hasStroke()) {
			setStrokeWidth(0.0f);
		}
	}

	/**
	 * Returns the <b>Action Button</b> stroke color
	 *
	 * @return <b>Action Button</b> stroke color
	 */
	public int getStrokeColor() {
		return strokeColor;
	}

	/**
	 * Sets the <b>Action Button</b> stroke color and 
	 * invalidates the view
	 *  
	 * @param strokeColor <b>Action Button</b> stroke color
	 */
	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
		invalidate();
		Log.v(LOG_TAG, "Stroke color changed to: " + getStrokeColor());
	}

	/**
	 * Returns the <b>Action Button</b> image drawable centered 
	 * inside the view
	 *  
	 * @return <b>Action Button</b> image drawable centered 
	 * inside the view
	 */
	public Drawable getImage() {
		return image;
	}

	/**
	 * Checks whether <b>Action Button</b> has an image centered 
	 * inside the view
	 *  
	 * @return true if <b>Action Button</b> has an image centered 
	 * inside the view, otherwise false 
	 */
	public boolean hasImage() {
		return getImage() != null;
	}

	/**
	 * Places the image drawable centered inside the view and
	 * invalidates the view 
	 * <p>
	 * Size of the image while drawing is fit to {@link #imageSize}     
	 *     
	 * @param image image drawable, which will be placed centered 
	 *              inside the view                 
	 */
	public void setImageDrawable(Drawable image) {
		this.image = image;
		invalidate();
		Log.v(LOG_TAG, "Image drawable set");
	}

	/**
	 * Resolves the drawable resource id and places the resolved image drawable
	 * centered inside the view
	 * 
	 * @param resId drawable resource id, which is to be resolved to 
	 *              image drawable and used as parameter when calling
	 *              {@link #setImageDrawable(android.graphics.drawable.Drawable)}              
	 */
	public void setImageResource(int resId) {
		setImageDrawable(getResources().getDrawable(resId));
	}

	/**
	 * Creates the {@link android.graphics.drawable.BitmapDrawable} from the given
	 * {@link android.graphics.Bitmap} and places it centered inside the view 
	 *  
	 * @param bitmap bitmap, from which {@link android.graphics.drawable.BitmapDrawable}
	 *               is created and used as parameter when calling
	 *               {@link #setImageDrawable(android.graphics.drawable.Drawable)}               
	 */
	public void setImageBitmap(Bitmap bitmap) {
		setImageDrawable(new BitmapDrawable(getResources(), bitmap));
	}

	/**
	 * Removes the <b>Action Button</b> image by setting its value to null 
	 */
	public void removeImage() {
		if (hasImage()) {
			setImageDrawable(null);
		}
	}

	/**
	 * Returns the <b>Action Button</b> image size in actual pixels (px).
	 * If <b>Action Button</b> image is not set returns 0 
	 *   
	 * @return <b>Action Button</b> image size in actual pixels (px), 
	 * 0 if image is not set
	 */
	public float getImageSize() {
		return getImage() != null ? imageSize : 0.0f;
	}

	/**
	 * Sets the size of the <b>Action Button</b> image
	 * <p>
	 * Changing the default size of the image breaks the rules of 
	 * <a href="http://www.google.com/design/spec/components/buttons.html">Material Design</a>
	 * <p>
	 * Must be specified in density-independent (dp) pixels, which are
	 * then converted into actual pixels (px)     
	 *
	 * @param size size of the <b>Action Button</b> image
	 *             specified in density-independent (dp) pixels                
	 */
	public void setImageSize(float size) {
		this.imageSize = dpToPx(size);
		Log.v(LOG_TAG, "Image size changed to: " + getImageSize());
	}

	/**
	 * Returns an animation, which is used while showing <b>Action Button</b>
	 *
	 * @return animation, which is used while showing <b>Action Button</b>
	 */
	public Animation getShowAnimation() {
		return showAnimation;
	}

	/**
	 * Sets the animation, which is used while showing <b>Action Button</b>
	 *
	 * @param animation animation, which is to be used while showing 
	 *                  <b>Action Button</b>
	 */
	public void setShowAnimation(Animation animation) {
		this.showAnimation = animation;
		Log.v(LOG_TAG, "Show animation set");
	}

	/**
	 * Sets one of the {@link Animations} as animation, which is used while showing
	 * <b>Action Button</b>
	 *
	 * @param animation one of the {@link Animations}, which is to be used while
	 *                  showing <b>Action Button</b>                     
	 */
	public void setShowAnimation(Animations animation) {
		setShowAnimation(Animations.load(getContext(), animation.animResId));
	}

	/**
	 * Removes the animation, which is used while showing <b>Action Button</b> 
	 */
	public void removeShowAnimation() {
		setShowAnimation(Animations.NONE);
		Log.v(LOG_TAG, "Show animation removed");
	}

	/**
	 * Returns an animation, which is used while hiding <b>Action Button</b>
	 *
	 * @return animation, which is used while hiding <b>Action Button</b>
	 */
	public Animation getHideAnimation() {
		return hideAnimation;
	}

	/**
	 * Sets the animation, which is used while hiding <b>Action Button</b>
	 *
	 * @param animation animation, which is to be used while hiding 
	 *                  <b>Action Button</b>
	 */
	public void setHideAnimation(Animation animation) {
		this.hideAnimation = animation;
		Log.v(LOG_TAG, "Hide animation set");
	}

	/**
	 * Sets one of the {@link Animations} as animation, which is used while hiding
	 * <b>Action Button</b>
	 *
	 * @param animation one of the {@link Animations}, which is to be used while
	 *                  hiding <b>Action Button</b>                     
	 */
	public void setHideAnimation(Animations animation) {
		setHideAnimation(Animations.load(getContext(), animation.animResId));
	}

	/**
	 * Removes the animation, which is used while hiding <b>Action Button</b>
	 */
	public void removeHideAnimation() {
		setHideAnimation(Animations.NONE);
		Log.v(LOG_TAG, "Hide animation removed");
	}

	/**
	 * Returns the <b>Action Button</b> touch point
	 * <p>
	 * {@link TouchPoint} contains information about X- and
	 * Y-axis touch points within the <b>Action Button</b>
	 *
	 * @return <b>Action Button</b> touch point
	 */
	public TouchPoint getTouchPoint() {
		return touchPoint;
	}

	/**
	 * Sets the <b>Action Button</b> touch point
	 *
	 * @param point <b>Action Button</b> touch point
	 */
	protected void setTouchPoint(TouchPoint point) {
		this.touchPoint = point;
	}
	
	/**
	 * Adds additional actions on motion events:
	 * 1. Changes the <b>Action Button</b> {@link #state} to {@link State#PRESSED}
	 *    on {@link android.view.MotionEvent#ACTION_DOWN}
	 * 2. Changes the <b>Action Button</b> {@link #state} to {@link State#NORMAL}
	 *    on {@link android.view.MotionEvent#ACTION_UP}
	 * 3. Changes the <b>Action Button</b> {@link #state} to {@link State#NORMAL}
	 *    on {@link android.view.MotionEvent#ACTION_MOVE} in case when touch point
	 *    leaves the main circle
	 *
	 * @param event motion event
	 * @return true if event was handled, otherwise false
	 */
	@SuppressWarnings("all")
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		TouchPoint point = new TouchPoint(event.getX(), event.getY());
		boolean touchPointInsideCircle = point.isInsideCircle(calculateCenterX(), calculateCenterY(),
				calculateCircleRadius());
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (touchPointInsideCircle) {
					setState(State.PRESSED);
					setTouchPoint(point);
					Log.v(LOG_TAG, "Motion event action down detected");
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (touchPointInsideCircle) {
					setState(State.NORMAL);
					getTouchPoint().reset();
					Log.v(LOG_TAG, "Motion event action up detected");
					return true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (!touchPointInsideCircle
						&& getState() == State.PRESSED) {
					setState(State.NORMAL);
					getTouchPoint().reset();
					Log.v(LOG_TAG, "Touch point is outside the circle");
					return true;
				}
				break;
			default:
				Log.v(LOG_TAG, "Unrecognized motion event detected");
				break;
		}
		return false;
	}

	/**
	 * Adds additional checking whether animation is null before starting to play it
	 *  
	 * @param animation animation to play
	 */
	@SuppressWarnings("all")
	@Override
	public void startAnimation(Animation animation) {
		if (animation != null &&
				(getAnimation() == null || getAnimation().hasEnded())) {
			super.startAnimation(animation);
		}
	}
	
	/**
	 * Resets the paint to its default values and sets initial flags to it
	 * <p>
	 * Use this method before drawing the new element of the view     
	 */
	protected final void resetPaint() {
		paint.reset();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		Log.v(LOG_TAG, "Paint reset");
	}

	/**
	 * Draws the elements of the <b>Action Button</b>
	 *  
	 * @param canvas canvas, on which the drawing is to be performed
	 */
	@SuppressWarnings("all")
	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);
		Log.v(LOG_TAG, "Action Button onDraw called");
		drawCircle(canvas);
		if (hasRipple()) {
			drawRipple(canvas);
		}
		if (hasElevation()) {
			drawElevation();
		}
		if (hasStroke()) {
			drawStroke(canvas);
		}
		if (hasImage()) {
			drawImage(canvas);
		}
	}

	/**
	 * Draws the main circle of the <b>Action Button</b> and calls
	 * {@link #drawShadow()} to draw the shadow if present
	 *  
	 * @param canvas canvas, on which circle is to be drawn
	 */
	protected void drawCircle(Canvas canvas) {
		resetPaint();
		if (hasShadow()) {
			drawShadow();
		}
		paint.setStyle(Paint.Style.FILL);
		boolean rippleInProgress = hasRipple() && rippleDrawer.isDrawingInProgress();
		paint.setColor(getState() == State.PRESSED || rippleInProgress ? getButtonColorPressed() : getButtonColor());
		canvas.drawCircle(calculateCenterX(), calculateCenterY(), calculateCircleRadius(), paint);
		Log.v(LOG_TAG, "Circle drawn");
	}

	/**
	 * Calculates the X-axis center coordinate of the entire view
	 *
	 * @return X-axis center coordinate of the entire view
	 */
	protected float calculateCenterX() {
		float centerX = getMeasuredWidth() / 2;
		Log.v(LOG_TAG, "Calculated center X = " + centerX);
		return centerX;
	}

	/**
	 * Calculates the Y-axis center coordinate of the entire view
	 *
	 * @return Y-axis center coordinate of the entire view
	 */
	protected float calculateCenterY() {
		float centerY = getMeasuredHeight() / 2;
		Log.v(LOG_TAG, "Calculated center Y = " + centerY);
		return centerY;
	}

	/**
	 * Calculates the radius of the main circle
	 *
	 * @return radius of the main circle
	 */
	protected final float calculateCircleRadius() {
		float circleRadius = getSize() / 2;
		Log.v(LOG_TAG, "Calculated circle circleRadius = " + circleRadius);
		return circleRadius;
	}

	/**
	 * Draws the shadow if view elevation is not enabled
	 */
	protected void drawShadow() {
		paint.setShadowLayer(getShadowRadius(), getShadowXOffset(), getShadowYOffset(), getShadowColor());
		Log.v(LOG_TAG, "Shadow drawn");
	}

	/**
	 * Draws the ripple effect
	 *
	 * @param canvas canvas, on which ripple effect is to be drawn
	 */
	protected void drawRipple(Canvas canvas) {
		rippleDrawer.draw(canvas);
		Log.v(LOG_TAG, "Ripple effect drawn");
	}
	
	/**
	 * Draws the elevation around the main circle
	 * <p>
	 * Stroke corrective is used due to ambiguity in drawing stroke in
	 * combination with elevation enabled (for API 21 and higher only.
	 * In such case there is no possibility to determine the accurate
	 * <b>Action Button</b> size, so width and height must be corrected
	 * <p>
	 * This logic may be changed in future if the better solution is found
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	protected void drawElevation() {
		int strokeWeightCorrective = (int) (getStrokeWidth() / 1.5f);
		final int width = getWidth() - strokeWeightCorrective;
		final int height = getHeight() - strokeWeightCorrective;
		ViewOutlineProvider provider = new ViewOutlineProvider() {
			@Override
			public void getOutline(View view, Outline outline) {
				outline.setOval(0, 0, width, height);
			}
		};
		setOutlineProvider(provider);
		Log.v(LOG_TAG, "Elevation drawn");
	}

	/**
	 * Checks whether view elevation is enabled
	 *  
	 * @return true if view elevation enabled, otherwise false
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private boolean hasElevation() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getElevation() > 0.0f;
	}

	/**
	 * Draws stroke around the main circle
	 *
	 * @param canvas canvas, on which circle is to be drawn
	 */
	protected void drawStroke(Canvas canvas) {
		resetPaint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(getStrokeWidth());
		paint.setColor(getStrokeColor());
		canvas.drawCircle(calculateCenterX(), calculateCenterY(), calculateCircleRadius(), paint);
		Log.v(LOG_TAG, "Stroke drawn");
	}

	/**
	 * Draws the image centered inside the view
	 *
	 * @param canvas canvas, on which circle is to be drawn
	 */
	protected void drawImage(Canvas canvas) {
		int startPointX = (int) (calculateCenterX() - getImageSize() / 2);
		int startPointY = (int) (calculateCenterY() - getImageSize() / 2);
		int endPointX = (int) (startPointX + getImageSize());
		int endPointY = (int) (startPointY + getImageSize());
		getImage().setBounds(startPointX, startPointY, endPointX, endPointY);
		getImage().draw(canvas);
		Log.v(LOG_TAG, String.format("Image drawn on canvas with coordinates: startPointX = %s, startPointY = %s, " +
				"endPointX = %s, endPointY = %s", startPointX, startPointY, endPointX, endPointY));
	}

	/**
	 * Sets the measured dimension for the entire view
	 *
	 * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
	 *                         The requirements are encoded with
	 *                         {@link android.view.View.MeasureSpec}
	 * @param heightMeasureSpec vertical space requirements as imposed by the parent.
	 *                         The requirements are encoded with
	 *                         {@link android.view.View.MeasureSpec}
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.v(LOG_TAG, "Action Button onMeasure called");
		setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight());
		Log.v(LOG_TAG, String.format("View size measured with: height = %s, width = %s", getHeight(), getWidth()));
	}

	/**
	 * Calculates the measured width in actual pixels for the entire view
	 *  
	 * @return measured width in actual pixels for the entire view
	 */
	private int calculateMeasuredWidth() {
		int measuredWidth = (int) (getSize() + calculateShadowWidth() + calculateStrokeWeight());
		Log.v(LOG_TAG, "Calculated measured width = " + measuredWidth);
		return measuredWidth;
	}

	/**
	 * Calculates the measured height in actual pixels for the entire view
	 *  
	 * @return measured width in actual pixels for the entire view
	 */
	private int calculateMeasuredHeight() {
		int measuredHeight = (int) (getSize() + calculateShadowHeight() + calculateStrokeWeight());
		Log.v(LOG_TAG, "Calculated measured height = " + measuredHeight);
		return measuredHeight;
	}

	/**
	 * Calculates shadow width in actual pixels
	 *  
	 * @return shadow width in actual pixels
	 */
	private int calculateShadowWidth() {
		int shadowWidth = hasShadow() ? (int) ((getShadowRadius() + Math.abs(getShadowXOffset())) * 2) : 0;
		Log.v(LOG_TAG, "Calculated shadow width = " + shadowWidth);
		return shadowWidth;
	}

	/**
	 * Calculates shadow height in actual pixels
	 *  
	 * @return shadow height in actual pixels
	 */
	private int calculateShadowHeight() {
		int shadowHeight = hasShadow() ? (int) ((getShadowRadius() + Math.abs(getShadowYOffset())) * 2) : 0;
		Log.v(LOG_TAG, "Calculated shadow height = " + shadowHeight);
		return shadowHeight;
	}

	/**
	 * Calculates the stroke weight in actual pixels
	 * *
	 * @return stroke weight in actual pixels
	 */
	private int calculateStrokeWeight() {
		int strokeWeight = (int) (getStrokeWidth() * 2.0f);
		Log.v(LOG_TAG, "Calculated stroke weight is: " + strokeWeight);
		return strokeWeight;
	}

	/**
	 * Converts the density-independent value into density-dependent one
	 *
	 * @param dp density-independent value
	 * @return density-dependent value
	 */
	protected float dpToPx(float dp) {
		return DensityConverter.dpToPx(getContext(), dp);
	}

	/**
	 * Determines the <b>Action Button</b> types 
	 */
	public enum Type {

		/**
		 * <b>Action Button</b> default (56dp) type
		 */
		DEFAULT {
			@Override
			int getId() {
				return 0;
			}
		
			@Override
			float getSize() {
				return 56.0f;
			}
		},

		/**
		 * <b>Action Button</b> mini (40dp) type 
		 */
		MINI {
			@Override
			int getId() {
				return 1;
			}

			@Override
			float getSize() {
				return 40.0f;
			}
		},

		/**
         * <b>Action Button</b> big (72dp) type
         */
		BIG {
			@Override
			int getId() {
                return 2;
			}

	        @Override
            float getSize() {
		        return 72.0f;
	        }
		};

		/**
		 * Returns an {@code id} for specific <b>Action Button</b> 
		 * type, which is defined in attributes  
		 *  
		 * @return {@code id} for particular <b>Action Button</b> type,
		 * which is defined in attributes 
		 */
		abstract int getId();

		/**
		 * Returns the size of the specific type of the <b>Action Button</b>
		 * in density-independent pixels, which then must be converted into
		 * real pixels 
		 *
		 * @return size of the particular type of the <b>Action Button</b>
		 */
		abstract float getSize();

		/**
		 * Returns the <b>Action Button</b> type for a specific {@code id}
		 *  
		 * @param id an {@code id}, for which <b>Action Button</b> type required
		 * @return <b>Action Button</b> type
		 */
		static Type forId(int id) {
			for (Type type : values()) {
				if (type.getId() == id) {
					return type;
				}
			}
			return DEFAULT;
		}
		
	}

	/**
	 * Determines the <b>Action Button</b> states 
	 */
	public enum State {

		/**
		 * <b>Action Button</b> normal state  
		 */
		NORMAL,

		/**
		 * <b>Action Button</b> pressed state 
		 */
		PRESSED
		
	}

	/**
	 * Determines the <b>Action Button</b> animations
	 */
	public enum Animations {

		/**
		 * None. Animation absent 
		 */
		NONE                (0),

		/**
		 * Fade in animation 
		 */
		FADE_IN             (R.anim.fab_fade_in),

		/**
		 * Fade out animation 
		 */
		FADE_OUT            (R.anim.fab_fade_out),

		/**
		 * Scale up animation 
		 */
		SCALE_UP            (R.anim.fab_scale_up),

		/**
		 * Scale down animation 
		 */
		SCALE_DOWN          (R.anim.fab_scale_down),

		/**
		 * Roll from down animation 
		 */
		ROLL_FROM_DOWN      (R.anim.fab_roll_from_down),

		/**
		 * Roll to down animation 
		 */
		ROLL_TO_DOWN        (R.anim.fab_roll_to_down),

		/**
		 * Roll from right animation 
		 */
		ROLL_FROM_RIGHT     (R.anim.fab_roll_from_right),

		/**
		 * Roll to right animation 
		 */
		ROLL_TO_RIGHT       (R.anim.fab_roll_to_right),

		/**
		 * Jump from down animation 
		 */
		JUMP_FROM_DOWN      (R.anim.fab_jump_from_down),

		/**
		 * Jump to down animation 
		 */
		JUMP_TO_DOWN        (R.anim.fab_jump_to_down),

		/**
		 * Jump from right animation 
		 */
		JUMP_FROM_RIGHT     (R.anim.fab_jump_from_right),

		/**
		 * Jump to right animation 
		 */
		JUMP_TO_RIGHT       (R.anim.fab_jump_to_right);

		/**
		 * Correspondent animation resource id 
		 */
		final int animResId;
		
		Animations(int animResId) {
			this.animResId = animResId;
		}

		/**
		 * Loads an animation from animation resource id
		 *
		 * @param context context the view is running in
		 * @param animResId resource id of the animation, which is to be loaded
		 * @return loaded animation
		 */
		protected static Animation load(Context context, int animResId) {
			return animResId == NONE.animResId ? null : AnimationUtils.loadAnimation(context, animResId);
		}

	}

}
