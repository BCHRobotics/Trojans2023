package frc.auto;

import java.util.ArrayList;

public class AutoControl {
    
    public ArrayList<AutoComponent> components = new ArrayList<>();
    private static AutoControl instance;

    public static AutoControl getInstance() {
        if (instance == null) {
            instance = new AutoControl();
        }
        return instance;
    }

    private AutoControl() {
        this.components.add(AutoOperate.getInstance());
		AutoSelecter.getInstance();
    }

    public void runCycle() {
		this.components.forEach(AutoComponent::run);
	}

	public void disable() {
		this.components.forEach(AutoComponent::disable);
	}

	public void initialize() {
		this.components.forEach(AutoComponent::firstCycle);
	}

}
