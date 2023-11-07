package my.application.api;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution3 {
    @Test
    public void aa() {
//        int[] solution = this.solution(10, 10, 3, 7, new int[][]{{7, 7}});
//        System.out.println(solution[0]);
    }

//    public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
//        int[] answer = new int[balls.length];
//        // 비례식
//        for (int i = 0; i < balls.length; i++) {
//            int x = balls[i][0];
//            int y = balls[i][1];
//
//            int v = xZero(x, y, startX, startY);
//            int v1 = xZero(x, Math.abs(n - y), startX, Math.abs(n - startY));
//            int v2 = xZero(y, x, startY, startX);
//            int v3 = xZero(y, Math.abs(m - x), startY, Math.abs(m - startX));
//            answer[i] = Math.min(Math.min(Math.min(v,v1),v2),v3);
//
//        }
//        return answer;
//    }

//    public int xZero(int x, int y, int startX, int startY) {
//        int minX = Math.min(x,startX);
//        int useY = minX == x ? y : startY;
//        try {
//            BigDecimal[] xPoints = BigDecimal.valueOf(((long) Math.abs(startX - x) * useY)).divideAndRemainder(BigDecimal.valueOf((startY + y) + minX));
//            BigDecimal xPoint = xPoints[0].add(xPoints[1]);
//            BigDecimal startLength = xPoint.subtract(BigDecimal.valueOf(startX)).pow(2).add(BigDecimal.valueOf(startY).pow(2)).sqrt(MathContext.DECIMAL32);
//            BigDecimal targetLength = xPoint.subtract(BigDecimal.valueOf(x)).pow(2).add(BigDecimal.valueOf(y).pow(2)).sqrt(MathContext.DECIMAL32);
////            double xPoint =  ((double) (Math.abs(startX - x) * useY) / (startY + y) + minX);
////            double startLength = Math.sqrt(Math.pow(xPoint - startX, 2) + Math.pow(startY,2));
////            double targetLength = Math.sqrt(Math.pow(xPoint - x,2) + Math.pow(y,2));
//            if (xPoint.equals(BigDecimal.valueOf(minX)) && startLength.compareTo(targetLength) > 0 ) {
//                throw new ArithmeticException();
//            } else {
//                return startLength.add(targetLength).pow(2).intValue();
//            }
//        } catch (ArithmeticException e) {
//            return Integer.MAX_VALUE;
//        }
// }

    @Test
    public void bb() {
//        System.out.println(this.solution2(new int[][]{{4,5},{4,8},{10,14},{11,13}, {5,12}, {3,7},{1,4}}));
        System.out.println(this.solution2(new int[][]{{1,4},{3,4},{5,6}}));
    }

    List<Integer> candidates;
    List<Integer> curList;
    int nesting;
    public int solution2(int[][] targets) {
        int answer = 0;
        List<int[]> collect = Arrays.stream(targets).sorted((o1, o2) -> o1[1] - o1[0] - (o2[1] - o2[0])).collect(Collectors.toList());

        while (!collect.isEmpty()) {
            candidates = new ArrayList<>();
            curList = new ArrayList<>();
            nesting = 1;
            nestingList(collect, 1,collect.get(0)[0],collect.get(0)[1],0 );
            for (Integer candidate : candidates) {
                collect.remove((int) candidate);
            }
            answer += 1;
        }

        return answer;
    }

    private void nestingList(List<int[]> collect, int startIdx, int min, int max, int inNesting) {
        if (nesting < inNesting) {
            candidates = Lists.newArrayList(curList);
            nesting = inNesting;
        }
        for (int i = startIdx; i < collect.size(); i++) {
            int l = collect.get(i)[0];
            int r = collect.get(i)[1];
            if ( r > min && l < max ) {
                curList.add(i);
                nestingList(collect, i + 1, Math.max(l,min), Math.min(r,max), inNesting + 1);
                curList.remove(curList.size() - 1);
            }
        }
    }
}
