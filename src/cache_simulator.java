import simulador.cache.Cache;
import simulador.cache.Bloco;

import java.io.IOException;
import java.util.List;

public class cache_simulator {
    public static void main(String[] args) throws IOException {

        // Verificar se o número de argumentos está correto
        if (args.length != 6){
            System.out.println("Numero de argumentos incorreto. Utilize:");
            System.out.println("java cache_simulator <nsets> <bsize> <assoc> <substituição> <flag_saida> arquivo_de_entrada");
            System.exit(1);
        }

        // Ler argumentos
        int nsets = Integer.parseInt(args[0]);
        int bsize = Integer.parseInt(args[1]);
        int assoc = Integer.parseInt(args[2]);
        String subst = args[3];
        int flagOut = Integer.parseInt(args[4]);
        String arquivoEntrada = args[5];

        String caminhoArquivo = "./simulador/enderecos/" + arquivoEntrada;

        Cache cacheObj = new Cache();

        // Verificar se o número de conjuntos é uma potência de 2
        Cache.nBitsOffset = (int) (Math.log(bsize) / Math.log(2)); // Calcular bits de offset
        Cache.nBitsIndice = (int) (Math.log(nsets) / Math.log(2));     // Calcular bits de índice
        Cache.nBitsTag = (32 - Cache.nBitsOffset - Cache.nBitsIndice); // Calcular bits de tag

        // Inicializar cache
        Cache.nSets = nsets;
        Cache.bSize = bsize;
        Cache.assoc = assoc;

        // Inicializar cache
        Bloco[][] cache = cacheObj.inicializarCache(nsets, assoc);

        // Ler endereços do arquivo
        List<Integer> listaEnderecos = cacheObj.lerEnderecosBinario(caminhoArquivo);

        // Simular cache
        Cache.simulaCache(cache, assoc, subst, listaEnderecos);

        // Montar resultado
        String resultado = cacheObj.montaResultado(flagOut);
        System.out.println(resultado);
    }
}
