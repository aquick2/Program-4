/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          Program 4
// FILE:             Interval.java
//
// TEAM:    Team 35a
// Authors: Jon Sharp, Lindsey Bohr, Allison Quick
// Author1: Jon Sharp, jsharp4@wisc.edu, jsharp4, 001
// Author2: Lindsey Bohr, bohr@wisc.edu, bohr, 001
// Author 3: Allison Quick, aquick2@wisc.edu, aquick2, 001
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class extends Comparable and implements IntervalADT. It has the methods
 * getStart, getEnd, getLabel, overlaps, contains, and compareTo. 
 *
 * <p>Bugs: None that we are aware
 *
 * @author Sharp, Bohr, Quick
 */
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {
	private T start;
	private T end;
	private String label;
	/**
	 * This constructor assigns the start, end, and label based on what is being
	 *passed in. 
	 *
	 * @param T start 
	 *	Start of the interval
	 * @param T end 
	 *	End of the interval
	 * @param String label
	 *	Label of the interval
	 */
	public Interval(T start, T end, String label) {
		try {
			if (start.compareTo(end) > 0) throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			System.out.println("ERROR: Start cannot be greater than end of interval.");
		}
		this.start = start;
		this.end = end;
		this.label = label;
	} //closes the constructor
	/**
	 * This accessor method overrides the IntervalADT to return the  
	 * start object of the Interval.
	 *
	 *@return start
	 *	The start of the interval
	 */
	@Override
	public T getStart() {
		return start;
	} //closes getStart
	/**
	 * This accessor method overrides the IntervalADT to return the  
	 * end object of the Interval.
	 *
	 *@return end
	 *	The end of the interval
	 */
	@Override
	public T getEnd() {
		return end;
	} //closes getEnd
	/**
	 * This accessor method overrides the IntervalADT to return the  
	 * label object of the Interval.
	 *
	 *@return label
	 *	The label of the interval
	 */
	@Override
	public String getLabel() {
		return label;
	} //closes getLabel
	/**
	 * This boolean method overrides IntervalADT to return true if 
	 * the interval overlaps with the other interval being passed in as a parameter.
	 * 
	 * @param other target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
	@Override
	public boolean overlaps(IntervalADT<T> other) throws IllegalArgumentException {
		try {
			if (other == null) throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			System.out.println("ERROR: Cannot overlap with a null interval.");
		}
		if (end.compareTo(other.getStart()) < 0 || start.compareTo(other.getEnd()) > 0) {
			return false;
		} //closes if statement
		return true;
	} //closes overlaps
	/**
	 * This method overrides IntervalADT and returns true if given point lies inside the interval.
	 * 
	 * @param point
	 *            to search
	 * @return true if it contains the point
	 */
	@Override
	public boolean contains(T point) {
		if (start.compareTo(point) <= 0 && end.compareTo(point) >= 0) {
			return true;
		} //closes if statement
		return false;
	} //closes contains
	/**
	 * This method overrides IntervalADT to compare this interval with the other 
	 * and return a negative value if this interval comes before the "other" interval.  
	 * Intervals are compared first on their start time.  The end time is only considered
	 * if the start time is the same.
	 * 
	 * @param other
	 *            the second interval to which compare this interval with
	 *            
	 * @return negative if this interval's comes before the other interval, 
	 * positive if this interval comes after the other interval,
	 * and 0 if the intervals are the same.  See above for details.
	 */
	@Override
	public int compareTo(IntervalADT<T> other) {
		if (start.compareTo(other.getStart()) > 0) {
			return 1;
		} //closes first if statement
		if (start.compareTo(other.getStart()) < 0) {
			return -1;
		} //closes second if statement
		if (end.compareTo(other.getEnd())> 0) {
			return 1;
		} //closes third if statement
		if (end.compareTo(other.getEnd()) < 0) {
			return -1;
		} //closes last if statement
		return 0;
	} //closes compareTo
	/**
	 * This method combines the label, start, and end into a string.
	 * Format: Label [start, end]
	 * @return The string with the above format
	 */
	public String toString() {
		return getLabel() + " [" + getStart() +", " + getEnd() + "]";
	} //closes toString

} //closes Interval class
