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
        gc.comment("this is a test");
        gc.move().x(0).y(0).z(0).f(800);
        gc.extrude().x(100).e(100);
        System.out.println(gc.toString());
    }
}
