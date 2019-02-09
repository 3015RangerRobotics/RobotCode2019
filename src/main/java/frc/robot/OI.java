package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
import frc.motionProfiles.MotionProfiles;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class OI {
	// Buttons for driver
	XboxController driver = new XboxController(0);
	Button driverA1 = new JoystickButton(driver, 1);
	Button driverB2 = new JoystickButton(driver, 2);
	Button driverX3 = new JoystickButton(driver, 3);
	Button driverY4 = new JoystickButton(driver, 4);
	Button driverLB5 = new JoystickButton(driver, 5);
	Button driverRB6 = new JoystickButton(driver, 6);
	Button driverSEL7 = new JoystickButton(driver, 7);
	Button driverSTART8 = new JoystickButton(driver, 8);
	Button driverLS9 = new JoystickButton(driver, 9);
	Button driverRS10 = new JoystickButton(driver, 10);
	Button driverDLeft = new DPadButton(driver, DPadButton.Value.kDPadLeft);
	Button driverDUp = new DPadButton(driver, DPadButton.Value.kDPadUp);
	Button driverDDown = new DPadButton(driver, DPadButton.Value.kDPadDown);
	Button driverDRight = new DPadButton(driver, DPadButton.Value.kDPadRight);
	Button driverLTrigger = new TriggerButton(driver, Hand.kLeft);
	Button driverRTrigger = new TriggerButton(driver, Hand.kRight);

	// Buttons for coDriver
	XboxController coDriver = new XboxController(1);
	Button coDriverA1 = new JoystickButton(coDriver, 1);
	Button coDriverB2 = new JoystickButton(coDriver, 2);
	Button coDriverX3 = new JoystickButton(coDriver, 3);
	Button coDriverY4 = new JoystickButton(coDriver, 4);
	Button coDriverLB5 = new JoystickButton(coDriver, 5);
	Button coDriverRB6 = new JoystickButton(coDriver, 6);
	Button coDriverSEL7 = new JoystickButton(coDriver, 7);
	Button coDriverSTART8 = new JoystickButton(coDriver, 8);
	Button coDriverLS9 = new JoystickButton(coDriver, 9);
	Button coDriverRS10 = new JoystickButton(coDriver, 10);
	Button coDriverDLeft = new DPadButton(coDriver, DPadButton.Value.kDPadLeft);
	Button coDriverDUp = new DPadButton(coDriver, DPadButton.Value.kDPadUp);
	Button coDriverDDown = new DPadButton(coDriver, DPadButton.Value.kDPadDown);
	Button coDriverDRight = new DPadButton(coDriver, DPadButton.Value.kDPadRight);
	Button coDriverLTrigger = new TriggerButton(coDriver, Hand.kLeft);
	Button coDriverRTrigger = new TriggerButton(coDriver, Hand.kRight);

	public OI() {
		driverB2.whenPressed(new IntakeUp());
		driverX3.whenPressed(new IntakeDown());
	
	}

	public double getDriverLeftStickY() {
		if (Math.abs(driver.getY(Hand.kLeft)) > 0.05) {
			return -driver.getY(Hand.kLeft);
		} else {
			return 0;
		}
	}

	public double getDriverLeftStickX() {
		if (Math.abs(driver.getX(Hand.kLeft)) > 0.05) {
			return driver.getX(Hand.kLeft);
		} else {
			return 0;
		}
	}

	public void coDriverRumble(double d) {
		coDriver.setRumble(RumbleType.kRightRumble, d);
		coDriver.setRumble(RumbleType.kLeftRumble, d);
	}

	public double getCoDriverSumTriggers() {
		return coDriver.getTriggerAxis(Hand.kRight) - coDriver.getTriggerAxis(Hand.kLeft);
	}
}
