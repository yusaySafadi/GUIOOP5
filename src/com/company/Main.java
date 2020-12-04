package com.company;
import java.awt.*;
import java.util.ArrayList;

import gui.Gui;

public class Main {
    static int anzahlZeile = 8;
    static int anzahlSpalte = 8;

    static int[] Arrayx = {0, 50};
    static int[] Arrayy = {50};
    public static void main(String[] args) {
	// write your code here

        Gui gui = new Gui(new Main(), 100, 100);
        gui.start();
    }

    public static void column(Gui gui){
        int spaltenbreite = gui.getWidth()/anzahlSpalte;
        int zeilenhöhe;

        //int[][] columnArray = new int[anzahlSpalte][2];
        //int[] rowArray = new int[anzahlZeile];

        /*for(int spalte = 0; spalte<anzahlSpalte; spalte++){
            //durch jede Spalte gehen

            int[] arrayx = new int[(100/anzahlSpalte)];
            for(int coordinate = 0; coordinate< spaltenbreite; coordinate++){
                arrayx[coordinate] = (coordinate+(spaltenbreite*spalte+1)) ;

            }
            for(int x : arrayx){
                System.out.println(x);
            }
            System.out.println("lmao");
            //die koor

        }*/
        ArrayList<int[]> col= new ArrayList<int[]>();
        ArrayList<Integer> xl = new ArrayList<Integer>();
        ArrayList<Integer> yl = new ArrayList<Integer>();


        for(int x = 0; x <= spaltenbreite; x++){
            for(int y = 0; y<=gui.getHeight()-1; y++){
                System.out.println("------------");
                System.out.println(x);
                System.out.println(y);
                System.out.println("-------------");
                xl.add(x+spaltenbreite);
                yl.add(y);
                //gui.rectangleAt(x,y, Color.BLACK);
            }
        }

        int[] xc = new int[xl.size()];
        int[]yc = new int[yl.size()];

        for(int i = 0; i<xl.size(); i++){
            xc[i] = xl.get(i);
        }
        for(int i = 0; i<yl.size(); i++){
            yc[i] = yl.get(i);
        }

        int[] arrayx = {0};
        int[] arrayy = {0,1,2,3,4,5,6,7,8,9,10,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5};
       gui.rectangleAt(arrayx, arrayy, Color.BLACK);






    }
    public static void row( Gui gui){

       int RECTANGLE_HEIGHT = gui.getHeight()/anzahlZeile;
        int RECTANGLE_WIDTH = gui.getWidth()/anzahlSpalte;
        int[] Spalten;
        ArrayList<Integer> listx = new ArrayList<>();
        ArrayList<Integer> listy = new ArrayList<>();
        for(int i = 0; i<=RECTANGLE_WIDTH; i++){
            for(int j =0 ; j<= RECTANGLE_HEIGHT; j++){
                listx.add(i);
                listy.add(j);

            }
        }
        int[] arrayx = new int[listx.size()];
        for (int i = 0; i< arrayx.length; i++){
            arrayx[i] = listx.get(i);
        }
        int[] arrayy = new int[listx.size()];
        for (int i = 0; i< arrayx.length; i++){
            arrayy[i] = listy.get(i);
        }
        gui.rectangleAt(arrayx, arrayy, Color.BLACK);
        //gui.rectangleAt(RECTANGLE_HEIGHT+20,RECTANGLE_WIDTH+50, Color.BLACK);




    }
    public static void generate(Gui gui){
        column(gui);
    }
}
