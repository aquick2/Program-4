
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {
	private T start;
	private T end;
	private String label;

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
