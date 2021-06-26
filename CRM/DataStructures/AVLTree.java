package DataStructures;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * AVLTree Class
 *
 * @author: Elliot B. Koffman
 * */

public class AVLTree<E
        extends Comparable<E>>
        extends BinarySearchTreeWithRotate<E> {

    private boolean increase;
    private boolean decrease;

    private static class AVLNode<E> extends Node<E> {
        public static final int LEFT_HEAVY = -1;
        public static final int BALANCED = 0;
        public static final int RIGHT_HEAVY = 1;
        private int balance;

        public AVLNode(E item) {
            super(item);
            balance = BALANCED;
        }

        public BinaryTree<E> getLeftSubtree(){
            if(left != null) return new BinaryTree<E> (left);
            return null;
        }

        public BinaryTree<E> getRightSubtree(){
            if(right != null) return new BinaryTree<E> (right);
            return null;
        }


        public String toString() {
            return balance + ": " + super.toString();
        }
    }

    public boolean add(E item) {
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }

        if (item.compareTo(localRoot.data) == 0) {
            // Item is already in the tree.
            increase = false;
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            // item < data
            localRoot.left = add((AVLNode<E>) localRoot.left, item);

            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot;
        } else {

            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    return rebalanceRight(localRoot);
                } else {
                    return localRoot;
                }
            } else {
                return localRoot;
            }
        }

    }

    public E delete(E item) {
        decrease = false;
        root = delete((AVLNode<E>) root, item);
        return deleteReturn;
    }

    private AVLNode<E> delete(AVLNode<E> localRoot, E item) {
        if (localRoot == null) { // item is not in tree
            deleteReturn = null;
            return localRoot;
        }
        if (item.compareTo(localRoot.data) == 0) {
            // item is in the tree -- need to remove it
            deleteReturn = localRoot.data;
            return findReplacementNode(localRoot);
        } else if (item.compareTo(localRoot.data) < 0) {
            // item is < localRoot.data
            localRoot.left = delete((AVLNode<E>) localRoot.left, item);
            if (decrease) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    return rebalanceRightL((AVLNode<E>) localRoot);
                } else {
                    return localRoot;
                }
            } else {
                return localRoot;
            }
        } else {
            // item is > localRoot.data
            localRoot.right = delete((AVLNode<E>) localRoot.right, item);
            if (decrease) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    return rebalanceLeftR(localRoot);
                } else {
                    return localRoot;
                }
            } else {
                return localRoot;
            }
        }
    }

    private AVLNode<E> findReplacementNode(AVLNode<E> node) {
        if (node.left == null) {
            decrease = true;
            return (AVLNode<E>) node.right;
        } else if (node.right == null) {
            decrease = true;
            return (AVLNode<E>) node.left;
        } else {
            if (node.left.right == null) {
                node.data = node.left.data;
                node.left = node.left.left;
                incrementBalance(node);
                return node;
            } else {
                node.data = findLargestChild((AVLNode<E>) node.left);
                if (((AVLNode<E>) node.left).balance < AVLNode.LEFT_HEAVY) {
                    node.left = rebalanceLeft((AVLNode<E>) node.left);
                }
                if (decrease) {
                    incrementBalance(node);
                }
                return node;
            }
        }
    }

    private E findLargestChild(AVLNode<E> parent) {
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            decrementBalance(parent);
            return returnValue;
        } else {
            E returnValue = findLargestChild((AVLNode<E>) parent.right);
            if (((AVLNode<E>) parent.right).balance < AVLNode.LEFT_HEAVY) {
                parent.right = rebalanceLeft((AVLNode<E>) parent.right);
            }
            if (decrease) {
                decrementBalance(parent);
            }
            return returnValue;
        }
    }

    private void incrementBalance(AVLNode<E> node) {
        node.balance++;
        if (node.balance > AVLNode.BALANCED) {
            increase = true;
            decrease = false;
        } else {
            increase = false;
            decrease = true;
        }
    }

    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
        // Obtain reference to right child
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        // See if right-left heavy
        if (rightChild.balance < AVLNode.BALANCED) {
            // Obtain reference to right-left child
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
      /* Adjust the balances to be their new values after
         the rotates are performed.
       */
            if (rightLeftChild.balance > AVLNode.BALANCED) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } else if (rightLeftChild.balance < AVLNode.BALANCED) {
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            } else {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            increase = false;
            decrease = true;
            // Perform double rotation
            localRoot.right = rotateRight(rightChild);
            return (AVLNode<E>) rotateLeft(localRoot);
        } else {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            increase = false;
            decrease = true;
            // Now rotate the
            return (AVLNode<E>) rotateLeft(localRoot);
        }
    }

    private AVLNode<E> rebalanceLeftR(AVLNode<E> localRoot) {
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        if (leftChild.balance > AVLNode.BALANCED) {
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            if (leftRightChild.balance < AVLNode.BALANCED) {
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            } else if (leftRightChild.balance > AVLNode.BALANCED) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else {
                leftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            increase = false;
            decrease = true;
            // Perform double rotation
            localRoot.left = rotateLeft(leftChild);
            return (AVLNode<E>) rotateRight(localRoot);
        }
        if (leftChild.balance < AVLNode.BALANCED) {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            increase = false;
            decrease = true;
        } else {

            leftChild.balance = AVLNode.RIGHT_HEAVY;
            localRoot.balance = AVLNode.LEFT_HEAVY;
        }
        // Now rotate the
        return (AVLNode<E>) rotateRight(localRoot);
    }

    private AVLNode<E> rebalanceRightL(AVLNode<E> localRoot) {
        // Obtain reference to right child
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        // See if right-left heavy
        if (rightChild.balance < AVLNode.BALANCED) {
            // Obtain reference to right-left child
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
            if (rightLeftChild.balance > AVLNode.BALANCED) {
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            } else if (rightLeftChild.balance < AVLNode.BALANCED) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } else {
                rightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            increase = false;
            decrease = true;
            // Perform double rotation
            localRoot.right = rotateRight(rightChild);
            return (AVLNode<E>) rotateLeft(localRoot);
        }
        if (rightChild.balance > AVLNode.BALANCED) {

            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            increase = false;
            decrease = true;
        } else {

            rightChild.balance = AVLNode.LEFT_HEAVY;
            localRoot.balance = AVLNode.RIGHT_HEAVY;
        }
        // Now rotate the
        return (AVLNode<E>) rotateLeft(localRoot);
    }

    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
        // Obtain reference to left child.
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        // See whether left-right heavy.
        if (leftChild.balance > AVLNode.BALANCED) {
            // Obtain reference to left-right child.
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            if (leftRightChild.balance < AVLNode.BALANCED) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else {
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform left rotation.
            localRoot.left = rotateLeft(leftChild);
        } else { //Left-Left case
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root right.
        return (AVLNode<E>) rotateRight(localRoot);
    }

    private void decrementBalance(AVLNode<E> node) {
        node.balance--;
        if (node.balance == AVLNode.BALANCED) {
            increase = false;
        }
    }

    /*
     * The codes below this line are written by me.
     *
     * */

    /**
     * Gets an item and returns a binary tree as a root of this item
     * @param item
     * @return BinaryTree with the root is the given item.
     */
    public BinaryTree<E> getSubtree(E item){
        if(root != null) return findNode((AVLNode<E>) root , item);
        return null;
    }

    /***
     * Gets an item and root and returns a binary tree as a root of this item
     * @param subTree
     * @param item
     * @return BinaryTree with the root is the given item.
     */
    private BinaryTree<E> findNode(AVLNode<E> subTree , E item){
        if(subTree == null) return null;

        if(subTree.data.equals(item)){
            return new BinaryTree<E>(subTree);
        }
        else {
            if(subTree.left != null){
                return findNode((AVLNode<E>)subTree.left , item);
            }
            if(subTree.right != null){
                return findNode((AVLNode<E>)subTree.right , item);
            }
        }
        return null;
    }

    /***
     * Creates an array with AVLTree's elements
     * @return An arraylist with AVLTree elements
     * @throws NullPointerException
     */
    public ArrayList<E> toArray() throws NullPointerException{
        if(root == null) throw new NullPointerException();
        cleanArray();
        buildArray((AVLNode<E>) root , null , false);
        return tempArray;
    }

    /***
     * Creates an array with AVLTree's elements which is less than or higher than the given item.
     * @param item
     * @param isMin
     * @return an array with AVLTree's elements which is less than or higher than the given item.
     * @throws NullPointerException
     * @throws NoSuchElementException
     */
    public ArrayList<E> toArray(E item , boolean isMin) throws NullPointerException, NoSuchElementException {
        if(root == null) throw new NullPointerException();
        cleanArray();
        if(item == null) throw new NoSuchElementException();
        buildArray((AVLNode<E>) root , item , isMin);
        return tempArray;
    }


    private ArrayList<E> tempArray = new ArrayList<E>();

    private ArrayList<E> buildArray(AVLNode<E> subTree,E item , boolean isMin ){
        if(subTree == null) return tempArray;

        if(item == null && !isMin) tempArray.add(subTree.data);
        else if(isMin && subTree.data.compareTo(item) > 0) tempArray.add(subTree.data);
        else if(!isMin && subTree.data.compareTo(item) < 0) tempArray.add(subTree.data);

        if(subTree.left != null){
            buildArray((AVLNode<E>) subTree.left , item , isMin);
        }
        if(subTree.right != null){
            buildArray((AVLNode<E>) subTree.right , item , isMin);
        }
        return tempArray;
    }

    private void cleanArray(){
        tempArray.clear();
    }

    public Iterator iterator(){
        return new Iterator();
    }

    /**
     * Iterator Class
     */
    public class Iterator{
        ArrayList<E> arr = (ArrayList<E>) toArray();
        int size = arr.size();
        int curr = 0;
        int lastItem = -1;

        public E next(){
            if(!hasNext()) return null;
            curr++;
            return arr.get(++lastItem);
        }

        public E remove(){
            curr--;
            return arr.remove(lastItem--);
        }

        public boolean hasNext(){
            return size != curr;
        }

    }


}
