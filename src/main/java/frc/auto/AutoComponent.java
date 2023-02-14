package frc.auto;

public abstract class AutoComponent {

    /**
     * Create an abstract method that initializes everything in the Autonomous
     * Component
     */
    public abstract void firstCycle();

    /**
     * Runs the Autonomous Pathway
     */
    public abstract void run();

    /**
     * Disables all systems involved
     */
    public abstract void disable();

}
