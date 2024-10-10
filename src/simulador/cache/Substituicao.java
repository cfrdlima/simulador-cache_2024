package simulador.cache;

//Este enum permite que o simulador aplique diferentes políticas de substituição, futuramente sera implementado FIFO e LRU.
public enum Substituicao {
    RANDOM;

    public static Substituicao getSubstituicao(String substituicao) {
        switch (substituicao) {
            case "R":
                return RANDOM;
            default:
                return null;
        }
    }
}
