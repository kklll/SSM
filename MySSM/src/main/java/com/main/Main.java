package com.main;

import org.junit.Test;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int a[][] = new int[num][num];
        int nums = num % 2 == 1 ? num / 2 + 1 : num / 2;
        for (int i = 0; i < nums; i++) {
            for (int j = i; j < num - i; j++) {
                a[i][j] = i + 1;
                a[num - (1 + i)][j] = i + 1;
                a[j][i] = i + 1;
                a[j][num - (1 + i)] = i + 1;
            }
        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void jiecheng() {
        int[] num = new int[1];
        num[0] = 1;
        MyBigNumber result = new MyBigNumber(num);
        for (int i = 1; i < 60; i++) {
            int[] init = new int[100];
            init[99] = 1;
            for (int j = 1; j <= i; j++) {
                result.setNumber(result.cheng(j));
            }
            System.out.print(i + "的阶乘为");
            result.show();
            System.out.println();
            result.setNumber(init);
        }
    }

    @Test
    public void tets() {
        int flag = 0;
        int[] num = {1, 2, 3, 4, 5, 6, 7, 8, 9, 5, 6, 7};
        MyBigNumber myBigNumber = new MyBigNumber(num);
        int[] result = myBigNumber.cheng(96);
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