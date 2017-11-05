package java_spc.algorithm;

import java.util.*;

public class BinaryTree {
    public static class TreeNode<T> {
        public T value;
        public TreeNode<T> left;
        public TreeNode<T> right;

        public TreeNode() {
        }

        public TreeNode(T value) {
            this.value = value;
        }
    }

    public static <T> TreeNode<T> create(T[] elements) {
        List<TreeNode<T>> list = new ArrayList<>();
        for (T element : elements) {
            list.add(new TreeNode<>(element));
        }
        for (int i = 0; i < elements.length / 2 - 1; i++) {
            list.get(i).left = list.get(i * 2 + 1);
            list.get(i).right = list.get(i * 2 + 2);
        }
        int last = elements.length / 2 - 1;
        list.get(last).left = list.get(last * 2 + 1);
        if (elements.length % 2 == 1) {
            list.get(last).right = list.get(last * 2 + 2);
        }
        return list.get(0);
    }

    public static <T> TreeNode<T> buildTree(T[] pre, T[] in, int preStart, int preEnd, int inStart, int inEnd) {
        TreeNode<T> root = new TreeNode<>(pre[preStart]);
        if (preStart == preEnd && inStart == inEnd) {
            return root;
        }
        int rootIndex = 0;
        for (rootIndex = inStart; rootIndex < inEnd; rootIndex++) {
            if (pre[preStart] == in[rootIndex]) {
                break;
            }
        }
        int leftLen = rootIndex - inStart;
        int rightLen = inEnd - rootIndex;
        if (leftLen > 0) {
            root.left = buildTree(pre, in, preStart + 1, preStart + leftLen, inStart, rootIndex - 1);
        }
        if (rightLen > 0) {
            root.right = buildTree(pre, in, preStart + leftLen + 1, preEnd, rootIndex + 1, inEnd);
        }
        return root;
    }

    public static <T> void preOrderRecursion(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        System.out.print(root.value + " ");
        preOrderRecursion(root.left);
        preOrderRecursion(root.right);
    }

    public static <T> void inOrderRecursion(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        inOrderRecursion(root.left);
        System.out.print(root.value + " ");
        inOrderRecursion(root.right);
    }

    public static <T> void postOrderRecursion(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        postOrderRecursion(root.left);
        postOrderRecursion(root.right);
        System.out.print(root.value + " ");
    }

    public static <T> void preOrderNoRecursion(TreeNode<T> root) {
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> current = root;
        if (current != null) {
            stack.push(current);
        }
        while (!stack.isEmpty()) {
            current = stack.pop();
            System.out.print(current.value + " ");
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
    }

    public static <T> void inOrderNoRecursion(TreeNode<T> root) {
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> current = root;
        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                current = stack.pop();
                System.out.print(current.value + " ");
                current = current.right;
            }
        }
    }

    public static <T> void postOrderNoRecursion(TreeNode<T> root) {
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> current = root, record;
        do {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            record = null;
            while (!stack.isEmpty()) {
                current = stack.pop();
                if (current.right == record) {
                    System.out.print(current.value + " ");
                    record = current;
                } else {
                    stack.push(current);
                    current = current.right;
                    break;
                }
            }
        } while (!stack.isEmpty());
    }

    public static <T> void levelOrder(TreeNode<T> root) {
        Queue<TreeNode<T>> queue = new LinkedList<>();
        TreeNode<T> current = root;
        if (current != null) {
            queue.add(current);
        }
        while (!queue.isEmpty()) {
            current = queue.poll();
            System.out.print(current.value + " ");
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }

    public static <T> void preOrderMorris(TreeNode<T> root) {
        TreeNode<T> cur, pre;
        cur = root;
        while (cur != null) {
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                pre = cur;
                cur = cur.right;
            } else {
                TreeNode<T> node = cur.left;
                while (node.right != null && node.right != cur) {
                    node = node.right;
                }
                if (node.right == null) {
                    System.out.print(cur.value + " ");
                    node.right = cur;
                    pre = cur;
                    cur = cur.left;
                } else {
                    node.right = null;
                    cur = cur.right;
                }
            }
        }
    }

    public static <T> void inOrderMorris(TreeNode<T> root) {
        TreeNode<T> cur, pre;
        cur = root;
        while (cur != null) {
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                pre = cur;
                cur = cur.right;
            } else {
                TreeNode<T> node = cur.left;
                while (node.right != null && node.right != cur) {
                    node = node.right;
                }
                if (node.right == null) {
                    node.right = cur;
                    cur = cur.left;
                } else {
                    System.out.print(cur.value + " ");
                    node.right = null;
                    pre = cur;
                    cur = cur.right;
                }
            }
        }
    }

    public static <T> void postOrderMorris(TreeNode<T> root) {
        TreeNode<T> dummy = new TreeNode<>();
        TreeNode<T> cur = null, pre = null;
        dummy.left = root;
        cur = dummy;
        while (cur != null) {
            if (cur.left == null) {
                pre = cur;
                cur = cur.right;
            } else {
                TreeNode<T> node = cur.left;
                while (node.right != null && node.right != cur) {
                    node = node.right;
                }
                if (node.right == null) {
                    node.right = cur;
                    pre = cur;
                    cur = cur.left;
                } else {
                    visitReverse(cur.left, pre);
                    pre.right = null;
                    pre = cur;
                    cur = cur.right;
                }
            }
        }
    }

    private static <T> void visitReverse(TreeNode<T> from, TreeNode<T> to) {
        TreeNode<T> p = to;
        reverse(from, to);
        while (true) {
            System.out.print(p.value + " ");
            if (p == from) {
                break;
            }
            p = p.right;
        }
        reverse(to, from);
    }

    private static <T> void reverse(TreeNode<T> from, TreeNode<T> to) {
        TreeNode<T> x = from, y = from.right, z;
        if (from == to) {
            return;
        }
        while (x != to) {
            z = y.right;
            y.right = x;
            x = y;
            y = z;
        }
    }
}
