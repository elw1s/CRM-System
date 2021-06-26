package DataStructures;

/**
 * BinarySearchTreeWithRotate Class
 *
 * @author: Elliot B. Koffman
 * */

public class BinarySearchTreeWithRotate<E
        extends Comparable<E>>
        extends BinarySearchTree<E> {
    protected BinaryTree.Node<E> rotateRight(BinaryTree.Node<E> root) {
        Node<E> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }

    protected Node<E> rotateLeft(Node<E> localRoot) {
        Node<E> temp = localRoot.right;
        localRoot.right = temp.left;
        temp.left = localRoot;
        return temp;
    }
}