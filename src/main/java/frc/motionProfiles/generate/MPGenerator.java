package frc.motionProfiles.generate;

import java.util.ArrayList;
import java.util.HashMap;

import frc.robot.RobotMap;
import frc.robot.RobotMap.Side;

public class MPGenerator {
    public static double timeStep = RobotMap.kPeriod;
	public static double wheelbaseWidth = RobotMap.wheelBaseWidth;
	
	public static ArrayList<PPPoint> get2DPP(double xDist, double yDist, double endAngle, double maxVel, double maxAcc){
		Vector2 a0 = new Vector2(0, 0);
        Vector2 a1 = new Vector2(xDist, yDist);
        double h = Vector2.subtract(a1, a0).magnitude / 2;
        double theta = Math.toRadians(endAngle);
        double o = Math.sin(theta) * h;
        double a = Math.cos(theta) * h;
        Vector2 c0 = new Vector2(h, 0);
        Vector2 c1 = Vector2.subtract(a1, new Vector2(a, o));

        ArrayList<PPPoint> pointsOnCurve = new ArrayList<>();
        for(double t = 0.0; t <= 1.0; t += 0.001){
			PPPoint point = new PPPoint(Util.cubicCurve(a0, c0, c1, a1, t), 0);
			if(Util.distanceBetweenPts(pointsOnCurve.get(pointsOnCurve.size() - 1), point) >= 0.25){
				pointsOnCurve.add(point);
			}
		}

		// for(int i = 1; i < pointsOnCurve.size(); i++){
		// 	PPPoint current = pointsOnCurve.get(i);
		// 	PPPoint last = pointsOnCurve.get(i - 1);
		// 	double distance = Util.distanceBetweenPts(last, current);

		// 	current.velocity = Math.min(Math.sqrt((last.velocity*last.velocity) + (2 * maxAcc * distance)), maxVel);
		// }

		for(int i = pointsOnCurve.size() - 2; i >= 0; i--){
			PPPoint current = pointsOnCurve.get(i);
			PPPoint next = pointsOnCurve.get(i + 1);

			double distance = Util.distanceBetweenPts(current, next);

			current.velocity = Math.min(maxVel, Math.sqrt((next.velocity*next.velocity) + (2 * maxAcc * distance)));
		}
		
		return pointsOnCurve;
	}

    public static HashMap<Side, double[][]> generate2DToTarget(double zDist, double xDist, double angle1, double angle2, double maxVel, double maxAcc){
        Vector2 a0 = new Vector2(0, 0);
        Vector2 a1 = new Vector2(zDist, xDist);
        double endAngle = angle1 + angle2;
        double h = Vector2.subtract(a1, a0).magnitude / 2;
        double theta = Math.toRadians(endAngle);
        double o = Math.sin(theta) * h;
        double a = Math.cos(theta) * h;
        Vector2 c0 = new Vector2(h, 0);
        Vector2 c1 = Vector2.subtract(a1, new Vector2(a, o));

        ArrayList<Vector2> pointsOnCurve = new ArrayList<>();
        for(double t = 0.0; t <= 1.0; t += 0.0002){
            pointsOnCurve.add(Util.cubicCurve(a0, c0, c1, a1, t));
        }

        // double length = 0.0;
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment());
        double prevLength = 0;
        for(int i = 1; i < pointsOnCurve.size(); i++){
            Vector2 diff = Vector2.subtract(pointsOnCurve.get(i), pointsOnCurve.get(i - 1));
            double c = Math.sqrt((diff.x * diff.x) + (diff.y * diff.y));
            // length += c;

            double l = c + prevLength;
            prevLength = l;

            Segment seg = new Segment();
            seg.x = pointsOnCurve.get(i).x;
            seg.y = pointsOnCurve.get(i).y;
            seg.pos = l;
            seg.dydx = Util.slope(pointsOnCurve.get(i - 1), pointsOnCurve.get(i));
            seg.dx = seg.pos - segments.get(segments.size() - 1).pos;

            double v0 = segments.get(segments.size() - 1).vel;
            double dx = segments.get(segments.size() - 1).dx;
            if (dx > 0) {
                double vMax = Math.sqrt(Math.abs(v0 * v0 + 2 * maxAcc * dx));
                if (Double.isNaN(vMax)) {
                    vMax = segments.get(segments.size() - 1).vel;
                }
                seg.vel = vMax;
            } else {
                seg.vel = segments.get(segments.size() - 1).vel;
            }

            segments.add(seg);
        }

