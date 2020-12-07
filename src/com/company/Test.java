package com.company;

import gui.Gui;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Test {
    public static ArrayList<String> data;
    public void generate(Gui gui){
        generateFromData(gui);//tesz
    }
    public static void main(String[] args){
        //Gui gui = new Gui(new Test(), 25, 10);
        //gui.start();
        datei();
    }
    public static void generateFromData(Gui gui){


        for(String line : data){

            String[] lineArray = line.split(",");
            int xCoordynate = Integer.parseInt(lineArray[0]);
            int yCoordynate = Integer.parseInt(lineArray[1]);



            float rColor = Float.parseFloat(lineArray[2]);
            float gColor = Float.parseFloat(lineArray[3]);
            float bColor = Float.parseFloat(lineArray[4]);



            if(xCoordynate>= gui.getWidth() || xCoordynate < 0){
                System.out.printf("x %d is out of bounds", xCoordynate);
                return;

            }else if(yCoordynate>= gui.getHeight() || yCoordynate < 0){
                System.out.printf("y %d is out of bounds", yCoordynate);
                return;
            } else if(rColor>1.0 || gColor >1.0 || bColor > 1.0){
                System.out.println("Color values are out of bounds (MAX: 1.0)");
                return;
            } else if(rColor<0 || gColor < 0 || bColor <0){
                System.out.println("Color values are out of bounds (MIN:0.0)");
                return;
            }
            Color color = new Color(rColor,gColor,bColor);
            gui.rectangleAt(xCoordynate,yCoordynate, color);
        }

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
        if(height %2 != 0){
            System.out.println("FEHLER");
            return;
        }
        boolean back = false;
        for(int row = 0; row< height; row++){

            for(int col = 0; col<width; col++){

                int[] arrayx = new int[4];
                int[] arrayy = new int[4];
                Color[] colors = new Color[4];



                if(back) {

                    arrayx[0] = col;
                    arrayy[0] = row;

                    arrayx[1] = row;
                    arrayy[1] = (height-col-1);

                    arrayx[2] = height-col-1;
                    arrayy[2] = height-row-1;

                    arrayx[3] = width- row -1;
                    arrayy[3] = col;


                } else {

                    arrayx[0] = (width-col)-1;
                    arrayy[0] = row;

                    arrayx[1] = row;
                    arrayy[1] = col;

                    arrayx[2] = col;
                    arrayy[2] = height-row-1;

                    arrayx[3] = width- row -1;
                    arrayy[3] = height-col - 1;

                }

                //if(col == height){
                  //  arrayy[0] = row+1;
                //}
                colors[0] = Color.GREEN;
                colors[1] = Color.RED;
                colors[2] = Color.magenta;
                colors[3] = Color.BLUE;
                gui.rectangleAt(arrayx, arrayy, colors);

            }
            back = !back;
        }
    }

    public static void datei(){
       /* try {
            File file = new File("data.txt");
            if (file.createNewFile()) {
                System.out.println("Datei erstellt");
            } else {
                System.out.println("Datei existiert bereits");
            }
        } catch(IOException e){
            System.out.println("FEHLER");
            e.printStackTrace();

        }*/

        data  = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "C:\\Users\\yusay\\IdeaProjects\\GUIOOP5\\src\\data.txt"));
            String line = reader.readLine().trim();
            String[] firstLine = line.split(",");


            int startWidth = Integer.parseInt(firstLine[0]);
            int startHeight = Integer.parseInt(firstLine[1]);
            int startDelay = Integer.parseInt(firstLine[2]);


            System.out.println(startWidth);
            System.out.println(startHeight);
            System.out.println(startDelay);

            while (line != null) {

                line = reader.readLine();
                // read next line
                if(line == null){
                    break;
                }
                data.add(line);
            }
            reader.close();

            Gui gui = new Gui(new Test(), startWidth, startHeight);
            gui.setWaitMs(startDelay);
            gui.start();




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
