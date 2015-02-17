# 1.0.0

1. The first release! Everything is new

# 1.0.1

1. Fixed issue in animations attributes parsing:

	* **animation_onShow**
	* **animation_onHide**
	
# 1.0.2

1. **Attention!** Deprecated classes and methods:
> **FloatingActionButton** class renamed to **ActionButton** class. You can still use **FloatingActionButton** class, however it is  marked as deprecated and will be removed in version 2.0.0. **FloatingActionButton** class contains other methods, which were deprecated. You can use these old deprecated methods and XML attributes listed below only with **FloatingActionButton** class. You can't use them with the new **ActionButton** class. 
> **ActionButton** class in turn, has the new version of these deprecated methods, which you can use.
	
    * **getAnimationOnShow()** method renamed to **getShowAnimation()**. You can still use **getAnimationOnShow()**
	method, however it is marked as deprecated and will be removed in version 2.0.0.
	* **setAnimationOnShow(android.view.Animation)** method renamed to **setShowAnimation(android.view.Animation)**.
	You can still use **setAnimationOnShow(android.view.Animation)** method, however it is marked as deprecated and 
	will be removed in version 2.0.0.
	* **setAnimationOnShow(com.software.shell.fab.FloatingActionButton.Animations)** method renamed to 
	**setShowAnimation(com.software.shell.fab.ActionButton.Animations)**. You can still use 
	**setAnimationOnShow(com.software.shell.fab.FloatingActionButton.Animations)** method, however it is marked as 
	deprecated and will be removed in version 2.0.0.
	* **getAnimationOnHide()** method renamed to **getHideAnimation()**. You can still use **getAnimationOnHide()**
    method, however it is marked as deprecated and will be removed in version 2.0.0.
    * **setAnimationOnHide(android.view.Animation)** method renamed to **setHideAnimation(android.view.Animation)**.
    You can still use **setAnimationOnHide(android.view.Animation)** method, however it is marked as deprecated and 
    will be removed in version 2.0.0.
    * **setAnimationOnHide(com.software.shell.fab.FloatingActionButton.Animations)** method renamed to 
    **setHideAnimation(com.software.shell.fab.ActionButton.Animations)**. You can still use 
    **setAnimationOnHide(com.software.shell.fab.FloatingActionButton.Animations)** method, however it is marked as 
    deprecated and will be removed in version 2.0.0.
    * **animation_onShow** XML attribute renamed to **show_animation**. You can still use **animation_onShow**
    XML attribute, however it will be removed in version 2.0.0
    * **animation_onHide** XML attribute renamed to **hide_animation**. You can still use **animation_onHide**
    XML attribute, however it will be removed in version 2.0.0
    
2. Added new public methods:

	* **playShowAnimation()** - plays the show animation
	* **playHideAnimation()** - plays the hide animation
	* **removeShowAnimation()** - removes the show animation
	* **removeHideAnimation()** - removes the hide animation
    
3. Changed the default values for shadow:

	* **shadowRadius** from 1.0f to 2.0f in density-independent pixels
	* **shadowXOffset** from 0.5f to 1.0f in density-independent pixels
	* **shadowYOffset** from 1.0f to 1.5f in density-independent pixels
	
# 1.0.3

1. **Attention!** Deprecated XML attributes:

	* **normal** XML attribute renamed to **DEFAULT**. You can still use **normal** XML attribute, however it will
    be removed in version 2.0.0
    * **mini** XML attribute renamed to **MINI**. You can still use **mini** XML attribute, however it will
    be removed in version 2.0.0
