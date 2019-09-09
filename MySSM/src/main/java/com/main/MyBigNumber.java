package com.main;

public class MyBigNumber {
    private int[] number = null;
    private  int[] result=new int[100];
    public MyBigNumber(int[] number) {
        this.number = number;
    }

    public int[] cheng(int x) {
        int jinwei=0;
        int weishu=1;
        int xx=0;
        for (int i=number.length-1;i>0;i--) {
            xx=this.number[i]*x+jinwei;
            result[result.length-weishu]=xx%10;
            jinwei=xx/10;
            weishu++;
        }
        while (xx!=0)
        {
            weishu++;
            result[result.length-weishu]=xx%10;
            xx/=10;
        }
        return result;
    }

}
