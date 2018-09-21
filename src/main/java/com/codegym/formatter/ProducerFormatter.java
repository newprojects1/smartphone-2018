package com.codegym.formatter;

import com.codegym.model.Producer;
import com.codegym.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class ProducerFormatter implements Formatter<Producer> {
    private ProducerService producerService;

    @Autowired
    public ProducerFormatter(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public Producer parse(String text, Locale locale) throws ParseException {
        return producerService.findById(Long.parseLong(text));
    }

    @Override
    public String print(Producer object, Locale locale) {
        return null;
    }
}
