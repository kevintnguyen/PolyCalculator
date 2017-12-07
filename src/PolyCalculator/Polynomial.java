package PolyCalculator; //place in proper package.

import PolyCalculator.LinkedList.Iterator;

/**
 * Polynomial is a class that creates a LinkedList and stores terms as
 * ListNodes.
 * 
 * @author Kevin Nguyen
 * @version 4.5.0 January 2015
 */
public class Polynomial {
	/**
	 * terms is the current LinkedList for this Polynomial.
	 */
	private LinkedList terms;

	/**
	 * Polynomial creates a new LinkedList.
	 */
	public Polynomial() {
		terms = new LinkedList();

	}

	/**
	 * derivative takes the derivative on the current polynomial.
	 */
	public Polynomial derivative() {
		Polynomial newPoly = new Polynomial();
		LinkedList newList = newPoly.terms;
		Iterator first = terms.iterator(); // Iterator for the current list.
		Iterator third = newList.zeroth(); // Iterator for the new list, or
											// third list. we will use the variable
											// name second for
											// polynomial parameters.

		/**
		 * while the current list has a literal, take the derivative and add the
		 * new Literal onto the new list.
		 */
		while (first.hasNext()) {
			Literal newLiteral = new Literal(
					((Literal) first.getElement()).getCoefficient() * ((Literal) first.getElement()).getExponent(),
					((Literal) first.getElement()).getExponent() - 1);
			newList.insert(newLiteral, third);
			third.next();
			first.next();
		}
		return newPoly;
	}

	/**
	 * minus takes the current polynomial and subtracts it to the polynomial
	 * that is passed over.
	 * 
	 * @param bPoly
	 *            is the second polynomial passed over.
	 * @return the new difference of the two polynomials.
	 */
	public Polynomial minus(Polynomial bPoly) {
		Iterator first = terms.iterator(); // Iterator for the current list.
		LinkedList bPolyLinkedList = bPoly.terms;
		Iterator second = bPolyLinkedList.iterator(); // Iterator for the second
														// list, or bPoly's
														// list.
		Polynomial newPoly = new Polynomial(); // create a new polynomial for
												// the return.
		LinkedList newList = newPoly.terms;
		Iterator third = newList.zeroth(); // Iterator for the new list, or
											// third list.

		/**
		 * while first & second has a next, compare each terms and add the
		 * larger exponent onto the new list, also set it to negative if it's
		 * from list 2. If the exponents are equal, subtract the coefficients
		 * from the two terms then set it as one new literal for the new list.
		 */
		while (first.hasNext() && second.hasNext()) {
			if (((Literal) first.getElement()).getExponent() > ((Literal) second.getElement()).getExponent()) {// first greater
				Literal newLiteral = new Literal
						(((Literal) first.getElement()).getCoefficient(),
						((Literal) first.getElement()).getExponent());

				newList.insert(newLiteral, third);
				third.next();
				first.next();
			} else if (((Literal) first.getElement()).getExponent() < ((Literal) second.getElement()).getExponent()) {// first lesser
				Literal newLiteral = new Literal
						(((Literal) second.getElement()).getCoefficient() * -1,
						((Literal) second.getElement()).getExponent());
				newList.insert(newLiteral, third);
				third.next();
				second.next();
			} else if (((Literal) first.getElement()).getExponent() == ((Literal) second.getElement()).getExponent()) {// first equals
				Literal newLiteral = new Literal(
						((Literal) first.getElement()).getCoefficient() - ((Literal) second.getElement()).getCoefficient(),
						((Literal) second.getElement()).getExponent());
				if (newLiteral.getCoefficient() == 0) {
					first.next();
					second.next();
				} else {
					newList.insert(newLiteral, third);
					third.next();
					first.next();
					second.next();
				}
			}

		}
		/**
		 * if there are leftovers from the first list, add leftovers to the new
		 * list.
		 */
		if (first.hasNext()) {
			while (first.hasNext()) {
				Literal newLiteral = new Literal
						(((Literal) first.getElement()).getCoefficient(),
						((Literal) first.getElement()).getExponent());
				newList.insert(newLiteral, third);
				first.next();
				third.next();
			}
			/**
			 * if there are leftovers from the second list, add leftovers to the
			 * new list with a negate.
			 */
		} else if (second.hasNext()) {
			while (second.hasNext()) {
				Literal newLiteral = new Literal
						(((Literal) second.getElement()).getCoefficient() * -1,
						((Literal) second.getElement()).getExponent());
				newList.insert(newLiteral, third);
				second.next();
				third.next();
			}
		}
		return newPoly;
	}

