package frc.peripherals.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Constants.CHASSIS;
import frc.robot.Constants.ROBOT;

public class Gyro extends AHRS {

    private static Gyro instance;

    public static Gyro getInstance() {
        if (instance == null)
            instance = new Gyro(CHASSIS.GYRO_PORT);
        return instance;
    }

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
    public float getPitch() {
        if (!ROBOT.GYRO_ENABLED)
            return 0;
        // Inverts and Rounds pitch angle to 1 decimal
        return (float) Math.round((super.getPitch() * (CHASSIS.GYRO_OUTPUT_INVERTED ? -1 : 1) * 1)) / 1;
    }

}