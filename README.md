JDK 17


Responsável 1

1. Configuração Inicial e Leitura dos Arquivos de Entrada
        ◦ Implementar a leitura dos parâmetros de entrada da linha de comando (nsets, bsize, assoc, substituição, etc.).
        ◦ Implementar a função de leitura do arquivo de entrada binário com endereços de 32 bits, garantindo que os dados sejam lidos corretamente em formato big-endian.
        ◦ Garantir que os parâmetros da cache sejam calculados corretamente, incluindo os bits de offset, índice e tag.
    • Código envolvido:
        ◦ Leitura e conversão dos parâmetros da linha de comando.
        ◦ Função para converter os endereços lidos do arquivo binário em inteiros utilizáveis na simulação.
        ◦ Cálculo de n_bits_offset, n_bits_indice e n_bits_tag.
3. Políticas de Substituição (Random, FIFO, LRU)
        ◦ Implementar a política de substituição Random (obrigatória).
        ◦ Implementar as políticas de substituição FIFO e LRU como bônus (opcional), garantindo que funcionem corretamente com as diferentes associatividades.
    • Código envolvido:
        ◦ Lógica da substituição Random.
        ◦ Implementar as funções adicionais para FIFO e LRU, e garantir que o simulador use a política correta com base nos parâmetros de entrada.


Responsável 2

2. Simulação do Comportamento da Cache (Acessos, Hits, Misses)
    • Responsável 2:
        ◦ Implementar a lógica da simulação da cache:
            ▪ Verificar se há hit ou miss.
            ▪ Se houver miss, diferenciar entre miss compulsório, de conflito ou de capacidade.
        ◦ Inicializar a estrutura da cache com as entradas necessárias, que armazenam as tags e os bits de validade.
        ◦ Implementar as estatísticas da cache (total de acessos, hits, misses, etc.).
    • Código envolvido:
        ◦ Estrutura da cache (inicialização e organização das entradas da cache).
        ◦ Lógica de acesso à cache, verificando hits e misses, e classificando os misses.
        ◦ Cálculo das estatísticas e taxa de hits e misses.
4. Geração do Relatório Final (Saída)
    • Responsável 2:
        ◦ Implementar a saída de dados com as estatísticas finais da simulação:
            ▪ Total de acessos.
            ▪ Taxa de hits, misses, e classificação dos misses (compulsório, de capacidade e de conflito).
        ◦ Implementar os dois formatos de saída, com base no valor de flag_saida:
            ▪ flag_saida = 0: formato livre.
            ▪ flag_saida = 1: formato padrão especificado.
    • Código envolvido:
        ◦ Função para calcular e exibir as estatísticas, formatando a saída corretamente com base no parâmetro flag_saida.


Responsável 1 e 2

5. Testes e Integração
        ◦ Testar a implementação com os arquivos de entrada binários fornecidos (100, 1000, 10.000, 186676 endereços).
        ◦ Verificar a funcionalidade completa do simulador com diferentes parâmetros de associatividade, número de conjuntos, e políticas de substituição.
        ◦ Gerar os relatórios de saída esperados e corrigir eventuais erros de cálculo ou lógica.
        ◦ Garantir que o código funcione corretamente com os parâmetros de linha de comando e as diferentes configurações da cache.
Resumo da Divisão:
    • Responsável 1:
        ◦ Leitura dos parâmetros de entrada e arquivo binário.
        ◦ Implementação das políticas de substituição (Random obrigatória, FIFO e LRU como bônus).
    • Responsável 2:
        ◦ Simulação da cache (verificar hits e misses, classificar misses).
        ◦ Geração do relatório final (estatísticas, formatos de saída).
    • Ambos:
        ◦ Testes e Integração do código final.

