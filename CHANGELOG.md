# 1.1.3

1. Fixed [**issue #28**: move() method smearing on KitKat with snackbar](https://github.com/Scalified/fab/issues/28):
	* A fix was made within the dependent **ViewMover** library.
	  Only **ActionButton** dependency on **ViewMover** library was updated
        
2. Changed the standard Android logging API to **SLF4J Logging API**

# 1.1.1

1. Fixed [**issue #27**: Icon is outside the circle](https://github.com/Scalified/fab/issues/27):

	A call to **Canvas.restore()** without calling the **Canvas.save()** first resulted in the unpredictable behaviour.
	Added call to **Canvas.save()** before **Canvas.restore()** in the **RippleEffectDrawer** class

# 1.1.0

Watch [Demo Video about new features](https://www.youtube.com/watch?v=7GHAcX2myh8)

1. Added Ripple Effect - [**issue #2: ripple effect**](https://github.com/Scalified/fab/issues/2). Disabled by default.
See [**README.md**](https://github.com/Scalified/fab/blob/master/README.md) for more info

2. Added Shadow Responsive Effect - [**issue #11: Question - Pressed State**](https://github.com/Scalified/fab/issues/11). Enabled by default.
See [**README.md**](https://github.com/Scalified/fab/blob/master/README.md) for more info

3. Added possibility to move the **Action Button** - [**issue #9: Move button up and down**](https://github.com/Scalified/fab/issues/9). 
See [**README.md**](https://github.com/Scalified/fab/blob/master/README.md) for more info

4. **Action Button** now has a dependency on an external [**ViewMover**](https://github.com/Scalified/view-mover) library. 
If it is used already in the project it must be excluded as a transitive dependency

5. Changed the **Action Button** default values:

	1. Button default color for **NORMAL** state changed from **Color.LTGRAY** to **#FF9B9B9B**
    2. Button default color for **PRESSED** state changed from **Color.DKGRAY** to **#FF696969**
    3. Shadow default color changed from **#757575** to **#42000000** according to Material Design Guidelines
    4. Shadow default radius changed from **2.0f** to **8.0f** (in density-independent pixels)
    5. Shadow default X-axis offset changed from **1.0f** to **0.0f** (in density-independent pixels)
    6. Shadow default Y-axis offset changed from **1.5f** to **8.0f** (in density-independent pixels)

6. **New** public methods, classes and attributes:

	1. Methods:

		* **setSize(float)** - sets the size of the **Action Button** (in density-independent pixels).
		Changing the default size of the button breaks the rules of [Material Design](http://www.google.com/design/spec/components/buttons.html).
	    Setting the button size explicitly means, that button types with its default sizes are completely ignored. Do not use this method, unless you know what you are doing
		* **getSize()** - returns the size of the **Action Button** in real pixels (the same as **getButtonSize()**, which is now marked as *deprecated*).
		* **move(MovingParams)** - moves the **Action Button** to the specified position obtained from **MovingParams** object
		* **moveRight(float)** - moves the **Action Button** right to a specified distance (in density-independent pixels)
		* **moveDown(float)** - moves the **Action Button** down to a specified distance (in density-independent pixels)
		* **moveLeft(float)** - moves the **Action Button** left to a specified distance (in density-independent pixels)
		* **moveUp(float)** - moves the **Action Button** up to a specified distance (in density-independent pixels)
		* **isRippleEffectEnabled()** - checks whether **Action Button** Ripple Effect enabled
		* **setRippleEffectEnabled(boolean)** - toggles the Ripple Effect state
		* **getButtonColorRipple()** - returns the **Action Button** Ripple Effect color
		* **setButtonColorRipple(int)** - sets the **Action Button** Ripple Effect color
		* **isShadowResponsiveEffectEnabled()** - returns whether Shadow Responsive Effect enabled
		* **setShadowResponsiveEffectEnabled(boolean)** - sets the Shadow Responsive Effect
		* **getTouchPoint()** - returns the **Action Button** touch point
			
	2. XML attributes:
	
		* **size** - dimension, declares the button size (the same as **setSize(float)**)
		* **BIG** - enum (value **2**), declares the **Action Button** **BIG** type, which has the size of **72.0dp** (the same as **setType(ActionButton.Type.BIG)**)
		* **rippleEffect_enabled** - boolean, enables the **Action Button** Ripple Effect (the same as **setRippleEffectEnabled(boolean)**)
		* **button_colorRipple** - color, declares the **Action Button** Ripple Effect color (the same as **setButtonColorRipple(int)**)
		* **shadowResponsiveEffect_enabled** - boolean, enables the **Action Button** Shadow Responsive Effect (the same as **setShadowResponsiveEffectEnabled(boolean)**)
		
	3. Classes:
	
		* added new **Action Button** type - **ActionButton.Type.BIG**, which has a size of ***72*** density-independent pixels. Also added a correspondent value **BIG** to **type** XML attribute
		([Pull request #16: *Added BIG size, 72dp size for fab buttons, Fix MOVE feedback, update gitignore*](https://github.com/Scalified/fab/pull/16) by [**Aracem**](https://github.com/Aracem))
        * **TouchPoint** - an entity class, which contains the information about X- and Y-axis coordinates of the touch point (can't be instantiated).
		
7. **Attention!** *Deprecated* methods:

	* **getButtonSize()** renamed to **getSize()**. You can still use **getButtonSize()** method, however it is marked as *deprecated* and will be removed in version 2.0.0.

8. Added checking of the *X* and *Y* touch points coordinates
	
	* If the touch *X* and *Y* coordinates are not inside the main button circle, the button won't react on click
	* If the button state is **PRESSED** and touch point moves outside the main circle the button state changes to **NORMAL** 
	([Pull request #14: *Update ActionButton.java*](https://github.com/Scalified/fab/pull/14) by [**uriel-frankel**](https://github.com/uriel-frankel))
	

# 1.0.5

1. Fixed [**issue #12**: Lollipop elevation disable shadow](https://github.com/Scalified/fab/issues/12):
	
	The fix enables elevation on devices with **API 21 Lollipop** and higher. Now if elevation is set and the device *API* meets requirements (has *API 21 Lollipop* and higher) elevation will be drawn instead of the default shadow.
	In this case configuration of any of the default shadow's parameters will be ignored.
	Previously elevation was not drawn for such devices if set.
	
  A fix was applied to:

  * **hasShadow()** method: now if **Action Button** has elevation enabled (for *API 21 Lollipop* and higher) the shadow won't be drawn at all
  * **calculateCenterX()** method: **getWidth()** method replaced by **getMeasuredWidth()** to calculate *X-axis* coordinate
  * **calculateCenterY()** method: **getHeight()** method replaced by **getMeasuredHeight()** is used to calculate *Y-axis* coordinate

  New methods added:

  * **drawElevation()**: protected void method, which is called by **onDraw(Canvas)** to draw the elevation for *API 21 Lollipop* devices and higher

# 1.0.4

1. Fixed [**issue #8**: Both buttons show up when I only want one at a time](https://github.com/Scalified/fab/issues/8):

	A small fix was applied to **show()**, **hide()** and **dismiss()** methods. Previously these methods might not work properly if the call was done within **onCreate()** method.
	This happened because of using **android.view.View#isShown()** method, which returned *false* even if the button was shown. Now these methods relay on **VISIBILITY** and work
	as expected wherever they called.

# 1.0.3

1. **Attention!** *Deprecated* XML attributes:

	* **normal** XML attribute renamed to **DEFAULT**.
    You can still use **normal** XML attribute, however it will be removed in version 2.0.0.
	* **mini** XML attribute renamed to **MINI**.
    You can still use **mini** XML attribute, however it will be removed in version 2.0.0.

# 1.0.2

> **FloatingActionButton** class has been renamed to **ActionButton** class. You can still use **FloatingActionButton** class, however it is  marked as *deprecated* and will be removed in version 2.0.0. **FloatingActionButton** class contains other methods, which were deprecated. You can use these old deprecated methods and XML attributes listed below only with **FloatingActionButton** class. You can't use them with the new **ActionButton** class.
> **ActionButton** class in turn, has the new version of these deprecated methods, which you can use.

1. **Attention!** *Deprecated* classes, methods, attributes:

    1. Classes:

    	* **FloatingActionButton** class renamed to **ActionButton** class.
        You can still use **FloatingActionButton** class, however it is marked as *deprecated* and will be removed in version 2.0.0.

	2. Public methods in **ActionButton** **(FloatingActionButton)** class:

    	* **getAnimationOnShow()** method renamed to **getShowAnimation()**.
        You can still use **getAnimationOnShow()** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnShow(android.view.Animation)** method renamed to **setShowAnimation(android.view.Animation)**.
        You can still use **setAnimationOnShow(android.view.Animation)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnShow(com.scalified.fab.FloatingActionButton.Animations)** method renamed to **setShowAnimation(com.scalified.fab.ActionButton.Animations)**.
        You can still use **setAnimationOnShow(com.scalified.fab.FloatingActionButton.Animations)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **getAnimationOnHide()** method renamed to **getHideAnimation()**.
        You can still use **getAnimationOnHide()** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnHide(android.view.Animation)** method renamed to **setHideAnimation(android.view.Animation)**.
        You can still use **setAnimationOnHide(android.view.Animation)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnHide(com.scalified.fab.FloatingActionButton.Animations)** method renamed to **setHideAnimation(com.scalified.fab.ActionButton.Animations)**.
        You can still use **setAnimationOnHide(com.scalified.fab.FloatingActionButton.Animations)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.

	3. XML attributes:

    	* **animation_onShow** XML attribute renamed to **show_animation**.
        You can still use **animation_onShow** XML attribute, however it will be removed in version 2.0.0.
        * **animation_onHide** XML attribute renamed to **hide_animation**.
        You can still use **animation_onHide** XML attribute, however it will be removed in version 2.0.0.

2. Added new public methods in **ActionButton** **(FloatingActionButton)** class:

	* **playShowAnimation()** - plays the show animation
	* **playHideAnimation()** - plays the hide animation
	* **removeShowAnimation()** - removes the show animation
	* **removeHideAnimation()** - removes the hide animation

3. Changed the default shadow values:

	* **shadowRadius** value changed from **1.0f** to **2.0f** in density-independent pixels
	* **shadowXOffset** value changed from **0.5f** to **1.0f** in density-independent pixels
	* **shadowYOffset** value changed from **1.0f** to **1.5f** in density-independent pixels

# 1.0.1

1. Fixed issue in animations attributes parsing:

	* **animation_onShow**
	* **animation_onHide**

# 1.0.0

1. The first release! Everything is new. Watch [Full Demo Video](https://www.youtube.com/watch?v=bIT_LAZoukg)
