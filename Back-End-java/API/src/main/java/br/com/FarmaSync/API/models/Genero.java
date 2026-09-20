package br.com.FarmaSync.API.models;

public enum Genero {

    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro"),
    PREFIRO_NAO_DIZER("Prefiro não dizer");

    private final String valorDb;

    Genero(String valorDb) {
        this.valorDb = valorDb;
    }

    public String getValorDb() {
        return valorDb;
    }

    public static Genero fromValorDb(String valor) {
        for (Genero genero : Genero.values()) {
            if (genero.valorDb.equalsIgnoreCase(valor)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Gênero inválido : " + valor);
    }

}


