package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GraphThread implements Runnable {
	private TalonSRX talonLeft;
	private TalonSRX talonRight;
	private boolean running = false;
	private Thread thread;

	public GraphThread(TalonSRX talonLeft, TalonSRX talonRight) {
		this.talonLeft = talonLeft;
		this.talonRight = talonRight;
		thread = new Thread(this);
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			double targetLeft = talonLeft.getActiveTrajectoryPosition();
			double actualLeft = talonLeft.getSelectedSensorPosition();

			double targetRight = talonRight.getActiveTrajectoryPosition();
			double actualRight = talonRight.getSelectedSensorPosition();

			SmartDashboard.putNumber("TargetLeft", targetLeft);
			SmartDashboard.putNumber("ActualLeft", actualLeft);

			SmartDashboard.putNumber("TargetRight", targetRight);
			SmartDashboard.putNumber("ActualRight", actualRight);
		}
	}

	public void stop() {
		running = false;
	}

	public void start() {
		running = true;
		thread.start();
	}
}
