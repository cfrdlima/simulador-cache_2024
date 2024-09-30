package simulador.cache;

public enum Substituicao {
    LRU, FIFO, RANDOM;

    public static Substituicao getSubstituicao(String substituicao) {
        switch (substituicao) {
            case "LRU":
                return LRU;
            case "FIFO":
                return FIFO;
            case "RANDOM":
                return RANDOM;
            default:
                return null;
        }
    }

    public String getDescricao() {
        switch (this) {
            case LRU:
                return "LRU";
            case FIFO:
                return "FIFO";
            case RANDOM:
                return "RANDOM";
            default:
                return null;
        }
    }

    public static String[] getSubstituicoes() {
        return new String[] {"LRU", "FIFO", "RANDOM"};
    }
}
