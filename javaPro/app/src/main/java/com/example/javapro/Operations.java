package com.example.javapro;
import java.util.Scanner;

public class Operations {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();

        float resx = operate(x,y);
        System.out.println("The result is " + resx);
    }

    public static float operate(int x,int y){
        return x % y;
    }
}
