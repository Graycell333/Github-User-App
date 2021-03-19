
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.gituserlistapp.NetworkStatusCheker


// TODO: Auto-generated Javadoc

@TargetApi(Build.VERSION_CODES.KITKAT)
class Utils {
    init {

        Log.e("Utils:::", "")
    }

    companion object {

        /**
         * Checking internet state.
         *
         * @param context the context
         * @return true if internet is enabled else false
         */
        fun networkStatus(context: Context): Boolean {
            return NetworkStatusCheker.checkNetConnection(context)
        }
    }


}