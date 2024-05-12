package com.example.pcconfigproject;

public class ConfigItem {
    private String id;
    private String name;
    private String cpu;
    private String mobo;
    private String ram;
    private String gpu;
    private String psu;
    private String pcCase;
    private String ssd;
    private int imageResource;

    public ConfigItem() {
    }

    public ConfigItem(String name, String cpu, String mobo, String ram, String gpu, String psu, String pcCase, String ssd, int imageResource) {
        this.name = name;
        this.cpu = cpu;
        this.mobo = mobo;
        this.ram = ram;
        this.gpu = gpu;
        this.psu = psu;
        this.pcCase = pcCase;
        this.ssd = ssd;
        this.imageResource = imageResource;
    }

    public int getImageResource() { return imageResource; }

    public String getName() {
        return name;
    }

    public String getCpu() {
        return cpu;
    }

    public String getMobo() {
        return mobo;
    }

    public String getRam() {
        return ram;
    }

    public String getGpu() {
        return gpu;
    }

    public String getPsu() {
        return psu;
    }

    public String getPcCase() {
        return pcCase;
    }

    public String getSsd() {
        return ssd;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
