package com.main;

public class MyBigNumber {
    private int[] number = null;
    private int[] result = new int[1000];

    public MyBigNumber(int[] number) {
        this.number = number;
    }

    public int[] cheng(int x) {
        int jinwei = 0;
        int weishu = 1;
        int xx = 0;
        for (int i = number.length - 1; i >= 0; i--) {
            xx = this.number[i] * x + jinwei;
            result[result.length - weishu] = xx % 10;
            jinwei = xx / 10;
            weishu++;
        }
        weishu--;
        while (xx != 0) {
            result[result.length - weishu] = xx % 10;
            weishu++;
            xx /= 10;
        }
        return result;
    }


    public void setNumber(int[] number) {
        this.number = number;
    }

    public void setResult(int[] result) {
        this.result = result;
    }

    public void show() {
        int flag=0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] != 0) {
                flag = i;
                break;
            }
        }
        for (int j = flag; j < result.length; j++)
            System.out.print(result[j]);
    }
}
