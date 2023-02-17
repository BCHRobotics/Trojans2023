package frc.teleop;

import java.util.ArrayList;

public class TeleopControl {

	public ArrayList<TeleopComponent> components = new ArrayList<>();
	private static TeleopControl instance;

	public static TeleopControl getInstance() {
		if (instance == null) {
			instance = new TeleopControl();
		}
		return instance;
	}

	private TeleopControl() {
		this.components.add(TeleopDriver.getInstance());
		this.components.add(TeleopOperator.getInstance());
	}

	public void initialize() {
		this.components.forEach(TeleopComponent::firstCycle);
	}

	public void runCycle() {
		this.components.forEach(TeleopComponent::run);
	}

	public void disable() {
		this.components.forEach(TeleopComponent::disable);
	}

}
