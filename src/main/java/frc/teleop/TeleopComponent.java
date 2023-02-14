package frc.teleop;

public interface TeleopComponent {

    /**
     * Create an abstract method that initializes everything in the Teleop Component
     */
    public abstract void firstCycle();

    /**
     * Runs the Teleop Component (does everything)
     */
    public abstract void run();

    /**
     * Disables all systems involved
     */
    public abstract void disable();

}
