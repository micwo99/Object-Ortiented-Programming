package algo_questions;

import java.util.Arrays;

public class Solutions {


    public static int alotStudyTime(int[] tasks, int[] timeSlots) {
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        int result=0;
        int indexTask=0;
        for(int i = 0; i < timeSlots.length ;i++) {
            if (indexTask== tasks.length){
                break;
            }
            if(timeSlots[i]>=tasks[indexTask]){
                indexTask++;
                result++;
            }
        }
        return result;
    }

    public static int minLeap(int[] leapNum) {
        if(leapNum.length<=1)
            return 0;
        int j = 0;
        int jump=0;
        int flag;
        int index=0;
        while (j < leapNum.length-1){
            int begin = leapNum[j];
            flag = 0;
            if(begin+j>=leapNum.length-1){
                jump++;
                break;
            }
            for (int i = j+1 ; i <= begin+j; i++) {
                if(leapNum[i]+i>=flag+index){
                    flag=leapNum[i];
                    index=i;
                }
            }
            j =index;
            jump++;
        }
        return jump;
    }

    public static int bucketWalk(int n) {
        int[] ways = new int[n];
        if (n <= 1)
            return 1;
        if (n == 2)
            return 2;
        else {
            ways[0] = 1;
            ways[1] = 2;
            for (int i = 2; i < n; i++) {
                ways[i] = ways[i-1] + ways[i-2];
            }
        }
        return ways[n-1];
    }

    public static int numTrees(int n) {
        int[] Trees = new int[n+1];
        Trees[0] = 1;
        for (int i = 1; i < n+1; i++) {
            for (int j = 0; j <= i-1; j++) {
                Trees[i] += (Trees[j]*Trees[i-j-1]);
            }
        }
        return Trees[n];
    }

}