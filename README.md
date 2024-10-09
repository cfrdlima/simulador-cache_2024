**Passo a passo execuçao do simulador da cache:**

> Necessário JDK 17
> Para rodar o simulador é necessário entrar na pasta raiz do projeto onde se encontra o executável do projeto.
> Para compilar o projeto é só executar o comando: **javac cache_simulator.java**

> Todos os arquivos necessarios ja se en contram no projeto na pasta simulador-cache_2024/src/simulador/enderecos. 

> Apenas a política de substituição "RANDOM" foi implementada, qualquer outra opção diferente ocorrerá erro.

**Comando para executar:**

> java cache_simulator <nsets> <bsize> <assoc> <substituição> <flag_saida> arquivo_de_entrada

**Exemplo:**

> java cache_simulator 16 2 8 R 1 bin_10000.bin
