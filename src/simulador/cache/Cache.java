package simulador.cache;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    /*  Ele percorre cada endereço, calcula a tag e o índice, e verifica se o bloco correspondente já está na cache.
    Se não estiver, ele determina se o miss é compulsório, conflito, ou capacidade, com base no estado atual da cache. */
    public static void simulaCache(Bloco[][] cache, int assoc, String subs, List<Integer> enderecos) throws IOException {
        Random random = new Random();
        Substituicao politicaSubstituicao = Substituicao.getSubstituicao(subs);

        for (int endereco : enderecos) {
            totalAcessos++;

            // Calcular tag e índice
            int indice = (endereco >> nBitsOffset) & (cache.length - 1);  // Cálculo do índice
            int tag = endereco >> (nBitsOffset + nBitsIndice);  // Cálculo do tag

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
                    // Miss compulsório, pois há espaço livre no conjunto
                    cache[indice][posicaoLivre].setBitValidade(true);
                    cache[indice][posicaoLivre].setTag(tag);
                    missCompulsorio++;
                } else {
                    // Verifica se o conjunto está cheio
                    if (isFull(cache[indice])) {
                        missCapacidade++; // O conjunto está cheio, é um miss de capacidade
                        if (politicaSubstituicao == Substituicao.RANDOM) {
                            // Substituição aleatória
                            int posicaoSubstituir = random.nextInt(assoc);
                            cache[indice][posicaoSubstituir].setTag(tag);
                            missConflito++;
                        }else{
                            throw new IOException("Politica de substituição não implementada.");
                        }
                    } else {
                        missConflito++;
                    }
                }
            }
        }
    }

    //Este metodo organiza os resultados da simulação e os apresenta de acordo com o formato definido por flag_saida.
    public String montaResultado(int flagSaida) {
        String resultado = "";

        // Configura a localidade para garantir o uso de ponto decimal
        Locale.setDefault(Locale.US);

        double taxaMiss = (double) totalMisses / totalAcessos;
        double taxaHit = (double) totalHits / totalAcessos;
        double taxaMissCompulsorio = (totalMisses > 0) ? (double) missCompulsorio / totalMisses : 0;
        double taxaMissConflito = (totalMisses > 0) ? (double) missConflito / totalMisses : 0;
        double taxaMissCapacidade = (totalMisses > 0) ? (double) missCapacidade / totalMisses : 0;

        if (flagSaida == 0) {
            resultado = "Total de acessos: " + totalAcessos + "\n" +
                    "Total de hits: " + totalHits + "\n" +
                    "Taxa de hits: " + String.format("%.2f", taxaHit) + "\n" +
                    "Total de misses: " + totalMisses + "\n" +
                    "Taxa de misses: " + String.format("%.2f", taxaMiss) + "\n" +
                    "Misses compulsórios: " + missCompulsorio + "\n" +
                    "Taxa de miss compulsório: " + String.format("%.2f", taxaMissCompulsorio) + "\n" +
                    "Misses de conflito: " + missConflito + "\n" +
                    "Taxa de miss de conflito: " + String.format("%.2f", taxaMissConflito) + "\n" +
                    "Misses de capacidade: " + missCapacidade + "\n" +
                    "Taxa de miss de capacidade: " + String.format("%.2f", taxaMissCapacidade) + "\n";
        } else if (flagSaida == 1) {
            resultado = totalAcessos + " " +
                    String.format("%.2f", taxaHit) + " " +
                    String.format("%.2f", taxaMiss) + " " +
                    String.format("%.2f", taxaMissCompulsorio) + " " +
                    String.format("%.2f", taxaMissConflito) + " " +
                    String.format("%.2f", taxaMissCapacidade);
        }

        return resultado;
    }

    // Inicializar a cache representando os conjuntos e blocos da cache.
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
                return false; // Se encontrar um bloco inválido, o conjunto não está cheio
            }
        }
        return true; // Se todos os blocos forem válidos, o conjunto está cheio
    }

    //O metodo abre o arquivo binário e converte os bytes lidos em endereços de memória inteiros.
    public List<Integer> lerEnderecosBinario(String caminhoArquivo) throws IOException {
        List<Integer> enderecos = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(caminhoArquivo)) {
            byte[] buffer = new byte[4]; // 4 bytes para um inteiro (32 bits)

            while (fis.read(buffer) != -1) {
                // Converte os 4 bytes para um inteiro (big-endian)
                int endereco = ByteBuffer.wrap(buffer).getInt();
                enderecos.add(endereco);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (enderecos.isEmpty()) {
            throw new IOException("O arquivo de entrada está vazio ou não foi lido corretamente.");
        }

        return enderecos;
    }
}
