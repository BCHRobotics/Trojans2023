package frc.subsystems;

public interface Subsystem {

	// Create an abstract method that initializes everything in the subsystem
	public abstract void firstCycle();

	// runs the subsytem (does everything)
	public abstract void run();

	// disables all systems involved
	public abstract void disable();

}
