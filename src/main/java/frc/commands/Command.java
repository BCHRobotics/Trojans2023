
package frc.commands;

public interface Command {

    // Called repeatedly when this Command is scheduled to run
    public abstract void calculate();

    // Returns true when command is over
    public abstract boolean isFinished();

    // Called once after isFinished returns true
    public abstract void end();

}