        for(int i = segments.size() - 2; i > 1; i--){
            double v0 = segments.get(i + 1).vel;
            double dx = segments.get(i + 1).dx;
            double vMax = Math.sqrt(Math.abs(v0 * v0 + 2 * maxAcc * dx));
            segments.get(i).vel = Math.min((Double.isNaN(vMax) ? maxVel : vMax), segments.get(i).vel);
        }

        double time = 0;
        for (int i = 1; i < segments.size(); i++) {
            double v = segments.get(i).vel;
            double dx = segments.get(i - 1).dx;
            double v0 = segments.get(i - 1).vel;
            time += (2 * dx) / (v + v0);
            if (Double.isNaN(time)) {
                time = 0;
            }
            segments.get(i).time = time;
        }

        for (int i = 1; i < segments.size(); i++) {
            double dt = segments.get(i).time - segments.get(i - 1).time;
            if (dt == 0 || !Double.isFinite(dt)) {
                segments.remove(i);
            }
        }

        for (int i = 1; i < segments.size(); i++) {
            double dv = segments.get(i).vel - segments.get(i - 1).vel;
            double dt = segments.get(i).time - segments.get(i - 1).time;
            if (dt == 0) {
                segments.get(i).acc = 0;
            } else {
                segments.get(i).acc = dv / dt;
            }
        }

        ArrayList<Segment> timeSegments = new ArrayList<>();
        int segNum = 0;
        // int numMessySeg = 0;
        for (int i = 0; i < segments.size(); i++) {
            if (i == 0) {
                timeSegments.add(segments.get(0));
                segNum++;
            }

            if (segments.get(i).time >= segNum * timeStep) {
                timeSegments.add(segments.get(i));
                timeSegments.get(timeSegments.size() - 1).dt = timeSegments.get(timeSegments.size() - 1).time - timeSegments.get(timeSegments.size() - 2).time;
                // if (Math.abs(segments.get(i).time - segNum * timeStep) > timeStep + 0.00005) {
                //     numMessySeg++;
                // }
                segNum++;
            }
        }

        for (int i = 0; i < timeSegments.size(); i++) {
            double angle;
            if (i == 0) {
                angle = Math.atan2(-timeSegments.get(i).y, -timeSegments.get(i).x) * (180 / Math.PI);
            } else {
                angle = Math.atan2(timeSegments.get(i - 1).y - timeSegments.get(i).y, timeSegments.get(i - 1).x - timeSegments.get(i).x) * (180 / Math.PI);
            }
            angle -= 180;
            if (angle < -180) {
                angle += 360;
            } else if (angle > 180) {
                angle -= 360;
            }
            timeSegments.get(i).heading = angle;
        }

        for (int i = 1; i < timeSegments.size(); i++) {
            Segment now = timeSegments.get(i);
            Segment last = timeSegments.get(i - 1);
            double dt = now.time - last.time;
            now.vel = (now.pos - last.pos) / dt;
            now.acc = (now.vel - last.vel) / dt;
        }

        double w = wheelbaseWidth / 2;
        ArrayList<Segment> leftSegments = new ArrayList<>();
        ArrayList<Segment> rightSegments = new ArrayList<>();
        for (int i = 0; i < timeSegments.size(); i++) {
            Segment seg = timeSegments.get(i);
            Segment left = new Segment();
            Segment right = new Segment();

            double cos_angle = Math.cos(seg.heading * (Math.PI / 180));
            double sin_angle = Math.sin(seg.heading * (Math.PI / 180));

            left.x = seg.x + (w * sin_angle);
            left.y = seg.y - (w * cos_angle);
            left.heading = seg.heading;
            left.dydx = seg.dydx;
            left.dt = seg.dt;
            left.time = seg.time;

            if (i != 0) {
                Segment last = leftSegments.get(i - 1);
                double distance = Math.sqrt((left.x - last.x) * (left.x - last.x) + (left.y - last.y) * (left.y - last.y));

                left.pos = last.pos + distance;
                left.vel = distance / seg.dt;
                left.acc = (left.vel - last.vel) / seg.dt;
            }

            right.x = seg.x - (w * sin_angle);
            right.y = seg.y + (w * cos_angle);
            right.heading = seg.heading;
            right.dydx = seg.dydx;
            right.dt = seg.dt;
            right.time = seg.time;

            if (i != 0) {
                Segment last = rightSegments.get(i - 1);
                double distance = Math.sqrt((right.x - last.x) * (right.x - last.x) + (right.y - last.y) * (right.y - last.y));

                right.pos = last.pos + distance;
                right.vel = distance / seg.dt;
                right.acc = (right.vel - last.vel) / seg.dt;
            }

            leftSegments.add(left);
            rightSegments.add(right);
        }

