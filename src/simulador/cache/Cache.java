package simulador.cache;

import java.util.List;
import java.util.Random;

public class Cache {
    private static int totalMisses = 0;
    private static int missCompulsorio = 0;
    private static int missConflito = 0;
    private static int missCapacidade = 0;
    private static int totalHits = 0;
    private static int totalAcessos = 0;
    public static int nBitsOffset;
    public static int nBitsIndice;

    public Cache() {}

    public static void simulaCache(Bloco[][] cache, int assoc, String subs, List<Integer> enderecos) {
        Random random = new Random();

        Substituicao politicaSubstituicao = Substituicao.getSubstituicao(subs);

        for (int endereco : enderecos) {
            totalAcessos++;
            int tag = endereco >> (nBitsOffset + nBitsIndice);
            int indice = (endereco >> nBitsOffset) & (cache.length - 1);  // Cálculo do índice

            boolean hit = false;
            int posicaoLivre = -1;  // Para verificar espaço vazio

            // Percorrer os blocos do conjunto
            for (int i = 0; i < assoc; i++) {
                if (cache[indice][i].isBitValidade() && cache[indice][i].getTag() == tag) {
                    // Hit encontrado
                    hit = true;
                    totalHits++;
                    break;
                }
                if (!cache[indice][i].isBitValidade() && posicaoLivre == -1) {
                    posicaoLivre = i;  // Encontrar a primeira posição livre
                }
            }

            if (!hit) {
                totalMisses++;
                if (posicaoLivre != -1) {
                    // Miss compulsório
                    cache[indice][posicaoLivre].setBitValidade(true);
                    cache[indice][posicaoLivre].setTag(tag);
                    missCompulsorio++;
                } else {
                    // Miss de conflito ou de capacidade
                    if (politicaSubstituicao == Substituicao.RANDOM) {
                        int posicaoSubstituir = random.nextInt(assoc);
                        cache[indice][posicaoSubstituir].setTag(tag);
                        missConflito++;
                    } else {
                        missCapacidade++;  // Se não puder substituir, miss de capacidade
                    }
                }
            }
        }
    }

    public String montaResultado(int flagSaida) {
        String resultado = "";
        double taxaMiss = (double) totalMisses / totalAcessos; // Converter para porcentagem
        double taxaHit = (double) totalHits / totalAcessos;   // Converter para porcentagem
        double taxaMissCompulsorio = (double) missCompulsorio / totalMisses; // Miss compulsório
        double taxaMissConflito = (double) missConflito / totalMisses; // Miss de conflito
        double taxaMissCapacidade = (double) missCapacidade / totalMisses; // Miss de capacidade

        if (flagSaida == 0) {
            resultado = "Total de acessos: " + totalAcessos + "\n" +
                    "Total de hits: " + totalHits + "\n" +
                    "Taxa de hits: " + String.format("%.2f", taxaHit) + " %\n" +  // Porcentagem
                    "Total de misses: " + totalMisses + "\n" +
                    "Taxa de misses: " + String.format("%.2f", taxaMiss) + " %\n" + // Porcentagem
                    "Misses compulsórios: " + missCompulsorio + "\n" +
                    "Taxa de miss compulsório: " + String.format("%.2f", taxaMissCompulsorio) + " %\n" +
                    "Misses de conflito: " + missConflito + "\n" +
                    "Taxa de miss de conflito: " + String.format("%.2f", taxaMissConflito) + " %\n" +
                    "Misses de capacidade: " + missCapacidade + "\n" +
                    "Taxa de miss de capacidade: " + String.format("%.2f", taxaMissCapacidade) + " %\n";
        } else if (flagSaida == 1) {
            resultado = totalAcessos + " " +
                    String.format("%.2f", taxaHit) + " " +
                    String.format("%.2f", taxaMiss) + " " +
                    String.format("%.2f",taxaMissCompulsorio) + " " +
                    String.format("%.2f",taxaMissConflito) + " " +
                    String.format("%.2f",taxaMissCapacidade);
        }

        return resultado;
    }

    public Bloco[][] inicializarCache(int nsets, int assoc) {
        Bloco[][] cache = new Bloco[nsets][assoc];
        for (int i = 0; i < nsets; i++) {
            for (int j = 0; j < assoc; j++) {
                cache[i][j] = new Bloco();  // Inicializar cada bloco
            }
        }
        return cache;
    }

    private static boolean isFull(Bloco[] conjunto) {
        for (Bloco bloco : conjunto) {
            if (!bloco.isBitValidade()) {
                return false;
            }
        }
        return true;
    }
}
