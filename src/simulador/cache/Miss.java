package simulador.cache;

public enum Miss {
    COMPULSORY, CAPACITY, CONFLICT;

    public static Miss getMiss(String miss) {
        switch (miss) {
            case "COMPULSORY":
                return COMPULSORY;
            case "CAPACITY":
                return CAPACITY;
            case "CONFLICT":
                return CONFLICT;
            default:
                return null;
        }
    }

    public String getDescricao() {
        switch (this) {
            case COMPULSORY:
                return "COMPULSORY";
            case CAPACITY:
                return "CAPACITY";
            case CONFLICT:
                return "CONFLICT";
            default:
                return null;
        }
    }

    public static String[] getMisses() {
        return new String[] {"COMPULSORY", "CAPACITY", "CONFLICT"};
    }
}
