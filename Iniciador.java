public class Iniciador {
    public static void main(String[] args) {
        int[][] tabela = {{1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
        int[] amostra = {0, 0, 1, 1};

        Perceptron perceptron = new Perceptron(3); // 3 sensores
        perceptron.treinar(tabela, amostra);
    }
}

class Sensor {
    private int peso;
    private int entrada;

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
        return soma == 0 ? 0 : 1;
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
            return 1;
        } else if (saidaAtual < resultadoEsperado) {
            houveErros = true;
            return -1;
        } else {
            return 0;
        }
    }
}

class Perceptron {
    private Sensor[] sensores;
    private Aprendizagem aprendizagem;

    public Perceptron(int numeroSensores) {
        this.sensores = new Sensor[numeroSensores];
        for (int i = 0; i < numeroSensores; i++) {
            sensores[i] = new Sensor();
        }
        this.aprendizagem = new Aprendizagem();
    }

    public void treinar(int[][] entradas, int[] amostras) {
        while (aprendizagem.houveErros()) {
            aprendizagem.reset();
            for (int i = 0; i < entradas.length; i++) {
                aprendizagem.setResultadoEsperado(amostras[i]);
                for (int j = 0; j < sensores.length; j++) {
                    sensores[j].receberSinal(entradas[i][j]);
                }
                int soma = Soma.somar(sensores);
                int saida = Ativacao.gerarSaida(soma);
                int regra = aprendizagem.conferirResposta(saida);

                ajustarPesos(regra);
            }
        }
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