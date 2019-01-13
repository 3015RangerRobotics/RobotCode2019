/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class DriveHelper {

private static final double kDeadband = 0;
private static final double kTurnSensitivity = 0;

private static double quickStopAccumulator = 0; 

public static DriveSignal tankDrive(double left, double right)
{
   return new DriveSignal(left, right);
}

}
