package frc.peripherals.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Constants.Chassis;
import frc.robot.Constants.Features;

public class NewGyro extends AHRS {

    public NewGyro(SerialPort.Port port) {
        super(port);
        this.reset();
    }

    public NewGyro(SPI.Port port) {
        super(port);
        this.reset();
    }

    public NewGyro() {
        super(SPI.Port.kMXP);
        this.reset();
    }

    /**
     * Returns the total accumulated pitch angle (Y Axis, in degrees) reported by
     * the sensor.
     */
    @Override
    public float getPitch() {
        if (!Features.GYRO_ENABLED)
            return 0;
        // Inverts and Rounds pitch angle to 1 decimal
        return (float) Math.round((super.getPitch() * (Chassis.GYRO_OUTPUT_INVERTED ? -1 : 1) * 10) / 10);
    }

}