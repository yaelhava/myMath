package myMath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author yael hava and naama har tuv
 *
 */
public class Polynom implements Polynom_able{

	public ArrayList<Monom> list;				
	private Monom_Comperator MonomCompare = new Monom_Comperator();

	/**
	 * polynom consreuctor that get polynom in type string and convert it to a list of monoms 
	 * @param s - string that we convert to polynom
	 */

	public Polynom (String s) {					//polynom consreuctor that get a string
		list = new ArrayList<Monom>();			//create new array list of monoms
		s = s.replace(" ", "");
		if(s == "0" || s == "")  list = this.list;		//if the string is empty or is zero, return empty string
		else {
			String[] splitMP = s.split("(?=[+-])");		//split the polynom to monoms by plus&minus

			double coefficient;			
			int power;
			for(int i = 0; i < splitMP.length; i++) {		//run over the monoms	
				if(splitMP[i].contains("x")) {				//if the monom contain an x
					String [] splitX = splitMP[i].split("x");	//split by x
					if(splitX.length == 0) {					//if the value is x
						coefficient = 1;
						power = 0;
					}
					else if(splitX[0].contains("*")) {				//if the monom contain an *
						coefficient = Double.parseDouble(splitX[0].substring(0, splitX[0].length() - 1));
					}
					else {			
						if(splitX[0].contains("-")) {		//if the monom contain an -
							if(splitX[0].length()  > 1) coefficient = Double.parseDouble(splitX[0]);	//if it has a number
							else	coefficient = -1;		//if it doesnt have a number
						}
						else if(splitX[0].contains("+")) {		//if the monom contain an +
							if(splitX[0].length()  > 1) 	coefficient = Double.parseDouble(splitX[0]);	//if it has a number
							else	coefficient = 1;			//if it doesnt have a number
						}
						else if(!splitX[0].isEmpty() && !splitX[0].contains("-")) {		//if it doesnt empty and it doesnt contain -
							coefficient =  Double.parseDouble(splitX[0]);
						}
						else 	coefficient = 1;				//if it doesn't empty
					}
					if(splitX.length == 2) 	power = Integer.parseInt(splitX[1].substring(1));	//if there is a power for the x
					else 	power = 1;			//if the x doesnt have a power
					Monom m = new Monom(coefficient, power);		//create the new monom 
					list.add(m);						//add the monom to the list
				}
				else {			//if it doesnt contain x
					coefficient = Double.parseDouble(splitMP[i]);					
					remove();		//remove zero
					power = 0;
					Monom m = new Monom(coefficient,power);		//create the new monom
					list.add(m);					//add the mono, to the list
				}
			}
		}
		remove();				//remove zero
		list.sort(this.MonomCompare);		//sort the monoms by power
		polynomMerge();						//merge the monoms that have the same power
	}

	/**
	 * empty constructor
	 */

	public Polynom() {					//empty constructor
		list = new ArrayList<Monom>();
	}

	/**
	 * remove the zero monom from the polynom
	 */

