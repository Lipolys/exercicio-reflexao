import java.util.ArrayList;
import java.util.Random;

public class Iniciador {
    public static void main(String[] args) {
        int[][] tabela = {{1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
        int[][] tabela2 = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}};
        int[] amostra = {0, 0, 1, 1};
        int[] resultados = {0, 0, 0, 0};
        Perceptron perceptron = new Perceptron(3); // 3 sensores
        perceptron.treinar(tabela, amostra);
        resultados = perceptron.ativar(tabela2);
        for (int resultado : resultados) {
            System.out.println (resultado);
        }
    }
}

class Sensor {
    private int peso;
    private int entrada;
    private Random random = new Random();
    public Sensor() {
        this.peso = 0;
        this.entrada = 0;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public void receberSinal(int entrada) {
        this.entrada = entrada;
    }

    public int enviarSinapse() {
        return entrada * peso;
    }

    public void sortearPeso() {
        peso = random.nextInt(2);
    }
}

class Soma {
    public static int somar(Sensor[] sensores) {
        int resultado = 0;
        for (Sensor sensor : sensores) {
            resultado += sensor.enviarSinapse();
        }
        return resultado;
    }
}

class Ativacao {
    public static int gerarSaida(int soma) {
        return soma <= 0 ? 0 : 1;
    }
}

class Aprendizagem {
    private boolean houveErros;
    private int resultadoEsperado;

    public Aprendizagem() {
        this.houveErros = true;
    }

    public boolean houveErros() {
        return houveErros;
    }

    public void reset() {
        houveErros = false;
    }

    public void setResultadoEsperado(int resultadoEsperado) {
        this.resultadoEsperado = resultadoEsperado;
    }

    public int conferirResposta(int saidaAtual) {
        if (saidaAtual > resultadoEsperado) {
            houveErros = true;
            return -1;
        } else if (saidaAtual < resultadoEsperado) {
            houveErros = true;
            return 1;
        } else {
            return 0;
        }
    }
}

class Perceptron {
    private Sensor[] sensores;
    private Aprendizagem aprendizagem;
    private ArrayList<Boolean> ciclos = new ArrayList<>();
    private int erros;

    public Perceptron(int numeroSensores) {
        this.sensores = new Sensor[numeroSensores];
        for (int i = 0; i < numeroSensores; i++) {
            sensores[i] = new Sensor();
            sensores[i].sortearPeso();
        }
        this.aprendizagem = new Aprendizagem();
    }

    public void treinar(int[][] entradas, int[] amostras) {
        while (aprendizagem.houveErros() && ciclos.size() < 1000) {
            aprendizagem.reset();
            for (int i = 0; i < entradas.length; i++) {
                aprendizagem.setResultadoEsperado(amostras[i]);
                for (int j = 0; j < sensores.length; j++) {
                    sensores[j].receberSinal(entradas[i][j]);
                }
                int soma = Soma.somar(sensores);
                int saida = Ativacao.gerarSaida(soma);
                int regra = aprendizagem.conferirResposta(saida);

                if (regra != 0) {
                    ciclos.add(true);
                } else {
                    ciclos.add(false);
                }
                ajustarPesos(regra);
            }
        }
        for (boolean ciclo : ciclos) {
            if (ciclo) {
                erros++;
            }
        }

        System.out.println("" + sensores[0].getPeso() + sensores[1].getPeso() + sensores[2].getPeso());
        System.out.println("Treinamento concluído após " + ciclos.size() + " ciclos.");
        System.out.println("Total de erros: " + erros);
    }

    public int[] ativar(int[][] entradas) {
        int[] saidas = {0, 0, 0, 0};
        for (int i = 0; i < entradas.length; i++) {
            for (int j = 0; j < sensores.length; j++) {
                sensores[j].receberSinal(entradas[i][j]);
            }
            int soma = Soma.somar(sensores);
            saidas[i] = Ativacao.gerarSaida(soma);
        }
        return saidas;
    }

    private void ajustarPesos(int regra) {
        if (regra != 0) {
            for (Sensor sensor : sensores) {
                int novoPeso = sensor.getPeso() + regra * sensor.getEntrada();
                sensor.setPeso(novoPeso);
            }
        }
    }
}