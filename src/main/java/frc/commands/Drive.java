package frc.commands;

import frc.subsystems.Drivetrain;

public class Drive extends Drivetrain implements Command {
    private static Drive instance;

    private static boolean isFinished;

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }
        return instance;
    }

    @Override
    public void firstCycle() {
        super.firstCycle();
        Drive.isFinished = false;
    }

    @Override
    public void calculate() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        return Drive.isFinished;
    }

    @Override
    public void end() {
        // TODO Auto-generated method stub

    }

}
