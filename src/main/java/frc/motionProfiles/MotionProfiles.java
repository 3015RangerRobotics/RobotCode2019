/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.motionProfiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MotionProfiles {
	/**
	 * Load a pre-generated motion profile from a text file
	 * 
	 * @param profileName The name of the file to load
	 * @return The motion profile contained in the file
	 */
	public static double[][] loadProfile(String profileName) {
		double[][] profile = new double[][] {};
		try {
			ArrayList<double[]> points = new ArrayList<double[]>();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(MotionProfiles.class.getResourceAsStream(profileName + ".txt")));

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] pointString = line.split(",");
				double[] point = new double[3];
				for (int i = 0; i < 3; i++) {
					point[i] = Double.parseDouble(pointString[i]);
				}
				points.add(point);
			}
			profile = new double[points.size()][3];
			for (int i = 0; i < points.size(); i++) {
				profile[i][0] = points.get(i)[0];
				profile[i][1] = points.get(i)[1];
				profile[i][2] = points.get(i)[2];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return profile;
	}
}