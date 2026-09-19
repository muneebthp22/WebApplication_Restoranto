package com.example.clockworkriddle.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

object AnimationDefaults {
    const val FAST = 200
    const val MEDIUM = 500
    const val SLOW = 800
    const val VERY_SLOW = 1200
}

@Composable
fun Modifier.rotatingGear(delayMillis: Int = 0): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "gear_rotation")
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                delayMillis = delayMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    return this.graphicsLayer(rotationZ = rotation.value)
}

@Composable
fun Modifier.pulseGlow(): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_glow")
    val alpha = infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = AnimationDefaults.SLOW,
                easing = EaseInOutSine
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    return this.graphicsLayer(alpha = alpha.value)
}

@Composable
fun Modifier.shimmerEffect(): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmer = infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = EaseInOutSine
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )
    return this.graphicsLayer(translationX = shimmer.value * 100)
}

@Composable
fun Modifier.subtleFloat(): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "subtle_float")
    val offset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = EaseInOutSine
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )
    return this.graphicsLayer(translationY = offset.value)
}

object EaseInOutSine : Easing {
    override fun transform(fraction: Float): Float {
        return (-(kotlin.math.cos(kotlin.math.PI * fraction.toDouble()) - 1) / 2).toFloat()
    }
}
