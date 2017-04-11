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
 * @param T start (Describe the first parameter here)
 * @param T end (Do the same for each additional parameter)
 * @param String label
 */
    public Interval(T start, T end, String label) {
        this.start = start;
        this.end = end;
        this.label = label;
    }

    @Override
    public T getStart() {
        return start;
    }

    @Override
    public T getEnd() {
        return end;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean overlaps(IntervalADT<T> other) throws IllegalArgumentException {
        if (other == null) throw new IllegalArgumentException();
        if (end.compareTo(other.getStart()) < 0 || start.compareTo(other.getEnd()) > 0) {
        	return false;
        }
        return true;
    }

    @Override
    public boolean contains(T point) {
        if (start.compareTo(point) <= 0 && end.compareTo(point) >= 0) {
        	return true;
        }
        return false;
    }

    @Override
    public int compareTo(IntervalADT<T> other) {
        if (start.compareTo(other.getStart()) > 0) {
        	return 1;
        }
        if (start.compareTo(other.getStart()) < 0) {
        	return -1;
        }
        if (end.compareTo(other.getEnd())> 0) {
        	return 1;
        }
        if (end.compareTo(other.getEnd()) < 0) {
        	return -1;
        }
        return 0;
    }

}
