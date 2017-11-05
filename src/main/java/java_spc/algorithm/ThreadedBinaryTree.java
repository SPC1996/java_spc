package java_spc.algorithm;

public class ThreadedBinaryTree<T> {
    private TreeNode<T> pre;

    public static class TreeNode<T> {
        public T value;
        public boolean ltag; //true 表示为线索， false表示为孩子
        public boolean rtag; //true 表示为线索， false表示为孩子
        public TreeNode<T> left;
        public TreeNode<T> right;

        public TreeNode(T value) {
            this.value = value;
        }
    }

    public TreeNode<T> create(T[] elements, int index) {
        TreeNode<T> node = null;
        if (index < elements.length) {
            node = new TreeNode<>(elements[index]);
            node.left = create(elements, index * 2 + 1);
            node.right = create(elements, index * 2 + 2);
        }
        return node;
    }

    public void inThreadOrder(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        inThreadOrder(root.left);
        if (root.left == null) {
            root.left = pre;
            root.ltag = true;
        }
        if (pre != null && pre.right == null) {
            pre.right = root;
            pre.rtag = true;
        }
        pre = root;
        inThreadOrder(root.right);
    }

    public void inOrderBySuccessor(TreeNode<T> root) {
        while (root != null && !root.ltag) {
            root = root.left;
        }
        while (root != null) {
            System.out.print(root.value + " ");
            if (root.rtag) {
                root = root.right;
            } else {
                root = root.right;
                while (root != null && !root.ltag) {
                    root = root.left;
                }
            }
        }
    }

    public void inOrderByPrecursor(TreeNode<T> root) {
        while (root.right != null && !root.rtag) {
            root = root.right;
        }
        while (root != null) {
            System.out.print(root.value + " ");
            if (root.ltag) {
                root = root.left;
            } else {
                root = root.left;
                while (root.right != null && !root.rtag) {
                    root = root.right;
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadedBinaryTree<Integer> tree = new ThreadedBinaryTree<>();
        TreeNode<Integer> root = tree.create(new Integer[]{1, 3, 5, 7, 9, 2, 4}, 0);
        tree.inThreadOrder(root);
        tree.inOrderBySuccessor(root);
        System.out.println();
        tree.inOrderByPrecursor(root);
    }
}
