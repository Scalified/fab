# Floating Action Button library for Android

## Description

This Library contains implementation of the [Floating Action Button](http://www.google.com/design/spec/components/buttons.html#buttons-floating-action-button) for Android.

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

Floating action buttons come in two sizes

* **DEFAULT** (should be used in most cases)
* **MINI** (should only be used to create visual continuity with other elements on the screen)

![Default](https://github.com/shell-software/fab/blob/master/demo/button_type_default.png) 
![Mini](https://github.com/shell-software/fab/blob/master/demo/button_type_mini.png)

## Color palette

The Library contains all of the **500** and **900** colors of the [**Material Color Palette**](http://www.google.com/design/spec/style/color.html#color-color-palette).

**500** are best used for the *normal* button state while **900** ones for the *pressed* state
//TODO add screenshots

## Shadow

Shadow enabled by default with default parameters, which are suitable in most cases. However, shadow can be modified in three ways: radius, X- or Y-axis offset and color

//FIXME change screenshots

![Shadow Radius](https://github.com/shell-software/fab/blob/master/demo/shadow_radius.png)
![Shadow Offset](https://github.com/shell-software/fab/blob/master/demo/shadow_offset.png)

## Stroke

Stroke disabled by default.
Stroke can be modified in two ways: width and color

//FIXME change screenshots

![Stroke Thin](https://github.com/shell-software/fab/blob/master/demo/stroke_thin.png)
![Stroke Thick](https://github.com/shell-software/fab/blob/master/demo/stroke_thick.png)

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

### Declare in XML

```android

```

### Create an object

```android
FloatingActionButton actionButton = new FloatingActionButton(this);
```
