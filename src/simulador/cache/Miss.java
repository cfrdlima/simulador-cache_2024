package simulador.cache;

public enum Miss {
    COMPULSORIO, CAPACIDADE, CONFLITO;

    public static Miss getMiss(String miss) {
        switch (miss) {
            case "COMPULSORIO":
                return COMPULSORIO;
            case "CAPACIDADE":
                return CAPACIDADE;
            case "CONFLITO":
                return CONFLITO;
            default:
                return null;
        }
    }

    public String getDescricao() {
        switch (this) {
            case COMPULSORIO:
                return "COMPULSORIO";
            case CAPACIDADE:
                return "CAPACIDADE";
            case CONFLITO:
                return "CONFLITO";
            default:
                return null;
        }
    }

    public static String[] getMisses() {
        return new String[] {"COMPULSORIO", "CAPACIDADE", "CONFLITO"};
    }
}