        double[][] leftPoints = new double[leftSegments.size()][3];
        for(int i = 0; i < leftSegments.size(); i++) {
            leftPoints[i] = new double[]{leftSegments.get(i).pos, leftSegments.get(i).vel, leftSegments.get(i).acc};
        }
        double[][] rightPoints = new double[rightSegments.size()][3];
        for(int i = 0; i < rightSegments.size(); i++) {
            rightPoints[i] = new double[]{rightSegments.get(i).pos, rightSegments.get(i).vel, rightSegments.get(i).acc};
        }

//        System.out.println(numMessySeg + " messy");
//
//        System.out.println(time + ", " + length);

		HashMap<Side, double[][]> map = new HashMap<Side, double[][]>();
		map.put(Side.kLeft, leftPoints);
		map.put(Side.kRight, rightPoints);

		return map;
    }

    public static double[][] generate1D(double d, double maxV, double a, boolean reverse) {
        ArrayList<double[]> points = new ArrayList<double[]>();
        points.add(new double[] {0, 0, 0});
        int state = 0;//0 = accel, 1 = maxV, 2 = decel
        double lastPos = 0;
        double lastVel = 0;
        boolean done = false;
        double vT;
        double pT;
        while(!done) {
            switch(state) {
                case 0:
                    vT = lastVel + (a * timeStep);
                    pT = lastPos + (lastVel * timeStep) + (0.5 * a * (timeStep*timeStep));

                    if(vT >= maxV) {
                        vT = maxV;
                        state = 1;//maxV
                        points.add(new double[] {pT, vT, 0});
                        lastPos = pT;
                        lastVel = vT;
                        break;
                    }

                    if(pT <= d/2 + (lastVel * timeStep) && pT >= d/2 - (lastVel * timeStep)) {
                        state = 2;//decel
                        points.add(new double[] {pT, vT, 0});
                        lastPos = pT;
                        lastVel = vT;
                        break;
                    }

                    points.add(new double[] {pT, vT, a});
                    lastPos = pT;
                    lastVel = vT;
                    break;
                case 1:
                    vT = maxV;
                    pT = lastPos + (lastVel * timeStep);

                    double vToA = vT / a;
                    double pVoA = lastPos + (lastVel * vToA) + (0.5 * a * (vToA * vToA));
                    if(pVoA <= d - pT + (maxV * timeStep) && pVoA >= d - pT - (maxV * timeStep)) {
                        state = 2;//decel
                        points.add(new double[] {pT, vT, 0});
                        lastPos = pT;
                        lastVel = vT;
                        break;
                    }

                    points.add(new double[] {pT, vT, 0});
                    lastPos = pT;
                    lastVel = vT;
                    break;
                case 2:
                    vT = lastVel + (-a * timeStep);
                    pT = lastPos + (lastVel * timeStep) + (0.5 * -a * (timeStep*timeStep));

//                    Log.e("something", "pT: " + pT + ", lastVel: " + lastVel + ((pT >= (d - (lastVel * period))) ? "true" : "false"));
//                    if(pT <= d + (lastVel * period) && (pT >= (d - (lastVel * period)))) {
//                        points.add(new double[] {pT, vT, 0});
//                        done = true;//stop
//                        break;
//                    }
                    if(pT <= d + 0.01 && pT >= d - 0.01) {
                        points.add(new double[] {pT, vT, 0});
                        done = true;//stop
                        break;
                    }

                    if(lastVel < 0){
                        done = true;
                    }

                    points.add(new double[] {pT, vT, -a});
                    lastPos = pT;
                    lastVel = vT;
                    break;
            }
        }
        points.add(new double[] {d, 0, 0});
        double[][] ret = new double[points.size()][3];
        for(int i = 0; i < points.size(); i++) {
			if(!reverse){
				ret[i] = points.get(i);
			}else{
				ret[i] = new double[]{-points.get(i)[0], -points.get(i)[1], -points.get(i)[2]};
			}
        }

        return ret;
    }
}
