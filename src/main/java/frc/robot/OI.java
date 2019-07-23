package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.motionProfiles.MotionProfiles;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class OI {
	// Buttons for driver
	static XboxController driver = new XboxController(0);
	static Button driverA1 = new JoystickButton(driver, 1);
	static Button driverB2 = new JoystickButton(driver, 2);
	static Button driverX3 = new JoystickButton(driver, 3);
	static Button driverY4 = new JoystickButton(driver, 4);
	static Button driverLB5 = new JoystickButton(driver, 5);
	static Button driverRB6 = new JoystickButton(driver, 6);
	static Button driverSEL7 = new JoystickButton(driver, 7);
	static Button driverSTART8 = new JoystickButton(driver, 8);
	static Button driverLS9 = new JoystickButton(driver, 9);
	static Button driverRS10 = new JoystickButton(driver, 10);
	static Button driverDLeft = new DPadButton(driver, DPadButton.Value.kDPadLeft);
	static Button driverDUp = new DPadButton(driver, DPadButton.Value.kDPadUp);
	static Button driverDDown = new DPadButton(driver, DPadButton.Value.kDPadDown);
	static Button driverDRight = new DPadButton(driver, DPadButton.Value.kDPadRight);
	static Button driverLTrigger = new TriggerButton(driver, Hand.kLeft);
	static Button driverRTrigger = new TriggerButton(driver, Hand.kRight);
	static Button driverStartSelect = new DoubleButton(driverSEL7, driverSTART8);

	// Buttons for coDriver
	static XboxController coDriver = new XboxController(1);
	static Button coDriverA1 = new JoystickButton(coDriver, 1);
	static Button coDriverB2 = new JoystickButton(coDriver, 2);
	static Button coDriverX3 = new JoystickButton(coDriver, 3);
	static Button coDriverY4 = new JoystickButton(coDriver, 4);
	static Button coDriverLB5 = new JoystickButton(coDriver, 5);
	static Button coDriverRB6 = new JoystickButton(coDriver, 6);
	static Button coDriverSEL7 = new JoystickButton(coDriver, 7);
	static Button coDriverSTART8 = new JoystickButton(coDriver, 8);
	static Button coDriverLS9 = new JoystickButton(coDriver, 9);
	static Button coDriverRS10 = new JoystickButton(coDriver, 10);
	static Button coDriverDLeft = new DPadButton(coDriver, DPadButton.Value.kDPadLeft);
	static Button coDriverDUp = new DPadButton(coDriver, DPadButton.Value.kDPadUp);
	static Button coDriverDDown = new DPadButton(coDriver, DPadButton.Value.kDPadDown);
	static Button coDriverDRight = new DPadButton(coDriver, DPadButton.Value.kDPadRight);
	static Button coDriverLTrigger = new TriggerButton(coDriver, Hand.kLeft);
	static Button coDriverRTrigger = new TriggerButton(coDriver, Hand.kRight);
	static Button coDriverStartSelect = new DoubleButton(coDriverSEL7, coDriverSTART8);
	static Button coDriverClimbLevel2 = new ConditionalButton(coDriverDUp, CommandBase.climber::isReadyToClimbLevel2);
	static Button coDriverClimbLevel3 = new ConditionalButton(coDriverDUp, CommandBase.climber::isReadyToClimbLevel3);

	public OI() {
		driverStartSelect.whenPressed(new CancelCommand());
		coDriverStartSelect.whenPressed(new CancelCommand());

		driverA1.whileHeld(new BallMechControlDown());
		driverB2.whileHeld(new BallMechControlUp());
		driverRB6.whileHeld(new DriveVisionAssist());
		driverRTrigger.whenPressed(new HatchGrabberToggle());
		driverLTrigger.whenPressed(new HatchArmToggle());

		// driverY4.whenPressed(new AutoFrontCargo());


		coDriverA1.whenPressed(new ElevatorToBottom());
		coDriverB2.whenPressed(new ElevatorToMiddle());
		coDriverY4.whenPressed(new ElevatorToTop());
		coDriverLB5.whenPressed(new ElevatorToAllianceWall());
		coDriverRB6.whenPressed(new ClimberJackRetract());
		coDriverLS9.whileHeld(new ElevatorManualControl());
		coDriverRTrigger.whenPressed(new HatchGrabberToggle());
		coDriverDLeft.whenPressed(new ClimberPrepareHigh());
		coDriverDRight.whenPressed(new ClimberPrepareLow());
		// coDriverClimbLevel2.whenPressed(new ClimberLevel2());
		// coDriverClimbLevel3.whenPressed(new ClimberLevel3());
		coDriverDUp.whenPressed(new ClimberLevel3());
		coDriverDDown.whenPressed(new ClimberLevel2());

		// coDriverX3.whileHeld(new ClimberManualJack());

		// coDriverA1.whenPressed(new ClimberPrepareHigh());
		// coDriverB2.whenPressed(new ClimberJackUpLow());
		// coDriverRB6.whenPressed(new ClimberJackRetract());
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

	public static double getDriverRightStickY() {
		if (Math.abs(driver.getY(Hand.kRight)) > 0.05) {
			return -driver.getY(Hand.kRight);
		} else {
			return 0;
		}
	}

	public double getCoDriverRightStickY() {
		if (Math.abs(coDriver.getY(Hand.kRight)) > 0.1) {
			return -coDriver.getY(Hand.kRight);
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

	public static boolean isDriverRTriggerPressed() {
		return driverRTrigger.get();
	}

	public static boolean isCancelledPressed() {
		return driverStartSelect.get() || coDriverStartSelect.get();
	}

	public static boolean isDriverXPressed() {
		return driverX3.get();
	}

	public static boolean isDriverDDownPressed() {
		return driverDDown.get();
	}
}