	private void remove() {									//remove the zero monom from the polynom
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).get_coefficient() == 0) {
				list.remove(i);
				i--;
			}
		}
	} 

	/**
	 * prints the polynom as a string
	 */

	public String toString() { 		//prints the polynom as a string 
		String s="";				//new empty string

		for(int i = 0; i < list.size(); i++) {			//run over the list
			if(list.get(i).get_coefficient() >= 0 && i > 0) {		//if the coefficient is a positive number and not the first monom
				s += "+" + list.get(i).toString();
			}
			else {								//if its the first monom or a negative coefficient
				s += list.get(i).toString();
			}
		}
		return s;
	}

	/**
	 *  function that calculate the function value at x
	 * @return the function value  at x
	 */

	@Override
	public double f(double x) {				//return the function value  at x
		double result = 0;
		Iterator<Monom> it = list.iterator();
		while(it.hasNext()) {
			result += it.next().f(x);
		}
		return result;
	}

	/**
	 * addition between two polynoms
	 * @param p1 - the polynom we add to the original polynom
	 */

	@Override
	public void add(Polynom_able p1) {			//add polynom to polynom
		Iterator<Monom> it = p1.iteretor();		//create a pointer to the monoms
		while(it.hasNext()) {					
			Monom m = it.next();				
			for(int i = 0; i < list.size(); i++) {		

				if(list.get(i).get_power() == m.get_power()) {		//if the powers are equals
					list.get(i).add(m);								
					break;
				}
				else if(i == list.size() - 1) {						//if it doesnt have an equal power at the polynom
					list.add(m);										
					break;
				}
			}
		}
		remove();													//remove zero
		polynomMerge();
		list.sort(MonomCompare);									//sort the polynom by power
	}

	/**
	 * addition between monom and polynom
	 * @param m1 - the monom we add to the polynom
	 */

	@Override
	public void add(Monom m1) {						//add monom to the polynom
		if(list.size()==0) list.add(m1);			//if the list is empty
		else {										
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).get_power() == m1.get_power()) {		//if the power is equal
					list.get(i).add(m1);		
					break;
				}
				else if(i == list.size() - 1) {				//if it doesnt have an equal power at the polynom
					list.add(m1);
					break;
				}
			}
			remove();										//remove zero
			polynomMerge();
			list.sort(MonomCompare);						//sort the list

		}
	}

	/**
	 * substract polynom from polynom
	 * @param p1 - the polynom we substract from the original polynom
	 */

	@Override
	public void substract(Polynom_able p1) {				//substract polynom from polynom
		Iterator<Monom> it = p1.iteretor();					//create a pointer to the monoms
		while(it.hasNext()) {
			Monom m = it.next();							
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).get_power() == m.get_power()) {	//if the power is equal
					list.get(i).set_coefficient(list.get(i).get_coefficient() - m.get_coefficient());	//substruct the coefficients of the monoms
					break;
				}
				else if(i == list.size() - 1) {			//if it doesnt have an equal power at the polynom
					list.add(m);
					m.set_coefficient(-m.get_coefficient());		//set it as a minus number
					break;
				}
			}	
		}
		remove();						//remove zero
		list.sort(MonomCompare);		//sort the list
	}

	/**
	 * merge the monoms with an equal power
	 */

	private void polynomMerge() {			//merge the monoms with an equal power
		for(int i = 0; i < list.size() - 1; i++) {		
			if(list.get(i).get_power() ==list.get(i+1).get_power()) {	//if the power is equal
				list.get(i).add(list.get(i+1));		
				list.remove(i+1);				//remove the extra monom that has been added
				i--;
			}
		}
	}

	/**
	 * multiply polynom with polynom
	 * @param p1 - the polynom we multiply with the original polynom
	 */

	@Override
	public void multiply(Polynom_able p1) { 		//multiply polynom with polynom
		Polynom_able savePoly = copy();				//create new polynom that same to the original polynom
		list = new ArrayList<Monom>();				//reset the original polynom
		Iterator<Monom> itP1 = p1.iteretor();		//points on the new polynom
		while(itP1.hasNext()) {
			Monom m = itP1.next();					
			Iterator<Monom> itSave = savePoly.iteretor();	//points on the original polynom	
			while(itSave.hasNext()) {
				Monom saveMonom = new Monom(itSave.next());		
				saveMonom.multiply(m);						//multiply the monoms
				list.add(saveMonom);						//add it to the new monom
			}
		}
		remove();									//remove zero
		list.sort(MonomCompare);					//sort list
		polynomMerge();								//merge monoms 
	}

	/**
	 * check if the excepted polynom is equal to the original polynom
	 * @param p1 - the polynom we check if is equal to the original polynom
	 * @return true for equal, false for not equal
	 */

	@Override
	public boolean equals(Polynom_able p1) {		//check if the polynom is equal to the original one
		Iterator<Monom> it = p1.iteretor();			//points on new polynom
		Iterator<Monom> it2 = this.iteretor();		//points on original polynom
		while(it.hasNext() && it2.hasNext()) {		
			Monom m = it.next();
			Monom m2 = it2.next();
			if((m.get_coefficient() != m2.get_coefficient()) ||		
					(m.get_power() != m2.get_power())) {			//if the coefficient or the power is different
				return false;
			}
		}
		if(it.hasNext() == true && it2.hasNext() == false) return false;	//if the polynoms doesnt have the same size
		if(it.hasNext() == false && it2.hasNext() == true) return false;	//if the polynoms doesnt have the same size
		return true;
	}

	/**
	 * check if the polynom is empty
	 * @return true for empty, false for not empty
	 */

	@Override
	public boolean isZero() {		//check if the polynom is empty
		if(list.size() == 0 ){
			return true; 
		}
		return false;
	}

	/**
	 * operate the bisection method on the polynom
	 * @param x0 - the x under the x-axis
	 * @param x1 - the x above the x-axis
	 * @param eps - the epsilon step value
	 * @return the root of the function
	 */

	@Override
	public double root(double x0, double x1, double eps) {			//operate the bisection method on the polynom
		double x2= 0;										//set x2 as one of the bounds
		while(Math.abs(x1-x0) > eps) {						//while the distance greater than the epsilon
			x2 = (x1 + x0)/2;								//calculate the middle
			if(f(x2) == 0) { 
				return x2;		
			}
			else {	
				if(f(x1) * f(x2) < 0) x0 = x2;
				else x1 = x2;								//if the value of the function at x2 is negative	
			}
		}
		return x2;
	}

	/**
	 * create a new polynom that identical to the original polynom
	 * @return the new polynom
	 */

	@Override
	public Polynom_able copy() { 							//create a new polynom similar to the original
		Polynom newPolynom = new Polynom();					//create a new polynom
		for(int i = 0; i < this.list.size(); i++) {			
			newPolynom.add(new Monom(this.list.get(i)));	//add the monom to the new list
		}
		return newPolynom;		
	} 

	/**
	 * the derivative of the polynom
	 * @return the derivative of the polynom
	 */

	@Override
	public Polynom_able derivative() {						//the derivative of the polynom
		Polynom_able newPoly = new Polynom();				//create a new polynom

		Iterator<Monom> it = list.iterator();				//points on the list
		while(it.hasNext()) {			
			Monom m = it.next();
			m.derivative();									//do the derivative on the monom
			if(m.get_coefficient() !=0) {
			newPoly.add(m);				
			}
			
		}
		remove();
		return newPoly;
	}

	/**
	 *  Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps
	 *  @param x0 - the start point of the range
	 *  @param x1 - the end point of the range
	 *  @param eps - the epsilon step value
	 *  @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range
	 */

	@Override
	public double area(double x0, double x1, double eps) {		//returns the area of the polynom at the range it gets
		double sum = 0;											//save the sum

		while(x1 - x0 > 0 ) {									//while x1 greater than x0
			if(f(x0) > 0) {										//if its above the the x-axis
				sum += eps * f(x0);								//calculate the area of a rectangle
			}			
			x0 += eps;											//do x0 plus epsilon
		}
		return sum;
	}

	/**
	 * creates a pointer to the polynom's monoms
	 * @return an Iterator (of Monoms) over this Polynom
	 */

	@Override
	public Iterator<Monom> iteretor() {					//creates a pointer to the polynom's monoms
		return list.iterator();
	}



}
