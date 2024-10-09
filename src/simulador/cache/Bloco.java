package simulador.cache;

public class Bloco {
    private boolean bitValidade;
    private int tag;

    public Bloco() {
        this.bitValidade = false;
        this.tag = 0;
    }

    public boolean isBitValidade() {
        return bitValidade;
    }

    public void setBitValidade(boolean bitValidade) {
        this.bitValidade = bitValidade;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}