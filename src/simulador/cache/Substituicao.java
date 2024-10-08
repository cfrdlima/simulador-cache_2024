package simulador.cache;

public enum Substituicao {
    RANDOM; //    LRU, FIFO;

    public static Substituicao getSubstituicao(String substituicao) {
        switch (substituicao) {
            case "R":
                return RANDOM;
            default:
                return null;
        }
    }
}
