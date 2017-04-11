import java.util.ArrayList;
import java.util.List;
/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          Program 4
// FILE:             IntervalTree.java
//
// TEAM:    Team 35a
// Authors: Jon Sharp, Lindsey Bohr, Allison Quick
// Author1: Jon Sharp, jsharp4@wisc.edu, jsharp4, 001
// Author2: Lindsey Bohr, bohr@wisc.edu, bohr, 001
// Author 3: Allison Quick, aquick2@wisc.edu, aquick2, 001
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class extends Comparable and implements IntervalTreeADT. It has the methods
 * getRoot, insert(interval), insert(interval, root), delete, deleteHelper, 
 *updateMaxEnd, findOverlapping, findOverlappingHelper, searchPoint, searchPointHelper,
 * getSize, getHeight, getHeightHelper, contains, containsHelper, printStats.
 *
 * <p>Bugs: None that we are aware
 *
 * @author Sharp, Bohr, Quick
 */
public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {

	private IntervalNode<T> root;

	@Override
	public IntervalNode<T> getRoot() {
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval)
			throws IllegalArgumentException {
		try {
			if (interval == null) throw new IllegalArgumentException();
			if (root == null) root = new IntervalNode<T>(interval);
			else {
				insert(interval, root);
			}
		} catch (IllegalArgumentException e) {
			System.out.print("Cannot insert a null or duplicate interval.");
		}
	}
	
private void insert(IntervalADT<T> interval, IntervalNode<T> root) {
	if (interval.compareTo(root.getInterval()) == 0) throw new IllegalArgumentException();
	if (interval.getEnd().compareTo(root.getMaxEnd()) > 0) {
		root.setMaxEnd(interval.getEnd());
	}
	if (interval.compareTo(root.getInterval()) > 0) {
		if (root.getRightNode() == null) {
			root.setRightNode(new IntervalNode(interval));
		}
		else {
			insert(interval, root.getRightNode());
		}
	}
	else {
		if (root.getLeftNode() == null) {
			root.setLeftNode(new IntervalNode(interval));
		}
		else {
			insert(interval, root.getLeftNode());
		}
	}
}

@Override
public void delete(IntervalADT<T> interval)
		throws IntervalNotFoundException, IllegalArgumentException {
	if (interval == null) throw new IllegalArgumentException();
	root = deleteHelper(root, interval);

}

@Override
public IntervalNode<T> deleteHelper(IntervalNode<T> node,
		IntervalADT<T> interval)
				throws IntervalNotFoundException, IllegalArgumentException {
	if (interval.compareTo(node.getInterval()) > 0) {
		if (node.getRightNode() == null) throw new IntervalNotFoundException(interval.toString());
		node.setRightNode(deleteHelper(node.getRightNode(), interval));
	}
	else if (interval.compareTo(node.getInterval()) < 0) {
		if (node.getLeftNode() == null) throw new IntervalNotFoundException(interval.toString());
		node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
	}
	else {
		if (node.getRightNode() != null) {
			node.setInterval(node.getSuccessor().getInterval());
			node.setRightNode(deleteHelper(node.getRightNode(), node.getSuccessor().getInterval()));
		}
		else {
			return node.getLeftNode();
		}
	}
	updateMaxEnd(node);
	return node;
}

private void updateMaxEnd(IntervalNode<T> node) {
	if (node.getLeftNode() != null && 
			node.getMaxEnd().compareTo(node.getLeftNode().getMaxEnd()) < 0) {
		node.setMaxEnd(node.getLeftNode().getMaxEnd());
	}
	if (node.getRightNode() != null &&
			node.getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) < 0) {
		node.setMaxEnd(node.getRightNode().getMaxEnd());
	}
}

@Override
public List<IntervalADT<T>> findOverlapping(
		IntervalADT<T> interval) {
	List<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
	findOverlappingHelper(root, interval, list);
	return list;
}
private void findOverlappingHelper(IntervalNode node, IntervalADT interval, List<IntervalADT<T>> result) {
	if (node == null) return;
	if (node.getInterval().overlaps(interval)) {
		result.add(node.getInterval());
	}
	if (node.getLeftNode() != null) {
		if (node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 0) {
			findOverlappingHelper(node.getLeftNode(), interval, result);
		}
	}
	if (node.getRightNode() != null) {
		if (node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 0) {
			findOverlappingHelper(node.getRightNode(), interval, result);
		}
	}
}

@Override
public List<IntervalADT<T>> searchPoint(T point) {
	if (point == null) throw new IllegalArgumentException();
	List<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
	searchPointHelper(root, point, list);
	return list;
}

public void searchPointHelper(IntervalNode<T> node, T point, List<IntervalADT<T>> list) {
	if (node == null) return;
	if (node.getInterval().contains(point)) {
		list.add(node.getInterval());
	}
	searchPointHelper(node.getLeftNode(), point, list);
	searchPointHelper(node.getRightNode(), point, list);
}

@Override
public int getSize() {
	return getSize(root);
}
private int getSize(IntervalNode<T> node) {
	if (node.getLeftNode() == null && node.getRightNode() == null) {
		return 1;
	}
	if (node.getLeftNode() == null) {
		return 1 + getSize(node.getRightNode());
	}
	if (node.getRightNode() == null) {
		return 1 + getSize(node.getLeftNode());
	}
	return 1 + getSize(node.getLeftNode()) + getSize(node.getRightNode());
}

@Override
public int getHeight() {
	if (root == null) {
		return 0;
	}
	return getHeightHelper(root);
}
private int getHeightHelper(IntervalNode<T> node) {
	if (node.getLeftNode() == null && node.getRightNode() == null) {
		return 1;
	}
	if (node.getLeftNode() == null) {
		return 1 + getHeightHelper(node.getRightNode());
	}
	if (node.getRightNode() == null) {
		return 1 + getHeightHelper(node.getLeftNode());
	}
	return 1 + Math.max(getHeightHelper(node.getLeftNode()), getHeightHelper(node.getRightNode()));
}

@Override
public boolean contains(IntervalADT<T> interval) {
	if (interval == null) throw new IllegalArgumentException();
	return containsHelper(root, interval);
}
private boolean containsHelper(IntervalNode<T> node, IntervalADT<T> interval) {
	if (node.getInterval().compareTo(interval) == 0) {
		return true;
	}
	if (node == null) {
		return false;
	}
	return containsHelper(node.getLeftNode(), interval) || 
			containsHelper(node.getRightNode(), interval);
}

@Override
public void printStats() {
	System.out.println("-----------------------------------------");
	System.out.println("Height: " + getHeight());
	System.out.println("Size: " + getSize());
	System.out.println("-----------------------------------------");		
}

}
