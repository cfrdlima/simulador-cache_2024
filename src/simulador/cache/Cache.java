package simulador.cache;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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
    public static int nSets;
    public static int bSize;
    public static int assoc;
    public static int nBitsTag;

    public Cache() {}

    public static void resetEstatisticas() {
        totalMisses = 0;
        missCompulsorio = 0;
        missConflito = 0;
        missCapacidade = 0;
        totalHits = 0;
        totalAcessos = 0;
    }

    public static void simulaCache(Bloco[][] cache, int assoc, String subs, List<Integer> enderecos) {
        Random random = new Random();
        Substituicao politicaSubstituicao = Substituicao.getSubstituicao(subs);
        List<Integer> PS_LRU[] = new List[nSets];  // Listas para guardar a ordem de uso para LRU
        List<Integer> PS_FIFO[] = new List[nSets]; // Listas para guardar a ordem de chegada para FIFO
    
        for (int i = 0; i < nSets; i++) {
            PS_LRU[i] = new ArrayList<>();
            PS_FIFO[i] = new ArrayList<>();
        }
    
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
    
                    // Atualizar LRU
                    if (politicaSubstituicao == Substituicao.LRU) {
                        PS_LRU[indice].remove((Integer) i);  // Remover a posição do bloco usado
                        PS_LRU[indice].add(i);  // Reposicionar no final (recente)
                    }
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
    
                    // Atualizar LRU e FIFO
                    if (politicaSubstituicao == Substituicao.LRU) {
                        PS_LRU[indice].add(posicaoLivre);  // Adicionar o bloco usado recentemente
                    } else if (politicaSubstituicao == Substituicao.FIFO) {
                        PS_FIFO[indice].add(posicaoLivre);  // Adicionar o bloco no final da fila
                    }
                } else {
                    // Se o conjunto está cheio
                    missConflito++;
    
                    int posicaoSubstituir = -1;
    
                    if (politicaSubstituicao == Substituicao.RANDOM) {
                        posicaoSubstituir = random.nextInt(assoc);
                    } else if (politicaSubstituicao == Substituicao.LRU) {
                        // Substituir o bloco menos recentemente usado (primeiro da lista)
                        posicaoSubstituir = PS_LRU[indice].remove(0);
                    } else if (politicaSubstituicao == Substituicao.FIFO) {
                        // Substituir o bloco mais antigo (primeiro da fila FIFO)
                        posicaoSubstituir = PS_FIFO[indice].remove(0);
                    }
    
                    // Realizar a substituição
                    cache[indice][posicaoSubstituir].setTag(tag);
    
                    // Atualizar as listas de LRU e FIFO após a substituição
                    if (politicaSubstituicao == Substituicao.LRU) {
                        PS_LRU[indice].add(posicaoSubstituir);  // Adicionar a nova posição usada
                    } else if (politicaSubstituicao == Substituicao.FIFO) {
                        PS_FIFO[indice].add(posicaoSubstituir);  // Adicionar ao final da fila
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
