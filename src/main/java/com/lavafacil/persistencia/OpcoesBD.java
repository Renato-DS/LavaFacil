package com.lavafacil.persistencia;

import com.lavafacil.gui.TelaInicial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class OpcoesBD {
    public void carregarConfiguracoes() {            
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Opcoes> query = em.createQuery("SELECT o FROM Opcoes o", Opcoes.class);
            List<Opcoes> opcoes = query.getResultList();

            for (Opcoes opcao : opcoes) {
                String nomeOpcao = opcao.getNomeOpcao();
                String valorOpcao = opcao.getValorOpcao();

                switch (nomeOpcao) {
                    case "funcGratis":
                        TelaInicial.funcGratis = Boolean.parseBoolean(valorOpcao);
                        break;
                    case "lavagens":
                        TelaInicial.lavagens = Integer.valueOf(valorOpcao);
                        break;
                    case "dias":
                        TelaInicial.dias = Integer.valueOf(valorOpcao);
                        break;
                    case "telefone":
                        TelaInicial.telefone = Boolean.parseBoolean(valorOpcao);
                        break;
                    default:
                        break; // Adicione tratamento para outras opções, se necessário
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        } }
   
    public void salvarConfiguracoes() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin(); // Inicia a transação

            // Atualiza ou persiste cada configuração
            atualizarOpcao(em, "funcGratis", Boolean.toString(TelaInicial.funcGratis));
            atualizarOpcao(em, "lavagens", TelaInicial.lavagens.toString());
            atualizarOpcao(em, "dias", TelaInicial.dias.toString());
            atualizarOpcao(em, "telefone", Boolean.toString(TelaInicial.telefone));

            transaction.commit(); // Confirma a transação
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback(); // Reverte a transação em caso de erro
            }
        } finally {
            carregarConfiguracoes();
            em.close();
        }
    }
    
    private void atualizarOpcao(EntityManager em, String nomeOpcao, String valorOpcao) {
        // Verifica se a opção já existe
        Opcoes opcaoExistente = em.createQuery("SELECT o FROM Opcoes o WHERE o.nomeOpcao = :nome", Opcoes.class)
                                   .setParameter("nome", nomeOpcao)
                                   .getSingleResult();

        if (opcaoExistente != null) {
            // Atualiza a opção existente
            opcaoExistente.setValorOpcao(valorOpcao);
            em.merge(opcaoExistente); // Atualiza a entidade
        } else {
            // Caso a opção não exista, cria uma nova
            Opcoes novaOpcao = new Opcoes();
            novaOpcao.setNomeOpcao(nomeOpcao);
            novaOpcao.setValorOpcao(valorOpcao);
            em.persist(novaOpcao); // Persiste a nova opção
        }
    }
}
