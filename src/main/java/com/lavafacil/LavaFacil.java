package com.lavafacil;

import com.lavafacil.persistencia.JPAUtil;
import com.lavafacil.gui.TelaLogin;
import jakarta.persistence.EntityManager;

public class LavaFacil {

    public static void main(String[] args) {   
           EntityManager em = JPAUtil.getEntityManager();
           JPAUtil.closeEtityManager();
           
           /* Main. Usado apenas para chamar a tela inical - TDS */
           TelaLogin TL = new TelaLogin();           
           TL.setVisible(true);
           TL.requestFocus();
    }
}
