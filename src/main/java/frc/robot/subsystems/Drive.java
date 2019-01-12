package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Drive extends Subsystem {
  private VictorSP rightMotors;
  private VictorSP leftMotors;

  private Encoder rightEncoder;
  private Encoder leftEncoder;

  public Drive(){
    this.rightMotors = new VictorSP(RobotMap.rightDriveMotors);
    this.leftMotors = new VictorSP(RobotMap.leftDriveMotors);
    this.rightEncoder = new Encoder(RobotMap.rightDriveEncoder1, RobotMap.rightDriveEncoder2);
    this.leftEncoder = new Encoder(RobotMap.leftDriveEncoder1, RobotMap.leftDriveEncoder2);
  }

  @Override
  public void initDefaultCommand() {
    
  }

  public void setMotorOutputs(double leftMotor, double rightMotor){
    this.leftMotors.set(leftMotor);
    this.rightMotors.set(rightMotor);
  }
}
