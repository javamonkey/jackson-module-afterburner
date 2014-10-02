package com.fasterxml.jackson.module.afterburner.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public final class IntFieldPropertyWriter
    extends OptimizedBeanPropertyWriter<IntFieldPropertyWriter>
{
    private final int _suppressableInt;
    private final boolean _suppressableIntSet;

    public IntFieldPropertyWriter(BeanPropertyWriter src, BeanPropertyAccessor acc, int index,
            JsonSerializer<Object> ser) {
        super(src, acc, index, ser);

        if (MARKER_FOR_EMPTY == _suppressableValue) {
            _suppressableInt = 0;
            _suppressableIntSet = true;
        } else if (_suppressableValue instanceof Integer) {
            _suppressableInt = (Integer)_suppressableValue;
            _suppressableIntSet = true;
        } else {
            _suppressableInt = 0;
            _suppressableIntSet = false;
        }
    }

    @Override
    public BeanPropertyWriter withSerializer(JsonSerializer<Object> ser) {
        return new IntFieldPropertyWriter(this, _propertyAccessor, _propertyIndex, ser);
    }
    
    @Override
    public IntFieldPropertyWriter withAccessor(BeanPropertyAccessor acc) {
        if (acc == null) throw new IllegalArgumentException();
        return new IntFieldPropertyWriter(this, acc, _propertyIndex, _serializer);
    }

    /*
    /**********************************************************
    /* Overrides
    /**********************************************************
     */

    @Override
    public final void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception
    {
        if (broken) {
            fallbackWriter.serializeAsField(bean, jgen, prov);
            return;
        }
        try {
            int value = _propertyAccessor.intField(bean, _propertyIndex);
            if (!_suppressableIntSet || _suppressableInt != value) {
                jgen.writeFieldName(_name);
                jgen.writeNumber(value);
            }
        } catch (IllegalAccessError e) {
            _reportProblem(bean, e);
            fallbackWriter.serializeAsField(bean, jgen, prov);
        } catch (SecurityException e) {
            _reportProblem(bean, e);
            fallbackWriter.serializeAsField(bean, jgen, prov);
        }
    }

    @Override
    public final void serializeAsElement(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception
    {
        if (broken) {
            fallbackWriter.serializeAsElement(bean, jgen, prov);
            return;
        }
        try {
            int value = _propertyAccessor.intField(bean, _propertyIndex);
            if (!_suppressableIntSet || _suppressableInt != value) {
                jgen.writeNumber(value);
            } else { // important: MUST output a placeholder
                serializeAsPlaceholder(bean, jgen, prov);
            }
        } catch (IllegalAccessError e) {
            _reportProblem(bean, e);
            fallbackWriter.serializeAsElement(bean, jgen, prov);
        } catch (SecurityException e) {
            _reportProblem(bean, e);
            fallbackWriter.serializeAsElement(bean, jgen, prov);
        }
    }
}
