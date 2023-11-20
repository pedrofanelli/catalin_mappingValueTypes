package com.example.demo.converter;

import com.example.demo.model.GermanZipcode;
import com.example.demo.model.SwissZipcode;
import com.example.demo.model.Zipcode;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ZipcodeConverter implements AttributeConverter<Zipcode,String>  {

	@Override
    public String convertToDatabaseColumn(Zipcode attribute) {
        return attribute.getValue();
    }

    @Override
    public Zipcode convertToEntityAttribute(String s) {
        if (s.length() == 5)
            return new GermanZipcode(s);
        else if (s.length() == 4)
            return new SwissZipcode(s);
        throw new IllegalArgumentException(
                "Unsupported zipcode in database: " + s
        );
    }
}
