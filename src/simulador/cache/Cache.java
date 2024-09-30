package simulador.cache;

public class Cache {
    private Bloco bloco;
    private int tamanhoTotalCache;
    private int tamanhoBloco;
    private int associatividade;
    private Substituicao tipoSubstituicao;
    private int totalMisses;
    private int totalHits;
    private int totalAcessos;
    private int totalLeituras;
    private Miss tipoMiss;
    private String nomeSimulador;
    private double taxaHit;
    private double taxaMiss;
    private final int flag = 0;
}
