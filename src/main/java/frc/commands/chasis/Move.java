package frc.commands.chasis;

import frc.commands.Command;
import frc.subsystems.Drivetrain;

public class Move extends Drivetrain implements Command {
    private static Move instance;

    private static boolean isFinished;

    public static Move getInstance() {
        if (instance == null) {
            instance = new Move();
        }
        return instance;
    }

    @Override
    public void calculate() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        return Move.isFinished;
    }

    @Override
    public void end() {
        // TODO Auto-generated method stub

    }

}
