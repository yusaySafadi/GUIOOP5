package com.company;

import gui.Gui;
import java.awt.Color;


public class Test {
    public void generate(Gui gui){
        contrapoints(gui);
    }
    public static void main(String[] args){
        Gui gui = new Gui(new Test(), 9, 9);
        gui.start();
    }

    public static void column(Gui gui){

        int[] arrayx = new int[gui.getHeight()];
        int[] arrayy = new int[gui.getHeight()];
        for(int j = 0; j<gui.getHeight(); j++){
            arrayx[j] = 1;
            arrayy[j] = j;
        }
        gui.rectangleAt(arrayx,arrayy, Color.MAGENTA);
        System.out.println(gui.getHeight());
        gui.setWaitMs(300);
        for(int i = 0; i< gui.getHeight(); i++){
            gui.rectangleAt(gui.getWidth()-2,i, Color.BLACK);
        }

    }
    public static void row(Gui gui){
        int[] arrayx = new int[gui.getWidth()];
        int[] arrayy = new int[gui.getWidth()];

        for(int row = 0; row<gui.getHeight(); row++){
            if(row == 1){
                for(int col = 0; col< gui.getWidth();col++){

                    arrayy[col] = row;
                    arrayx[col] =col;
                }
            }
        }

        gui.rectangleAt(arrayx,arrayy, Color.BLACK);

        gui.setWaitMs(300);
        for(int row = 0; row <gui.getHeight(); row++){
            if(row == gui.getHeight()-2){
                for(int col = 0; col< gui.getWidth();col++){
                    gui.rectangleAt(col,row, Color.MAGENTA);


                }
            }
        }

    }
    public static void checkerPattern(Gui gui){
        gui.setInitialWaitMs(200);
        gui.setWaitMs(200);


        for(int row = 0; row<gui.getHeight(); row++){
            for(int col = 0; col<gui.getWidth(); col++){
                if((row +col)%2 == 0){
                    gui.rectangleAt(col,row, Color.BLACK);

                } else{
                    gui.rectangleAt(col,row, Color.WHITE);

                }

            }


        }
    }
    public static void rectangle(Gui gui){
        gui.setWaitMs(100);
        for(int row = 1; row < gui.getHeight()-1; row++){
            for(int col = 1; col <=gui.getWidth()-2; col++){
                gui.rectangleAt(col,row, Color.BLACK);
            }
        }
    }

    public static void diagonal(Gui gui){
        gui.setWaitMs(100);
        int width = gui.getWidth();
        int height = gui.getHeight();
        if(width > height){
            width = height;
        } else{
            height = width;
        }
        for(int row = 0; row < height; row++){
            for(int col = 0; col <width; col++){
                if(col == row){
                    gui.rectangleAt(col, row, Color.MAGENTA);
                }
                else gui.rectangleAt(col,row, Color.BLACK);
            }
        }

    }

    public static void diagonalLeftRight(Gui gui){

        gui.setWaitMs(100);
        int width = gui.getWidth();
        int height = gui.getHeight();
        if(width > height){
            width = height;
        } else{
            height = width;
        }
        for(int row = 0; row < height; row++){
            for(int col = 0; col <width; col++){
                if(col == row){
                    gui.rectangleAt(col, row, Color.MAGENTA);
                } else if(col == ((width-row)-1)){
                    gui.rectangleAt(col,row, Color.GREEN);

                }else {
                    gui.rectangleAt(col,row, Color.BLACK);
                }
            }
        }

    }

    public static void everySecondRow(Gui gui){
    gui.setWaitMs(200);
        boolean instant = false;
        for(int row = 0; row<gui.getHeight(); row++){
            int[] arrayx = new int[gui.getWidth()];
            int[] arrayy = new int[gui.getWidth()];
            if(row %2 == 0){
                if(instant == false){
                    for(int col = 0; col< gui.getWidth();col++){
                        gui.rectangleAt(col,row, Color.MAGENTA);
                    }

                }else{
                    for(int col = 0; col < gui.getWidth(); col++){
                        arrayy[col] = row;
                        arrayx[col] = col;
                    }
                    gui.rectangleAt(arrayx,arrayy, Color.BLACK);

                }
                instant = !instant;
            }

        }




        /*for(int row = 0; row <gui.getHeight(); row++){
            if(row == gui.getHeight()-2){
                for(int col = 0; col< gui.getWidth();col++){
                    gui.rectangleAt(col,row, Color.MAGENTA);


                }
            }
        }*/
    }

    public static void checkerPatternArray(Gui gui){

        gui.setDiscardOldDrawings();
        gui.setInitialWaitMs(100);
        gui.setWaitMs(500);


        for(int row = 0; row<gui.getHeight(); row++){
            int[] arrayx = new int[gui.getWidth()];
            int[] arrayy = new int[gui.getWidth()];
            Color[] arrayColor = new Color[gui.getWidth()];

            for(int col = 0; col< gui.getWidth();col++){

                arrayy[col] = row;
                arrayx[col] =col;

                if((row +col)%2 == 0){

                    arrayColor[col] = Color.BLACK;
                    //black = false;
                } else{
                    arrayColor[col] = new Color(0,0,0,0);//Color.WHITE;
                    //black = true;
                }


            }

            gui.rectangleAt(arrayx,arrayy,arrayColor);

        }


    }
    public static void cromatic(Gui gui){

        gui.setWaitMs(10);
        int height = gui.getHeight();
        int width = gui.getWidth();

        float rfac= 255f/((width-1+height-1));
        float gfac = 255f/((height-1)*(width-1));
        double bhelp = ((0.3 * (height-1))) + (width-1);
        double bfac2 = 255f/ bhelp;
        float bfac =  255f/((0.3f *(width-1)) + (height-1));
        System.out.println(bhelp);
        System.out.println(bfac2);
        System.out.println(bfac);

        for(int row = 0; row<height; row++){
            for(int col = 0; col <width; col++){



                float r =((rfac*((row+col)))/255f) ;



                float g = (gfac*((row)*(col)))/255f;


                float b = ((bfac*((0.3f*(col)+(row)))))/255f;
                if(b== 1.0f){
                    System.out.println(b);
                }

                //float a =1.0f ;
                Color color = new Color(r,g , b);
                System.out.println(color);
                gui.rectangleAt(col,row,color);
            }
        }


    }

    public static void contrapoints(Gui gui){
        gui.setInitialWaitMs(500);
        gui.setWaitMs(200);
        gui.setDiscardOldDrawings();
        int width = gui.getWidth();
        int height = gui.getHeight();
        if(width != height){
            System.out.println("Zeilen und Spalten müssen gleich sein");
            return;
        }
        boolean back = false;
        for(int row = 0; row< height; row++){

            for(int col = 0; col<width; col++){

                int[] arrayx = new int[2];
                int[] arrayy = new int[2];
                Color[] colors = new Color[2];



                if(back) {

                    arrayx[0] = col;
                    arrayy[0] = row;

                    arrayx[1] = row;
                    arrayy[1] = (height-(row));


                } else {

                    arrayx[0] = (width-col)-1;
                    arrayy[0] = row;

                    arrayx[1] = row;
                    arrayy[1] = col;

                }

                //if(col == height){
                  //  arrayy[0] = row+1;
                //}
                colors[0] = Color.GREEN;
                colors[1] = Color.RED;

                gui.rectangleAt(arrayx, arrayy, colors);

            }
            back = !back;
        }
    }

}
