package frc.util.devices;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Constants;

public class Gyro extends AHRS {

    public Gyro(SerialPort.Port port) {
        super(port);
        this.reset();
    }

    public Gyro(SPI.Port port) {
        super(port);
        this.reset();
    }

    public Gyro() {
        super(SPI.Port.kMXP);
        this.reset();
    }

    /**
     * Returns the total accumulated pitch angle (Y Axis, in degrees) reported by
     * the sensor.
     */
    @Override
    public double getAngle() {
        if (!Constants.GYRO_ENABLED)
            return 0;
        // Inverts and Rounds pitch angle to 1 decimal
        return (double) Math.round((this.getPitch() * (Constants.GYRO_OUTPUT_INVERTED ? -1 : 1) * 10) / 10);
    }

    public void resetGyroPosition() {
        if (!Constants.GYRO_ENABLED)
            return;
        this.calibrate();
    }
}