	/**
	 * times takes the current polynomial and multiplies it to the polynomial
	 * passed over.
	 * 
	 * @param bPoly is the second polynomial passed over.
	 * @return the new product of the two polynomials.
	 */
	public Polynomial times(Polynomial bPoly) {
		Iterator first = terms.iterator(); // Iterator for the current list.
		LinkedList bPolyLinkedList = bPoly.terms;
		Iterator second = bPolyLinkedList.iterator(); // Iterator for the second
														// list, or bPoly's
														// List.
		Polynomial newPoly = new Polynomial(); // new Polynomial for the return.
		LinkedList newList = newPoly.terms;
		Iterator third = newList.zeroth(); // Iterator for the third list, or
											// the new list.

		/**
		 * while first and second has a next literal. Grab one term from list
		 * one and multiply is to each /*of the terms from list 2. Then set the
		 * pointer of the second list back to the front so that the the first
		 * list could multiply the next term from the first list and goes until
		 * list one runs out.
		 **/
		while (first.hasNext() && second.hasNext()) {
			while (second.hasNext()) {
				Literal newLiteral = new Literal(
						((Literal) first.getElement()).getCoefficient() * ((Literal) second.getElement()).getCoefficient(),
						((Literal) first.getElement()).getExponent() + ((Literal) second.getElement()).getExponent());
				newList.insert(newLiteral, third);
				third.next();
				second.next();
			}
			first.next();
			second = bPolyLinkedList.iterator();
		}
		return newPoly;
	}

	/**
	 * print() prints out the current polynomial in String format.
	 * 
	 * @return a new String representing the current polynomial.
	 */
	public String print() {

		Iterator current = terms.iterator();
		Iterator previous = terms.zeroth();
		StringBuilder polyNomialString = new StringBuilder();

		/**
		 * Go through the list to check if there exist a zero coefficient, and
		 * remove it.
		 */
		while (previous.hasNext() && current.hasNext()) {
			Literal test = (Literal) current.next();
			if (test.getCoefficient() == 0) {
				terms.remove(previous);
			} else {
				previous.next();
			}
		}

		/**
		 * We make two test cases to solve the sign symbols problem that creates
		 * a extra sign. We first choose by printing out the starting term or
		 * the ending term, but for now we'll use the starting term. Then we can
		 * use the other terms for it's special cases.
		 */

		// print the starting term.
		Iterator thisIter = terms.iterator();

		// return a zero if the list is empty.
		if (!thisIter.hasNext()) {
			return "0";
		}

		// print the starting term out.
		startingTerm(polyNomialString, thisIter);

		// print the other terms out.
		otherTerms(polyNomialString, thisIter);

		return polyNomialString.toString();

	}

