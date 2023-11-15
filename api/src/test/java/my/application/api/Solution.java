package my.application.api;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Solution {

    private int startX;
    private int startY;
    private int xLength;
    private int yLength;
    private boolean[][][] gridPointInfo;
    private final ArrayList<String[]> GRID = new ArrayList<>();
    private final int[][] dxdy = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};

    public int[] solution(String[] grid) {
        ArrayList<Integer> answer = new ArrayList<>();
        xLength = grid[0].length();
        yLength = grid.length;
        for (String s : grid) {
            GRID.add(s.split(""));
        }

        gridPointInfo  = new boolean[yLength][xLength][4];
        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                startY = i; startX = j;
                for (int k = 0; k < 4; k++) {
                    if (!gridPointInfo[i][j][k]) {
                        int currentX = j;
                        int currentY = i;
                        int directionNum = k;
                        int number=0;

                        while (true) {
                            if (gridPointInfo[currentY][currentX][directionNum]) {
                                if (currentX == startX && currentY == startY) {
                                    break;
                                }
                                number = 0;
                                break;
                            }

                            gridPointInfo[currentY][currentX][directionNum] = true;

                            String s = GRID.get(currentY)[currentX];
                            int dx = dxdy[directionNum][0];
                            int dy = dxdy[directionNum][1];

                            number += 1;

                            if (s.equals("S")) {
                                if (dx == 1) {
                                    currentX = currentX + dx;
                                    directionNum = 1;
                                } else if (dx == -1){
                                    currentX = currentX + dx;
                                    directionNum = 0;
                                } else if (dy == 1) {
                                    currentY = currentY + dy;
                                    directionNum = 3;
                                } else {
                                    currentY = currentY + dy;
                                    directionNum = 2;
                                }
                            } else if (s.equals("L")) {
                                if (dx == 1) {
                                    currentY = currentY - dx;
                                    directionNum = 2;
                                } else if (dx == -1){
                                    currentY = currentY - dx;
                                    directionNum = 3;
                                } else if (dy == 1) {
                                    currentX =  currentX + dy;
                                    directionNum = 1;
                                } else {
                                    currentX =  currentX + dy;
                                    directionNum = 0;
                                }
                            } else {
                                if (dx == 1) {
                                    currentY = currentY + dx;
                                    directionNum = 3;
                                } else if (dx == -1){
                                    currentY = currentY + dx;
                                    directionNum = 2;
                                } else if (dy == 1) {
                                    currentX = currentX - dy;
                                    directionNum = 0;
                                } else {
                                    currentX = currentX - dy;
                                    directionNum = 1;
                                }
                            }

                            currentX = convertX(currentX);
                            currentY = convertY(currentY);
                        }

                        if (number != 0) {
                            answer.add(number);
                        }
                    }
                }
            }
        }

        return answer.stream().mapToInt(Integer::intValue).sorted().toArray();
    }

    private int convertX(int x) {
        if (x >= xLength) {
            return 0;
        } else if (x < 0) {
            return xLength - 1;
        }
        return x;
    }

    private int convertY(int y) {
        if (y >= yLength) {
            return 0;
        } else if (y < 0 ) {
            return yLength - 1;
        }
        return y;
    }

    @Test
    void vvvoid() {
        System.out.println(System.getProperty("user.home"));
    }

}
