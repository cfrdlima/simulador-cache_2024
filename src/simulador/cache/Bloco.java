package simulador.cache;

public class Bloco {
    private boolean bitValidade;
    private String tag;

    public Bloco(String tag, boolean bitValidade) {
        this.tag = tag;
        this.bitValidade = bitValidade;
    }

    public Bloco() {
        this.bitValidade = false;
        this.tag = "";
    }

    public boolean isBitValidade() {
        return bitValidade;
    }

    public void setBitValidade(boolean bitValidade) {
        this.bitValidade = bitValidade;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
