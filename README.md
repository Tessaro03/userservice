# UserService

## Objetivo
O **UserService** é um microserviço do sistema **Dente Convênio**, responsável pelo cadastro e gestão de usuários, incluindo:
- Consultórios
- Dentistas
- Empresas
- Beneficiários

Além disso, ele permite realizar login e cadastro, além de criar relações entre:
- Consultórios e dentistas
- Empresas e beneficiários

---

## Principais Recursos
- **Login e Cadastro de Usuários**:
  - Registro de diferentes tipos de usuários com validações específicas.
  - Autenticação utilizando Spring Security com JWT.

- **Gerenciamento de Relações**:
  - Criação e manutenção de relações entre consultórios/dentistas e empresas/beneficiários.

- **Funcionalidades Avançadas**:
  - Integração com serviços externos via RabbitMQ.
  - Sincronização e cache para melhor desempenho.
  - Tarefas agendadas com Scheduled para operações periódicas.

---

## Tecnologias Utilizadas
- **Linguagem e Frameworks**:
  - [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
  - [Spring Security](https://spring.io/projects/spring-security)
  - [Spring Web](https://spring.io/projects/spring-framework)

- **Banco de Dados**:
  - [MySQL](https://www.mysql.com/)

- **Infraestrutura e Comunicação**:
  - [Eureka](https://spring.io/projects/spring-cloud-netflix)
  - [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
  - [RabbitMQ](https://www.rabbitmq.com/)

- **Desempenho e Gerenciamento**:
  - Cache para otimizar consultas frequentes.
  - Agendamento de tarefas com **Spring Scheduled**.

---

