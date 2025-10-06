package com.lavafacil.persistencia;

import com.lavafacil.gui.TelaLogin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioBD {
      
public void cadastrar(Usuario u){
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
  }catch(Exception e){
        em.getTransaction().rollback();
         throw e;
 }
   finally{
     JPAUtil.closeEtityManager();
          } }

    public String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public boolean verificarSenha(String senhaDigitada, String senhaHashDoBanco) {
        return BCrypt.checkpw(senhaDigitada, senhaHashDoBanco);
    }

public List<Usuario> listar(){    
    EntityManager em = JPAUtil.getEntityManager();
 List<Usuario> usuarios = new ArrayList<Usuario>();
      try{
          Query consulta = em.createQuery("SELECT u FROM Usuario u");
          usuarios = consulta.getResultList();
  }catch(Exception e){
          e.printStackTrace();
          throw e;
 }
      finally{
          JPAUtil.closeEtityManager();
   }
      return usuarios;
  }

public Usuario validarUsuario(String l, String s) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
List<Usuario> usuarioEncontrados = em
            .createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
            .setParameter("login", l)
            .getResultList(); 
    if(!usuarioEncontrados.isEmpty()) { if (verificarSenha(s, usuarioEncontrados.get(0).getSenha())) return usuarioEncontrados.get(0); } 
} catch (Exception e) { JOptionPane.showMessageDialog(null, "Houve um erro! " + e.getMessage()); }
 finally { JPAUtil.closeEtityManager(); }
 return null; }  

public boolean verificarSeHaUsuario(String login, String senhaDigitada) {
    EntityManager em = JPAUtil.getEntityManager();
    boolean autenticado = false;

    try {
        TypedQuery<Usuario> consulta = em.createQuery( "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
        consulta.setParameter("login", login);

        List<Usuario> usuarios = consulta.getResultList();

        if (!usuarios.isEmpty()) {
            Usuario usuarioEncontrado = usuarios.get(0);
            String senhaHashArmazenada = usuarioEncontrado.getSenha();

            if (verificarSenha(senhaDigitada, senhaHashArmazenada)) {
                autenticado = true;
            } }
        } 
    finally { JPAUtil.closeEtityManager(); }
    return autenticado;
}

public boolean loginJaExiste(String login, int idAtual) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login AND u.id <> :idAtual", Long.class);
        query.setParameter("login", login);
        query.setParameter("idAtual", idAtual);
        return query.getSingleResult() > 0;
    } finally {
        JPAUtil.closeEtityManager();
    }
}

public void atualizarUsuario(Usuario u) {
    EntityManager em = JPAUtil.getEntityManager();
    try {em.getTransaction().begin();
         em.merge(u);
         em.getTransaction().commit();  
         TelaLogin.l = u.getLogin();
         TelaLogin.s = u.getSenha();  }
    catch(Exception e){ em.getTransaction().rollback(); throw e; }
    finally {if (em.isOpen()) {JPAUtil.closeEtityManager();}}
}

public void excluirUsuario(Usuario u) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, u.getId());
        if (usuario != null) { em.remove(usuario); } 
        else { System.out.println("Usuário com ID " + u.getId() + " não encontrado."); }
        em.getTransaction().commit();
    }
    catch(Exception e){ em.getTransaction().rollback(); throw e; }
    finally {if (em.isOpen())JPAUtil.closeEtityManager();}}

public Usuario buscarUsuarioPorId(int id) {
   EntityManager em = JPAUtil.getEntityManager();
   Usuario u = null;
     try{ u = em.find(Usuario.class, id);  }
     catch(Exception e){ throw e; } 
     finally{ JPAUtil.closeEtityManager(); }
    return u;
 }
}
