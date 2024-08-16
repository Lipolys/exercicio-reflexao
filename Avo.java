package atividade;

public class Avo extends Pessoa {
    private String inss;

    public String getInss() {
        return inss;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    @Override
    public String getTipoVoz() {
        return "Grave";
    }
}
