import java.util.ArrayList;
import java.util.List;

public class casoBase {

    public static void main(String[] args) {

        ArrayList<Integer> conjConflito = new ArrayList<>();
        int iteracao = 0;
        int regra = 0;
        String leitura = "";
        String memoriaDeTrabalho = "dcbadca";

        List<tabelaDeProducaoLinha> linhasDeProducao = carregarLinhasDeProducao();

        do {
            conjConflito.clear();

            for (int i = 0; i < memoriaDeTrabalho.length() - 1; i++) {
                leitura = memoriaDeTrabalho.substring(i, i + 2);
                for (tabelaDeProducaoLinha linha : linhasDeProducao) {
                    conjConflito.add(linha.encontrarRegra(leitura));
                }
            }

            for (Integer integer : conjConflito) {
                if (integer != 0) {
                    regra = integer;
                    break;
                } else {
                    regra = 0;
                }
            }
            for (tabelaDeProducaoLinha linha : linhasDeProducao) {
                if (regra == linha.getNumero()) {
                    memoriaDeTrabalho = memoriaDeTrabalho.replaceFirst(linha.getPadrao(), linha.getRegra());
                    break;
                }
            }

            iteracao++;
        } while (conjConflito.stream().anyMatch(integer -> integer != 0));

        System.out.println("Resultado: " + memoriaDeTrabalho);
        System.out.println("Iterações: " + iteracao);
    }
    private static List<tabelaDeProducaoLinha> carregarLinhasDeProducao() {
        List<tabelaDeProducaoLinha> linhas = new ArrayList<>();
        linhas.add(new linha1());
        linhas.add(new linha2());
        linhas.add(new linha3());
        linhas.add(new linha4());
        linhas.add(new linha5());
        linhas.add(new linha6());

        return linhas;
    }
}


abstract class tabelaDeProducaoLinha {
    protected int numero;
    protected String padrao;
    protected String regra;

    public int getNumero() {
        return numero;
    }
    public String getPadrao() {
        return padrao;
    }
    public String getRegra() {
        return regra;
    }

    public int encontrarRegra(String leitura) {
        if(leitura.equals(padrao)){
            return numero;
        } else {
            return 0;
        }
    }
}

class linha1 extends tabelaDeProducaoLinha {

    public linha1() {
        this.numero = 1;
        this.padrao = "ba";
        this.regra = "ab";
    }
}

class linha2 extends tabelaDeProducaoLinha {

    public linha2() {
        this.numero = 2;
        this.padrao = "ca";
        this.regra = "ac";
    }
}

class linha3 extends tabelaDeProducaoLinha {

    public linha3() {
        this.numero = 3;
        this.padrao = "cb";
        this.regra = "bc";
    }
}

class linha4 extends tabelaDeProducaoLinha {

    public linha4() {
        this.numero = 4;
        this.padrao = "da";
        this.regra = "ad";
    }
}

class linha5 extends tabelaDeProducaoLinha {

    public linha5() {
        this.numero = 5;
        this.padrao = "dc";
        this.regra = "cd";
    }
}

class linha6 extends tabelaDeProducaoLinha {

    public linha6() {
        this.numero = 6;
        this.padrao = "db";
        this.regra = "bd";
    }
}