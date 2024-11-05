public class Filho extends Pessoa {
    private String reservista;

    public String getReservista() {
        return reservista;
    }

    public void setReservista(String reservista) {
        this.reservista = reservista;
    }

    @Override
    public String getTipoVoz() {
        return "Aguda";
    }
}
