package itemDetails.ui.bottomSheet

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import kotlin.math.abs

fun <T> DraggableAnchors<T>.computeTarget(
    currentOffset: Float,
    velocity: Float,
    positionalThreshold: (totalDistance: Float) -> Float,
    velocityThreshold: () -> Float,
): T {
    val currentAnchors = this
    require(!currentOffset.isNaN()) { "The offset provided to computeTarget must not be NaN." }
    val isMoving = abs(velocity) > 0.0f
    val isMovingForward = isMoving && velocity > 0f
    return if (abs(velocity) >= abs(velocityThreshold())) {
        currentAnchors.closestAnchor(currentOffset, searchUpwards = isMovingForward)!!
    } else {
        val left = currentAnchors.closestAnchor(currentOffset, false)!!
        val leftAnchorPosition = currentAnchors.positionOf(left)
        val right = currentAnchors.closestAnchor(currentOffset, true)!!
        val rightAnchorPosition = currentAnchors.positionOf(right)
        val distance = abs(leftAnchorPosition - rightAnchorPosition)

        val relativeThreshold = abs(positionalThreshold(distance))
        val closestAnchorFromStart =
            if (isMovingForward) leftAnchorPosition else rightAnchorPosition
        val relativePosition = abs(closestAnchorFromStart - currentOffset)

        println("r: $relativePosition   t: $relativeThreshold")

        when (relativePosition >= relativeThreshold) {
            true -> if (isMovingForward) right else left
            false -> if (isMovingForward) left else right
        }
    }
}

fun <T> AnchoredDraggableLayoutInfoProvider(
    state: AnchoredDraggableState<T>,
    positionalThreshold: (totalDistance: Float) -> Float,
    velocityThreshold: () -> Float,
): SnapLayoutInfoProvider =
    object : SnapLayoutInfoProvider {

        // We never decay in AnchoredDraggable's fling
        override fun calculateApproachOffset(velocity: Float, decayOffset: Float) = 0f

        override fun calculateSnapOffset(velocity: Float): Float {
            val currentOffset = state.requireOffset()
            val proposedTargetValue =
                state.anchors.computeTarget(
                    currentOffset = currentOffset,
                    velocity = velocity,
                    positionalThreshold = positionalThreshold,
                    velocityThreshold = velocityThreshold,
                )
            val targetValue = proposedTargetValue
            return state.anchors.positionOf(targetValue) - currentOffset
        }
    }