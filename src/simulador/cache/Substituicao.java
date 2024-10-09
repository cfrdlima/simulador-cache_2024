package simulador.cache;

public enum Substituicao {
    RANDOM, LRU, FIFO;

    public static Substituicao getSubstituicao(String substituicao) {
        switch (substituicao) {
            case "R":
                return RANDOM;
            case "L":
                return LRU;
            case "F":
                return FIFO;
            default:
                return null;
        }
    }
}
