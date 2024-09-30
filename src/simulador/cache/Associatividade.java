package simulador.cache;

public enum Associatividade {
    DIRETA, CONJUNTO, TOTAL;

    public String getDescricao() {
        switch (this) {
            case DIRETA:
                return "DIRETA";
            case CONJUNTO:
                return "CONJUNTO";
            case TOTAL:
                return "TOTAL";
            default:
                return null;
        }
    }

    public static Associatividade getAssociatividade(int associatividade) {
        switch (associatividade) {
            case 1:
                return DIRETA;
            case 2:
                return CONJUNTO;
            case 3:
                return TOTAL;
            default:
                return null;
        }
    }

    public static String[] getAssociatividades() {
        return new String[] {"DIRETA", "CONJUNTO", "TOTAL"};
    }
}
