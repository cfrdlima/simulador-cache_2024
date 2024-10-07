import simulador.cache.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Parâmetros de configuração da cache
        int nsets = 4;  // Exemplo: 4 conjuntos
        int assoc = 8;  // Exemplo: associatividade 2
        int flagSaida = 0;  // Exemplo: saída em formato livre

        // Inicializar a cache
        Cache cacheObj = new Cache();

        // Inicializar cache com número de conjuntos e associatividade
        Bloco[][] cache = cacheObj.inicializarCache(nsets, assoc);

        // Inicializar os bits
        Cache.nBitsOffset = (int) (Math.log(4) / Math.log(2)); // Exemplo: bloco de 4 bytes
        Cache.nBitsIndice = (int) (Math.log(nsets) / Math.log(2));

        // Exemplo de lista de endereços a serem simulados
        List<Integer> listaEnderecos = Arrays.asList(
                20,
                2,
                22,
                4,
                22,
                10,
                5,
                5,
                4,
                1,
                15,
                31,
                31,
                6,
                31,
                8,
                2,
                16,
                9,
                31,
                10,
                3,
                21,
                5,
                12,
                28,
                8,
                19,
                31,
                24,
                3,
                14,
                4,
                2,
                30,
                10,
                10,
                10,
                11,
                26,
                4,
                31,
                26,
                7,
                13,
                26,
                16,
                25,
                3,
                24,
                16,
                10,
                3,
                1,
                20,
                27,
                9,
                29,
                25,
                26,
                20,
                18,
                2,
                2,
                5,
                1,
                17,
                31,
                12,
                26,
                10,
                15,
                19,
                1,
                25,
                11,
                1,
                12,
                25,
                8,
                31,
                2,
                15,
                11,
                20,
                3,
                13,
                16,
                27,
                1,
                4,
                1,
                23,
                1,
                31,
                29,
                0,
                28,
                15,
                29
        );

        // Simular acessos à cache com política de substituição "Random"
        cacheObj.simulaCache(cache, assoc, "R", listaEnderecos);

        // Gerar e exibir o resultado
        String resultado = cacheObj.montaResultado(flagSaida);
        System.out.println(resultado);
        System.out.println("2");
    }
}
