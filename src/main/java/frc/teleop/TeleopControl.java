package frc.teleop;

import java.util.ArrayList;

import frc.robot.Constants;

public class TeleopControl {

	public ArrayList<TeleopComponent> components;
	private static TeleopControl instance;

	public static TeleopControl getInstance() {
		if (instance == null) {
			instance = new TeleopControl();
		}
		return instance;
	}

	private TeleopControl() {
		this.components = new ArrayList<>();

		this.components.add(TeleopOperator.getInstance());
		this.components.add(TeleopOperator.getInstance());
	}

	public void initialize() {
		for (TeleopComponent t : this.components) {
			t.firstCycle();
		}
	}

	public void runCycle() {
		for (TeleopComponent t : this.components) {
			t.run();
		}
	}

	public void disable() {
		for (TeleopComponent t : this.components) {
			t.disable();
		}
	}

}
