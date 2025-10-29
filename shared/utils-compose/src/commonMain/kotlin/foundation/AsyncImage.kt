package foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import careshare.shared.SharedRes
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest

@Composable
fun AsyncImage(
    path: String,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop
) {
    val model = ImageRequest.Builder(LocalPlatformContext.current)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.DISABLED) // TODO
        .data(
            SharedRes.getUri(path)
        ).build()

    AsyncImage(
        model = model,
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = contentDescription
    )
//    SubcomposeAsyncImage(
//        model = ,
//        contentDescription = contentDescription,
//        modifier = modifier,
//        contentScale = contentScale
//    ) {
//        val state by painter.state.collectAsState()
//        this.SubcomposeAsyncImageContent()
//    }

}