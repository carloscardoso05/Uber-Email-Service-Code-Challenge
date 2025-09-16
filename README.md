# Email Service - A Robust Email Sending API

[![Java](https://img.shields.io/badge/Java-21+-blue.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![AWS](https://img.shields.io/badge/AWS-SES-orange.svg)](https://aws.amazon.com/ses/)
[![SendGrid](https://img.shields.io/badge/Twillio-SendGrid-purple.svg)](https://sendgrid.com/)

## Sobre o Projeto

O **Email Service** é uma API robusta para envio de e-mails, construída com Java e Spring Boot. O projeto foi desenhado para ser altamente resiliente, utilizando múltiplos provedores de e-mail (AWS SES e SendGrid) como gateways. Se um provedor falhar, o sistema automaticamente tenta o próximo, garantindo uma alta taxa de entrega.

## Como Funciona

A API expõe um endpoint para o envio de e-mails. Internamente, a aplicação processa a requisição e a encaminha para o primeiro gateway de envio configurado (AWS SES). Em caso de falha, o serviço tenta reenviar o mesmo e-mail utilizando o gateway secundário (SendGrid). Essa abordagem de fallback aumenta significativamente a confiabilidade do serviço.

**Principais características:**

  - **Alta Disponibilidade:** Utiliza múltiplos provedores de e-mail para garantir o envio.
  - **Tolerância a Falhas:** Mecanismo de fallback que alterna entre provedores em caso de erro.
  - **Validação de Dados:** Garante que os dados do e-mail (destinatário, assunto, corpo) sejam válidos antes do envio.
  - **Fácil Integração:** Expondo um único endpoint RESTful para envio de e-mails.

## Tecnologias Utilizadas

  - **[Java 21](https://openjdk.org/projects/jdk/21/)** - Linguagem de programação principal
  - **[Spring Boot](https://spring.io/projects/spring-boot)** - Framework para criação da API RESTful
  - **[AWS SES SDK](https://aws.amazon.com/ses/)** - Integração com o serviço de e-mail da AWS
  - **[SendGrid SDK](https://github.com/sendgrid/sendgrid-java)** - Integração com o serviço de e-mail da Twilio SendGrid
  - **[Maven](https://maven.apache.org/)** - Gerenciamento de dependências e build
  - **[JUnit & Mockito](https://junit.org/junit5/)** - Para testes unitários e de integração

## Como Executar Localmente

### Pré-requisitos

  - Java 21 ou superior
  - Maven 3.9+
  - Credenciais para AWS SES e SendGrid

### Passos para execução

1.  **Clone o repositório**

    ```bash
    git clone https://github.com/carloscardoso05/uber-email-service-code-challenge.git
    cd uber-email-service-code-challenge
    ```

2.  **Configure as variáveis de ambiente**

    Crie um arquivo `.env` na raiz do projeto, baseado no exemplo abaixo, e preencha com suas credenciais:

    ```env
    # AWS SES
    AWS_REGION=sua-regiao
    AWS_ACCESS_KEY=sua-access-key
    AWS_ACCESS_KEY_ID=seu-access-key-id

    # SendGrid
    SENDGRID_API_KEY=sua-api-key

    # E-mail verificado
    VERIFIED_EMAIL=seu-email-verificado
    ```

3.  **Execute com Maven**

    ```bash
    ./mvnw spring-boot:run
    ```

    A API estará disponível em `http://localhost:8080`.

## Autor

**Carlos Cardoso** - Desenvolvedor Backend Java

[](https://github.com/carloscardoso05)
[](https://www.linkedin.com/in/carloscardoso05/)

**Repositório**: [https://github.com/carloscardoso05/uber-email-service-code-challenge](https://www.google.com/search?q=https://github.com/carloscardoso05/uber-email-service-code-challenge)

-----

Este projeto demonstra habilidades em:

  - Desenvolvimento de APIs RESTful com Java e Spring Boot
  - Arquitetura de microsserviços resilientes
  - Integração com serviços de terceiros (AWS SES, SendGrid)
  - Princípios de Clean Architecture
  - Boas práticas de desenvolvimento e testes
