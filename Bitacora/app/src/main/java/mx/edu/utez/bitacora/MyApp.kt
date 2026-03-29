package mx.edu.utez.bitacora

import android.app.Application
import com.cloudinary.android.MediaManager

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val config = mapOf(
            "cloud_name" to "real_cloud_name",
            "secure" to true
        )
        MediaManager.init(this, config)
    }
}