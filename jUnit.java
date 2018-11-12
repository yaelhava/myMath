package myMath;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class jUnit {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	public void testFx() {
		Polynom p = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		double pTest = p.f(-2);
		assertEquals(263.5, pTest);
	}

	@Test
	public void testAddPolynom() {
		Polynom p1 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom p2 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		p1.add(p2);
		Polynom addTest = new Polynom("-6x + 10x^2 - 37 + 2*x^8");
		assertEquals(addTest.toString(), p1.toString());
	}
	
	
	@Test
	public void testAddMonom() {
		Polynom p = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Monom m1 = new Monom(4,2);
		Monom m2 = new Monom(-4,3);
		Monom m3 = new Monom(4,0);
		p.add(m1);
		p.add(m2);
		p.add(m3);
		Polynom addTest = new Polynom("-3x + 9*x^2 +0 - 14.5 + x^8 - 4x^3");
		assertEquals(addTest.toString(), p.toString());
	}
	
	@Test
	public void testSubstract() {
		Polynom p1 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom p2 = new Polynom("x + 2*x^2-4x +0 - 5.5 + 2x^8 + 2x^2");
		p1.substract(p2);
		Polynom subTest = new Polynom("x^2 -13 -x^8");
		assertEquals(subTest.toString(), p1.toString());
	}
	
	@Test
	public void testMultiply() {
		Polynom p1 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom p2 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		p1.multiply(p2);
		Polynom multTest = new Polynom("-176x^2 -30x^3 + 25x^4 +111x-6x^9 +342.25-37x^8 + 10x^10+x^16");
		assertEquals(multTest.toString(), p1.toString());
	}
	
	@Test
	public void testEquals() {
		Polynom p1 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom p2 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom p3 = new Polynom("2x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		assertTrue(p1.equals(p2));
		assertFalse(p1.equals(p3));
	}
	
	@Test
	public void testIsZero() {
		Polynom p1 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom p2 = new Polynom("0");
		Polynom p3 = new Polynom("");
		assertTrue(p2.isZero());
		assertFalse(p1.isZero());
		assertTrue(p3.isZero());
	}
	
	@Test 
	public void testRoot() {
		Polynom p = new Polynom("x^2 + 20x +5");
		double x = p.f(p.root(-10, 10, 0.001));
		assertTrue(x <= 0.001 );
	}
	
	@Test
	public void testCopy() {
		Polynom p1 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom p2 = new Polynom("x");
		p2 = (Polynom) p1.copy();
		assertEquals(p1.toString(), p2.toString());
	}
	
	@Test
	public void testDerivative() {
		Polynom p1 = new Polynom("x + 3*x^2-4x +0 - 18.5 + x^8 + 2x^2");
		Polynom deriveTest = new Polynom("-3 + 10x +8x^7 ");
		p1.derivative();
		assertEquals(deriveTest.toString(), p1.toString());		
	}
	
	@Test
	public void testArea() {
		Polynom p1 = new Polynom("x^2 +3");
		double x1 = p1.area(-5, 5, 0.001);
		assertTrue(x1 <= 113.34 && x1 >= 113.33);
		
		Polynom p2 = new Polynom("8x^3 -3x^2 +3x -6");
		double x2 = p2.area(-1, 1, 0.000001);
		assertTrue(x2 >= 0.102 && x2 <= 0.1022);
	}
	
}
