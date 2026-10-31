package com.example.ev2_master_martini.ui.components

import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

/**
 * @param videoUrl URL del video técnico a reproducir.
 * @param modifier Modifier aplicado al contenedor del reproductor.
 */
@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Indica si el reproductor ya está listo (para mostrar/ocultar el spinner).
    var isReady by remember {
        mutableStateOf(false)
    }

    // El ExoPlayer se crea una sola vez por composición.
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            // Reproducción en bucle: refuerza la experiencia de "video técnico"
            // sin exigir interacción adicional del usuario.
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    isReady = playbackState == Player.STATE_READY
                }
            })
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    useController = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    this.player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Indicador de carga solo mientras el video no está listo.
        if (!isReady) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Indicador de estado del reproductor mostrado cuando el video no puede
 * cargarse (por ejemplo, sin conexión). Se usa en ContentDetailScreen.
 */
@Composable
fun VideoPlayerErrorHint() {
    Text(
        text = "No se pudo cargar el video. Revisa tu conexión e inténtalo nuevamente.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error
    )
}