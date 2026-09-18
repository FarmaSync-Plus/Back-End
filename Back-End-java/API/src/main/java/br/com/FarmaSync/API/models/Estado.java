package br.com.FarmaSync.API.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Estado {

    AC, AL, AP, AM, BA, CE, DF, ES, GO,
    MA, MT, MS, MG, PA, PB, PR, PE, PI,
    RJ, RN, RS, RO, RR, SC, SP, SE, TO;



@JsonCreator
  public static Estado fromString(String value) {
        if(value == null){
            return null;
        }
        for(Estado estado : Estado.values()) {
            if (estado.toString().equalsIgnoreCase(value.trim())) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado Inválido " +value);
}

}
