package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
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
	Button driverStartSelect = new DoubleButton(driverSEL7, driverSTART8);

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
	Button coDriverStartSelect = new DoubleButton(coDriverSEL7, coDriverSTART8);

	public OI() {
		// driverRB6.whenPressed(new AutoCargoSide());
		// driverA1.whenPressed(new HatchGrabberExtend());
		// driverB2.whenPressed(new HatchGrabberRetract());
		// driverX3.whenPressed(new HatchArmExtend());
		// driverY4.whenPressed(new HatchArmRetract());

		// driverLB5.whileHeld(new BallMechDown());
		// driverRB6.whileHeld(new BallMechUp());
		// driverA1.whenPressed(new ElevatorToBottom());
		// driverB2.whenPressed(new ElevatorToBallMiddle());
		// driverY4.whenPressed(new ElevatorToBallTop());
		// driverStartSelect.whenPressed(new CancelCommand());

		// // driverRB6.whenPressed(new AutoCenterCargo());
		// driverRB6.whenPressed(new DriveMotionProfile("traction_config"));
		// driverLB5.whenPressed(new DriveForTime(0.3, 5));
		// // driverRB6.whenPressed(new AutoRocketNear());
		// // driverRB6.whenPressed(new AutoCargoSide());
		// // // driverRB6.whenPressed(new AutoCargoSideSegmented());
		// driverLB5.whenPressed(new DriveTurnToAngleWithEncoders(90, 14, 10));
		// driverRB6.whenPressed(new DriveTurnToAngleWithEncoders(90, 14, 10));
		// driverRB6.whenPressed(new DriveTurnToAngle(90));
		// driverA1.whenPressed(new HatchGrabberExtend());
		// driverB2.whenPressed(new HatchGrabberRetract());
		// driverX3.whenPressed(new HatchArmExtend());
		// driverY4.whenPressed(new HatchArmRetract());

		driverA1.whileHeld(new BallMechControlDown());
		driverB2.whileHeld(new BallMechControlUp());
		driverRTrigger.whenPressed(new HatchGrabberToggle());
		driverLTrigger.whenPressed(new HatchArmToggle());
		driverStartSelect.whenPressed(new CancelCommand());

		driverLB5.whenPressed(new DriveTurnMotionProfile(90));

		coDriverA1.whenPressed(new ElevatorToBottom());
		coDriverB2.whenPressed(new ElevatorToBallMiddle());
		coDriverY4.whenPressed(new ElevatorToBallTop());
		coDriverLB5.whenPressed(new ElevatorToAllianceWall());
		coDriverRTrigger.whenPressed(new HatchGrabberToggle());
		coDriverDLeft.whenPressed(new ClimberPrepareHigh());
		coDriverDUp.whenPressed(new ClimberLevel3());
		coDriverDDown.whenPressed(new ClimberLevel2());
		coDriverDRight.whenPressed(new ClimberCenterPrepareLow());
		coDriverStartSelect.whenPressed(new CancelCommand());
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

	public double getDriverRightStickY() {
		if (Math.abs(driver.getY(Hand.kRight)) > 0.05) {
			return -driver.getY(Hand.kRight);
		} else {
			return 0;
		}
	}

	public double getDriverRightStickX() {
		if (Math.abs(driver.getX(Hand.kRight)) > 0.05) {
			return driver.getX(Hand.kRight);
		} else {
			return 0;
		}
	}

	public void coDriverRumble(double d) {
		coDriver.setRumble(RumbleType.kRightRumble, d);
		coDriver.setRumble(RumbleType.kLeftRumble, d);
	}

	public void driverRumble(double d) {
		driver.setRumble(RumbleType.kRightRumble, d);
		driver.setRumble(RumbleType.kLeftRumble, d);
	}

	public double getCoDriverSumTriggers() {
		return coDriver.getTriggerAxis(Hand.kRight) - coDriver.getTriggerAxis(Hand.kLeft);
	}

	public boolean isDriverRightStickPressed() {
		return driverRS10.get();
	}

	public boolean isDriverUpPressed() {
		return driverDUp.get();
	}
}
