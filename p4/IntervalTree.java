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
/**
 * This accessor method overrides IntervalTreeADT to return root of IntervalNode.
 *
 * @return root root of IntervalNode
 */
	@Override
	public IntervalNode<T> getRoot() {
		return root;
	} //closes getRoot
/**
 * This method inserts an interval in the tree, overriding IntervalTreeADT.
 * Calls a helper method to help with recursion.
 * 
 * @param interval the interval (item) to insert in the tree.
 * @throws IllegalArgumentException if interval is null or is found 
 * to be a duplicate of an existing interval in this tree.            
 */
	@Override
	public void insert(IntervalADT<T> interval)
			throws IllegalArgumentException {
		try {
			if (interval == null) throw new IllegalArgumentException();
			if (root == null) root = new IntervalNode<T>(interval);
			else {
				insert(interval, root);
			}//closes try block
		} catch (IllegalArgumentException e) {
			System.out.print("Cannot insert a null or duplicate interval.");
		} //closes catch block
	} //closes insert
/**
 * This method recursively inserts interval in the tree, called by insert(interval).
 * 
 * @param interval the interval (item) to insert in the tree.
 * @param root the root node of the interval tree
 * @throws IllegalArgumentException if interval is null or is found 
 * to be a duplicate of an existing interval in this tree.            
 */
private void insert(IntervalADT<T> interval, IntervalNode<T> root) {
	if (interval.compareTo(root.getInterval()) == 0) throw new IllegalArgumentException();
	if (interval.getEnd().compareTo(root.getMaxEnd()) > 0) {
		root.setMaxEnd(interval.getEnd());
	} //closes first if statement
	if (interval.compareTo(root.getInterval()) > 0) {
		if (root.getRightNode() == null) {
			root.setRightNode(new IntervalNode(interval));
		} //closes if (root.getRightNode() == null)
		else {
			insert(interval, root.getRightNode());
		} //closes else statement
	} //closes if (interval.compareTo(root.getInterval()) > 0)
	else {
		if (root.getLeftNode() == null) {
			root.setLeftNode(new IntervalNode(interval));
		} //closes inner if statement
		else {
			insert(interval, root.getLeftNode());
		} //closes inner else
	} //closes else statement
} //closes insert(interval, root)
/**
 * Overrides IntervalTreeADT and calls deleteHelper. 
 * Delete the node containing the specified interval in the tree.
 * Delete operations must also update the maxEnd of interval nodes
 * that change as a result of deletion.  
 *  
 * @param interval the interval (item) to insert in the tree.
 * @throws IllegalArgumentException if interval is null
 * @throws IntervalNotFoundException if the interval does not exist.
 */
@Override
public void delete(IntervalADT<T> interval)
		throws IntervalNotFoundException, IllegalArgumentException {
	if (interval == null) throw new IllegalArgumentException();
	root = deleteHelper(root, interval);

} //closes delete
/** 
 * Recursive helper method for the delete operation. Calls updateMaxEnd
 * 
 * @param node the interval node that is currently being checked.
 * @param interval the interval to delete.
 * @throws IllegalArgumentException if the interval is null.
 * @throws IntervalNotFoundException
 *             if the interval is not null, but is not found in the tree.
 * @return Root of the tree after deleting the specified interval.
 */
@Override
public IntervalNode<T> deleteHelper(IntervalNode<T> node,
		IntervalADT<T> interval)
				throws IntervalNotFoundException, IllegalArgumentException {
	if (interval.compareTo(node.getInterval()) > 0) {
		if (node.getRightNode() == null) throw new IntervalNotFoundException(interval.toString());
		node.setRightNode(deleteHelper(node.getRightNode(), interval));
	} //closes if (interval.compareTo(node.getInterval()) > 0)
	else if (interval.compareTo(node.getInterval()) < 0) {
		if (node.getLeftNode() == null) throw new IntervalNotFoundException(interval.toString());
		node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
	} //closes else if statement
	else {
		if (node.getRightNode() != null) {
			node.setInterval(node.getSuccessor().getInterval());
			node.setRightNode(deleteHelper(node.getRightNode(), node.getSuccessor().getInterval()));
		} //closes if (node.getRightNode() != null)
		else {
			return node.getLeftNode();
		} //closes inner else
	} //closes outer else statement
	updateMaxEnd(node);
	return node;
} //closes deleteHelper
/**
 * Called by deleteHelper to update the MaxEnd after a node is deleted.
 *  
 * @param node the interval node that is currently being checked.
 */
private void updateMaxEnd(IntervalNode<T> node) {
	if (node.getLeftNode() != null && 
			node.getMaxEnd().compareTo(node.getLeftNode().getMaxEnd()) < 0) {
		node.setMaxEnd(node.getLeftNode().getMaxEnd());
	} //closes first if statement
	if (node.getRightNode() != null &&
			node.getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) < 0) {
		node.setMaxEnd(node.getRightNode().getMaxEnd());
	} //closes second if statement
} //closes updateMaxEnd
/**
 * Find and return a list of all intervals that overlap with the given interval. 
 * This override method calls findOverlappingHelper to find the overlap recursively.
 *  
 * @param interval the interval to search for overlapping
 * 
 * @return list of intervals that overlap with the input interval.
 */
@Override
public List<IntervalADT<T>> findOverlapping(
		IntervalADT<T> interval) {
	List<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
	findOverlappingHelper(root, interval, list);
	return list;
} //closes findOverlapping
/**
 * Find and return a list of all intervals that overlap with the given interval. 
 * Called by findOverlapping. Uses recursion.
 *
 * @param node the interval node that is currently being checked.
 * @param interval the interval to search for overlapping
 * @param result list of intervals that are being searched for overlap
 */
