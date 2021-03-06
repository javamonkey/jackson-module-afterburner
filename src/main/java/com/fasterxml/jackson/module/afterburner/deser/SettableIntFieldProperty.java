package com.fasterxml.jackson.module.afterburner.deser;

import java.io.IOException;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

public final class SettableIntFieldProperty
    extends OptimizedSettableBeanProperty<SettableIntFieldProperty>
{
    private static final long serialVersionUID = 1L;

    public SettableIntFieldProperty(SettableBeanProperty src,
            BeanPropertyMutator mutator, int index)
    {
        super(src, mutator, index);
    }

    public SettableIntFieldProperty(SettableIntFieldProperty src,
            JsonDeserializer<?> deser)
    {
        super(src, deser);
    }

    public SettableIntFieldProperty(SettableIntFieldProperty src, PropertyName name) {
        super(src, name);
    }
    
    @Override
    public SettableIntFieldProperty withName(PropertyName name) {
        return new SettableIntFieldProperty(this, name);
    }
    
    @Override
    public SettableIntFieldProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new SettableIntFieldProperty(this, deser);
    }
    
    @Override
    public SettableIntFieldProperty withMutator(BeanPropertyMutator mut) {
        return new SettableIntFieldProperty(_originalSettable, mut, _optimizedIndex);
    }

    /*
    /********************************************************************** 
    /* Deserialization
    /********************************************************************** 
     */

    @Override
    public void deserializeAndSet(JsonParser p, DeserializationContext ctxt,
            Object bean) throws IOException
    {
        _propertyMutator.intField(bean, p.getValueAsInt());
    }

    @Override
    public void set(Object bean, Object value) throws IOException {
        // not optimal (due to boxing), but better than using reflection:
        _propertyMutator.intField(bean, ((Number) value).intValue());
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser p,
            DeserializationContext ctxt, Object instance) throws IOException
    {
        return setAndReturn(instance, p.getValueAsInt());
    }    
}
