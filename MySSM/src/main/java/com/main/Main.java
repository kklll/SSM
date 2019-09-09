package com.main;

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
}