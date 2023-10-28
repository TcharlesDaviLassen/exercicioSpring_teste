package com.example.exercicio.Utils.classUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataSource implements JRDataSource {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Map<String, Object>> data;
    private int index = -1;

    public JsonDataSource(String jsonData) throws IOException {
        this.data = objectMapper.readValue(jsonData, List.class);
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return index < data.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Map<String, Object> map = data.get(index);
        return map.get(jrField.getName());
    }
}
