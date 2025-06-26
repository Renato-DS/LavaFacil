package com.rm.lavarapidorm.persistencia;
  
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author Renato Rodrigues
 */ 
/* Classe para estabelecer a conexão com o JPA - TDS */
  public class JPAUtil {
    private static final String PERSISTENCE_UNIT = "LavaRapido-PU";
    
    private static EntityManager em;
    private static EntityManagerFactory fabrica;
    
 
    public static EntityManager getEntityManager(){
        if(fabrica == null || !fabrica.isOpen())
            fabrica = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        
        if(em == null || !em.isOpen())
            em = fabrica.createEntityManager();

        return em;
    }
    

public static void closeEtityManager(){
        if(em.isOpen() && em != null){
            em.close();
            fabrica.close();
        }
   }
}    
