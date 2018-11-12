package myMath;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {

	public static void main(String[] args) {

		
		//************Monom Test*************
		
		Monom m1 = new Monom(3, 2);
		Monom m2 = new Monom(m1);
		Monom m3 = new Monom(4,2);
		
		System.out.println("m1: " + m1);
		System.out.println("m2: " + m2);
		System.out.println("f(x): " + m1.f(3));
		m1.derivative();
		System.out.println("derivative m1: " + m1);
		System.out.println(m2);
		m2.add(m3);
		System.out.println("add: " + m2);
		m1.multiply(m3);
		System.out.println("multiply: " + m1);
		System.out.println();
		
		//***********Polynom Test************
		
		Polynom p1 = new Polynom("3x^2 + 12x - 4");
		Polynom p2 = new Polynom("x^3 - x");
		Polynom p3 = new Polynom("3");
		Polynom p4 = new Polynom();
		Polynom p5 = new Polynom("4x^3 + 2x - 1");
		Polynom p6 = new Polynom("x^3 + 2x");
		Polynom or = new Polynom("x - 5");
		System.out.println("yael");
		System.out.println("orrrrrrr: " + or.root(2, 8, 0.01));
		
	
		
		
		System.out.println("p1: " + p1);
		System.out.println("p2: " + p2);
		System.out.println("p3: " + p3);
		p4 = (Polynom) p1.copy();
		System.out.println("copy p1 to p4: " + p4);
		
		System.out.println("f(x) of p2: " + p2.f(2));
		p1.add(p2);
		System.out.println("add: " + p1);
		p2.add(m3);
		System.out.println("add Monom: " + p2);
		p5.substract(p6);
		System.out.println("substruct: " + p5);
		p6.multiply(p3);
		System.out.println("multiply: " + p6);
		System.out.println("equal: " + p1.equals(p5));
		System.out.println("isZero: " + p3.isZero());
		System.out.println("root: " + p1.root(-2, 5, Double.MIN_VALUE));
		p5.derivative();
		System.out.println("derivative: " + p5);
		System.out.println(p1);
		System.out.println("area: " + p1.area(-3, 3, 0.0001));
		
		


		
	
	}

}
