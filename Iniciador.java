import java.util.Random;

class Perceptron {
    private static Perceptron instanciaUnica;
    private final Sensor[] sensores;
    private final Aprendizagem aprendizagem;
    private int erros = 0;
    private int ciclos;

    private Perceptron(int numeroSensores) {
        this.sensores = new Sensor[numeroSensores];
        for (int i = 0; i < numeroSensores; i++) {
            sensores[i] = new Sensor();
            sensores[i].sortearPeso();
        }
        this.aprendizagem = new Aprendizagem();
    }

    public static Perceptron getPerceptron(int numeroSensores) {
        if (instanciaUnica == null) {
            instanciaUnica = new Perceptron(numeroSensores);
        }
        return instanciaUnica;
    }

    private void ajustarPesos(int regra) {
        if (regra != 0) {
            for (Sensor sensor : sensores) {
                int novoPeso = sensor.getPeso() + regra * sensor.getEntrada();
                sensor.setPeso(novoPeso);
            }
        }
    }

    public void ciclar(int[][] entradas, int[] amostras) {
        System.out.println("Pesos iniciais:" + sensores[0].getPeso() + sensores[1].getPeso() + sensores[2].getPeso());
        while (aprendizagem.houveErros() && ciclos < 1000) {
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
                    erros++;
                }
                ajustarPesos(regra);
            }
            ciclos++;
        }

        System.out.println("" + sensores[0].getPeso() + sensores[1].getPeso() + sensores[2].getPeso());
        System.out.println("Treinamento concluído após " + ciclos + " ciclos.");
        System.out.println("Total de erros: " + erros);
    }

    public int[] ativar(int[][] entradas) {
        int[] saidas = new int[entradas.length];
        for (int i = 0; i < entradas.length; i++) {
            for (int j = 0; j < sensores.length; j++) {
                sensores[j].receberSinal(entradas[i][j]);
            }
            int soma = Soma.somar(sensores);
            saidas[i] = Ativacao.gerarSaida(soma);
        }
        return saidas;
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

class Treinador {
    public static void treinar() {
        int[][] tabela = {{1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
        int[] amostra = {0, 0, 1, 1};
        Perceptron perceptron = Perceptron.getPerceptron(3);
        perceptron.ciclar(tabela, amostra);
    }
}

class Utilizador {
    public static void utilizar() {
        int[][] tabela = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
        int[] resultados = new int[8];
        Perceptron perceptron = Perceptron.getPerceptron(3);
        resultados = perceptron.ativar(tabela);
        for (int resultado : resultados) {
            System.out.print(resultado + " ");
        }
    }
}

public class Iniciador {
    public static void main (String[] args) {
        Treinador.treinar();
        Utilizador.utilizar();
    }
}