	private void otherTerms(StringBuilder polyNomialString, Iterator thisIter) {
		Literal lit;
		while (thisIter.hasNext()) {

			lit = (Literal) thisIter.next();
			if (lit.getCoefficient() == 0) { // zero.

			} else if (lit.getExponent() == 0) { // a constant.
				if (lit.getCoefficient() <= -1) {
					polyNomialString.append(" - " + lit.getCoefficient() * -1);
				} else {
					polyNomialString.append(" + " + lit.getCoefficient());
				}
			} else if (lit.getExponent() < 0) { // negative exponent.
				if (lit.getCoefficient() == 1) {
					polyNomialString.append(" + " + "x^(" + lit.getExponent() + ")");
				} else if (lit.getCoefficient() == -1) {
					polyNomialString.append(" - " + "x^(" + lit.getExponent() + ")");
				} else if (lit.getCoefficient() < -1) {
					polyNomialString.append(" - " + lit.getCoefficient() * -1 + "x^(" + lit.getExponent() + ")");
				} else {
					polyNomialString.append(" + " + lit.getCoefficient() + "x^(" + lit.getExponent() + ")");
				}

			} else if (lit.getExponent() == 1) {// exponent of one.
				if (lit.getCoefficient() == 1) {
					polyNomialString.append(" + " + "x");
				} else if (lit.getCoefficient() == -1) {
					polyNomialString.append(" - " + "x");
				} else if (lit.getCoefficient() < -1) {
					polyNomialString.append(" - " + lit.getCoefficient() * -1 + "x");
				} else {
					polyNomialString.append(" + " + lit.getCoefficient() + "x");
				}

			} else { // regular exponent.
				if (lit.getCoefficient() == 1) {
					polyNomialString.append(" + " + "x^" + lit.getExponent());
				} else if (lit.getCoefficient() == -1) {
					polyNomialString.append(" - " + "x^" + lit.getExponent());
				} else if (lit.getCoefficient() < -1) {
					polyNomialString.append(" - " + lit.getCoefficient() * -1 + "x^" + lit.getExponent());
				} else {
					polyNomialString.append(" + " + lit.getCoefficient() + "x^" + lit.getExponent());
				}

			}
		}

	}

	private void startingTerm(StringBuilder polyNomialString, Iterator thisIter) {
		Literal lit = (Literal) thisIter.next();
		if (lit.getCoefficient() == 0) { // zero.

		} else if (lit.getExponent() == 0) { // a constant.
			polyNomialString.append(lit.getCoefficient());
		} else if (lit.getExponent() < 0) { // negative exponent.
			if (lit.getCoefficient() == 1) {
				polyNomialString.append("x^(" + lit.getExponent() + ")");
			} else if (lit.getCoefficient() == -1) {
				polyNomialString.append("-x^(" + lit.getExponent() + ")");
			} else {
				polyNomialString.append(lit.getCoefficient() + "x^(" + lit.getExponent() + ")");
			}
		} else if (lit.getExponent() == 1) { // exponent of one.
			if (lit.getCoefficient() == 1) {
				polyNomialString.append("x");
			}

			else if (lit.getCoefficient() == -1) {
				polyNomialString.append("-x");
			} else {

				polyNomialString.append(lit.getCoefficient() + "x");
			}

		} else { // regular exponent.
			if (lit.getCoefficient() == 1) {
				polyNomialString.append("x^" + lit.getExponent());
			} else if (lit.getCoefficient() == -1) {

				polyNomialString.append("-x^" + lit.getExponent());
			} else {

				polyNomialString.append(lit.getCoefficient() + "x^" + lit.getExponent());
			}

		}

	}

