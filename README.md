# LavaFacil
O terceiro dos meus projetos principais e mais recente projeto desenvolvido em Java com banco de dados SQL. Foi criado com a ideia de auxiliar um lava rápido real.

Sistema de gestão para lava-rápido. Permite controlar clientes, ordens de serviço, tipos de lavagem e faturamento.

## Tecnologias

- Java 17+  
- Maven  
- MySQL  
- JDBC  
- Swing (interface gráfica) 

## Funcionalidades

- Sistema de LOGIN
- Cadastro, atualização e remoção de funcionários
- Cadastro de clientes
- Criação de ordens de serviço
- Controle de tipos de lavagem e valores
- Consulta de ordens com filtros (cliente/data/placa de carro/marca)
- Relatórios de faturamento
- Documentação completa de lavagens (dia [dia/mês/ano horas], valor, tipo de lavagem, cliente)

## Contato
- LinkedIn: https://www.linkedin.com/in/renato-rodrigues-76837a322/

- GitHub: https://github.com/Renato-DS

- Email: renatorodrigues0302@gmail.com

## Se quiser executar o projeto:
- Pré-requisitos: Java 17+, Maven e MySQL configurados.

1. Clone o repositório ou baixe o código-fonte.

2. Execute o arquivo `lavaFacilbd.sql` (presente na pasta exe) para criar e preencher o banco de dados local.

3. Ajustar senha para conexão no arquivo `persistence.xml` disponível na pasta raiz do projeto. 
Altere o campo "value" da linha: "<property name="jakarta.persistence.jdbc.password" value="SuaSenhaAqui" />"

4. Execute `Compilar.bat` na pasta raiz do projeto

5. Execute `LavaFacil.bat` disponível na pasta `exe`

## Licença
Este projeto está licenciado sob a MIT License. Consulte o arquivo LICENSE para mais detalhes.