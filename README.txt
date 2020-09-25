A Java library for generating GCode.  An API for GCode, if you will.  For example:

```
GCode gc = new GCode();
gc.comment("This is a test");
gc.move().x(0).y(0).z(0).f(800);
gc.extrude().x(100).e(100);
System.out.println(gc.toString());
```

Released under the Apache License 2.0 .

-Erhannis