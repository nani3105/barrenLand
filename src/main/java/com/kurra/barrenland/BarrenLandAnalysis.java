package com.kurra.barrenland;

import java.util.*;

/**
 * User: Niranjan.kurra - Date: 8/21/18 1:04 AM
 */
public class BarrenLandAnalysis {

    private int width;
    private int height;
    private boolean[][] farm;

    BarrenLandAnalysis(int width, int height) {
        this.width = width;
        this.height = height;
        // Farm land with false as Fertile land.
        this.farm = new boolean[width][height];
    }

    public static void main(String[] args) {
        // Given farm dimensions.
        int MAX_WIDTH = 400;
        int MAX_HEIGHT = 600;

        // Initialize with farm width and height.
        BarrenLandAnalysis barrenLandAnalysis = new BarrenLandAnalysis(MAX_WIDTH, MAX_HEIGHT);

        // Get barren land boundaries.
        List<List<Integer>> barrenLands = barrenLandAnalysis.getBarrenLands();

        List<Integer> fertileLands = barrenLandAnalysis.getFertileLands(barrenLands);

        // Print all fertile land areas.
        for (Integer fertileArea : fertileLands) {
            System.out.print(fertileArea + " ");
        }
    }

    public List<Integer> getFertileLands(List<List<Integer>> barrenLands) {
        // Fill barren land with true in Farm land.
        this.fillBarrenLands(barrenLands);

        // Calculate Fertile land areas using DFS.
        List<Integer> fertileAreas = this.calculateFertileLandAreas();
        return fertileAreas;
    }



    /**
     * Calculate Fertile Land Areas using DFS.
     *
     * @return List of sorted fertile land areas.
     */
    private List<Integer> calculateFertileLandAreas() {
        if (this.farm == null) {
            this.throwInputException("Invalid input");
        }

        List<Integer> fertileLandAreas = new ArrayList<>();

        for (int i = 0; i < this.farm.length; i++) {
            for (int j = 0; j < this.farm[i].length; j++) {
                if (!this.farm[i][j]) {
                    fertileLandAreas.add(this.calculateFertileLandAreaIterative(i, j));
                }
            }
        }

        fertileLandAreas.sort(Comparator.naturalOrder());

        return fertileLandAreas;
    }

    /**
     * Traverse Fertile Land area using DFS with iteration. Will traverse Down, Up, Back and Front of each fertile cell until entire fertile area is covered.
     *
     * @param coordinateX
     * @param coordinateY
     * @return Area of each Fertile land.
     */
    private Integer calculateFertileLandAreaIterative(int coordinateX, int coordinateY) {

        Stack<Integer[]> stack = new Stack<>();
        stack.push(new Integer[]{coordinateX, coordinateY});
        int fertileLandArea = 0;
        while (!stack.empty()) {
            Integer[] point = stack.pop();
            coordinateX = point[0];
            coordinateY = point[1];
            if (!this.farm[coordinateX][coordinateY]) {
                fertileLandArea++;
                this.farm[coordinateX][coordinateY] = true;
                if (this.isWithIn(coordinateX - 1, 0, this.width) && !this.farm[coordinateX - 1][coordinateY]) {
                    stack.push(new Integer[]{coordinateX - 1, coordinateY});
                }
                if (this.isWithIn(coordinateX + 1, 0, this.width) && !this.farm[coordinateX + 1][coordinateY]) {
                    stack.push(new Integer[]{coordinateX + 1, coordinateY});
                }
                if (this.isWithIn(coordinateY - 1, 0, this.height) && !this.farm[coordinateX][coordinateY - 1]) {
                    stack.push(new Integer[]{coordinateX, coordinateY - 1});
                }
                if (this.isWithIn(coordinateY + 1, 0, this.height) && !this.farm[coordinateX][coordinateY + 1]) {
                    stack.push(new Integer[]{coordinateX, coordinateY + 1});
                }
            }
        }
        return fertileLandArea;
    }

