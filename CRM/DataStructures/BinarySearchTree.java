package DataStructures;


/**
 * BinarySearchTree Class
 *
 * @author: Elliot B. Koffman
 * */

public class BinarySearchTree<E
        extends Comparable<E>>
        extends BinaryTree<E>
        implements SearchTree<E> {
    protected boolean addReturn;

    protected E deleteReturn;

    public E find(E target) {
        return find(root, target);
    }

    private E find(Node<E> localRoot, E target) {
        if (localRoot == null)
            return null;

        // Compare the target with the data field at the root.
        int compResult = target.compareTo(localRoot.data);
        if (compResult == 0)
            return localRoot.data;
        else if (compResult < 0)
            return find(localRoot.left, target);
        else
            return find(localRoot.right, target);
    }

    public boolean add(E item) {
        root = add(root, item);
        return addReturn;
    }

    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree � insert it.
            addReturn = true;
            return new Node<E>(item);
        } else if (item.compareTo(localRoot.data) == 0) {
            // item is equal to localRoot.data
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            // item is less than localRoot.data
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        } else {
            // item is greater than localRoot.data
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }

    public E delete(E target) {
        root = delete(root, target);
        return deleteReturn;
    }

    private Node<E> delete(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree.
            deleteReturn = null;
            return localRoot;
        }

        // Search for item to delete.
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data.
            localRoot.left = delete(localRoot.left, item);
            return localRoot;
        } else if (compResult > 0) {
            // item is larger than localRoot.data.
            localRoot.right = delete(localRoot.right, item);
            return localRoot;
        } else {
            // item is at local root.
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {
                // If there is no left child, return right child
                // which can also be null.
                return localRoot.right;
            } else if (localRoot.right == null) {
                // If there is no right child, return left child.
                return localRoot.left;
            } else {
                // Node being deleted has 2 children, replace the data
                // with inorder predecessor.
                if (localRoot.left.right == null) {
                    // The left child has no right child.
                    // Replace the data with the data in the
                    // left child.
                    localRoot.data = localRoot.left.data;
                    // Replace the left child with its left child.
                    localRoot.left = localRoot.left.left;
                    return localRoot;
                } else {
                    // Search for the inorder predecessor (ip) and
                    // replace deleted node�s data with ip.
                    localRoot.data = findLargestChild(localRoot.left);
                    return localRoot;
                }
            }
        }
    }

    public boolean remove(E target) {
        return delete(target) != null;
    }

    public boolean contains(E target) {
        return find(target) != null;
    }

    private E findLargestChild(Node<E> parent) {
        // If the right child has no right child, it is
        // the inorder predecessor.
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        } else {
            return findLargestChild(parent.right);
        }
    }

    /**
     * Checks that the given BinarySearchTree is a RedBlack or an AVL. Prints the result
     * @param tree
     */
    public void checkTree(BinarySearchTree tree){
        if(tree.root == null) {
            System.out.println("There is no root.");
            return;
        }

        if(isAVL(tree.root) && isRed(tree.root)){
            System.out.println("This tree is both Red-Black and AVL");
        }
        else if(isAVL(tree.root)){
            System.out.println("This tree is AVL");
        }
        else if(isRed(tree.root)){
            System.out.println("This tree is Red-Black");
        }
        else{
            System.out.println("This tree is neither AVL nor Red-Black");
        }

    }

    /**
     * Checks that given tree is whether Red Black or not.
     * @param subTree
     * @return true if the given tree is Red Black
     */
    private boolean isRed(Node subTree){
        if(subTree == null) return true;

        int max_heightL = 0 , min_heightL = 0; //For Left Subtree
        int max_heightR = 0 , min_heightR = 0; //For Right subtree

        if(subTree.left != null){
            max_heightL = height(subTree.left , 0 , true);
            min_heightL = height(subTree.left , 0 , false);
        }
        if(subTree.right != null){
            max_heightR = height(subTree.right, 0, true);
            min_heightR = height(subTree.right , 0 , false);
        }

        int minHeight = min_heightL >= min_heightR ? min_heightR : min_heightL;
        int maxHeight = max_heightL >= max_heightR ? max_heightL : max_heightR;

        if(maxHeight - minHeight > 2) return false;
        else{
            boolean resultRight = true,resultLeft = true;
            if (subTree.right != null)
                resultRight = isRed(subTree.right);
            if(subTree.left != null)
                resultLeft = isRed(subTree.left);
            return resultRight && resultLeft;
        }
    }

    /**
     * Checks that given tree is whether AVL or not.
     * @param subTree
     * @return true if the given tree is AVL
     */
    private boolean isAVL(Node subTree){
        if(subTree == null) return true;

        int left = 0, right = 0;
        if(subTree.left != null)
            left = height(subTree.left , 0 , true);
        if(subTree.right != null)
            right = height(subTree.right , 0 , true);

        if(Math.abs(right - left) >= 2)
            return false;
        else {
            boolean resultRight = true,resultLeft = true;
            if (subTree.right != null)
                resultRight = isAVL(subTree.right);
            if(subTree.left != null)
                resultLeft = isAVL(subTree.left);

            return resultRight && resultLeft;

        }
    }

    /**
     * Returns the height of the given node.
     * @param subTree
     * @param height
     * @param longest
     * @return Returns the height of the given node.
     */
    private int height(Node subTree , int height , boolean longest){
        if(subTree == null) return 0;

        int left = 0, right = 0;
        if(subTree.left != null)
            left = height(subTree.left , height , longest);
        if(subTree.right != null)
            right = height(subTree.right , height , longest);
        if(left == 0 && right == 0)
            return height + 1;

        if(longest)
            return left > right ? left + 1 : right + 1;
        else
            return left > right ? right + 1 : left + 1;
    }


}