	/**
	 * plus takes the current polynomial and adds it to the polynomial that is
	 * passed over.
	 * 
	 * @param bPoly is the second polynomial passed over.
	 * @return the new sum of the two polynomials.
	 */
	public Polynomial plus(Polynomial bPoly) {
		Iterator first = terms.iterator(); // Iterator for the current list.
		LinkedList bPolyLinkedList = bPoly.terms;
		Iterator second = bPolyLinkedList.iterator();// Iterator for the second
														// list,
														// or bPoly's list.
		Polynomial newPoly = new Polynomial();
		LinkedList newList = newPoly.terms;
		Iterator third = newList.zeroth(); // Iterator for the third list, or
											// the new list.
		/**
		 * while first & second has a next, compare each terms and add the
		 * larger exponent onto the new list, If the exponents are equal, add
		 * the coefficients from the two terms then set it as one literal for
		 * the new list.
		 */
		while (first.hasNext() && second.hasNext()) {
			if (((Literal) first.getElement()).getExponent() > ((Literal) second.getElement()).getExponent()) {
				Literal newLiteral = new Literal
						(((Literal) first.getElement()).getCoefficient(),
						((Literal) first.getElement()).getExponent());
				newList.insert(newLiteral, third);
				third.next();
				first.next();
			} else if (((Literal) first.getElement()).getExponent() < ((Literal) second.getElement()).getExponent()) {
				Literal newLiteral = new Literal
						(((Literal) second.getElement()).getCoefficient(),
						((Literal) second.getElement()).getExponent());
				newList.insert(newLiteral, third);
				third.next();
				second.next();
			} else if (((Literal) first.getElement()).getExponent() == ((Literal) second.getElement()).getExponent()) {
				Literal newLiteral = new Literal(
						((Literal) first.getElement()).getCoefficient() + ((Literal) second.getElement()).getCoefficient(),
						((Literal) second.getElement()).getExponent());
				newList.insert(newLiteral, third);
				third.next();
				first.next();
				second.next();
			}

		}
		/**
		 * if the first list has extras, bring the extra terms into the new
		 * list.
		 */
		if (first.hasNext()) {
			while (first.hasNext()) {
				Literal newLiteral = new Literal
						(((Literal) first.getElement()).getCoefficient(),
						((Literal) first.getElement()).getExponent());
				newList.insert(newLiteral, third);
				first.next();
				third.next();
			}
			/**
			 * if the second list has extras, bring the extra terms into the new
			 * list.
			 */
		} else if (second.hasNext()) {
			while (second.hasNext()) {
				Literal newLiteral = new Literal
						(((Literal) second.getElement()).getCoefficient(),
						((Literal) second.getElement()).getExponent());
				newList.insert(newLiteral, third);
				second.next();
				third.next();
			}
		}

		return newPoly;
	}

	/**
	 * insertTerm inserts a new term to the current polynomial.
	 * 
	 * @param coefficient sets the coefficient for the new added term.
	 * @param exponent sets the exponent for the new added term.
	 */
	public void insertTerm(int coefficient, int exponent) {
		Literal lit = new Literal(coefficient, exponent);
		Iterator previous = terms.zeroth();
		Iterator current = terms.iterator();
		if (terms.isEmpty()) {
			terms.insert(lit, terms.zeroth());
		} else {

			while (current.hasNext() && lit.getExponent() < ((Literal) current.getElement()).getExponent()) {
				previous.next();
				current.next();
			}
			terms.insert(lit, previous);
			/**
			 * move the previous next to the newly constructed literal, and
			 * compare it with the literal after it. If they have the same
			 * exponent, add the coefficients.
			 */
			previous.next();
			if (previous.getNode() != null && current.getNode() != null) {
				if (((Literal) previous.getElement()).getExponent() == ((Literal) current.getElement()).getExponent()) {
					((Literal) previous.getElement()).setCoefficient(((Literal) current.getElement()).getCoefficient()
							+ ((Literal) previous.getElement()).getCoefficient());
					terms.remove(previous);

				}

			}

		}
	}

	/**
	 * negate() negates the current polynomial.
	 * 
	 * @return a new polynomial that is negated.
	 */
	public Polynomial negate() {
		Iterator curr = terms.iterator();
		Polynomial newPoly = new Polynomial();
		LinkedList newlist = newPoly.terms;
		Iterator third = newlist.zeroth(); // we say this is a third list or new
											// list
											// and not second, to not get
											// confused with earlier
											// examples.
		while (curr.hasNext()) {
			Literal newLiteral = new Literal
					(((Literal) curr.getElement()).getCoefficient() * -1,
					((Literal) curr.getElement()).getExponent());
			newlist.insert(newLiteral, third);
			third.next();
			curr.next();
		}
		return newPoly;

	}

	/**
	 * zeroPolynomial sets the current polynomial to empty.
	 */
	public void zeroPolynomial() {
		terms.makeEmpty();
	}

}
