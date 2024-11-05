public class Pai extends Pessoa{
    private String ctps;

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    @Override
    public String getTipoVoz() {
        return "Media";
    }
}
