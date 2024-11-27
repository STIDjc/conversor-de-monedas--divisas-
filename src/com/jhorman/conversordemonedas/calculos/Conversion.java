package com.jhorman.conversordemonedas.calculos;

import com.google.gson.annotations.SerializedName;
import com.jhorman.conversordemonedas.consultas.ConversionApi;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conversion {
    @SerializedName("base_code")
    private final String monedaOrigen;
    @SerializedName("target_code")
    private final String monedaDestino;
    private final BigDecimal monto;
    private final double tasaDeConversion;
    private final BigDecimal resultado;

    public Conversion(ConversionApi conversionApi, BigDecimal monto) {
        this.monedaOrigen = conversionApi.baseCode();
        this.monedaDestino = conversionApi.targetCode();
        this.tasaDeConversion = Double.parseDouble(conversionApi.conversionRate());
        this.monto = monto;
        this.resultado = (this.monto
                .multiply(new BigDecimal(this.tasaDeConversion))
                        .setScale(2, RoundingMode.DOWN));
    }


    @Override
    public String toString() {
        return "\nTasa de cambio del día:\n"+monedaOrigen+" $1 ==> "+monedaDestino+" $"+tasaDeConversion+
                "\n\n¡Conversión realizada!\n"+monedaOrigen+" $"+monto+" ==> "+monedaDestino+" $"+resultado;
    }
}
