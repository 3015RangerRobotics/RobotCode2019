package frc.motionProfiles.generate;

public class Vector2 {
    public double x;
    public double y;
    public double magnitude;

    public Vector2(double z, double x){
        this.x = z;
        this.y = x;
        this.magnitude = Math.sqrt((z*z) + (x*x));
	}

    public Vector2 normalized() {
        if (this.magnitude > 0) {
            return Vector2.divide(this, this.magnitude);
        }
        return new Vector2(0, 0);
    }

    public static Vector2 add(Vector2 a, Vector2 b){
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b){
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 multiply(Vector2 a, double mult){
        return new Vector2(a.x * mult, a.y * mult);
    }

    public static Vector2 divide(Vector2 a, double div){
        return new Vector2(a.x / div, a.y / div);
	}
	
	public static double dot(Vector2 a, Vector2 b){
		return (a.x * b.x) + (a.y * b.y);
	}

    @Override
    public String toString(){
        return "X: " + x + ", Y: " + y;
    }
}
