import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	private IntervalNode<T> root;

	@Override
	public IntervalNode<T> getRoot() {
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		if (interval == null) throw new IllegalArgumentException();
		insert(interval, root);

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
			node.setLeftNode(deleteHelper(node.getRightNode(), interval));
		}
		else if (interval.compareTo(node.getInterval()) < 0) {
			if (node.getLeftNode() == null) throw new IntervalNotFoundException(interval.toString());
			node.setRightNode(deleteHelper(node.getLeftNode(), interval));
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
		if (node.getMaxEnd().compareTo(node.getLeftNode().getMaxEnd()) < 0) {
			node.setMaxEnd(node.getLeftNode().getMaxEnd());
		}
		if (node.getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) < 0) {
			node.setMaxEnd(node.getRightNode().getMaxEnd());
		}
	}

	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		// TODO Auto-generated method stub
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
		return getHeight(root);
	}
	private int getHeight(IntervalNode<T> node) {
		if (node.getLeftNode() == null || node.getRightNode() == null) {
			return 1;
		}
		if (node.getLeftNode() == null) {
			return 1 + getHeight(node.getRightNode());
		}
		if (node.getRightNode() == null) {
			return 1 + getHeight(node.getLeftNode());
		}
		return 1 + Math.max(getHeight(node.getLeftNode()), getHeight(node.getRightNode()));
	}

	@Override
	public boolean contains(IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public void printStats() {
		// TODO Auto-generated method stub

	}

}
