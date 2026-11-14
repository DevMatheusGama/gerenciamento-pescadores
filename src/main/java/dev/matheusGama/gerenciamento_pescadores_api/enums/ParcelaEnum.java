package dev.matheusGama.gerenciamento_pescadores_api.enuns;

public enum ParcelaEnum {
    JANEIRO(1, "Janeiro"),
    FEVEREIRO(2, "Fevereiro"),
    MARCO(3, "Março"),
    ABRIL(4, "Abril"),
    MAIO(5, "Maio"),
    JUNHO(6, "Junho"),
    JULHO(7, "Julho"),
    AGOSTO(8, "Agosto"),
    SETEMBRO(9, "Setembro"),
    OUTUBRO(10, "Outubro"),
    NOVEMBRO(11, "Novembro"),
    DEZEMBRO(12, "Dezembro");

    private long numeroMes;
    private String nomeMes;

    ParcelaEnum( long numeroMes, String nomeMes) {
        this.numeroMes = numeroMes;
        this.nomeMes = nomeMes;
    }

    public long getNumeroMes() {
        return numeroMes;
    }

    public String getNomeMes() {
        return nomeMes;
    }
    public static String obterNomeMes(int numero) {
        for (ParcelaEnum parcela : ParcelaEnum.values()) {
            if (parcela.numeroMes == numero) {
                return parcela.getNomeMes();
            }
        }

        throw new IllegalArgumentException("Mês inválido: " + numero);
    }
}
