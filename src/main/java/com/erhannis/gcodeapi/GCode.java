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
    public static abstract class Command<THIS extends Command<THIS>> {
        public abstract List<String> toGCode();
    }
    public static class Raw extends Command<Raw> {
        public String command;
        
        public Raw(String cmd) {
            this.command = cmd;
        }
        
        @Override
        public List<String> toGCode() {
            return Arrays.asList(command);
        }
    }
    public static abstract class LinearMove<THIS extends LinearMove<THIS>> extends Command<THIS> {
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
    public static class G0 extends LinearMove<G0> {
        @Override
        public List<String> toGCode() {
            return Arrays.asList(super.addParams("G0"));
        }
    }
    public static class G1 extends LinearMove<G1> {
        public Double e;

        public G1 e(double v) {
            this.e = v;
            return this;
        }
        
        @Override
        public List<String> toGCode() {
            String command = "G1";
            if (e != null) {
                command += " E"+e;
            }
            return Arrays.asList(super.addParams(command));
        }
    }
    public static class Set extends Command<Set> {
        public Double e;
        public Double x;
        public Double y;
        public Double z;
        
        public Set e(double v) {
            this.e = v;
            return this;
        }

        public Set x(double v) {
            this.x = v;
            return this;
        }

        public Set y(double v) {
            this.y = v;
            return this;
        }

        public Set z(double v) {
            this.z = v;
            return this;
        }
        
        @Override
        public List<String> toGCode() {
            String command = "G92";
            if (e != null) {
                command += " E"+e;
            }
            if (x != null) {
                command += " X"+x;
            }
            if (y != null) {
                command += " Y"+y;
            }
            if (z != null) {
                command += " Z"+z;
            }
            return Arrays.asList(command);
        }
    }
    
    
    public ArrayList<Command<?>> commands = new ArrayList<Command<?>>();
    
    public G0 move() {
        G0 cmd = new G0();
        commands.add(cmd);
        return cmd;
    }

    public G1 extrude() {
        G1 cmd = new G1();
        commands.add(cmd);
        return cmd;
    }

    /** Set the current position to the values specified. */
    public Set set() {
        Set cmd = new Set();
        commands.add(cmd);
        return cmd;
    }
    
    public Raw raw(String cmd) {
        Raw c = new Raw(cmd);
        commands.add(c);
        return c;
    }

    public Raw comment(String comment) {
        Raw c = new Raw(";"+comment);
        commands.add(c);
        return c;
    }
    
    private Raw oneOff(String cmd) {
        Raw c = new Raw(cmd);
        commands.add(c);
        return c;
    }
    
    /** Set E to absolute positioning. */
    public Raw eAbsolute() {
        return oneOff("M82");
    }

    /** Set E to relative positioning. */
    public Raw eRelative() {
        return oneOff("M83");
    }

    /** Set the interpreter to absolute positions */
    public Raw absolute() {
        return oneOff("G90");
    }

    /** Set the interpreter to relative positions */
    public Raw relative() {
        return oneOff("G91");
    }
    
    /** Set Units to Inches */
    public Raw inches() {
        return oneOff("G20");
    }

    /** Set Units to Millimeters */
    public Raw millimeters() {
        return oneOff("G21");
    }

    /** Display `prompt` and wait for the user to continue. */
    public Raw prompt(String prompt) {
        return oneOff("M0 " + prompt);
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
