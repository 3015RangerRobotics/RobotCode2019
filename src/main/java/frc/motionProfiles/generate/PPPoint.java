package frc.motionProfiles.generate;

public class PPPoint extends Vector2{
	public double velocity;

	public PPPoint(double x, double y, double velocity){
		super(x, y);
		this.velocity = velocity;
	}

	public PPPoint(Vector2 xy, double velocity){
		this(xy.x, xy.y, velocity);
	}
}
