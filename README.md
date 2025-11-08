# Email Service

Aplicacao Spring Boot feita para expor um endpoint REST responsavel por disparar e-mails atraves do Amazon Simple Email Service (SES). O projeto segue os principios da Clean Architecture, com uma separacao simples em camadas (`controller` -> `application` -> `adapter`) para manter as regras de negocio desacopladas da implementacao concreta do provedor de e-mail.

## Tecnologias e protocolos
- Java 21 + Spring Boot 3.5.7.
- AWS SDK for Java v1 (`aws-java-sdk-ses`) para integracao com o SES.
- Protocolos adotados: envio via **SMTP** do Amazon SES e suporte a monitoramento por **POP3/IMAP** quando integrado a caixas de e-mail verificadas.
- Transporte de mensagens atraves da **API HTTPS do Amazon SES** assinada com **AWS Signature Version 4**, complementando o suporte SMTP/POP3/IMAP disponibilizado pelo servico.

## Pre-requisitos
- JDK 21 instalado e configurado no `PATH`.
- Maven 3.9+ ou apenas o wrapper (`mvnw`/`mvnw.cmd`) incluso no repositorio.
- Conta AWS com acesso ao Amazon SES (regiao em modo Production ou sandbox com remetentes verificados).
- Credenciais configuradas via [Default Credentials Chain](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html). Exemplos:
  ```powershell
  $Env:AWS_ACCESS_KEY_ID="AKIA..."
  $Env:AWS_SECRET_ACCESS_KEY="xxxx"
  $Env:AWS_REGION="us-east-1"
  ```
  ou use um perfil em `~/.aws/credentials`.

> **Importante:** o remetente padrao esta definido como `caio.m.c.leonardo@gmail.com` em `SesEmailSender`. Certifique-se de verificar esse endereco (ou troca-lo por outro verificado) no console do SES.

## Configuracao
1. Ajuste `src/main/resources/application.properties` conforme necessario (por padrao apenas o `spring.application.name` esta definido).
2. Caso deseje parametrizar o remetente/destinatario padrao, extraia-os para variaveis de ambiente ou `application.properties` e injete-os em `SesEmailSender`.

## Executando localmente
```bash
# usando o wrapper no Linux/macOS
./mvnw spring-boot:run

# ou no Windows
mvnw.cmd spring-boot:run
```

O servico sobe em `http://localhost:8080`.

## Testes
```bash
./mvnw test
```

## Endpoint disponivel
- `POST /api/email`

Request body (JSON):
```json
{
  "to": "destinatario@dominio.com",
  "subject": "Assunto",
  "body": "Conteudo em texto puro"
}
```

### Exemplo com `curl`
```bash
curl --request POST http://localhost:8080/api/email \
  --header "Content-Type: application/json" \
  --data '{
    "to": "destinatario@dominio.com",
    "subject": "Assunto de teste",
    "body": "Ola! Este e um disparo via SES."
  }'
```

## Detalhes do fluxo
1. `EmailSenderController` recebe a requisicao REST.
2. `EmailSenderService` orquestra a chamada para o caso de uso `EmailSenderUserCase`.
3. `SesEmailSender` monta um `SendEmailRequest` e chama `AmazonSimpleEmailService#sendEmail`.
4. O AWS SDK abre uma conexao **HTTPS/TLS** com o endpoint regional do SES, assinando a requisicao com SigV4 e entregando a mensagem.

Se o Amazon SES estiver em sandbox, todos os remetentes e destinatarios precisam estar verificados.

## Proximos passos sugeridos
1. Externalizar endereco remetente e template de mensagem para configuracao.
2. Implementar suporte a corpo HTML e anexos.
3. Adicionar validacao de payload e tratamento de erros mais detalhado no controller.





