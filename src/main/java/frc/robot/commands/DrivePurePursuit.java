package frc.robot.commands;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.generate.MPGenerator;
import frc.motionProfiles.generate.PPPoint;
import frc.motionProfiles.generate.Util;
import frc.motionProfiles.generate.Vector2;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DrivePurePursuit extends CommandBase {
	private boolean isFinished = false;
	private ArrayList<PPPoint> points;
	private Vector2 robotPos = new Vector2(0, 0);
	private double lastLookAhead = 0;
	private double prevErrorL = 0;
	private double prevErrorR = 0;
	private double lastVelL = 0;
	private double lastVelR = 0;

	private final double LOOK_AHEAD = 1;
	private final double MAX_CHANGE;
	private double lastVel = 0;

	public DrivePurePursuit(double xDist, double yDist, double endAngle, double maxVel, double maxAcc) {
		this.points = MPGenerator.get2DPP(xDist, yDist, endAngle, maxVel, maxAcc);
		this.MAX_CHANGE = RobotMap.kPeriod * maxAcc;
	}

	public DrivePurePursuit(ArrayList<PPPoint> points, double maxAcc) {
		this.points = points;
		this.MAX_CHANGE = RobotMap.kPeriod * maxAcc;
	}

	@Override
	protected void initialize() {
		robotPos = new Vector2(0, 0);
		lastLookAhead = 0;
		lastVel = 0;
		prevErrorL = 0;
		prevErrorR = 0;
		lastVelL = 0;
		lastVelR = 0;
		drive.resetEncoders();
		Robot.resetIMU();
		SmartDashboard.putBoolean("PathRunning", true);
		isFinished = false;

		new Thread(() -> {
			double lastTime = 0;

			while (!isFinished && DriverStation.getInstance().isEnabled()) {
				if (Timer.getFPGATimestamp() >= lastTime + RobotMap.kPeriod) {
					lastTime = Timer.getFPGATimestamp();
					threadedExecute();
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	protected void threadedExecute(){
		double v = (drive.getLeftVelocity() + drive.getRightVelocity()) / 2;
		double delta = v * RobotMap.kPeriod;
		
		robotPos.x += delta * Math.cos(Math.toRadians(Robot.getYaw()));
		robotPos.y += delta * Math.sin(Math.toRadians(Robot.getYaw()));

		int closestPoint = 0;
		for(int i = 1; i < points.size(); i++){
			if(Util.distanceBetweenPts(robotPos, points.get(i)) <= Util.distanceBetweenPts(robotPos, points.get(closestPoint))){
				closestPoint = i;
			}
		}

		if(closestPoint == points.size() - 1){
			isFinished = true;
			return;
		}

		Vector2 lookAheadPoint = getLookAheadPoint();

		double a = -Math.tan(Math.toRadians(Robot.getYaw()));
		double b = 1;
		double c = Math.tan(Math.toRadians(Robot.getYaw())) * robotPos.x - robotPos.y;
		double x = Math.abs(a * lookAheadPoint.x + b * lookAheadPoint.y + c) / Math.sqrt(a*a + b*b);

		Vector2 B = new Vector2(robotPos.x + Math.cos(Math.toRadians(Robot.getYaw())), robotPos.y + Math.sin(Math.toRadians(Robot.getYaw())));
		double side = Math.signum(Math.sin(Math.toRadians(Robot.getYaw())) * (lookAheadPoint.x - robotPos.x) - Math.cos(Math.toRadians(Robot.getYaw())) * (lookAheadPoint.y - robotPos.y));

		double L2 = LOOK_AHEAD*LOOK_AHEAD;
		double r = L2 / (2 * x);
		double curvature = (1/r) * side;

		PPPoint closest = points.get(closestPoint);
		double targetVel = rateLimiter(closest.velocity);

		double targetVelLeft = targetVel * (2 + (curvature * RobotMap.wheelBaseWidth) / 2);
		double targetPosLeft = drive.getLeftDistance() + (targetVelLeft * RobotMap.kPeriod);
		double targetAccLeft = (targetVelLeft - lastVelL) / RobotMap.kPeriod;

		double targetVelRight = targetVel * (2 - (curvature * RobotMap.wheelBaseWidth) / 2);
		double targetPosRight = drive.getRightDistance() + (targetVelRight * RobotMap.kPeriod);
		double targetAccRight = (targetVelRight - lastVelR) / RobotMap.kPeriod;

		double errorL = targetPosLeft - drive.getLeftDistance();
		double errorDerivL = ((errorL - prevErrorL) / RobotMap.kPeriod) - targetVelLeft;

		double errorR = targetPosRight - drive.getRightDistance();
		double errorDerivR = ((errorR - prevErrorR) / RobotMap.kPeriod) - targetVelRight;

		double kP = drive.kDriveP;
		double kD = drive.kDriveD;
		double kV = drive.kV;
		double kA = drive.kA;

		double pwmL = (kP * errorL) + (kD * errorDerivL) + (kV * targetVelLeft) + (kA * targetAccLeft);
		double pwmR = (kP * errorR) + (kD * errorDerivR) + (kV * targetVelRight) + (kA * targetAccRight);

		SmartDashboard.putNumber("TargetLeft", targetPosLeft);
		SmartDashboard.putNumber("ActualLeft", drive.getLeftDistance());

		SmartDashboard.putNumber("TargetRight", targetPosRight);
		SmartDashboard.putNumber("ActualRight", drive.getRightDistance());

		prevErrorL = errorL;
		prevErrorR = errorR;
		lastVel = targetVel;
		lastVelL = targetVelLeft;
		lastVelR = targetVelRight;

		drive.setMotorOutputs(ControlMode.PercentOutput, pwmL, pwmR);
	}

	private Vector2 getLookAheadPoint(){
		for(int i = (int) Math.floor(lastLookAhead); i < points.size() - 1; i++){
			Vector2 d = Vector2.subtract(points.get(i + 1), points.get(i));
			Vector2 f = Vector2.subtract(points.get(i), robotPos);

			double a = Vector2.dot(d, d);
			double b = 2 * Vector2.dot(f, d);
			double c = Vector2.dot(f, f) - (LOOK_AHEAD * LOOK_AHEAD);
			double discriminant = b*b - 4*a*c;

			if(discriminant >= 0){
				discriminant = Math.sqrt(discriminant);
				double t1 = (-b - discriminant) / (2 * a);
				double t2 = (-b + discriminant) / (2 * a);

				if(t1 >= 0 && t1 <= 1){
					double fIndex = t1 + i;
					if(fIndex > lastLookAhead){
						lastLookAhead = fIndex;
						return Vector2.add(points.get(i), Vector2.multiply(d, t1));
					}
				}else if(t2 >= 0 && t2 <= 1){
					double fIndex = t2 + i;
					if(fIndex > lastLookAhead){
						lastLookAhead = fIndex;
						return Vector2.add(points.get(i), Vector2.multiply(d, t2));
					}
				}
			}	
		}
		Vector2 d = Vector2.subtract(points.get((int) Math.ceil(lastLookAhead)), points.get((int) Math.floor(lastLookAhead)));
		return Vector2.add(points.get((int) Math.floor(lastLookAhead)), Vector2.multiply(d, lastLookAhead % 1));
	}

	private double rateLimiter(double vel){
		return lastVel + constrain(vel - lastVel, -MAX_CHANGE, MAX_CHANGE);
	}

	private double constrain(double n, double lower, double upper){
		if(n < lower) return lower;
		if(n > upper) return upper;

		return n;
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		SmartDashboard.putBoolean("PathRunning", false);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
