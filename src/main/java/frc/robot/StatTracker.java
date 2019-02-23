package frc.robot;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.JSONObject;

import edu.wpi.first.wpilibj.Timer;

public class StatTracker implements Runnable {
	private static volatile double driveDistanceLeft = 0.0;
	private static volatile double driveDistanceRight = 0.0;
	private static volatile double elevatorDistance = 0.0;
	private static volatile int cargoHandled = 0;
	private static volatile int hatchExtensions = 0;
	private static volatile double climbDistance = 0.0;

	public static void init() {
		new Thread(new StatTracker()).start();
	}

	public static void addDriveDistance(double left, double right) {
		driveDistanceLeft += Math.abs(left);
		driveDistanceRight += Math.abs(right);
	}

	public static void addElevatorDistance(double distance) {
		elevatorDistance += Math.abs(distance);
	}

	public static void addCargoHandled() {
		cargoHandled++;
	}

	public static void addHatchExtension() {
		hatchExtensions++;
	}

	public static void addClimbDistance(double distance) {
		climbDistance += distance;
	}

	@Override
	public void run() {
		try {
			File file = new File("/home/lvuser/stats.json");
			if (file.exists()) {
				Scanner s = new Scanner(file);
				if (s.hasNextLine()) {
					JSONObject contents = new JSONObject(s.nextLine());
					driveDistanceLeft = contents.getDouble("driveDistanceLeft");
					driveDistanceRight = contents.getDouble("driveDistanceRight");
					elevatorDistance = contents.getDouble("elevatorDistance");
					cargoHandled = contents.getInt("cargoHandled");
					hatchExtensions = contents.getInt("hatchExtensions");
					climbDistance = contents.getDouble("climbDistance");
				}
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (true) {
			Timer.delay(1.0);
			try (PrintWriter writer = new PrintWriter("/home/lvuser/stats.json")) {
				JSONObject json = new JSONObject();
				json.put("driveDistanceLeft", driveDistanceLeft);
				json.put("driveDistanceRight", driveDistanceRight);
				json.put("elevatorDistance", elevatorDistance);
				json.put("cargoHandled", cargoHandled);
				json.put("hatchExtensions", hatchExtensions);
				json.put("climbDistance", climbDistance);
				writer.print(json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
