# Floating Action Button Library for Android

[![Build Status](https://travis-ci.org/shell-software/fab.svg?branch=master)](https://travis-ci.org/shell-software/fab)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.shell-software/fab.svg)](http://search.maven.org/#search|gav|1|g%3A%22com.github.shell-software%22%20AND%20a%3A%22fab%22)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-fab-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1522)

## Donation

Donation helps to improve the project development and speed up the release of new versions. I appreciate any contribution

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=44CVJBPFRKXJL)

## Description

This Library contains implementation of the [**Floating Action Button**](http://www.google.com/design/spec/components/buttons.html#buttons-floating-action-button) for Android.

Floating action buttons are used for a special type of promoted action. They are distinguished by a circled icon floating above the UI and have special motion behaviors related to morphing, launching, and the transferring anchor point.

## Requirements

The Library requires **Android SDK version 9 (Gingerbread)** and higher.

## Gradle Dependency

```java
dependencies {
	compile 'com.github.shell-software:fab:1.0.5'
}
```

## Activity Stream

[**Full ChangeLog**](https://github.com/shell-software/fab/blob/master/CHANGELOG.md)

### 1.0.5 - *current*

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

### 1.0.4 - *previous*

1. Fixed [**issue #8**: Both buttons show up when I only want one at a time](https://github.com/shell-software/fab/issues/8):

	A small fix was applied to **show()**, **hide()** and **dismiss()** methods. Previously these methods might not work properly if the call was done within **onCreate()** method.
	This happened because of using **android.view.View#isShown()** method, which returned *false* even if the button was shown. Now these methods relay on **VISIBILITY** and work
	as expected wherever they called.

### Features in the next versions:

* **1.1.0**:

	New features - **confirmed**:
	* [**issue #2**: ripple effect](https://github.com/shell-software/fab/issues/2)
	* [**issue #9**: Move button up and down](https://github.com/shell-software/fab/issues/9)
	
* **2.0.0**: Action Menu - *TBD* - please <a href="mailto:com.software.shell@gmail.com?subject=[Action Button]: Add Action Menu TBD">send me</a> your propositions.

## Demo

Watch the [**Full Demo Video**](https://www.youtube.com/watch?v=skSApXvi4xM) on YouTube

### Button types

Floating action buttons come in two sizes: the **DEFAULT**, which should be used in most cases, and the **MINI**, which should only be used to create visual continuity with other elements on the screen.

DEFAULT | MINI
:-:|:-:
![Default](https://github.com/shell-software/fab/blob/master/demo/button_type_default.png) | ![Mini](https://github.com/shell-software/fab/blob/master/demo/button_type_mini.png)

### Color palette

The Library contains all of the colors **500** and **900** of the [**Material Color Palette**](http://www.google.com/design/spec/style/color.html#color-color-palette).

Colors **500** are used for the **NORMAL** button state while **900** ones for the **PRESSED** state:

Green 500 | Amber 500 | Blue Grey 500
:-:|:-:|:-:
![Green 500](https://github.com/shell-software/fab/blob/master/demo/color_green.png) | ![Amber Color](https://github.com/shell-software/fab/blob/master/demo/color_amber.png) | ![Blue Grey](https://github.com/shell-software/fab/blob/master/demo/color_blue_grey.png)

### Shadow

Shadow is **enabled** by default and has standard settings. These settings are suitable in most cases. However, shadow can be modified in three ways: *radius*, *X-* or *Y-axis offset* and *color*.

Default | Radius | X- and Y- axis offset
:-:|:-:|:-:
![Default Shadow](https://github.com/shell-software/fab/blob/master/demo/default_shadow.png) | ![Shadow Radius](https://github.com/shell-software/fab/blob/master/demo/shadow_radius.png) | ![Shadow Offset](https://github.com/shell-software/fab/blob/master/demo/shadow_axis_offset.png)

### Stroke

Stroke is **disabled** by default.
Stroke can be modified in two ways: *width* and *color*.

Thin | Medium | Thick
:-:|:-:|:-:
![Thin Stroke](https://github.com/shell-software/fab/blob/master/demo/stroke_thin.png) | ![Medium Stroke](https://github.com/shell-software/fab/blob/master/demo/stroke_medium.png) | ![Thick Stroke](https://github.com/shell-software/fab/blob/master/demo/stroke_thick.png)


### Animations

The Library has several predefined animations:

Fade In - Fade Out | Roll From Down - Roll To Down | Jump From Down - Jump To Down
:-:|:-:|:-:
![Fade In Fade Out](https://github.com/shell-software/fab/blob/master/demo/animation_fade_in_fade_out.gif) | ![Roll From Down Roll To Down](https://github.com/shell-software/fab/blob/master/demo/animation_roll_from_down_roll_to_down.gif) | ![Jump From Down - Jump To Down](https://github.com/shell-software/fab/blob/master/demo/animation_jump_from_down_jump_to_down.gif)

Scale In - Scale Out | Roll From Right - Roll To Right | Jump From Right - Jump To Right
:-:|:-:|:-:
![Scale In Scale Out](https://github.com/shell-software/fab/blob/master/demo/animation_scale_in_scale_out.gif) | ![Roll From Right Roll To Right](https://github.com/shell-software/fab/blob/master/demo/animation_roll_from_right_roll_to_right.gif) | ![Jump From Right Jump To Right](https://github.com/shell-software/fab/blob/master/demo/animation_jump_from_right_jump_to_right.gif)

## Usage

### Creation

#### Declaration inside XML resource

**ActionButton** button can be declared in the XML resource. For instance, using [**RelativeLayout**](http://developer.android.com/reference/android/widget/RelativeLayout.html):

```xml
<RelativeLayout 
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:fab="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
        <com.software.shell.fab.ActionButton 
            android:id="@+id/action_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true" 
            android:layout_alignParentRight="true"
            android:layout_marginRight="@dimen/fab_margin"
            android:layout_marginBottom="@dimen/fab_margin"
            />
</RelativeLayout>
```


```java
// And then find it within the content view:
ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
```

> There are no required configuration parameters for it. All of the configuration parameters are optional.

#### Programmatically

```java
Context context = getContext();
ActionButton actionButton = new ActionButton(context);
// And then add it to the content view
```

### Button actions

**Action Button** can be *shown*, *hidden* or completely *dismissed*:

```java
actionButton.show();    // shows the button if it is hidden and plays the show animation if set
actionButton.hide();    // hides the button if it is shown and plays the hide animation if set
actionButton.dismiss(); // completely dismisses the button and plays the hide animation if set
```

> After dismissal the button is completely removed from its parent view, so any calls to **show()** etc. won't be processed.

The current status of the button can be checked with the help of:

```java
// To check whether button is shown (inherited from android.view.View class):
boolean shown = actionButton.isShown();

// To check whether button is hidden:
boolean hidden = actionButton.isHidden();

// To check whether button is dismissed:
boolean dismissed = actionButton.isDismissed();
```

There are some cases when you need to force playing the animation without calling the **show()**, 
**hide()** or **dismiss()** methods. For instance, when your button appears within layout in the Activity
for the first time. In such cases animations can be played apart from *showing*, *hiding*, or *dismissing*
the button:

```java
actionButton.playShowAnimation();   // plays the show animation
actionButton.playHideAnimation();   // plays the hide animation
```

> Animations are played only if set. By default animations are not set.

### Customization

#### Button types

There are two types of **Action Button**, which you can work with:

* **DEFAULT**
* **MINI**

By default the button type is set to **DEFAULT**.

To work with button types use:

```java
// To set the button type:
actionButton.setType(ActionButton.Type.MINI);

// To get the button type:
ActionButton.Type type = actionButton.getType();
```
	
#### Button states

There are two button states, which **Action Button** may reside in:

* **NORMAL**
* **PRESSED**
	
To work with button states use:

```java
// To set the button state:
actionButton.setState(ActionButton.State.PRESSED);

// To get the button state:
ActionButton.State state = actionButton.getState();
```

#### Button colors

Button colors can be set as for the **NORMAL** state, so for the **PRESSED** one. 
By default the following colors are set:

* **Color.LTGRAY** for the **NORMAL** state
* **Color.DKGRAY** for the **PRESSED** state
	
To work with button colors use:

```java
// To set button color for normal state:
actionButton.setButtonColor(getResources().getColor(R.color.fab_material_lime_500));

// To get button color for normal state:
int buttonColor = actionButton.getButtonColor();

// To set button color for pressed state:
actionButton.setButtonColorPressed(getResources().getColor(R.color.fab_material_lime_900));

// To get button color for pressed state:
int buttonColorPressed = actionButton.getButtonColorPressed();
```

#### Shadow

Shadow can be customized in three ways: *color*, *radius* and *offset*.
By default shadow is **enabled** and has the following default values:
	
* **shadowColor** = **#757575** (~ middle-grey)
* **shadowRadius** = **2.0f**   (in density-independent pixels)
* **shadowXOffset** = **1.0f**  (in density-independent pixels)
* **shadowYOffset** = **1.5f**  (in density-independent pixels)

To work with shadow use:

```java
// To check whether shadow is present:
boolean hasShadow = actionButton.hasShadow();

// To set the shadow color:
actionButton.setShadowColor(getResources().getColor(R.color.fab_material_grey_500));

// To get the shadow color:
int shadowColor = actionButton.getShadowColor();

// To set the shadow radius:
actionButton.setShadowRadius(5.0f);

// To get the shadow radius:
float shadowRadius = actionButton.getShadowRadius();

// To set the shadow X-axis offset:
actionButton.setShadowXOffset(3.5f);

// To get the shadow X-axis offset:
float shadowXOffset = actionButton.getShadowXOffset();

// To set the shadow Y-axis offset:
actionButton.setShadowYOffset(3.0f);

// To get the shadow Y-axis offset:
float shadowYOffset = actionButton.getShadowYOffset();

// To remove shadow:
actionButton.removeShadow();
```

> Shadow radius and offset must be specified in density-independent pixels.
> For *API 21 Lollipop* and higher **elevation** can be enabled. In this case the default shadow becomes disabled and configuration of any of its parameters will be ignored.

#### Image

**Action Button** can have an image centered inside. By default the image is **absent**. 
Any image can be used for adding. The Library has **fab_plus_icon** drawable, which can also be used. 
When an image is set its width and height are changed to the default values, which both are set to
**24.0dp** (according to material design guidelines). However this size is also adjustable.
 
To work with image use:

```java
// To check whether image is present:
boolean hasImage = actionButton.hasImage();

// To set an image (either bitmap, drawable or resource id):
actionButton.setImageBitmap(bitmap);
actionButton.setImageDrawable(getResource.getDrawable(R.drawable.fab_plus_icon));
actionButton.setImageResource(R.drawable.fab_plus_icon);

// To get an image:
Drawable image = actionButton.getImage();

// To set the image size (which is by default 24.0dp):
actionButton.setImageSize(30.0dp);

// To get the image size:
float imageSize = actionButton.getImageSize();

// To remove the image:
actionButton.removeImage();
```

> Image size must be specified in density-independent pixels. Changing the image size breaks the rules of *Material Design Guidelines*

#### Stroke

Stroke can be customized in two ways: *width* and the *color*. Stroke is **disabled** by default, however it has the default values:

* **strokeColor** = **Color.BLACK**
* **strokeWidth** = **0.0f** (no stroke)

To work with stroke use:

```java
// To check whether stroke is enabled:
boolean hasStroke = actionButton.hasStroke();

// To set stroke color:
actionButton.setStrokeColor(getResources().getColor(R.color.fab_material_blue_grey_500));

// To get stroke color:
int strokeColor = actionButton.getStrokeColor();

// To set stroke width:
actionButton.setStrokeWidth(1.5f);

// To get stroke width:
float strokeWidth = actionButton.getStrokeWidth();

// To remove the stroke:
actionButton.removeStroke();
```

> Stroke width must be specified in density-independent pixels

#### Animations

**Action Button** supports 2 animation types: animation, which is played while *showing*
the button and animation, which is played while *hiding* the button. By default neither show animation,
nor hide animation **are set**.

To work with animations use:

```java
// To set show animation:
actionButton.setShowAnimation(getResources().getAnimation(R.anim.fab_fade_in));
actionButton.setShowAnimation(ActionButton.Animations.FADE_IN);

// To get show animation:
Animation animation = actionButton.getShowAnimation();

// To remove show animation:
actionButton.removeShowAnimation();

// To set hide animation:
actionButton.setHideAnimation(getResources().getAnimation(R.anim.fab_fade_out));
actionButton.setHideAnimation(ActionButton.Animations.FADE_OUT);

// To get hide animation:
Animation animation = actionButton.getHideAnimation();

// To remove hide animation:
actionButton.removeHideAnimation();
```

#### XML full configuration example

Firstly add the namespace:
```xml
xmlns:fab="http://schemas.android.com/apk/res-auto"
```

Then refer the added namespace to configure **Action Button** parameters
```xml
<com.software.shell.fab.ActionButton 
            android:id="@+id/action_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginRight="@dimen/fab_margin"
            android:layout_marginBottom="@dimen/fab_margin"
            fab:type="DEFAULT"
            fab:button_color="@color/fab_material_lime_500"
            fab:button_colorPressed="@color/fab_material_lime_900"
            fab:image="@drawable/fab_plus_icon"
            fab:image_size="24dp"
            fab:shadow_color="#757575"
            fab:shadow_radius="1.0dp"
            fab:shadow_xOffset="0.5dp"
            fab:shadow_yOffset="1.0dp"
            fab:stroke_color="@color/fab_material_blue_grey_500"
            fab:stroke_width="1.0dp"
            fab:show_animation="@anim/fab_roll_from_down"
            fab:hide_animation="@anim/fab_roll_to_down"
            />
```

> The above example contains all of the configuration parameters for **Action Button**, so there is no need to configure all of them, because they all have default values

## License

```
  Copyright 2015 Shell Software Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```

## Shell Software Inc. links

* [Google+](https://plus.google.com/112119444427380215269)
* [Twitter Page](https://twitter.com/shell_software)
