package frc.robot.commands;

public class DriveForTime extends CommandBase {
  private double speed;
  private double time;

  public DriveForTime(double speed, double time) {
    requires(drive);
    this.speed = speed;
    this.time = time;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.setTimeout(time);
    drive.setMotorOutputs(-speed, speed);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    drive.setMotorOutputs(-speed, speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    drive.setMotorOutputs(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
