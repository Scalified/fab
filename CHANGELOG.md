# 1.1.0

1. **New** public methods, classes and attributes:

	1. Methods:

		* **setSize(float)** - sets the size of the **Action Button** (in density-independent pixels).
		Changing the default size of the button breaks the rules of <a href="http://www.google.com/design/spec/components/buttons.html">Material Design</a>.
	    Setting the button size explicitly means, that button types with its default sizes are completely ignored. Do not use this method, unless you know what you are doing
		* **getSize()** - returns the size of the **Action Button** in real pixels (the same as **getButtonSize()**, which is now *deprecated*).
	
	2. XML attributes:
	
		* **size** - lets to declare the button size (the same as **setSize(float)**)
		
	3. Classes:
	
		* added new **Action Button** type - **ActionButton.Type.BIG**, which has a size of 72 density-independent pixels. Also added a correspondent value **BIG** to **type** XML attribute
		([Pull request #16: *Added BIG size, 72dp size for fab buttons, Fix MOVE feedback, update gitignore*](https://github.com/shell-software/fab/pull/16) by [**Aracem**](https://github.com/Aracem))
		
2. **Attention!** *Deprecated* methods:

	* **getButtonSize()** renamed to **getSize()**. You can still use **getButtonSize()** method, however it is marked as *deprecated* and will be removed in version 2.0.0.

3. Added checking of the *X* and *Y* touch coordinate
	
	* If the touch *X* and *Y* coordinates are not inside the main button circle, the button won't react on click
	* If the button state is **PRESSED** and touch point moves outside the main circle the button state changes to **NORMAL** 
	([Pull request #14: *Update ActionButton.java*](https://github.com/shell-software/fab/pull/14) by [**uriel-frankel**](https://github.com/uriel-frankel))

# 1.0.5

1. Fixed [**issue #12**: Lollipop elevation disable shadow](https://github.com/shell-software/fab/issues/12):
	
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

1. Fixed [**issue #8**: Both buttons show up when I only want one at a time](https://github.com/shell-software/fab/issues/8):

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
        You can still use **FloatingActionButton** class, however it is marked as *deprecated* and wiil be removed in version 2.0.0.

	2. Public methods in **ActionButton** **(FloatingActionButton)** class:

    	* **getAnimationOnShow()** method renamed to **getShowAnimation()**.
        You can still use **getAnimationOnShow()** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnShow(android.view.Animation)** method renamed to **setShowAnimation(android.view.Animation)**.
        You can still use **setAnimationOnShow(android.view.Animation)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnShow(com.software.shell.fab.FloatingActionButton.Animations)** method renamed to **setShowAnimation(com.software.shell.fab.ActionButton.Animations)**.
        You can still use **setAnimationOnShow(com.software.shell.fab.FloatingActionButton.Animations)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **getAnimationOnHide()** method renamed to **getHideAnimation()**.
        You can still use **getAnimationOnHide()** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnHide(android.view.Animation)** method renamed to **setHideAnimation(android.view.Animation)**.
        You can still use **setAnimationOnHide(android.view.Animation)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.
        * **setAnimationOnHide(com.software.shell.fab.FloatingActionButton.Animations)** method renamed to **setHideAnimation(com.software.shell.fab.ActionButton.Animations)**.
        You can still use **setAnimationOnHide(com.software.shell.fab.FloatingActionButton.Animations)** method, however it is marked as *deprecated* and will be removed in version 2.0.0.

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

1. The first release! Everything is new.
