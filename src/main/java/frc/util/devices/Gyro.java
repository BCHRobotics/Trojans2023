package frc.util.devices;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

public class Gyro extends AHRS {
    
    private double prevAngle;
    private double angle;

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

    @Override
    public double getAngle() {
        return 90 - (angle + this.getFusedHeading());
    }

    public void update() {
        double diff = this.getFusedHeading() - this.prevAngle;
        if (diff > 180) {
            this.angle -= 360;
        } else if (diff < -180) {
            this.angle += 360;
        }
        this.prevAngle += diff;
    }

    @Override
    public void reset() {
        this.angle = 90 - this.getFusedHeading();
        this.prevAngle = this.getFusedHeading();
    }
}