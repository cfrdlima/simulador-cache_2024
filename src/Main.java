import simulador.cache.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int nsets = 256;
        int bsize = 4;
        int assoc = 1;
        String tipoSubstituicao = "R";
        int flagSaida = 0;

        Cache cacheObj = new Cache();

        Bloco[][] cache = cacheObj.inicializarCache(nsets, assoc);

        Cache.nBitsOffset = (int) (Math.log(bsize) / Math.log(2)); // Calcular bits de offset
        Cache.nBitsIndice = (int) (Math.log(nsets) / Math.log(2));     // Calcular bits de índice

        Cache.nSets = nsets;
        Cache.bSize = bsize;
        Cache.assoc = assoc;

        List<Integer> listaEnderecos = Arrays.asList(
                20, 2, 22, 4, 22, 10, 5, 5, 4, 1,
                15, 31, 31, 6, 31, 8, 2, 16, 9, 31,
                10, 3, 21, 5, 12, 28, 8, 19, 31, 24,
                3, 14, 4, 2, 30, 10, 10, 10, 11, 26,
                4, 31, 26, 7, 13, 26, 16, 25, 3, 24,
                16, 10, 3, 1, 20, 27, 9, 29, 25, 26,
                20, 18, 2, 2, 5, 1, 17, 31, 12, 26,
                10, 15, 19, 1, 25, 11, 1, 12, 25, 8,
                31, 2, 15, 11, 20, 3, 13, 16, 27, 1,
                4, 1, 23, 1, 31, 29, 0, 28, 15, 29
        );

        Cache.simulaCache(cache, assoc, tipoSubstituicao, listaEnderecos);

        String resultado = cacheObj.montaResultado(flagSaida);
        System.out.println(resultado);
    }
}
