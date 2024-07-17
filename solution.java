import java.util.*;

class Tree {
    public boolean isLocked;
    public int id;
    public Tree parent;
    public List<Tree> children;

    public Tree(Tree parent) {
        this.isLocked = false;
        this.id = -1;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public boolean lock(Tree node, int userId) {
        if (node.isLocked) return false;

        Tree current = node;
        while (current != null) {
            if (current.isLocked) return false;
            current = current.parent;
        }

        Queue<Tree> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Tree temp = queue.poll();
            for (Tree child : temp.children) {
                if (child.isLocked) return false;
                queue.add(child);
            }
        }

        node.isLocked = true;
        node.id = userId;
        return true;
    }

    public boolean unlock(Tree node, int userId) {
        if (!node.isLocked || node.id != userId) return false;

        node.isLocked = false;
        node.id = -1;
        return true;
    }

    public boolean upgradeLock(Tree node, int userId) {
        if (node.isLocked) return false;

        Queue<Tree> queue = new LinkedList<>();
        queue.add(node);
        boolean hasLockedDescendant = false;

        while (!queue.isEmpty()) {
            Tree temp = queue.poll();
            for (Tree child : temp.children) {
                if (child.isLocked) {
                    if (child.id != userId) return false;
                    hasLockedDescendant = true;
                }
                queue.add(child);
            }
        }

        if (!hasLockedDescendant) return false;

        queue.add(node);
        while (!queue.isEmpty()) {
            Tree temp = queue.poll();
            for (Tree child : temp.children) {
                if (child.isLocked && !unlock(child, userId)) return false;
                queue.add(child);
            }
        }

        return lock(node, userId);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int q = sc.nextInt();
        String rootName = sc.next();

        Tree root = new Tree(null);
        Map<String, Tree> nodeMap = new HashMap<>();
        nodeMap.put(rootName, root);

        Queue<Tree> queue = new LinkedList<>();
        queue.add(root);

        for (int i = 1; i < n; i++) {
            Tree parent = queue.peek();
            String nodeName = sc.next();

            Tree node = new Tree(parent);
            nodeMap.put(nodeName, node);
            parent.children.add(node);

            if (parent.children.size() == m) {
                queue.poll();
            }
            queue.add(node);
        }

        Tree tree = new Tree(null);

        for (int i = 0; i < q; i++) {
            int operationType = sc.nextInt();
            String nodeName = sc.next();
            int userId = sc.nextInt();
            Tree node = nodeMap.get(nodeName);
            boolean result = false;

            if (operationType == 1) {
                result = tree.lock(node, userId);
            } else if (operationType == 2) {
                result = tree.unlock(node, userId);
            } else if (operationType == 3) {
                result = tree.upgradeLock(node, userId);
            }

            System.out.println(result ? "true" : "false");
        }

        sc.close();
    }
}
