package com.rm.lavarapidorm;

import com.rm.lavarapidorm.gui.*;
import com.rm.lavarapidorm.persistencia.*;
import jakarta.persistence.EntityManager;

public class LavaRapidoRM {

    public static void main(String[] args) {   
           EntityManager em = JPAUtil.getEntityManager();
           JPAUtil.closeEtityManager();
           
           /* Main. Usado apenas para chamar a tela inical - TDS */
           TelaLogin TL = new TelaLogin();           
           TL.setVisible(true);
           TL.requestFocus();
    }
}
