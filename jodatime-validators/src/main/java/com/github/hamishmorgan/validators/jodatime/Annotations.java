package com.github.hamishmorgan.validators.jodatime;

import java.lang.annotation.Annotation;

class Annotations {

    static int getDefaultValueAsInt(Class<? extends Annotation> annotation, String fieldName) throws NoSuchMethodException {
        return (Integer) annotation.getMethod(fieldName).getDefaultValue();
    }

}
