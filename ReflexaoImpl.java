package atividade;

import javax.swing.*;

public class ReflexaoImpl {
    public static void main (String []args) {
        Pai pai = new Pai();
        String[] atributosPai = Reflexao.listarNomesAtributos(pai);
        String[] metodosPai = Reflexao.listarNomesMetodos(pai);

        String todosAtributos = "";
        String todosMetodos = "";
        for (String atributo : atributosPai) {
            atributo = atributo.concat("\n");
            todosAtributos = todosAtributos.concat(atributo);
        }
        for (String metodo : metodosPai) {
            metodo = metodo.concat("\n");
            todosMetodos = todosMetodos.concat(metodo);
        }

        JOptionPane.showMessageDialog(null, "Atributos do Pai: \n" + todosAtributos
        + "\n\nMétodos do Pai: \n" + todosMetodos);
    }
}
