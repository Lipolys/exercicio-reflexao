import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class filaMercado {

    public static void main(String[] args) {

        ArrayList<Integer> conjConflito = new ArrayList<>();
        int iteracao = 0;
        int regra = 0;
        String[] leitura = new String [] {"", ""};
        String[] memoriaDeTrabalho = new String [] {"gestante", "idoso", "demais", "gestante", "deficiente", "demais",  "demais", "idoso"};
        List<tabelaDeProducaoLinha> linhasDeProducao = carregarLinhasDeProducao();

        do {
            conjConflito.clear();
            boolean flag = false;
            for (int i = 0; i < memoriaDeTrabalho.length - 1; i++) {
                leitura[0] = memoriaDeTrabalho[i];
                leitura[1] = memoriaDeTrabalho[i + 1];
                for (tabelaDeProducaoLinha linha : linhasDeProducao) {
                    conjConflito.add(linha.encontrarRegra(leitura));
                }
            }

            for (Integer integer : conjConflito) {
                if (integer == 3 || integer == 4 || integer == 5) {
                    regra = integer;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                for (Integer integer : conjConflito) {
                    if (integer != 0) {
                        regra = integer;
                        break;
                    } else {
                        regra = 0;
                    }
                }
            }
            for (tabelaDeProducaoLinha linha : linhasDeProducao) {
                if (regra == linha.getNumero()) {
                    for (int i = 0; i < memoriaDeTrabalho.length - 1; i++) {
                        if (memoriaDeTrabalho[i].equals(linha.getPadrao()[0]) && memoriaDeTrabalho[i + 1].equals(linha.getPadrao()[1])) {
                            memoriaDeTrabalho[i] = linha.getRegra()[0];
                            memoriaDeTrabalho[i + 1] = linha.getRegra()[1];
                            break;
                        }
                    }
                    break;
                }
            }
            System.out.println("Resultado da iteração " + iteracao + ": " + Arrays.toString(memoriaDeTrabalho));
            iteracao++;
        } while (conjConflito.stream().anyMatch(integer -> integer != 0));

        System.out.println("Resultado final: " + Arrays.toString(memoriaDeTrabalho));
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
    protected String[] padrao = new String[2];
    protected String[] regra = new String[2];
    protected String a = "demais";
    protected String b = "idoso";
    protected String c = "deficiente";
    protected String d = "gestante";

    public int getNumero() {
        return numero;
    }
    public String[] getPadrao() {
        return padrao;
    }
    public String[] getRegra() {
        return regra;
    }

    public int encontrarRegra(String[] leitura) {
        if(leitura[0].equals(padrao[0]) && leitura[1].equals(padrao[1])) {
            return numero;
        } else {
            return 0;
        }
    }
}
//dcbadca
class linha1 extends tabelaDeProducaoLinha {

    public linha1() {
        this.numero = 1;
        this.padrao[0] = b;
        this.padrao[1] = a;
        this.regra[0] = a;
        this.regra[1] = b;
    }
}

class linha2 extends tabelaDeProducaoLinha {

    public linha2() {
        this.numero = 2;
        this.padrao[0] = c;
        this.padrao[1] = a;
        this.regra[0] = a;
        this.regra[1] = c;
    }
}

class linha3 extends tabelaDeProducaoLinha {

    public linha3() {
        this.numero = 3;
        this.padrao[0] = d;
        this.padrao[1] = c;
        this.regra[0] = c;
        this.regra[1] = d;
    }
}

class linha4 extends tabelaDeProducaoLinha {

    public linha4() {
        this.numero = 4;
        this.padrao[0] = d;
        this.padrao[1] = a;
        this.regra[0] = a;
        this.regra[1] = d;
    }
}

class linha5 extends tabelaDeProducaoLinha {

    public linha5() {
        this.numero = 5;
        this.padrao[0] = d;
        this.padrao[1] = b;
        this.regra[0] = b;
        this.regra[1] = d;
    }
}

class linha6 extends tabelaDeProducaoLinha {

    public linha6() {
        this.numero = 6;
        this.padrao[0] = c;
        this.padrao[1] = b;
        this.regra[0] = b;
        this.regra[1] = c;
    }
}
