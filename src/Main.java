import simulador.cache.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int nsets = 256;
        int bsize = 4;
        int assoc = 1;
        String tipoSubstituicao = "R";
        int flagSaida = 1;

        String caminhoArquivo = "src/simulador/enderecos/bin_100.bin";

        Cache cacheObj = new Cache();

        Cache.nBitsOffset = (int) (Math.log(bsize) / Math.log(2)); // Calcular bits de offset
        Cache.nBitsIndice = (int) (Math.log(nsets) / Math.log(2));     // Calcular bits de índice
        Cache.nBitsTag = (32 - Cache.nBitsOffset - Cache.nBitsIndice); // Calcular bits de tag

        Cache.nSets = nsets;
        Cache.bSize = bsize;
        Cache.assoc = assoc;

        Bloco[][] cache = cacheObj.inicializarCache(nsets, assoc);

        List<Integer> listaEnderecos = cacheObj.lerEnderecosBinario(caminhoArquivo);

        Cache.simulaCache(cache, assoc, tipoSubstituicao, listaEnderecos);

        String resultado = cacheObj.montaResultado(flagSaida);
        System.out.println(resultado);
    }
}
