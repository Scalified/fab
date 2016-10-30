/*
 * Copyright 2016 Scalified <http://www.scalified.com>
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
 */

package com.scalified.fab;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.scalified.uitools.convert.DensityConverter;
import com.scalified.uitools.resutils.color.ColorModifier;
import com.scalified.uitools.resutils.id.IdGenerator;
import com.scalified.viewmover.configuration.MovingParams;
import com.scalified.viewmover.movers.ViewMover;
import com.scalified.viewmover.movers.ViewMoverFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionButton.class);

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
	 * Determines whether <b>Action Button</b> Ripple Effect enabled or not
	 */
	private boolean rippleEffectEnabled;

	/**
	 * <b>Action Button</b> Ripple Effect color
	 */
	private int buttonColorRipple = darkenButtonColorPressed();

	/**
	 * Shadow radius expressed in actual pixels
	 */
	private float shadowRadius = dpToPx(8.0f);

	/**
	 * Shadow X-axis offset expressed in actual pixels
	 */
	private float shadowXOffset = dpToPx(0.0f);

	/**
	 * Shadow Y-axis offset expressed in actual pixels 
	 */
	private float shadowYOffset = dpToPx(8.0f);

	/**
	 * Shadow color 
	 */
	private int shadowColor = Color.parseColor("#42000000");

	/**
	 * Determines whether Shadow Responsive Effect enabled
	 * <p>
	 * Responsive Shadow means that shadow is enlarged up to the certain limits
	 * while in the {@link ActionButton.State#PRESSED} state
	 */
	private boolean shadowResponsiveEffectEnabled = true;

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
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * A view invalidator, which is used to invalidate the <b>Action Button</b>
	 */
	private final ViewInvalidator invalidator = new ViewInvalidator(this);

	/**
	 * A drawer, which is used for drawing the <b>Action Button</b> Ripple Effect
	 */
	protected final EffectDrawer rippleEffectDrawer = new RippleEffectDrawer(this);

	/**
	 * A drawer, which is used for drawing the <b>Action Button</b> Shadow Responsive Effect
	 */
	protected final EffectDrawer shadowResponsiveDrawer = new ShadowResponsiveDrawer(this);

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
		initActionButton();
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
		initActionButton();
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
		initActionButton();
		initActionButtonAttrs(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * Initializes the <b>Action Button</b>, which is created programmatically 
	 */
	private void initActionButton() {
		initLayerType();
		LOGGER.trace("Initialized the Action Button");
	}

	/**
	 * Initializes the <b>Action Button</b> attributes, declared within XML resource
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
		TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ActionButton,
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
			initShadowResponsiveEffectEnabled(attributes);
			initStrokeWidth(attributes);
			initStrokeColor(attributes);
			initImage(attributes);
			initImageSize(attributes);
			initShowAnimation(attributes);
			initHideAnimation(attributes);
		} catch (Exception e) {
			LOGGER.trace("Failed to read attribute", e);
		} finally {
			attributes.recycle();
		}
		LOGGER.trace("Successfully initialized the Action Button attributes");
	}

	/**
	 * Initializes the layer type needed for shadows drawing
	 * <p>
	 * Might be called if target API is {@code HONEYCOMB (11)} and higher
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initLayerType() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(LAYER_TYPE_SOFTWARE, getPaint());
			LOGGER.trace("Initialized the layer type");
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
			LOGGER.trace("Initialized Action Button type: {}", getType());
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
		LOGGER.trace("Initialized Action Button size: {}", getSize());
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
			LOGGER.trace("Initialized Action Button color: {}", getButtonColor());
		}
	}

	/**
	 * Initializes the <b>Action Button</b> color for the {@link State#PRESSED}
	 * {@link #state}
	 * <p>
	 * Initialized the <b>Action Button</b> default Ripple Effect color
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
			LOGGER.trace("Initialized Action Button color pressed: {}", getButtonColorPressed());
		}
	}

	/**
	 * Initializes the <b>Action Button</b> Ripple Effect state
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initRippleEffectEnabled(TypedArray attrs) {
		int index = R.styleable.ActionButton_rippleEffect_enabled;
		if (attrs.hasValue(index)) {
			rippleEffectEnabled = attrs.getBoolean(index, rippleEffectEnabled);
			LOGGER.trace("Initialized Action Button Ripple Effect enabled: {}", isRippleEffectEnabled());
		}
	}

	/**
	 * Initializes the <b>Action Button</b> Ripple Effect color
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
			LOGGER.trace("Initialized Action Button Ripple Effect color: {}", getButtonColorRipple());
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
			LOGGER.trace("Initialized Action Button shadow radius: {}", getShadowRadius());
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
			LOGGER.trace("Initialized Action Button X-axis offset: {}", getShadowXOffset());
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
			LOGGER.trace("Initialized Action Button shadow Y-axis offset: {}", getShadowYOffset());
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
			LOGGER.trace("Initialized Action Button shadow color: {}", getShadowColor());
		}
	}

	/**
	 * Initializes the Shadow Responsive Effect
	 *
	 * @param attrs attributes of the XML tag that is inflating the view
	 */
	private void initShadowResponsiveEffectEnabled(TypedArray attrs) {
		int index = R.styleable.ActionButton_shadowResponsiveEffect_enabled;
		if (attrs.hasValue(index)) {
			shadowResponsiveEffectEnabled = attrs.getBoolean(index, shadowResponsiveEffectEnabled);
			LOGGER.trace("Initialized Action Button Shadow Responsive Effect enabled: {}",
					isShadowResponsiveEffectEnabled());
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
			LOGGER.trace("Initialized Action Button stroke width: {}", getStrokeWidth());
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
			LOGGER.trace("Initialized Action Button stroke color: {}", getStrokeColor());
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
			LOGGER.trace("Initialized Action Button show animation");
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
			LOGGER.trace("Initialized Action Button hide animation");
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
			LOGGER.trace("Initialized Action Button image");
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
			LOGGER.trace("Initialized Action Button image size: {}", getImageSize());
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
			LOGGER.trace("Shown the Action Button");
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
			LOGGER.trace("Hidden the Action Button");
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
			LOGGER.trace("Dismissed the Action Button");
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
	 * parameters
	 *
	 * @param params moving parameters, which contain the desired position to move
	 */
	public void move(MovingParams params) {
		LOGGER.trace("About to move the Action Button: X-axis delta = {}, Y-axis delta = {}",
				params.getXAxisDelta(), params.getYAxisDelta());
		mover.move(params);
	}

	/**
	 * Moves the <b>Action Button</b> right to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels to move
	 *                 the <b>Action Button</b> right
	 */
	public void moveRight(float distance) {
		final MovingParams params = new MovingParams(getContext(), distance, 0);
		move(params);
	}

	/**
	 * Moves the <b>Action Button</b> down to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels
	 *                 to move the <b>Action Button</b> down
	 */
	public void moveDown(float distance) {
		final MovingParams params = new MovingParams(getContext(), 0, distance);
		move(params);
	}

	/**
	 * Moves the <b>Action Button</b> left to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels
	 *                 to move the <b>Action Button</b> left
	 */
	public void moveLeft(float distance) {
		final MovingParams params = new MovingParams(getContext(), -distance, 0);
		move(params);
	}

	/**
	 * Moves the <b>Action Button</b> up to a specified distance
	 *
	 * @param distance distance specified in density-independent pixels
	 *                 to move the <b>Action Button</b> up
	 */
	public void moveUp(float distance) {
		final MovingParams params = new MovingParams(getContext(), 0, -distance);
		move(params);
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
	 * Sets the <b>Action Button</b> {@link ActionButton.Type}
	 * and calls {@link #setSize(float)} to set the size depending on {@link #type} set
	 *
	 * @param type type of the <b>Action Button</b>
	 */
	public void setType(Type type) {
		this.type = type;
		LOGGER.trace("Changed the Action Button type to: {}", getType());
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
		LOGGER.trace("Set the Action Button size to: {}", getSize());
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
		LOGGER.trace("Changed the Action Button state to: {}", getState());
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
		LOGGER.trace("Changed the Action Button color to: {}", getButtonColor());
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
		LOGGER.trace("Changed the Action Button color pressed to: {}", getButtonColorPressed());
	}

	/**
	 * Darkens the {@link #buttonColorPressed} using the darkening factor
	 *
	 * @return darker color variant of {@link #buttonColorPressed}
	 */
	private int darkenButtonColorPressed() {
		float darkenFactor = 0.8f;
		return ColorModifier.modifyExposure(getButtonColorPressed(), darkenFactor);
	}

	/**
	 * Checks whether <b>Action Button</b> Ripple Effect enabled
	 *
	 * @return true, if Ripple Effect enabled, otherwise false
	 */
	public boolean isRippleEffectEnabled() {
		return rippleEffectEnabled;
	}

	/**
	 * Toggles the Ripple Effect state
	 *
	 * @param enabled true if Ripple Effect needs to be enabled, otherwise false
	 */
	public void setRippleEffectEnabled(boolean enabled) {
		this.rippleEffectEnabled = enabled;
		LOGGER.trace("{} the Action Button Ripple Effect", isRippleEffectEnabled() ? "Enabled" : "Disabled");
	}

	/**
	 * Returns the <b>Action Button</b> Ripple Effect color
	 *
	 * @return <b>Action Button</b> Ripple Effect color
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
		LOGGER.trace("Action Button Ripple Effect color changed to: {}", getButtonColorRipple());
	}

	/**
	 * Checks whether <b>Action Button</b> has shadow by determining shadow radius
	 * <p>
	 * Shadow is disabled if elevation is set
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
	 * <p>
	 * Additionally sets the {@link #shadowResponsiveDrawer} current radius
	 * in case if Shadow Responsive Effect enabled
	 *
	 * @param shadowRadius shadow radius specified in density-independent 
	 *                     (dp) pixels
	 */
	public void setShadowRadius(float shadowRadius) {
		this.shadowRadius = dpToPx(shadowRadius);
		if (isShadowResponsiveEffectEnabled()) {
			((ShadowResponsiveDrawer) shadowResponsiveDrawer).setCurrentShadowRadius(getShadowRadius());
		}
		requestLayout();
		LOGGER.trace("Action Button shadow radius changed to: {}", getShadowRadius());
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
		LOGGER.trace("Changed the Action Button shadow X offset to: {}", getShadowXOffset());
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
		LOGGER.trace("Changed the Action Button shadow Y offset to: {}", getShadowYOffset());
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
		LOGGER.trace("Changed the Action Button shadow color to: {}", getShadowColor());
	}

	/**
	 * Checks whether Shadow Responsive Effect enabled
	 * <p>
	 * Shadow Responsive Effect means that shadow is enlarged up to the certain limits
	 * while in the {@link ActionButton.State#PRESSED} state
	 *
	 * @return true if <b>Action Button</b> Shadow Responsive Effect enabled, otherwise false
	 */
	public boolean isShadowResponsiveEffectEnabled() {
		return shadowResponsiveEffectEnabled;
	}

	/**
	 * Toggles the Shadow Responsive Effect
	 * <p>
	 * Shadow Responsive Effect means that shadow is enlarged up to the certain limits
	 * while in the {@link ActionButton.State#PRESSED} state
	 *
	 * @param shadowResponsiveEffectEnabled true if Shadow Responsive Effect must be
	 *                                      enabled, otherwise false
	 */
	public void setShadowResponsiveEffectEnabled(boolean shadowResponsiveEffectEnabled) {
		this.shadowResponsiveEffectEnabled = shadowResponsiveEffectEnabled;
		requestLayout();
		LOGGER.trace("{} the Shadow Responsive Effect", isShadowResponsiveEffectEnabled() ? "Enabled" : "Disabled");
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
		LOGGER.trace("Changed the stroke width to: {}", getStrokeWidth());
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
		LOGGER.trace("Changed the stroke color to: {}", getStrokeColor());
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
		LOGGER.trace("Set the Action Button image drawable");
	}

	/**
	 * Resolves the drawable resource id and places the resolved image drawable
	 * centered inside the view
	 * 
	 * @param resId drawable resource id, which is to be resolved to 
	 *              image drawable and used as parameter when calling
	 *              {@link #setImageDrawable(android.graphics.drawable.Drawable)}              
	 */
	@SuppressWarnings("deprecation")
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
		LOGGER.trace("Changed the Action Button image size to: {}", getImageSize());
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
		LOGGER.trace("Set the Action Button show animation");
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
		LOGGER.trace("Removed the Action Button show animation");
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
		LOGGER.trace("Set the Action Button hide animation");
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
		LOGGER.trace("Removed the Action Button hide animation");
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
					LOGGER.trace("Detected the ACTION_DOWN motion event");
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (touchPointInsideCircle) {
					setState(State.NORMAL);
					getTouchPoint().reset();
					LOGGER.trace("Detected the ACTION_UP motion event");
					return true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (!touchPointInsideCircle
						&& getState() == State.PRESSED) {
					setState(State.NORMAL);
					getTouchPoint().reset();
					LOGGER.trace("Detected the ACTION_MOVE motion event");
					return true;
				}
				break;
			default:
				LOGGER.warn("Detected unrecognized motion event");
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
	 * Returns the paint, which is used for drawing the <b>Action Button</b> elements
	 *
	 * return paint, which is used for drawing the <b>Action Button</b> elements
	 */
	protected Paint getPaint() {
		return paint;
	}
	
	/**
	 * Resets the paint to its default values and sets initial flags to it
	 * <p>
	 * Use this method before drawing the new element of the view     
	 */
	protected final void resetPaint() {
		getPaint().reset();
		getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
		LOGGER.trace("Reset the Action Button paint");
	}

	/**
	 * Returns the view invalidator, which is used to invalidate the
	 * <b>Action Button</b>
	 *
	 * @return view invalidator, which is used to invalidate the <b>Action Button</b>
	 */
	protected ViewInvalidator getInvalidator() {
		return invalidator;
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
		LOGGER.trace("Called Action Button onDraw");
		drawCircle(canvas);
		if (isRippleEffectEnabled()) {
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
		getInvalidator().invalidate();
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
			if (isShadowResponsiveEffectEnabled()) {
				shadowResponsiveDrawer.draw(canvas);
			} else {
				drawShadow();
			}
		}
		getPaint().setStyle(Paint.Style.FILL);
		boolean rippleInProgress = isRippleEffectEnabled()
				&& ((RippleEffectDrawer) rippleEffectDrawer).isDrawingInProgress();
		getPaint().setColor(getState() == State.PRESSED || rippleInProgress ?
				getButtonColorPressed() : getButtonColor());
		canvas.drawCircle(calculateCenterX(), calculateCenterY(), calculateCircleRadius(), getPaint());
		LOGGER.trace("Drawn the Action Button circle");
	}

	/**
	 * Calculates the X-axis center coordinate of the entire view
	 *
	 * @return X-axis center coordinate of the entire view
	 */
	protected float calculateCenterX() {
		float centerX = getMeasuredWidth() / 2;
		LOGGER.trace("Calculated Action Button center X: {}", centerX);
		return centerX;
	}

	/**
	 * Calculates the Y-axis center coordinate of the entire view
	 *
	 * @return Y-axis center coordinate of the entire view
	 */
	protected float calculateCenterY() {
		float centerY = getMeasuredHeight() / 2;
		LOGGER.trace("Calculated Action Button center Y: {}", centerY);
		return centerY;
	}

	/**
	 * Calculates the radius of the main circle
	 *
	 * @return radius of the main circle
	 */
	protected final float calculateCircleRadius() {
		float circleRadius = getSize() / 2;
		LOGGER.trace("Calculated Action Button circle radius: {}", circleRadius);
		return circleRadius;
	}

	/**
	 * Draws the shadow if view elevation is not enabled
	 */
	protected void drawShadow() {
		getPaint().setShadowLayer(getShadowRadius(), getShadowXOffset(), getShadowYOffset(), getShadowColor());
		LOGGER.trace("Drawn the Action Button shadow");
	}

	/**
	 * Draws the Ripple Effect
	 *
	 * @param canvas canvas, on which ripple effect is to be drawn
	 */
	protected void drawRipple(Canvas canvas) {
		rippleEffectDrawer.draw(canvas);
		LOGGER.trace("Drawn the Action Button Ripple Effect");
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
		float halfSize = getSize() / 2;
		final int left = (int) (calculateCenterX() - halfSize);
		final int top = (int) (calculateCenterY() - halfSize);
		final int right = (int) (calculateCenterX() + halfSize);
		final int bottom = (int) (calculateCenterY() + halfSize);
		ViewOutlineProvider provider = new ViewOutlineProvider() {
			@Override
			public void getOutline(View view, Outline outline) {
				outline.setOval(left, top, right, bottom);
			}
		};
		setOutlineProvider(provider);
		LOGGER.trace("Drawn the Action Button elevation");
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
		getPaint().setStyle(Paint.Style.STROKE);
		getPaint().setStrokeWidth(getStrokeWidth());
		getPaint().setColor(getStrokeColor());
		canvas.drawCircle(calculateCenterX(), calculateCenterY(), calculateCircleRadius(), getPaint());
		LOGGER.trace("Drawn the Action Button stroke");
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
		LOGGER.trace("Drawn the Action Button image on canvas with coordinates: X start point = {}, " +
				"Y start point = {}, X end point = {}, Y end point = {}",
				startPointX, startPointY, endPointX, endPointY);
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
		LOGGER.trace("Called Action Button onMeasure");
		setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight());
		LOGGER.trace("Measured the Action Button size: height = {}, width = {}", getHeight(), getWidth());
	}

	/**
	 * Calculates the measured width in actual pixels for the entire view
	 *  
	 * @return measured width in actual pixels for the entire view
	 */
	private int calculateMeasuredWidth() {
		int measuredWidth = (int) (getSize() + calculateShadowWidth() + calculateStrokeWeight());
		LOGGER.trace("Calculated Action Button measured width: {}", measuredWidth);
		return measuredWidth;
	}

	/**
	 * Calculates the measured height in actual pixels for the entire view
	 *  
	 * @return measured width in actual pixels for the entire view
	 */
	private int calculateMeasuredHeight() {
		int measuredHeight = (int) (getSize() + calculateShadowHeight() + calculateStrokeWeight());
		LOGGER.trace("Calculated Action Button measured height: {}", measuredHeight);
		return measuredHeight;
	}

	/**
	 * Calculates shadow width in actual pixels
	 *  
	 * @return shadow width in actual pixels
	 */
	private int calculateShadowWidth() {
		float mShadowRadius = isShadowResponsiveEffectEnabled() ?
				((ShadowResponsiveDrawer) shadowResponsiveDrawer).getMaxShadowRadius() : getShadowRadius();
		int shadowWidth = hasShadow() ? (int) ((mShadowRadius	+ Math.abs(getShadowXOffset())) * 2) : 0;
		LOGGER.trace("Calculated Action Button shadow width: {}", shadowWidth);
		return shadowWidth;
	}

	/**
	 * Calculates shadow height in actual pixels
	 *  
	 * @return shadow height in actual pixels
	 */
	private int calculateShadowHeight() {
		float mShadowRadius = isShadowResponsiveEffectEnabled() ?
				((ShadowResponsiveDrawer) shadowResponsiveDrawer).getMaxShadowRadius() : getShadowRadius();
		int shadowHeight = hasShadow() ? (int) ((mShadowRadius + Math.abs(getShadowYOffset())) * 2) : 0;
		LOGGER.trace("Calculated Action Button shadow height: {}", shadowHeight);
		return shadowHeight;
	}

	/**
	 * Calculates the stroke weight in actual pixels
	 * *
	 * @return stroke weight in actual pixels
	 */
	private int calculateStrokeWeight() {
		int strokeWeight = (int) (getStrokeWidth() * 2.0f);
		LOGGER.trace("Calculated Action Button stroke width: {}", strokeWidth);
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
		NONE                (IdGenerator.next()),

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
