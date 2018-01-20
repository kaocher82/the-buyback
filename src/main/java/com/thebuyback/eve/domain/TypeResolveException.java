package com.thebuyback.eve.domain;

public class TypeResolveException extends RuntimeException {
    public TypeResolveException(final Object obj) {
        super(obj.toString());
    }
}
