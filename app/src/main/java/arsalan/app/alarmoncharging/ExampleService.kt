package arsalan.app.alarmoncharging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat


class ExampleService : Service() {
    private val CHANNEL_ID = "ForegroundService Kotlin"


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

         createNotificationChannel()

       // var MainActivity = MainActivity()

       // val mainClassObj = MainActivity()
       // mainClassObj.tvLevel
       // var input = intent?.getStringExtra("inputExtra")
      //  Toast.makeText(this, "batteryLevel : "+MainActivity.tvLevel, Toast.LENGTH_SHORT).show().toString()

        // getting the bundle back from the android
       // val result: String = intent?.getStringExtra("inputExtra").toString()
       // Toast.makeText(this, "batteryLevel : " + result, Toast.LENGTH_SHORT).show().toString()

        //whwen click on notification open mainActivity..
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Alarm Set")
            .setContentText("charger connected")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        //stopSelf();
        return START_NOT_STICKY

    }


    override fun onDestroy() {
        super.onDestroy()

    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}