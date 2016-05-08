package com.venturesity;

import java.util.Scanner;

public class Gameofpools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);

		// Define the co-ordinates of the pool table
		System.out
				.println("In the game of pools, The default co-ordinates are");
		Tuple<Integer, Integer> left_bottom = new Tuple<Integer, Integer>(0, 0);// Tuple
																				// class
																				// is
																				// created
																				// to
																				// store
																				// custom
																				// x
																				// and
																				// y
																				// co-ordinates
		Tuple<Integer, Integer> left_top = new Tuple<Integer, Integer>(0, 142);
		Tuple<Integer, Integer> right_bottom = new Tuple<Integer, Integer>(284,
				0);
		Tuple<Integer, Integer> right_top = new Tuple<Integer, Integer>(284,
				142);
		System.out.println("The default co-ordinates are left_bottom "
				+ left_bottom + " left_top" + left_top + " right_bottom "
				+ right_bottom + " right_top " + right_top);
		System.out
				.println("If you want to change the co-ordinates: please enter '1' without quotes else give any thing");
		int value = scan.nextInt();
		if (value == 1) {
			System.out
					.println("Enter x an y co-ordinates for left_bottom point");
			int x = scan.nextInt();
			int y = scan.nextInt();
			left_bottom = new Tuple<Integer, Integer>(x, y);

			System.out.println("Enter x an y co-ordinates for left_top point");
			x = scan.nextInt();
			y = scan.nextInt();
			left_top = new Tuple<Integer, Integer>(x, y);

			System.out
					.println("Enter x an y co-ordinates for right_bottom point");
			x = scan.nextInt();
			y = scan.nextInt();
			right_bottom = new Tuple<Integer, Integer>(x, y);

			System.out.println("Enter x an y co-ordinates for right_top point");
			x = scan.nextInt();
			y = scan.nextInt();
			right_top = new Tuple<Integer, Integer>(x, y);
		}

		// Get initial position of the ball
		System.out.println("Enter initial position:");

		System.out.println("Enter X-axis position");
		float x = scan.nextInt();
		System.out.println("Enter Y-axis position");
		float y = scan.nextInt();
		Tuple<Float, Float> initial_pos = new Tuple<Float, Float>(x, y);
		System.out.println("The initial position is: " + initial_pos);

		// Get the cushion point of the pool table
		System.out.println("Enter position of point on cushion:");
		System.out.println("Enter X-axis position");
		x = scan.nextInt();
		System.out.println("Enter Y-axis position");
		y = scan.nextInt();
		Tuple<Float, Float> cushion_point = new Tuple<Float, Float>(x, y);
		System.out
				.println("The position of point on cushion: " + cushion_point);

		// Get the speed of the ball untill user enters valid number
		float speed = -1;
		while (speed < 0 && speed < 1000) {
			System.out
					.println("Enter speed of ball (in cm/s). (Between 0 and 1000 cm/s) :");
			speed = scan.nextFloat();
		}

		// Get the travelling time of the ball untill user enters valid number
		float time = 0;
		while (time <= 0) {
			System.out.println("Enter travelling time (in seconds):");
			time = scan.nextFloat();
		}

		// Draw a line between the point and the cushion point
		// find the slope
		float slope = slope_value(initial_pos, cushion_point);

		// find the distance from the initial point to cushion point
		float distance_origin = (float) Math.sqrt(Math.pow(
				(cushion_point.getx() - initial_pos.getx()), 2)
				+ Math.pow((cushion_point.gety() - initial_pos.gety()), 2));
		// System.out.println(distance_origin);

		// find the equation of the line connecting cushion point and initial
		// point
		Equation line = line_of_travel(initial_pos, slope);

		// find the angle of the line with the since it forms triangle with the
		// pool table boundaries
		float angle = angle_of_line(line, cushion_point, slope);
		//System.out.println(angle);

		// distance travelled will be speed * time
		float distance_travelled = speed * time;
		float other_end_point;

		if (cushion_point.equals(left_bottom)
				|| (cushion_point.equals(right_bottom))) { // to find the other
															// point end point
															// y-cordinate will
															// be known if
															// cushion point is
															// in bottom
			other_end_point = end_point_x(line, left_top.gety());

		} else { // to find the other point end point y-cordinate will be known
					// if cushion point is in bottom
			other_end_point = end_point_x(line, left_bottom.gety());

		}

		Tuple<Float, Float> end_point = new Tuple<Float, Float>(
				other_end_point, (float) left_top.gety());
		// find the entire distance of the ball to travell
		float entire_distance = (float) Math.sqrt((end_point.getx() * end_point
				.getx()) + (end_point.gety() * end_point.gety()));
		// System.out.println(entire_distance);

		if (distance_travelled <= distance_origin) {
			// if distance travelled is less than the distance between initial
			// position to cushion point
			float dist = distance_origin - distance_travelled;
			float xcord = xcordcalculate_dest(dist, angle);
			float ycord = ycordcalculate_dest(dist, angle);
			System.out.println("Final position of ball: ");
			System.out.println(xcord + "," + ycord);
			System.out.println("On rounding off it is : (" + Math.round(xcord)
					+ "," + Math.round(ycord) + ")");
		} else {

			// if distance travelled is greater than the distance between
			// initial position to cushion point
			// We need to take modulo to find the current distance
			// Entire distance should be multiplied by 2 to check if it
			// completes a cycle
			float dis = distance_travelled - distance_origin;
			float dis_traverse = (dis % (2 * entire_distance));
			if (dis_traverse < entire_distance) {

				// If the modulo is less than the entire distance then the ball
				// is moving towards cushion and it is not returning back
				float xcord = xcordcalculate_dest(dis_traverse, angle);
				float ycord = ycordcalculate_dest(dis_traverse, angle);
				System.out.println("Final position of ball: ");
				System.out.println(xcord + "," + ycord);
				System.out.println("On rounding off it is : ("
						+ Math.round(xcord) + "," + Math.round(ycord) + ")");
			} else {

				// If the modulo is greater than the entire distance then the
				// ball is moving away from cushion and it is not returning back
				dis_traverse = (dis_traverse % entire_distance);
				dis_traverse = entire_distance - dis_traverse;
				float xcord = xcordcalculate_dest(dis_traverse, angle);
				float ycord = ycordcalculate_dest(dis_traverse, angle);
				System.out.println("Final position of ball: ");
				System.out.println(xcord + "," + ycord);
				System.out.println("On rounding off it is : ("
						+ Math.round(xcord) + "," + Math.round(ycord) + ")");
			}

		}
	}

	private static float ycordcalculate_dest(float dist, float angle) {
		// TODO Auto-generated method stub
		// to find the y-cordinate the we need to find the adjacent side of the
		// right angled triangle since we know the hypotenuse
		// The formula is cos 0 = adjacent / hypotenuse
		// 0 means theota
		float ycord = (float) (Math.cos((angle * 3.14) / 180) * dist);
		return ycord;
	}

	private static float xcordcalculate_dest(float dist, float angle) {
		// TODO Auto-generated method stub
		// to find the x-cordinate the we need to find the opposite side of the
		// right angled triangle since we know the hypotenuse
		// The formula is sin 0 = opposite / hypotenuse
		// 0 means theota
		float xcord = (float) (Math.sin((angle * 3.14) / 180) * dist);
		return xcord;

	}

	private static float end_point_x(Equation line, Integer ycord) {
		// TODO Auto-generated method stub
		// System.out.println(line);
		// Need to find the extreme end point of the line to calculate the
		// distance
		float xcord = (ycord - line.constant()) / line.xvalue();
		return xcord;
	}

	private static float angle_of_line(Equation line,
			Tuple<Float, Float> cushion_point, float slope) {
		// TODO Auto-generated method stub
		// The formula for angle between two intersecting line is tan O = |
		// (m2-m1)/1+m1m2|
		// since it is intersecting with the horizontal line the slope is 0
		// So the angle of the modified formula is 0=tan-1|m2|
		float angle = (float) Math.atan(Math.abs(slope)); // gives radians
		angle = (float) ((angle * 180) / 3.14); // convert it from radians to
												// degree
		angle = 180 - (90 + angle);// sum of all angles in the triangle is 180.
									// So we need to find the other angle of
									// right angled triangle
		return angle;

	}

	private static float slope_value(Tuple<Float, Float> initial_pos,
			Tuple<Float, Float> cushion_point) {
		// TODO Auto-generated method stub
		// To find the slope the formula is m=(y2-y1)/(x2-x1)
		float change_of_y = cushion_point.gety() - initial_pos.gety();
		float change_of_x = cushion_point.getx() - initial_pos.getx();

		float slope = change_of_y / change_of_x;
		return slope;
	}

	private static Equation line_of_travel(Tuple<Float, Float> initial_pos,
			float slope) {
		// TODO Auto-generated method stub
		// The equation of the line is y=mx+c. So find the constant value where
		// slope is x co-ordinate and -1 is y co-ordinate
		float xcord = slope;
		float ycord = -1;
		float constant = initial_pos.gety() - (slope * initial_pos.getx());
		Equation line = new Equation(xcord, ycord, constant);

		return line;

	}

}

class Equation {
	// To handle equation of the line new class is created
	private final float x;
	private final float y;
	private final float c;

	public Equation(float x, float y, float c) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public String toString() {
		if (c < 0)
			return String.format("%fy=%fx%f", x, y, c);
		else
			return String.format("%fy=%fx+%f", x, y, c);
	}

	public float xvalue() {
		return x;
	}

	public float yvalue() {
		return y;
	}

	public float constant() {
		return c;
	}

}

class Tuple<Key, Value> {
	// To handle co-ordinates (x,y). New Tuple class is created
	private final Key key;
	private final Value value;

	public Tuple(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	public String toString() {
		return String.format("(" + key + "," + value + ")");
	}

	public Key getx() {
		return key;
	}

	public Value gety() {
		return value;
	}
}