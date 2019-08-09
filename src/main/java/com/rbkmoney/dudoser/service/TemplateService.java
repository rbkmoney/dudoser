package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.exception.UnknownException;
import com.rbkmoney.dudoser.service.template.JsonParseMethod;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by inal on 18.11.2016.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class TemplateService {

    private static final String UNUSED_TEMPLATE_NAME = "templateName";
    private final Configuration freemarkerConfiguration;

    @PostConstruct
    public void postConstruct() {
        freemarkerConfiguration.setObjectWrapper(
                new Java8ObjectWrapper(freemarker.template.Configuration.getVersion()));
    }

    public String getFilledContent(String templateString, Map<String, Object> model) {
        try {
            model.put("jsonParse", new JsonParseMethod());
            Template t = new Template(UNUSED_TEMPLATE_NAME, new StringReader(templateString), freemarkerConfiguration);
            Writer out = new StringWriter();
            t.process(model, out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            log.error("Throwing unknown exception while template processing", e);
            throw new UnknownException(e);
        }
    }
}
