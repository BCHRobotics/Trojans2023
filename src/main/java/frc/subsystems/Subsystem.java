package frc.subsystems;

public abstract class Subsystem {

	/**
	 * Create an abstract method that initializes everything in the subsystem
	 */
	public abstract void init();

	/**
	 * Runs the subsytem (does everything)
	 */
	public abstract void run();

	/**
	 * Disables all systems involved
	 */
	public abstract void disable();

}
