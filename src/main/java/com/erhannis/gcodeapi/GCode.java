/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.gcodeapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Erhannis
 */
public class GCode {
    public static abstract class Command<THIS extends Command> {
//        public GCode owner;
        public abstract List<String> toGCode();
        
//        public void owner(GCode owner) {
//            this.owner = owner;
//        }
//        
//        /** Short alias for `commit()`. */
//        public void q() {
//            commit();
//        }
//        
//        public void commit() {
//            owner
//        }
    }
    public static class Raw<THIS extends Raw> extends Command<THIS> {
        public String command;
        
        public Raw(String cmd) {
            this.command = cmd;
        }
        
        @Override
        public List<String> toGCode() {
            return Arrays.asList(command);
        }
    }
    public static abstract class LinearMove<THIS extends LinearMove> extends Command<THIS> {
        public Double x;
        public Double y;
        public Double z;
        public Double f;
        
        public THIS x(double v) {
            this.x = v;
            return (THIS) this;
        }

        public THIS y(double v) {
            this.y = v;
            return (THIS) this;
        }

        public THIS z(double v) {
            this.z = v;
            return (THIS) this;
        }

        /** Set feedrate */
        public THIS f(double v) {
            this.f = v;
            return (THIS) this;
        }
        
        private String addParams(String command) {
            if (x != null) {
                command += " X"+x;
            }
            if (y != null) {
                command += " Y"+y;
            }
            if (z != null) {
                command += " Z"+z;
            }
            if (f != null) {
                command += " F"+f;
            }
            return command;
        }
    }
    public static class G0<THIS extends G0> extends LinearMove<THIS> {
        @Override
        public List<String> toGCode() {
            return Arrays.asList(super.addParams("G0 "));
        }
    }
    public static class G1<THIS extends G1> extends LinearMove<THIS> {
        public Double e;

        public THIS e(double v) {
            this.e = v;
            return (THIS) this;
        }
        
        @Override
        public List<String> toGCode() {
            String command = "G1 ";
            if (e != null) {
                command += " E"+e;
            }
            return Arrays.asList(super.addParams(command));
        }
    }
    
    public ArrayList<Command<?>> commands = new ArrayList<Command<?>>();
    
    public G0<G0> move() {
        G0<G0> cmd = new G0<G0>();
        commands.add(cmd);
        return cmd;
    }

    public G1<G1> extrude() {
        G1<G1> cmd = new G1<G1>();
        commands.add(cmd);
        return cmd;
    }
    
    public Raw<Raw> raw(String cmd) {
        Raw<Raw> c = new Raw<Raw>(cmd);
        commands.add(c);
        return c;
    }

    public Raw<Raw> comment(String comment) {
        Raw<Raw> c = new Raw<Raw>(";"+comment);
        commands.add(c);
        return c;
    }
    
    private Raw<Raw> oneOff(String cmd) {
        Raw<Raw> c = new Raw<Raw>(cmd);
        commands.add(c);
        return c;
    }
    
    /** Set E to absolute positioning. */
    public Raw<Raw> eAbsolute() {
        return oneOff("M82");
    }

    /** Set E to relative positioning. */
    public Raw<Raw> eRelative() {
        return oneOff("M83");
    }

    /** Set the interpreter to absolute positions */
    public Raw<Raw> absolute() {
        return oneOff("G90");
    }

    /** Set the interpreter to relative positions */
    public Raw<Raw> relative() {
        return oneOff("G91");
    }
    
    /** Set Units to Inches */
    public Raw<Raw> inches() {
        return oneOff("G20");
    }

    /** Set Units to Millimeters */
    public Raw<Raw> millimeters() {
        return oneOff("G21");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Command<?> cmd : commands) {
            for (String s : cmd.toGCode()) {
                sb.append(s + "\n");  //TODO Platform-sensitive newlines?
            }
        }
        return sb.toString();
    }
}
