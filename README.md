We saw weird behaviour on Galaxy S5 (samsung-sm-G900A, Android 4.4.2). This code is to reproduce it.

This sample app has four animations, and all four work as expected on Android 4.4.2 emulator.
However, on our Galaxy S5, first two animation won't work (AnimationListener#onAnimationEnd is called after ~1ms despite we set duration as 1000ms).
It seems andorid.view.animation.Animation and nineoldandorid's ObjectAnimator is working fine.