    /**
     * Fill Barren lands in Farm as false.
     *
     * @param barrenLands
     */
    private void fillBarrenLands(List<List<Integer>> barrenLands) {

        if (this.farm == null || barrenLands == null) {
            this.throwInputException("Invalid input");
        }

        for (List<Integer> barrenLand : barrenLands) {
            if (barrenLand.size() != 4) {
                this.throwInputException("Invalid input");
            }
            if (!validBarrenLand(barrenLand.get(0), barrenLand.get(1), barrenLand.get(2), barrenLand.get(3))) {
                this.throwInputException("Invalid input");
            }
            for (int i = barrenLand.get(0); i <= barrenLand.get(2); i++) {
                for (int j = barrenLand.get(1); j <= barrenLand.get(3); j++) {
                    this.farm[i][j] = true;
                }
            }
        }
    }

    /**
     * Get Barren lands boundaries.
     *
     * @return list of boundaries.
     */
    private List<List<Integer>> getBarrenLands() {
        String input = this.readInput();
        //String input = "48 192 351 207,48 392 351 407,120 52 135 547,260 52 275 547";
        return this.parseInput(input);
    }

    /**
     * Parse input string to list of boundaries.
     *
     * @param input
     * @return list of boundaries.
     */
    private List<List<Integer>> parseInput(String input) {
        if (input == null || input.length() == 0) {
            this.throwInputException("Invalid input. Enter values in specified format.");
        }
        List<List<Integer>> barrenLands = new ArrayList<>();
        String[] rectangles = input.split(",");

        for (String rectangle : rectangles) {
            String[] coordinates = rectangle.trim().split(" ");
            if (coordinates.length != 4) {
                this.throwInputException("All rectangles should be provided with 4 co-ordinates");
            }
            try {
                int x1 = Integer.parseInt(coordinates[0]);
                int y1 = Integer.parseInt(coordinates[1]);
                int x2 = Integer.parseInt(coordinates[2]);
                int y2 = Integer.parseInt(coordinates[3]);

                if (this.validBarrenLand(x1, y1, x2, y2)) {
                    barrenLands.add(Arrays.asList(x1, y1, x2, y2));
                } else {
                    this.throwInputException(String.format("Specified barren land should be within farm range (0, 0) to (%d, %d):", this.width, this.height));
                }

            } catch (NumberFormatException nfe) {
                this.throwInputException("All co-ordinates should be integer.");
            }
        }
        return barrenLands;
    }

    /**
     * Util function to throw invalid input exceptions.
     *
     * @param exception
     */
    private void throwInputException(String exception) {
        System.out.println("Exception : " + exception);
        throw new InputMismatchException(exception);
    }

    /**
     * Check provided boundaries form a valid Barren Land.
     *
     * Battery checks if the co-ordinates for the rectangles overflow and top right corner should be more than bottom left corner
     * And check if the point denote a point or st line with which we cannot form a rectangle.
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return true if a valid barren land is formed otherwise false.
     */
    private boolean validBarrenLand(int x1, int y1, int x2, int y2) {
        return isWithIn(x1, 0, this.width) && isWithIn(y1, 0, this.height) // bottom - left is within grid
                && isWithIn(x2, 0, this.width) && isWithIn(y2, 0, this.height) // top - right is within grid
                && isWithIn(x2, x1, this.width) && isWithIn(y2, y1, this.height)
                && !isPoint(x1, y1, x2, y2) && !isStraightLine(x1, y1, x2, y2);
    }

    private boolean isPoint(int x1, int y1, int x2, int y2) {
        return (x1 == x2) && (y1 == y2);
    }

    private boolean isStraightLine(int x1, int y1, int x2, int y2) {
        return ((x1 == x2) ^ (y1 == y2));
    }

    /**
     * Util function to check whether x is in range [a, b).
     *
     * @param x
     * @param a
     * @param b
     * @return return true with x is in range [a, b) otherwise false.
     */
    private boolean isWithIn(int x, int a, int b) {
        return (x >= a && x < b);
    }

    /**
     * Read input from STDIN.
     *
     * @return input from STDIN.
     */
    private String readInput() {

        System.out.println("Barren Land Analysis");
        System.out.println("Please enter input in the following way:");
        System.out.println("X11 Y11 X12 Y12,X21 Y21 X22 Y22,...");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(input);
        scanner.close();

        return input;
    }
}
