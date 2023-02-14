package frc.sequences;

public interface Sequence {
    /**
     * Called just before this Sequence runs the first time
     */
    public abstract void initialize();

    /**
     * Called repeatedly when this Sequence is scheduled to run
     */
    public abstract void calculate();

    /**
     * Called repeatedly to execute "runCycle" Sequences
     */
    public abstract void execute();

    /**
     * Called once after isFinished returns true
     */
    public abstract void end();

    /**
     * Returns true when Sequence is over
     * 
     * @return Sequence is Finished
     */
    public abstract boolean isFinished();

    /**
     * Disables all related subsystems
     */
    public abstract void disable();
}
