import simulador.cache.Cache;
import simulador.cache.Bloco;

import java.io.IOException;
import java.util.List;

public class cache_simulator {
    public static void main(String[] args) {

        if (args.length != 6) {
            System.out.println("Numero de argumentos incorreto. Utilize:");
            System.out.println("java cache_simulator <nsets> <bsize> <assoc> <substituição> <flag_saida> arquivo_de_entrada");
            System.exit(1);
        }

        try {
            // Tenta converter os argumentos para inteiros
            int nsets = Integer.parseInt(args[0]);
            int bsize = Integer.parseInt(args[1]);
            int assoc = Integer.parseInt(args[2]);
            int flagOut = Integer.parseInt(args[4]);

            // Verifica se a política de substituição é válida
            String subst = args[3];
            if (!subst.equals("R")) {
                throw new IllegalArgumentException("Política de substituição inválida. Use 'R' para Random.");
            }

            // Verifica o arquivo de entrada
            String arquivoEntrada = args[5];

            // Caminho para o arquivo
            String caminhoArquivo = "./simulador/enderecos/" + arquivoEntrada;

            // Inicializa a cache e simula
            Cache cacheObj = new Cache();

            // Calcula os bits de offset, índice e tag
            Cache.nBitsOffset = (int) (Math.log(bsize) / Math.log(2)); // Calcular bits de offset
            Cache.nBitsIndice = (int) (Math.log(nsets) / Math.log(2)); // Calcular bits de índice
            Cache.nBitsTag = (32 - Cache.nBitsOffset - Cache.nBitsIndice); // Calcular bits de tag

            // Configura os parâmetros globais da cache
            Cache.nSets = nsets;
            Cache.bSize = bsize;
            Cache.assoc = assoc;

            // Inicializa a cache
            Bloco[][] cache = cacheObj.inicializarCache(nsets, assoc);

            // Lê os endereços do arquivo binário
            List<Integer> listaEnderecos = cacheObj.lerEnderecosBinario(caminhoArquivo);

            // Executa a simulação da cache
            Cache.simulaCache(cache, assoc, subst, listaEnderecos);

            // Gera e exibe o resultado
            String resultado = cacheObj.montaResultado(flagOut);
            System.out.println(resultado);

        } catch (NumberFormatException e) {
            System.out.println("Erro: Certifique-se de que <nsets>, <bsize>, <assoc> e <flag_saida> são inteiros válidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro de entrada/saída: " + e.getMessage());
        }
    }
}

