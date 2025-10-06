package com.lavafacil.persistencia;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LavagensBD {
    public void cadastrar(Lavagens l){
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
  }catch(Exception e){
        em.getTransaction().rollback();
         throw e;
 }
   finally{
     JPAUtil.closeEtityManager();
          } }
    
    public Lavagens buscarLavagemPorId(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        Lavagens l = null;
        try{  
             l = em.find(Lavagens.class, id);
            }
        catch(Exception e){  throw e; } 
        finally{ JPAUtil.closeEtityManager(); }
 return l;
}
       
    public BigDecimal somarValores() {
    EntityManager em = JPAUtil.getEntityManager();
    BigDecimal total = BigDecimal.ZERO; // Iniciar com zero
    try {
        // Suponha que você está usando uma consulta que retorna um Double
        Query query = em.createQuery("SELECT SUM(l.valor) FROM Lavagens l");
        Double resultado = (Double) query.getSingleResult(); // Resultado da soma como Double
        
        if (resultado != null) {
            total = BigDecimal.valueOf(resultado); // Converter para BigDecimal
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        em.close();
    }
    return total; // Retornar BigDecimal
}

    public List<Lavagens> listar(){    
    EntityManager em = JPAUtil.getEntityManager();
 List<Lavagens> lavagens = new ArrayList<Lavagens>();
      try{
          Query consulta = em.createQuery("SELECT l FROM Lavagens l");
          lavagens = consulta.getResultList();
  }catch(Exception e){
          em.getTransaction().rollback();
          throw e;
 }
      finally{
          JPAUtil.closeEtityManager();
   }
      return lavagens;
  }
    
    public List<Lavagens> listar(String pesquisa, int metodo){  
    EntityManager em = JPAUtil.getEntityManager();
     List<Lavagens> lavagens = new ArrayList<Lavagens>();

       try{
         Query consulta = null;
         
         switch (metodo) {             
         case 1: consulta = em.createQuery("SELECT l FROM Lavagens l WHERE l.placa= :pesquisa").setParameter("pesquisa", pesquisa); break;
         case 2: consulta = em.createQuery("SELECT l FROM Lavagens l WHERE l.nomeCliente= :pesquisa").setParameter("pesquisa", pesquisa); break; 
         case 3: consulta = em.createQuery("SELECT l FROM Lavagens l WHERE l.marcaCarro LIKE :pesquisa").setParameter("pesquisa", "%" + pesquisa + "%"); break;
         case 4: consulta = em.createQuery("SELECT l FROM Lavagens l WHERE l.idCarro= :pesquisa").setParameter("pesquisa", pesquisa); break;
         default: throw new IllegalArgumentException("Método de pesquisa inválido."); }
         
         lavagens = consulta.getResultList(); }
       catch(Exception e){ throw e; }
       finally { if (em != null && em.isOpen()) {  em.close(); } }
     return lavagens; 
    }
   
    public List<Lavagens> listarData(LocalDateTime data){  
    EntityManager em = JPAUtil.getEntityManager();
     List<Lavagens> lavagens = new ArrayList<Lavagens>();

       try{     
            Query query = em.createQuery("SELECT l FROM Lavagens l WHERE l.data BETWEEN :inicio AND :fim", Lavagens.class);
            query.setParameter("inicio", Timestamp.valueOf(data.toLocalDate().atStartOfDay()));
            query.setParameter("fim", Timestamp.valueOf(data.toLocalDate().atTime(23, 59, 59)));
        
        lavagens = query.getResultList(); }
       catch(Exception e){ throw e; }
       finally{ JPAUtil.closeEtityManager(); }       
     return lavagens; 
    }
    
    public List<Lavagens> listarEntreDatas(LocalDateTime dataInicial, LocalDateTime dataFinal) {
    EntityManager em = JPAUtil.getEntityManager();
    List<Lavagens> lavagens = new ArrayList<>();
    try {
        Query query = em.createQuery("SELECT l FROM Lavagens l WHERE l.data BETWEEN :dataInicial AND :dataFinal", Lavagens.class);
        query.setParameter("dataInicial", Timestamp.valueOf(dataInicial));
        query.setParameter("dataFinal", Timestamp.valueOf(dataFinal));
        lavagens = query.getResultList();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        em.close();
    }
    return lavagens;
 }
    
    public void gerarPDFRecibo(Lavagens lavagem) { 
        Document document = new Document();
        try {
            // Caminho relativo para a pasta pdfs na raiz do projeto
            String diretorioPdfs = "./pdfs/";
            File pastaPdfs = new File(diretorioPdfs);

            // Cria a pasta se ela não existir
            if (!pastaPdfs.exists()) {
                pastaPdfs.mkdirs();
            }

            // Define o nome completo do arquivo
            String nomeArquivo = diretorioPdfs + "Recibo_Lavagem_" + lavagem.getId() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));

            document.open(); // Abre o documento para edição

            // Título do recibo
            Paragraph titulo = new Paragraph("Recibo de Lavagem", 
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n\n"));

            // Informações do cliente
            document.add(new Paragraph("Nome do Cliente: " + lavagem.getNomeCliente()));
            document.add(new Paragraph("Placa: " + lavagem.getPlaca()));
            document.add(new Paragraph("Nome do Carro: " + lavagem.getMarcaCarro()));
            document.add(new Paragraph("Telefone: " + lavagem.getTelefone()));
            document.add(new Paragraph("\n"));

            // Detalhes da lavagem
            document.add(new Paragraph("ID da Lavagem: " + lavagem.getId()));
            document.add(new Paragraph("Valor: R$ " + String.format("%.2f", lavagem.getValor())));
            document.add(new Paragraph("Tipo de Lavagem: " + (lavagem.isCompleto() ? "Completa" : "Simples")));
            document.add(new Paragraph("Observações: " + lavagem.getObservacoes()));

            // Formata a data para o padrão brasileiro (dd/MM/yyyy HH:mm)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dataFormatada = sdf.format(lavagem.getData());
            document.add(new Paragraph("Data: " + dataFormatada));

            // Mensagem de rodapé
            document.add(new Paragraph("\n\n\nObrigado pela preferência!", 
                            FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.GRAY)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close(); // Fecha o documento
        }
 } 
}