private void findOverlappingHelper(IntervalNode node, IntervalADT interval, List<IntervalADT<T>> result) {
	if (node == null) return;
	if (node.getInterval().overlaps(interval)) {
		result.add(node.getInterval());
	} //closes if (node.getInterval().overlaps(interval))
	if (node.getLeftNode() != null) {
		if (node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 0) {
			findOverlappingHelper(node.getLeftNode(), interval, result);
		} //closes inner if statement
	} //closes if (node.getLeftNode() != null)
	if (node.getRightNode() != null) {
		if (node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 0) {
			findOverlappingHelper(node.getRightNode(), interval, result);
		} //closes inner if statement
	} //closes if (node.getRightNode() != null)
} //closes findOverlappingHelper
/**
 * Search and return a list of all intervals containing a given point. 
 * This method may return an empty list. Calls searchPointHelper to perform
 * recursive search.
 * 
 * @throws IllegalArgumentException if point is null
 * @param point
 *            input point to search for.
 * @return List of intervals containing the point.
 */
@Override
public List<IntervalADT<T>> searchPoint(T point) {
	if (point == null) throw new IllegalArgumentException();
	List<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
	searchPointHelper(root, point, list);
	return list;
} //closes searchPoint
/**
 * Search and return a list of all intervals containing a given point. 
 * Recursive method that is called by searchPoint.
 * 
 * @throws IllegalArgumentException if point is null
 * @param node the interval node that is currently being checked.
 * @param point
 *            input point to search for.
 * @param list  list of intervals that is being searched
 * @return List of intervals containing the point.
 */
public void searchPointHelper(IntervalNode<T> node, T point, List<IntervalADT<T>> list) {
	if (node == null) return;
	if (node.getInterval().contains(point)) {
		list.add(node.getInterval());
	} //closes if (node.getInterval().contains(point))
	searchPointHelper(node.getLeftNode(), point, list);
	searchPointHelper(node.getRightNode(), point, list);
} //closes searchPointHelper
/**
 * Get the size of the interval tree. The size is the total number of
 * nodes present in the tree. Calls getSize(node) to perform recursion.
 * 
 * @return int number of nodes in the tree.
 */
@Override
public int getSize() {
	return getSize(root);
} //closes getSize
/**
 * Get the size of the interval tree. The size is the total number of
 * nodes present in the tree. Calls getSize(node) to perform recursion.
 *
 * @param node the interval node (root) that will start the recursion
 * @return int number of nodes in the tree.
 */	
private int getSize(IntervalNode<T> node) {
	if (node.getLeftNode() == null && node.getRightNode() == null) {
		return 1;
	} //closes first if statement
	if (node.getLeftNode() == null) {
		return 1 + getSize(node.getRightNode());
	} //closes if (node.getLeftNode() == null)
	if (node.getRightNode() == null) {
		return 1 + getSize(node.getLeftNode());
	} //closes if (node.getRightNode() == null)
	return 1 + getSize(node.getLeftNode()) + getSize(node.getRightNode());
} //closes getSize(node)
/**
 * Return the height of the interval tree at the root of the tree. 
 * Calls getHeightHelper to solve the height recursively.
 * 
 * @return the height of the interval tree
 */
@Override
public int getHeight() {
	if (root == null) {
		return 0;
	} //closes if statement
	return getHeightHelper(root);
} //closes getHeight
/**
 * Return the height of the interval tree at the root of the tree. 
 * Called by getHeight to solve the height recursively.
 * 
 * @param node the interval node (root) that will start the recursion
 * @return the height of the interval tree
 */	
private int getHeightHelper(IntervalNode<T> node) {
	if (node.getLeftNode() == null && node.getRightNode() == null) {
		return 1;
	} // closes first if statement
	if (node.getLeftNode() == null) {
		return 1 + getHeightHelper(node.getRightNode());
	} //closes if (node.getLeftNode() == null)
	if (node.getRightNode() == null) {
		return 1 + getHeightHelper(node.getLeftNode());
	} //closes if (node.getRightNode() == null)
	return 1 + Math.max(getHeightHelper(node.getLeftNode()), getHeightHelper(node.getRightNode()));
} //closes getHeightHelper

/**
 * Returns true if the tree contains an exact match for the start and end of the given interval.
 * The label is not considered for this operation. Calls containsHelper to solve recursively.
 *  
 * @param interval
 * 				target interval for which to search the tree for. 
 * @return boolean 
 * 			   	representing if the tree contains the interval.
 * @throws IllegalArgumentException
 *             	if interval is null.
 */
@Override
public boolean contains(IntervalADT<T> interval) {
	if (interval == null) throw new IllegalArgumentException();
	return containsHelper(root, interval);
} //closes contains

/**
 * Returns true if the tree contains an exact match for the start and end of the given interval.
 * The label is not considered for this operation. Called by contains to solve recursively.
 *  
 * @param interval
 * 		target interval for which to search the tree for. 
 * @return boolean 
 * 		representing if the tree contains the interval.
 * @throws IllegalArgumentException
 *             	if interval is null.
 */	
private boolean containsHelper(IntervalNode<T> node, IntervalADT<T> interval) {
	if (node.getInterval().compareTo(interval) == 0) {
		return true;
	} //closes if (node.getInterval().compareTo(interval) == 0)
	if (node == null) {
		return false;
	} //closes if (node==null)
	return containsHelper(node.getLeftNode(), interval) || 
			containsHelper(node.getRightNode(), interval);
} //closes containsHelper
/**
 * Print the statistics of the tree in the specified format.
  */
@Override
public void printStats() {
	System.out.println("-----------------------------------------");
	System.out.println("Height: " + getHeight());
	System.out.println("Size: " + getSize());
	System.out.println("-----------------------------------------");		
} //closes printStats

} //closes IntervalTree
