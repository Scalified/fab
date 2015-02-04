# Floating Action Button library for Android

## Description

This Library contains implementation of the [**Floating Action Button**](http://www.google.com/design/spec/components/buttons.html#buttons-floating-action-button) for Android.

Floating action buttons are used for a special type of promoted action. They are distinguished by a circled icon floating above the UI and have special motion behaviors related to morphing, launching, and the transferring anchor point

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

# Requirements

The Library requires **Android SDK version 9 (Gingerbread) and higher**

# Gradle Dependency

//TODO

# Demo

Watch the [**Full Demo Video**](https://www.youtube.com/watch?v=skSApXvi4xM) on YouTube

## Floating Action Button types

Floating action buttons come in two sizes: the **DEFAULT**, which should be used in most cases, and the **MINI**, which should only be used to create visual continuity with other elements on the screen

DEFAULT | MINI
:-:|:-:
![Default](https://github.com/shell-software/fab/blob/master/demo/button_type_default.png) | ![Mini](https://github.com/shell-software/fab/blob/master/demo/button_type_mini.png)

## Color palette

The Library contains all of the colors **500** and **900** of the [**Material Color Palette**](http://www.google.com/design/spec/style/color.html#color-color-palette).

Colors **500** are used for the *normal* button state while **900** ones for the *pressed* state

Here are some colors examples:

Green 500 | Amber 500 | Blue Grey 500
:-:|:-:|:-:
![Green 500](https://github.com/shell-software/fab/blob/master/demo/color_green.png) | ![Amber Color](https://github.com/shell-software/fab/blob/master/demo/color_amber.png) | ![Blue Grey](https://github.com/shell-software/fab/blob/master/demo/color_blue_grey.png)

## Shadow

Shadow enabled by default and has starndard settings. These settings are suitable in most cases. However, shadow can be modified in three ways: radius, X- or Y-axis offset and color

Default | Radius | X- and Y- axis offset
:-:|:-:|:-:
![Default Shadow](https://github.com/shell-software/fab/blob/master/demo/default_shadow.png) | ![Shadow Radius](https://github.com/shell-software/fab/blob/master/demo/shadow_radius.png) | ![Shadow Offset](https://github.com/shell-software/fab/blob/master/demo/shadow_axis_offset.png)

## Stroke

Stroke disabled by default.
Stroke can be modified in two ways: width and color

Thin | Medium | Thick
:-:|:-:|:-:
![Thin Stroke](https://github.com/shell-software/fab/blob/master/demo/stroke_thin.png) | ![Medium Stroke](https://github.com/shell-software/fab/blob/master/demo/stroke_medium.png) | ![Thick Stroke](https://github.com/shell-software/fab/blob/master/demo/stroke_thick.png)


## Animations

The Library has several predefined animations:

Fade In - Fade Out | Roll From Down - Roll To Down | Jump From Down - Jump To Down
:-----------------:|:-----------------------------:|:----------------------------:
![Fade In Fade Out](https://github.com/shell-software/fab/blob/master/demo/animation_fade_in_fade_out.gif) | ![Roll From Down Roll To Down](https://github.com/shell-software/fab/blob/master/demo/animation_roll_from_down_roll_to_down.gif) | ![Jump From Down - Jump To Down](https://github.com/shell-software/fab/blob/master/demo/animation_jump_from_down_jump_to_down.gif)

Scale In - Scale Out | Roll From Right - Roll To Right | Jump From Right - Jump To Right
:-------------------:|:-------------------------------:|:------------------------------:
![Scale In Scale Out](https://github.com/shell-software/fab/blob/master/demo/animation_scale_in_scale_out.gif) | ![Roll From Right Roll To Right](https://github.com/shell-software/fab/blob/master/demo/animation_roll_from_right_roll_to_right.gif) | ![Jump From Right Jump To Right](https://github.com/shell-software/fab/blob/master/demo/animation_jump_from_right_jump_to_right.gif)

# Usage

## Creation

### Declare inside XML resource

For instance, using [**RelativeLayout**](http://developer.android.com/reference/android/widget/RelativeLayout.html):

```xml
<RelativeLayout 
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:fab="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
        <com.software.shell.fab.FloatingActionButton 
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

### Create an object

```java
import com.software.shell.fab.FloatingActionButton;

// ...

Context context = getContext();
FloatingActionButton actionButton = new FloatingActionButton(context);
```

## Customization

### XML configuration example

Firstly add the namespace:
```xml
xmlns:fab="http://schemas.android.com/apk/res-auto"
```

Then refer the added namespace to configure **Floating Action Button** parameters
```xml
<com.software.shell.fab.FloatingActionButton 
            android:id="@+id/action_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginRight="@dimen/fab_margin"
            android:layout_marginBottom="@dimen/fab_margin"
            fab:type="normal"
            fab:button_color="@color/material_green_500"
            fab:button_colorPressed="@color/material_green_900"
            fab:image="@drawable/fab_plus_icon"
            fab:image_size="24dp"
            fab:shadow_color="#757575"
            fab:shadow_radius="1.0dp"
            fab:shadow_xOffset="0.5dp"
            fab:shadow_yOffset="1.0dp"
            fab:stroke_color="@color/material_blue_grey_500"
            fab:stroke_width="1.0dp"
            fab:animation_onShow="@anim/fab_roll_from_down"
            fab:animation_onHide="@anim/fab_roll_to_down"
            />
```

#### Programatically

```java
//Button type
actionButton.setType(FloatingActionButton.Type.MINI);

//Button colors
actionButton.setButtonColor(getResources().getColor(R.color.material_green_500));
actionButton.setButtonColorPressed(getResources().getColor(R.color.material_green_900));

//Image
actionButton.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));
actionButton.setImageSize(24.0f);

//Shadow
actionButton.setShadowColor(Color.parseColor("#757575"));
actionButton.setShadowRadius(1.0f);
actionButton.setShadowXOffset(0.5f);
actionButton.setShadowYOffset(1.0f);

//Stroke
actionButton.setStrokeColor(getResources().getColor(R.color.material_blue_grey_500));
actionButton.setStrokeWidth(1.0f);

//Animations
actionButton.setAnimationOnShow(FloatingActionButton.Animations.ROLL_FROM_DOWN);
actionButton.setAnimationOnHide(FloatingActionButton.Animations.ROLL_TO_DOWN);
```
