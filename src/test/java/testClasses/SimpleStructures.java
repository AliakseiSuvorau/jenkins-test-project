package testClasses;

import com.google.gson.annotations.Expose;

public class SimpleStructures {
    @Expose
    public byte b = 0;

    @Expose
    public short s = 0;

    @Expose
    public int i = 0;

    @Expose
    public long l = 0;

    @Expose
    public float f = 0;

    @Expose
    public double d = 0;

    @Expose
    public char c = 'a';

    @Expose
    public boolean bool = false;

    @Expose
    public String str = "Hello!";

    @Expose
    public Byte objB = 0;

    @Expose
    public Short objS = 0;

    @Expose
    public Integer objI = 0;

    @Expose
    public Long objL = 0L;

    @Expose
    public Float objF = 0.F;

    @Expose
    public Double objD = 0.D;

    @Expose
    public Character objC = 'a';

    @Expose
    public Boolean objBool = false;

    @Expose
    private byte bPrivate = 0;

    @Expose
    private short sPrivate = 0;

    @Expose
    private int iPrivate = 0;

    @Expose
    private long lPrivate = 0;

    @Expose
    private float fPrivate = 0;

    @Expose
    private double dPrivate = 0;

    @Expose
    private char cPrivate = 'a';

    private boolean boolPrivate = false;

    @Expose
    private String strPrivate = "Hello Private!";

    @Expose
    private Byte objBPrivate = 0;

    @Expose
    private Short objSPrivate = 0;

    @Expose
    private Integer objIPrivate = 0;

    @Expose
    private Long objLPrivate = 0L;

    @Expose
    private Float objFPrivate = 0.0F;

    @Expose
    private Double objDPrivate = 0.0D;

    @Expose
    private Character objCPrivate = 'a';

    @Expose
    private Boolean objBoolPrivate = false;

    public byte getBPrivate() {
        return bPrivate;
    }

    public short getSPrivate() {
        return sPrivate;
    }

    public int getIPrivate() {
        return iPrivate;
    }

    public long getLPrivate() {
        return lPrivate;
    }

    public float getFPrivate() {
        return fPrivate;
    }

    public double getDPrivate() {
        return dPrivate;
    }

    public char getCPrivate() {
        return cPrivate;
    }

    public boolean getObjBoolPrivate() {
        return boolPrivate;
    }

    public String getStrPrivate() {
        return strPrivate;
    }

    public Byte getObjBPrivate() {
        return objBPrivate;
    }

    public Short getObjSPrivate() {
        return objSPrivate;
    }

    public Integer getObjIPrivate() {
        return objIPrivate;
    }

    public Long getObjLPrivate() {
        return objLPrivate;
    }

    public Float getObjFPrivate() {
        return objFPrivate;
    }

    public Double getObjDPrivate() {
        return objDPrivate;
    }

    public Character getObjCPrivate() {
        return objCPrivate;
    }

    private Boolean getBoolPrivate() {
        return objBoolPrivate;
    }
}
