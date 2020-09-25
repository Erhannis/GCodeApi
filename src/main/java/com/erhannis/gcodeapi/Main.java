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
        
        gc.absolute();
        gc.millimeters();
        gc.move().f(800);
        gc.move().z(5);
        gc.move().x(100).y(100);
        
        System.out.println(gc.toString());
    }
}
