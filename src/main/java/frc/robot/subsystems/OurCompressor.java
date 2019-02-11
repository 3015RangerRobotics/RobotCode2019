/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.OurCompressorAuto;

/**
 * Add your docs here.
 */
public class OurCompressor extends Subsystem {
  private Compressor compressor;

  public OurCompressor() {
	  compressor = new Compressor();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new OurCompressorAuto());
  }

  public void startCompressor() {
	  compressor.start();
  }
  
  public void stopCompressor() {
	  compressor.stop();
  }
}
