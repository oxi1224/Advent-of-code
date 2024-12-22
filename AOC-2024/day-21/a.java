
import java.util.*;

class RecursiveKeypadSolution {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final String[] MOVES = {"^", "v", "<", ">"};

    public static void main(String[] args) {
        // Define keypads
        HashMap<String, int[]> keypad = new HashMap<>();
        keypad.put("7", new int[]{0, 0});
        keypad.put("8", new int[]{0, 1});
        keypad.put("9", new int[]{0, 2});
        keypad.put("4", new int[]{1, 0});
        keypad.put("5", new int[]{1, 1});
        keypad.put("6", new int[]{1, 2});
        keypad.put("1", new int[]{2, 0});
        keypad.put("2", new int[]{2, 1});
        keypad.put("3", new int[]{2, 2});
        keypad.put("0", new int[]{3, 1});
        keypad.put("A", new int[]{3, 2});

        String[] codes = {"029A", "980A", "179A", "456A", "379A"};
        int totalComplexity = 0;

        for (String code : codes) {
            int numericPart = Integer.parseInt(code.substring(0, code.length() - 1));
            String sequence = findShortestSequence("A", code, keypad);
            int complexity = sequence.length() * numericPart;
            totalComplexity += complexity;
        }

        System.out.println("Total Complexity: " + totalComplexity);
    }

    private static String findShortestSequence(String start, String code, HashMap<String, int[]> keypad) {
        StringBuilder fullPath = new StringBuilder();
        String current = start;

        for (char targetChar : code.toCharArray()) {
            String target = String.valueOf(targetChar);
            String path = findPath(current, target, keypad);
            fullPath.append(path).append("A"); // Add "A" to simulate pressing the button
            current = target;
        }

        return fullPath.toString();
    }

    private static String findPath(String start, String target, HashMap<String, int[]> keypad) {
        int[] startCoords = keypad.get(start);
        int[] targetCoords = keypad.get(target);

        Queue<PathNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new PathNode(startCoords, ""));
        visited.add(Arrays.toString(startCoords));

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            if (Arrays.equals(current.coords, targetCoords)) {
                return current.path;
            }

            for (int i = 0; i < DIRECTIONS.length; i++) {
                int[] direction = DIRECTIONS[i];
                int newY = current.coords[0] + direction[0];
                int newX = current.coords[1] + direction[1];
                int[] newCoords = new int[]{newY, newX};

                if (!isValid(newCoords, keypad) || visited.contains(Arrays.toString(newCoords))) {
                    continue;
                }

                visited.add(Arrays.toString(newCoords));
                queue.add(new PathNode(newCoords, current.path + MOVES[i]));
            }
        }

        return ""; // Should never reach here if inputs are valid
    }

    private static boolean isValid(int[] coords, HashMap<String, int[]> keypad) {
        for (int[] value : keypad.values()) {
            if (Arrays.equals(coords, value)) {
                return true;
            }
        }
        return false;
    }

    private static class PathNode {
        int[] coords;
        String path;

        PathNode(int[] coords, String path) {
            this.coords = coords;
            this.path = path;
        }
    }
}
