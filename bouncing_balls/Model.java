package bouncing_balls;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * The physics model.
 * 
 * This class is where you should implement your bouncing balls model.
 * 
 * The code has intentionally been kept as simple as possible, but if you wish,
 * you can improve the design.
 * 
 * @author Simon Robillard
 *
 */
class Model {

	double areaWidth, areaHeight;

	Ball[] balls;

	Model(double width, double height) {
		areaWidth = width;
		areaHeight = height;

		// Initialize the model with a few balls
		balls = new Ball[2];
		balls[0] = new Ball(width / 3, height * 0.9, 1.2, 0, 0, -9.8, 0.2);
		balls[1] = new Ball(2 * width / 3, height * 0.7, -0.6, 0, 0, -9.8, 0.3);
	}

	void step(double deltaT) {
		// TODO this method implements one step of simulation with a step deltaT
		for (Ball b : balls) {
			// detect collision with the border
			if (b.x < b.radius || b.x > areaWidth - b.radius) {
				b.vx *= -1; // change direction of ball
			} else {
				b.vx += deltaT * b.ax;
			}
			if (b.y < b.radius || b.y > areaHeight - b.radius) {
				b.vy *= -1;
			} else {
				b.vy += deltaT * b.ay;
			}
			// compute new position according to the speed of the ball
			b.x += deltaT * b.vx;
			b.y += deltaT * b.vy;
		}
		// Detect ball collisions in each unique pair of balls
		for (int i = 0; i < balls.length; i++) {
			for (int j = i + 1; j < balls.length; j++) {
				Ball b1 = balls[i];
				Ball b2 = balls[j];
				double distance = sqrt(pow((b1.x - b2.x), 2) + pow((b1.y - b2.y), 2));
				if (distance < b1.radius + b2.radius) {
					System.out.println("COLLISION!");
				}
			}
		}
	}

	public static double[] rectToPolar(double x, double y) {
		double r = Math.sqrt(x * x + y * y);
		double theta = Math.atan2(y, x);

		return new double[] { r, theta };
	}

	public static double[] polarToRect(double r, double theta) {
		double x = r * Math.cos(theta);
		double y = r * Math.sin(theta);

		return new double[] { x, y };
	}

	/**
	 * Simple inner class describing balls.
	 */
	class Ball {

		Ball(double x, double y, double vx, double vy, double ax, double ay, double r) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.radius = r;
			this.ax = ax;
			this.ay = ay;
			this.mass = r * r * 3.0; // mass proportional to area
		}

		/**
		 * Position, speed, acceleration and radius of the ball. You may wish to add
		 * other
		 * attributes.
		 */
		double x, y, vx, vy, ax, ay, radius, mass;
	}
}
