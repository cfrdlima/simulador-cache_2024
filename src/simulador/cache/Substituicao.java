package simulador.cache;

public enum Substituicao {
    LRU, FIFO, RANDOM;

    public static Substituicao getSubstituicao(String substituicao) {
        switch (substituicao) {
            case "L":
                return LRU;
            case "F":
                return FIFO;
            case "R":
                return RANDOM;
            default:
                return null;
        }
    }
}
