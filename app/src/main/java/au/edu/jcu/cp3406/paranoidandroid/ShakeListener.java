package au.edu.jcu.cp3406.paranoidandroid;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class ShakeListener implements SensorEventListener
{
    private static final int MILLIS_TIMEOUT = 1;
    private static final float ACCEL_THRESHOLD = 1.5f;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastActivationTime;

    public ShakeListener(Context context)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else
        {
            Toast.makeText(context, "Accelerometer is Not Available", Toast.LENGTH_LONG).show();
        }

        lastActivationTime = 0;
    }

    public void onResume()
    {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            long now = System.currentTimeMillis();

            if((now - lastActivationTime) > MILLIS_TIMEOUT)
            {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2))
                        -SensorManager.GRAVITY_EARTH;

                if(acceleration > ACCEL_THRESHOLD)
                {
                    lastActivationTime = now;
                    Log.i("ShakeListener", "We cooking with gas baby");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
