/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.gcodeapi;

/**
 *
 * @author Erhannis
 */
public class Main {
    public static void main(String[] args) {
        GCode gc = new GCode();

        final int NX = 6;
        final int NY = 24;
        final double SX = 30;
        final double SY = 10;
        final double OVER = 10;
        
        gc.absolute();
        gc.millimeters();
        gc.move().f(800);
        gc.move().z(5);
        gc.move().x(0).y(0);

        gc.move().x(10).y(10);
        gc.set().x(0).y(0);
        gc.prompt("Center corner of word, then click to continue");
        
        for (int iy = 0; iy <= NY; iy++) {
            gc.move().x(-OVER).y(iy*SY);
            gc.move().z(0);
            gc.move().x(NX*SX+OVER);
            gc.move().z(5);
        }
        for (int ix = 0; ix <= NX; ix++) {
            gc.move().x(ix*SX).y(-OVER);
            gc.move().z(0);
            gc.move().y(NY*SY+OVER);
            gc.move().z(5);
        }
        
        gc.move().x(0).y(0);
        
        System.out.println(gc.toString());
    }
}
