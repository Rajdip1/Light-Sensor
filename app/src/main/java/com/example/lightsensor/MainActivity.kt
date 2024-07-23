package com.example.lightsensor

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lightsensor.ui.theme.LightSensorTheme
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensorManager: SensorManager? = null
    var sensor: Sensor? = null

    lateinit var image: ImageView
    lateinit var backgorund: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.displayImage)
        backgorund = findViewById(R.id.background)

        image.visibility = View.INVISIBLE

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        //most imp function
        try {
            Log.d(TAG, "onSensorChanged: "+ event!!.values[0])
            if(event!!.values[0]<30){
                //light is dim
                image.visibility = View.INVISIBLE
                backgorund.setBackgroundColor(resources.getColor(R.color.black))
            }
            else{
                //show torch if the light intensity is good or high
                image.visibility = View.VISIBLE
                backgorund.setBackgroundColor(resources.getColor(R.color.yellow))
            }
        }catch (e:IOException){
            Log.d(TAG, "onSensorChanged: + ${e.message}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, "onAccuracyChanged: ")
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}

