/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private TalonSRX centerJackTalonSRX; 
  private TalonSRX leftJackTalonSRX; 
  private TalonSRX rightJackTalonSRX; 
  private VictorSP centerWheelsVictorSP;
  private AnalogInput centerWheelsAnalogInput;

  public final double pulsesPerInch = 200;
  public final double centerUpPos = 24;
  public final double centerDownPos = 20;
  public final double backBasePos = 0; 
  public final double backDownPos = 20;
  

  public Climber() {
	  this.centerJackTalonSRX = new TalonSRX(RobotMap.climberCenterJackTalonSRX); 
	  this.leftJackTalonSRX = new TalonSRX(RobotMap.climberLeftJackTalonSRX);
	  this.rightJackTalonSRX = new TalonSRX(RobotMap.climberRightJackTalonSRX);
	  this.centerWheelsVictorSP = new VictorSP(RobotMap.climberCenterWheelsVictorSP);
	  this.centerWheelsAnalogInput = new AnalogInput(RobotMap.climberCenterWheelsAnalogInput);

	  centerJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
	  leftJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
	  rightJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);	  
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setCenterPosition(double position){
	  centerJackTalonSRX.set(ControlMode.Position, position);
  }

  public void setBackPosition(double position) {
	  leftJackTalonSRX.set(ControlMode.Position, position);
	  rightJackTalonSRX.set(ControlMode.Position, position);
  }

}
