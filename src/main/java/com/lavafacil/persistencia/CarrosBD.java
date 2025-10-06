package com.lavafacil.persistencia;

import com.lavafacil.gui.TelaInicial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CarrosBD {    
    
   public void cadastrarCarro(Carros c) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
         try {
            transaction.begin();

            Timestamp timeStamp = Timestamp.valueOf("1970-01-01 00:00:00");
            c.setTempoLimite(timeStamp);
            c.setGratis(TelaInicial.lavagens);
            c.setLavagens(0);
            
            // Persiste o carro no banco de dados
            em.persist(c);
            
            transaction.commit();
        } catch (Exception e) { if (transaction.isActive()) { transaction.rollback(); } throw e; } 
    }
        
   public List<Carros> listar(){   
     EntityManager em = JPAUtil.getEntityManager();
     List<Carros> carros = new ArrayList<Carros>();
        try{
            Query consulta = em.createQuery("SELECT c FROM Carros c");
            carros = consulta.getResultList(); } 
        catch (Exception e) { throw e; }
        finally { JPAUtil.closeEtityManager(); }
          return carros;
      }
   
   public Carros buscar(String placa){   
     EntityManager em = JPAUtil.getEntityManager();
     List<Carros> carros = new ArrayList<Carros>();
     Carros carro = null;
        try{
         Query consulta = em.createQuery("SELECT c FROM Carros c WHERE c.placa = :placa").setParameter("placa", placa);
         carros = consulta.getResultList();
            if (carros.size() > 1) { JOptionPane.showMessageDialog(null, "Carro encontrado, porém há múltiplos registros com a mesma placa. \nPor favor, atualize os dados duplicados."); return null; }
                else if (carros.size() == 1) { JOptionPane.showMessageDialog(null, "Placa encontrada!");
                      if (JOptionPane.showConfirmDialog(null, "Adicionar lavagem?") == 0) {  carro = carros.get(0); } 
                      else { return null; }
                }
            else { JOptionPane.showMessageDialog(null, "Placa não cadastrada"); return null; } }
        
        catch(Exception e){ if (em.getTransaction().isActive()) { em.getTransaction().rollback(); } }
        finally { if (em != null && em.isOpen()) { em.close(); } }
          return carro;
      }
   
   public List<Carros> buscarPlaca(String placa){   
    EntityManager em = JPAUtil.getEntityManager();
    List<Carros> carros = new ArrayList<Carros>();
      try{
       Query consulta = em.createQuery("SELECT c FROM Carros c WHERE c.placa = :placa").setParameter("placa", placa);
       carros = consulta.getResultList();
          if (carros.size() > 1) { JOptionPane.showMessageDialog(null, "Placa cadastrada com múltiplos registros."); return null; }
          else if (carros.size() == 1) { JOptionPane.showMessageDialog(null, "Esta placa já está cadastrada."); }
          else { return null; } }
      catch(Exception e){ if (em.getTransaction().isActive()) { em.getTransaction().rollback(); } }
      finally { if (em != null && em.isOpen()) { em.close(); } }
        return carros;
    }
   
   public boolean buscarPlacaO(Carros carro, String placa){   
    EntityManager em = JPAUtil.getEntityManager();
    List<Carros> carros = new ArrayList<Carros>();
    
      try{
       Query consulta = em.createQuery("SELECT c FROM Carros c WHERE c.placa = :placa").setParameter("placa", placa);
       carros = consulta.getResultList();
          if (carros.size() > 1) { JOptionPane.showMessageDialog(null, "Placa cadastrada e com múltiplos registros.", "Erro", JOptionPane.ERROR_MESSAGE); return false; }
          else if (carros.size() == 1) { if (carros.get(0).getId() == carro.getId()) { return true; } else { JOptionPane.showMessageDialog(null, "Placa já cadastrada.", "Erro", JOptionPane.ERROR_MESSAGE); return false; } }
          else {  return true;  } }
      catch(Exception e){}
      finally { if (em != null && em.isOpen()) { em.close(); } }
      
      return false; 
    }
    
   public void excluirCarro(Carros c) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        Carros carros = em.find(Carros.class, c.getId());
        if (carros != null) { em.remove(carros); } 
        em.getTransaction().commit();
    }
    catch(Exception e){ em.getTransaction().rollback(); throw e; }
    finally {if (em.isOpen())JPAUtil.closeEtityManager();}}
   
   public List<Carros> listar(String pesquisa, int metodo){  
    EntityManager em = JPAUtil.getEntityManager();
     List<Carros> carros = new ArrayList<Carros>();

       try{
         Query consulta;
         switch (metodo) {
             
         case 1: consulta = em.createQuery("SELECT c FROM Carros c WHERE c.placa= :pesquisa").setParameter("pesquisa", pesquisa); break;
         case 2: consulta = em.createQuery("SELECT c FROM Carros c WHERE c.nomeCliente= :pesquisa").setParameter("pesquisa", pesquisa); break;
         case 3: consulta = em.createQuery("SELECT c FROM Carros c WHERE c.telefone= :pesquisa").setParameter("pesquisa", pesquisa); break;
         case 4: consulta = em.createQuery("SELECT c FROM Carros c WHERE c.marcaCarro LIKE :pesquisa").setParameter("pesquisa", "%" + pesquisa + "%"); break;
         default: consulta = em.createQuery("SELECT c FROM Carros c WHERE c.visitas= 0 AND c.tempoLimite IS null"); break; }
         
         carros = consulta.getResultList(); }
       catch(Exception e){ throw e; }
       finally{ JPAUtil.closeEtityManager(); }       
     return carros; 
    }
    
   public Carros buscarCarroPorId(int id) {
   EntityManager em = JPAUtil.getEntityManager();
   Carros c = null;
    try{  
         c = em.find(Carros.class, id);
        }
    catch(Exception e){  throw e; } 
    finally{ JPAUtil.closeEtityManager(); }
    return c;
 }
   
   public void atualizarCarro(Carros carro) {
    EntityManager em = JPAUtil.getEntityManager();
    try {em.getTransaction().begin();
         em.merge(carro);
         em.getTransaction().commit(); } 
    catch (Exception e) { em.getTransaction().rollback(); throw e; } 
    finally { if (em.isOpen()) { em.close(); } } 
   }
}
