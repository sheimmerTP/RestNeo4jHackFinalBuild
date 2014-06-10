/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.travelport.restneohack.model.domain;


import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.neo4j.annotation.NodeEntity;

public class EmailAddressConverter implements GenericConverter {
    @Override
    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return new HashSet<GenericConverter.ConvertiblePair>(asList(new GenericConverter.ConvertiblePair(EmailAddress.class,String.class), new GenericConverter.ConvertiblePair(String.class, EmailAddress.class)));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source instanceof EmailAddress) {
            return ((EmailAddress) source).getEmail();
        }
        if (source instanceof String) {
            return new EmailAddress((String)source);
        }
        throw new ConversionFailedException(sourceType,targetType,source,null);
    }
}
