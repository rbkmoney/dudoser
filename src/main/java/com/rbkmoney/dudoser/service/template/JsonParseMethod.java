package com.rbkmoney.dudoser.service.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.List;

public class JsonParseMethod implements TemplateMethodModel {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() != 2) {
            throw new TemplateModelException("Wrong arguments");
        }

        try {
            return objectMapper.readValue((String) args.get(0), Class.forName((String) args.get(1)));
        } catch (IOException | ClassNotFoundException e) {
            throw new TemplateModelException("Exception while reading json value", e);
        }
    }

}
