package frc.robot;

public class DriveHelper {
	private static final double kDeadband = 0.02;

	/**
	 * Tank drive helper
	 * 
	 * @param left  The signal for the left motors
	 * @param right The signal to the right motors
	 * @return Outputs for left and right motors
	 */
	public static DriveSignal tankDrive(double left, double right) {
		return new DriveSignal(left, right);
	}

	/**
	 * Arcade drive
	 * 
	 * @param moveValue     Forward/Reverse throttle
	 * @param rotateValue   The rate of rotation
	 * @param squaredInputs Square inputs to increase fine control
	 * @return The left and right motor outputs
	 */
	public static DriveSignal arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		// local variables to hold the computed PWM values for the motors
		double leftMotorSpeed;
		double rightMotorSpeed;
		moveValue = handleDeadzone(moveValue, kDeadband);
		rotateValue = -handleDeadzone(rotateValue, kDeadband);
		if (squaredInputs) {
			// square the inputs (while preserving the sign) to increase fine control
			// while permitting full power
			if (moveValue >= 0.0) {
				moveValue = moveValue * moveValue;
			} else {
				moveValue = -(moveValue * moveValue);
			}
			if (rotateValue >= 0.0) {
				rotateValue = rotateValue * rotateValue;
			} else {
				rotateValue = -(rotateValue * rotateValue);
			}
		}

		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			} else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}
		return new DriveSignal(leftMotorSpeed, rightMotorSpeed);
	}

	/**
	 * Handles a deadzone
	 * 
	 * @param value    The value to handle
	 * @param deadzone The deadzone
	 * @return The handled value
	 */
	protected static double handleDeadzone(double value, double deadzone) {
		return (Math.abs(value) > Math.abs(deadzone)) ? limit(value, 1.0) : 0.0;
	}

	/**
	 * Limits a number between a given range
	 * 
	 * @param value The value to limit
	 * @param max   The absolute value of the maximum value
	 * @return The limited value
	 */
	protected static double limit(double value, double max) {
		if (value > max) {
			return max;
		}
		if (value < -max) {
			return -max;
		}
		return value;
	}
}