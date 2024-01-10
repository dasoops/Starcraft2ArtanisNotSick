import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.dasoops.common.App
import com.dasoops.common.AppWrap
import com.dasoops.common.resources.R
import com.dasoops.common.resources.image
import com.dasoops.common.resources.localization.str


fun main() = application {
    AppWrap {
        Window(
            onCloseRequest = ::exitApplication,
            icon = R.image.icon,
            title = R.str.title,
            resizable = true,
            state = rememberWindowState(
                placement = WindowPlacement.Floating,
                position = WindowPosition.Aligned(Alignment.Center),
                size = DpSize(1024.dp, 768.dp),
            )
        ) {
            App()
        }
    }
}
