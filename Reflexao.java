package atividade;

import javax.swing.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reflexao {
    private static Field[] listarAtributos (Object objeto){
        Field[] atributos = null;
        try {
            Class<?> classe = objeto.getClass();
            atributos = classe.getDeclaredFields();
            while (classe.getSuperclass() != null && classe.getSuperclass() != Object.class) {
                classe = classe.getSuperclass();
                Field[] atributosSuperClasse = classe.getDeclaredFields();
                Field[] atributosTemp = new Field[atributos.length + atributosSuperClasse.length];
                System.arraycopy(atributos, 0, atributosTemp, 0, atributos.length);
                System.arraycopy(atributosSuperClasse, 0, atributosTemp, atributos.length, atributosSuperClasse.length);
                atributos = atributosTemp;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return atributos;
    }

    private static Method[] listarMetodos (Object objeto) {
        Method[] metodos = null;
        try {
            Class<?> classe = objeto.getClass();
            metodos = classe.getDeclaredMethods();
            while (classe.getSuperclass() != null && classe.getSuperclass() != Object.class) {
                classe = classe.getSuperclass();
                Method[] metodosSuperClasse = classe.getDeclaredMethods();
                Method[] metodosTemp = new Method[metodos.length + metodosSuperClasse.length];
                System.arraycopy(metodos, 0, metodosTemp, 0, metodos.length);
                System.arraycopy(metodosSuperClasse, 0, metodosTemp, metodos.length, metodosSuperClasse.length);
                metodos = metodosTemp;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return metodos;
    }

    public static String[] listarNomesAtributos (Object objeto) {
        Field[] atributos = listarAtributos(objeto);
        String[] nomes = new String[atributos.length];
        for (int i = 0; i < atributos.length; i++) {
            nomes[i] = atributos[i].getName();
        }
        return nomes;
    }

    public static String[] listarNomesMetodos (Object objeto) {
        Method[] metodos = listarMetodos(objeto);
        String[] nomes = new String[metodos.length];
        for (int i = 0; i < metodos.length; i++) {
            nomes[i] = metodos[i].getName();
        }
        return nomes;
    }
}
