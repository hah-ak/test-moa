package my.application.api;

public class Solution2 {

    private int zeroNum = 0;
    private int convertNum = 0;
    public int[] solution(String s) {
        while(true) {
            if (s.equals("s")) {
                break;
            }
            s = convertBinary(clearZero(s));
        }
        return new int[]{convertNum, zeroNum};
    }
    
    private String clearZero(String s) {
        String[] split = s.split("");
        String newString = "";
        for (String string : split) {
            if (string.equals("0")) {
                zeroNum += 1;
            }else{
                newString += string;
            }
        }
        return newString;
    }

    private String convertBinary(String s) {
        String newString = "";
        int length = s.length();
        while (true) {
            if (length == 0) {
                break;
            }
            newString = (length % 2) + newString ;
            length = length / 2;
        }
        convertNum += 1;
        return newString;
    }
}
