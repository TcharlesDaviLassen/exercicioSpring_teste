package com.example.exercicio.enumType;

import java.util.Calendar;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ReportEntity {

    public static final String TITLE = "TITULO_RELATORIO";
    public static final String PARAM_SEP = "/SEP/";

    private Map<String, Object> params;
    public String viewId;
    public String id;
    public Calendar data;
    public String file;



    public void addParam(final String key, final RequireTypes value) {
        if (value == null) {
            return;
        }

        this.params.put(key, value.getId());
    }
}
