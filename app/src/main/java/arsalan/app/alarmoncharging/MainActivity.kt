package arsalan.app.alarmoncharging

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.Ringtone
import android.os.BatteryManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.concurrent.thread


open class MainActivity : AppCompatActivity() {
 lateinit var tvLevel:TextView
    lateinit var tvSeekbarValue:TextView
    lateinit var sekbar: SeekBar
    lateinit var ringtone:Ringtone
    //lateinit var ab:S

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLevel=findViewById(R.id.tvLevel)
        tvSeekbarValue=findViewById(R.id.tvSetLevel)
        sekbar = findViewById(R.id.sekbar)

                 seekBar()
                 broadCst()



    }

    private fun seekBar() {
        sekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvSeekbarValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun broadCst() {



                val broadcastReceiverLevel: BroadcastReceiver = object: BroadcastReceiver(){
                    override fun onReceive(context: Context?, intent: Intent) {

                        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                tvLevel.setText("%" + batteryLevel.toString())

val ab = batteryLevel
                if (intent.action == Intent.ACTION_POWER_CONNECTED) {
                    Toast.makeText(context, "Power Connected"+batteryLevel.toString(), Toast.LENGTH_SHORT).show()
                  /*  val i = Intent(context, ExampleService::class.java)
                    val b = Bundle()
                    b.putString("keyvalue", tvSeekbarValue.toString())
                    i.putExtras(b)*/
                    // serviceIntent.putExtra("inputExtra","ali")
                   // Toast.makeText(context, "Power Connected"+tvLevelBattery, Toast.LENGTH_SHORT).show()
                    val serviceIntent = Intent(context, ExampleService::class.java)
                    // serviceIntent.putExtra("inputExtra", tvLevel.toString())
                    ContextCompat.startForegroundService(context!!, serviceIntent)
                    context.sendBroadcast(serviceIntent)
                    Log.d("abc", "service running")


                } else if (intent.action == Intent.ACTION_POWER_DISCONNECTED) {
                    Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show()
                    val serviceIntent = Intent(context, ExampleService::class.java)

                    if (context != null) {
                        context.stopService(serviceIntent)
                        Log.d("lodhi", "service stop........")
                    }

                }

            }
        }
        registerReceiver(broadcastReceiverLevel, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        registerReceiver(broadcastReceiverLevel, IntentFilter(Intent.ACTION_POWER_CONNECTED))
        registerReceiver(broadcastReceiverLevel, IntentFilter(Intent.ACTION_POWER_DISCONNECTED))
    }


}