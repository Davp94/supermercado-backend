package com.blumbit.supermercado.constant.enums;

public enum TipoMovimientoEnum {
    INGRESO((short)0),
    SALIDA((short)1),
    DEVOLUCION((short)2);

    private final short value;

    TipoMovimientoEnum(short value) {
        this.value = value;
    }

    public short getValue(){
        return value;
    }

}
