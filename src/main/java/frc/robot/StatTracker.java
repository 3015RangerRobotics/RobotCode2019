package frc.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	private static volatile boolean hasChange = false;

	public static void init() {
		new Thread(new StatTracker()).start();
	}

	public static void addDriveDistance(double left, double right) {
		driveDistanceLeft += Math.abs(left);
		driveDistanceRight += Math.abs(right);
		hasChange = true;
	}

	public static void addElevatorDistance(double distance) {
		elevatorDistance += Math.abs(distance);
		hasChange = true;
	}

	public static void addCargoHandled() {
		cargoHandled++;
		hasChange = true;
	}

	public static void addHatchExtension() {
		hatchExtensions++;
		hasChange = true;
	}

	public static void addClimbDistance(double distance) {
		climbDistance += distance;
		hasChange = true;
	}

	@Override
	public void run() {
		File file = new File("/home/lvuser/stats.json");
		String contents = "";
		try (Scanner s = new Scanner(file)) {
			while (s.hasNextLine()) {
				contents += s.nextLine() + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject json = new JSONObject(contents);
		driveDistanceLeft = json.getDouble("driveDistanceLeft");
		driveDistanceRight = json.getDouble("driveDistanceRight");
		elevatorDistance = json.getDouble("elevatorDistance");
		cargoHandled = json.getInt("cargoHandled");
		hatchExtensions = json.getInt("hatchExtensions");
		climbDistance = json.getDouble("climbDistance");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			while (true) {
				Timer.delay(1.0);
				if (hasChange) {
					json.put("driveDistanceLeft", driveDistanceLeft);
					json.put("driveDistanceRight", driveDistanceRight);
					json.put("elevatorDistance", elevatorDistance);
					json.put("cargoHandled", cargoHandled);
					json.put("hatchExtensions", hatchExtensions);
					json.put("climbDistance", climbDistance);
					writer.write(json.toString());
					writer.flush();
					hasChange = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
