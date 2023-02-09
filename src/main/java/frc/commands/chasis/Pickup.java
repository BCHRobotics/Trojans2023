package frc.commands.chasis;

import frc.commands.Command;
import frc.subsystems.Intake;

public final class Pickup extends Intake implements Command {

    private static Pickup instance;

    private static boolean isFinished;

    public static Pickup getInstance() {
        if (instance == null) {
            instance = new Pickup();
        }
        return instance;
    }

    @Override
    public void firstCycle() {
        super.firstCycle();
        Pickup.isFinished = false;
    }

    @Override
    public void calculate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void end() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        return Pickup.isFinished;
    }

}
