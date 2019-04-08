package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.buttons.Button;

public class ConditionalButton extends Button{
	Button sourceButton;
	BooleanSupplier conditionFunction;

	public ConditionalButton(Button sourceButton, BooleanSupplier conditionFunction){
		this.sourceButton = sourceButton;
		this.conditionFunction = conditionFunction;
	}

	@Override
	public boolean get() {
		return sourceButton.get() && conditionFunction.getAsBoolean();
	}
}
