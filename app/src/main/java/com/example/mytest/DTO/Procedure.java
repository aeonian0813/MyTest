package com.example.mytest.DTO;

public class Procedure {
    String cok_procedure_proc;
    String cok_procedure_sub;
    int cok_procedure_img;

    public Procedure(String cok_procedure_proc, String cok_procedure_sub, int cok_procedure_img) {
        this.cok_procedure_proc = cok_procedure_proc;
        this.cok_procedure_sub = cok_procedure_sub;
        this.cok_procedure_img = cok_procedure_img;
    }

    public String getCok_procedure_proc() {
        return cok_procedure_proc;
    }

    public void setCok_procedure_proc(String cok_procedure_proc) {
        this.cok_procedure_proc = cok_procedure_proc;
    }

    public String getCok_procedure_sub() {
        return cok_procedure_sub;
    }

    public void setCok_procedure_sub(String cok_procedure_sub) {
        this.cok_procedure_sub = cok_procedure_sub;
    }

    public int getCok_procedure_img() {
        return cok_procedure_img;
    }

    public void setCok_procedure_img(int cok_procedure_img) {
        this.cok_procedure_img = cok_procedure_img;
    }
}

