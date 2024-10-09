package simulador.cache;

import com.sun.tools.javac.Main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cache {
    private static int totalMisses = 0;
    private static int missCompulsorio = 0;
    private static int missConflito = 0;
    private static int missCapacidade = 0;
    private static int totalHits = 0;
    private static int totalAcessos = 0;
    public static int nBitsOffset;
    public static int nBitsIndice;
    public static int nSets;
    public static int bSize;
    public static int assoc;
    public static int nBitsTag;

    public Cache() {
    }

    public static void simulaCache(Bloco[][] cache, int assoc, String subs, List<Integer> enderecos) {
        Random random = new Random();
        Substituicao politicaSubstituicao = Substituicao.getSubstituicao(subs);

        for (int endereco : enderecos) {
            totalAcessos++;
            int tag = endereco >> (nBitsOffset + nBitsIndice); // Calcule a tag corretamente
            int indice = (endereco >> nBitsOffset) & (cache.length - 1); // Cálculo do índice

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
                    // Miss de conflito
                    if (politicaSubstituicao == Substituicao.RANDOM) {
                        int posicaoSubstituir = random.nextInt(assoc);
                        cache[indice][posicaoSubstituir].setTag(tag);
                        missConflito++;
                    } else {
                        // Lógica para LRU ou FIFO se implementado
                        missCapacidade++;
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
        int tamanhoTotalCache = (nSets * bSize * assoc);

        if (flagSaida == 0) {
            resultado =
                    "Tamanho total da cache: " + tamanhoTotalCache + "\n" +
                            "Total de acessos: " + totalAcessos + "\n" +
                            "Total de hits: " + totalHits + "\n" +
                            "Taxa de hits: " + String.format("%.0f", taxaHit * 100) + "%\n" +  // Converter para porcentagem
                            "Total de misses: " + totalMisses + "\n" +
                            "Taxa de misses: " + String.format("%.0f", taxaMiss * 100) + "%\n" + // Converter para porcentagem
                            "Misses compulsórios: " + missCompulsorio + "\n" +
                            "Taxa de miss compulsório: " + String.format("%.0f", taxaMissCompulsorio * 100) + "%\n" + // Converter para porcentagem
                            "Misses de conflito: " + missConflito + "\n" +
                            "Taxa de miss de conflito: " + String.format("%.0f", taxaMissConflito * 100) + "%\n" + // Converter para porcentagem
                            "Misses de capacidade: " + missCapacidade + "\n" +
                            "Taxa de miss de capacidade: " + String.format("%.0f", taxaMissCapacidade * 100) + "%\n"; // Converter para porcentagem
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

    public List<Integer> lerEnderecosBinario(String caminhoArquivo) {
        List<Integer> enderecos = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(caminhoArquivo)) {
            byte[] buffer = new byte[4]; // 4 bytes para um inteiro (32 bits)

            while (fis.read(buffer) != -1) {
                // Converte os 4 bytes para um inteiro (big-endian)
                int endereco = ByteBuffer.wrap(buffer).getInt();
                enderecos.add(endereco);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Tratar exceção
        }

        return enderecos;
    }
}
