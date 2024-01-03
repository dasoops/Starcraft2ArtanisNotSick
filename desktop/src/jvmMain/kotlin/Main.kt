import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.dasoops.common.app
import com.dasoops.common.appWrap
import com.dasoops.common.resources.R
import com.dasoops.common.resources.image
import com.dasoops.common.resources.str


fun main() = application {
    appWrap {
        Window(
            onCloseRequest = ::exitApplication,
            icon = painterResource(R.image.icon),
            title = R.str.title,
            resizable = false,
            state = WindowState(
                placement = WindowPlacement.Floating,
                position = WindowPosition.Aligned(Alignment.Center),
                size = DpSize(800.dp, 600.dp),
            )
        ) {
            app()
        }
    }
}
