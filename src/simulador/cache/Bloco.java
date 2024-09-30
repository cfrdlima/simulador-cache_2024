package simulador.cache;

public class Bloco {
    private boolean bitValidade;
    private String tag;

    Bloco(String tag, boolean bitValidade) {
        this.tag = tag;
        this.bitValidade = bitValidade;
    }

    Bloco() {
        this.bitValidade = false;
        this.tag = "";
    }

}
