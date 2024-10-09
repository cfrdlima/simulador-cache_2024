import simulador.cache.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int nsets = 16;
        int bsize = 2;
        int assoc = 8;
        String tipoSubstituicao;
        int flagSaida = 1;

        String caminhoArquivo = "src/simulador/enderecos/bin_1000.bin";

        Cache cacheObj = new Cache();

        Cache.nBitsOffset = (int) (Math.log(bsize) / Math.log(2)); // Calcular bits de offset
        Cache.nBitsIndice = (int) (Math.log(nsets) / Math.log(2));     // Calcular bits de índice
        Cache.nBitsTag = (32 - Cache.nBitsOffset - Cache.nBitsIndice); // Calcular bits de tag

        Cache.nSets = nsets;
        Cache.bSize = bsize;
        Cache.assoc = assoc;

        Bloco[][] cache = cacheObj.inicializarCache(nsets, assoc);

        List<Integer> listaEnderecos = cacheObj.lerEnderecosBinario(caminhoArquivo);

        //Testando RANDOM
        tipoSubstituicao = "R";
        Cache.simulaCache(cache, assoc, tipoSubstituicao, listaEnderecos);
        String resultadoRANDOM = cacheObj.montaResultado(flagSaida);
        System.out.println("\nResultados para RANDOM:");
        System.out.println(resultadoRANDOM);

        Cache.resetEstatisticas();

        // Testando LRU
        tipoSubstituicao = "L";
        Cache.simulaCache(cache, assoc, tipoSubstituicao, listaEnderecos);
        String resultadoLRU = cacheObj.montaResultado(flagSaida);
        System.out.println("Resultados para LRU:");
        System.out.println(resultadoLRU);

        // Reinicia as estatísticas da cache para o próximo teste
        Cache.resetEstatisticas();

        // Testando FIFO
        tipoSubstituicao = "F"; 
        Cache.simulaCache(cache, assoc, tipoSubstituicao, listaEnderecos);
        String resultadoFIFO = cacheObj.montaResultado(flagSaida);
        System.out.println("Resultados para FIFO:");
        System.out.println(resultadoFIFO);

        // Reinicie as estatísticas da cache para o próximo teste
        Cache.resetEstatisticas();
    }
}
