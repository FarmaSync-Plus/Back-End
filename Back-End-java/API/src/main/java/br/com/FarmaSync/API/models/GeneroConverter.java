package br.com.FarmaSync.API.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GeneroConverter implements AttributeConverter<Genero, String> {

@Override
public String convertToDatabaseColumn(Genero genero) {
    return (genero!=null) ? genero.getValorDb() : null;
}
@Override
public Genero convertToEntityAttribute(String dbData) {
    if(dbData==null) {
        return null;
    }
    for (Genero genero : Genero.values()) {
        if (genero.getValorDb().equalsIgnoreCase(dbData)) {
            return genero;
        }
    }
    throw new IllegalArgumentException("Valor de genero desconhecido : " + dbData);
}







}
