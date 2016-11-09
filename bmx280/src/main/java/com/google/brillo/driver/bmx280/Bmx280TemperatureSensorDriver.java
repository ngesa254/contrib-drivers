package com.google.brillo.driver.bmx280;

import android.hardware.userdriver.sensors.TemperatureSensorDriver;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

class Bmx280TemperatureSensorDriver {
    private static final String TAG = "TemperatureSensorDriver";
    // DRIVER parameters
    // documented at https://source.android.com/devices/sensors/hal-interface.html#sensor_t
    private static final String DRIVER_NAME = "BMP280/BME280";
    private static final String DRIVER_VENDOR = "Bosch";
    private static final float DRIVER_MAX_RANGE = Bmx280.MAX_TEMP_C;
    private static final float DRIVER_RESOLUTION = 0.005f;
    private static final float DRIVER_POWER = Bmx280.MAX_POWER_CONSUMPTION_TEMP_UA / 1000.f;
    private static final int DRIVER_MIN_DELAY_US = Math.round(1000000.f/Bmx280.MAX_FREQ_HZ);
    private static final int DRIVER_MAX_DELAY_US = Math.round(1000000.f/Bmx280.MIN_FREQ_HZ);
    private static final int DRIVER_VERSION = 1;
    private static final String DRIVER_REQUIRED_PERMISSION = "";

    static TemperatureSensorDriver build(Bmx280 driver) {
        return new TemperatureSensorDriver(DRIVER_NAME, DRIVER_VENDOR, DRIVER_VERSION,
                DRIVER_MAX_RANGE, DRIVER_RESOLUTION, DRIVER_POWER,
                DRIVER_MIN_DELAY_US, DRIVER_REQUIRED_PERMISSION, DRIVER_MAX_DELAY_US,
                UUID.randomUUID()) {
            @Override
            public float read() {
                try {
                    return driver.readTemperature();
                } catch (IOException | IllegalStateException e) {
                    Log.e(TAG, "error reading bmx280 sensor data: ", e);
                    return Float.NaN;
                }
            }
        };
    }
}