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

    public Cache() {
    }

    public static void simulaCache(Bloco[][] cache, int assoc, String subs, List<Integer> enderecos) {
        Random random = new Random();

        // Converter a string de substituição para enumeração
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
                } else if (isFull(cache[indice])) {
                    // Se o conjunto está cheio, aplicar política de substituição
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
        double taxaMiss = (double) totalMisses / totalAcessos;
        double taxaHit = (double) totalHits / totalAcessos;

        if (flagSaida == 0) {
            resultado = "Total de acessos: " + totalAcessos + "\n" +
                    "Total de hits: " + totalHits + "\n" +
                    "Taxa de hits: " + String.format("%.2f", taxaHit) + "\n" +
                    "Total de misses: " + totalMisses + "\n" +
                    "Taxa de misses: " + String.format("%.2f", taxaMiss) + "\n" +
                    "Misses compulsórios: " + missCompulsorio + "\n" +
                    "Misses de conflito: " + missConflito + "\n" +
                    "Misses de capacidade: " + missCapacidade + "\n";
        } else if (flagSaida == 1) {
            resultado = totalAcessos + "," + taxaHit + "," + taxaMiss + "," + missCompulsorio + "," + missConflito + "," + missCapacidade;
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
