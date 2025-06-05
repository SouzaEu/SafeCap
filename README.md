
# SafeCap API - Global Solution FIAP

API REST em Spring Boot para monitoramento térmico inteligente via IoT. Projeto completo com autenticação, validação, tratamento de erros, testes e documentação.

## ✅ Funcionalidades

- Login e Registro com JWT
- CRUD de Usuários, Dispositivos e Alertas
- Validação por DTOs e regras de negócio em services
- Filtros de temperatura e umidade com paginação
- Swagger com exemplos e respostas de erro
- Tratamento de erros com `ApiError` + exceções customizadas
- Testes unitários e de integração completos
- Logging estruturado com SLF4J e JSON
- Perfis `dev`, `prod`, variáveis de ambiente e timeout de banco
- Dockerfile + docker-compose + Oracle

## 🛡️ Segurança

- JWT com expiração
- Chave lida via variável de ambiente (`JWT_SECRET`)
- Senhas criptografadas com `BCrypt`
- Filtro de autenticação com JWT
- CORS configurado

## 💡 Validação

- DTOs com `@Email`, `@Size`, `@Min`, `@Max`
- Regras de negócio no `UsuarioService` e `DispositivoService`
- Email e nome únicos validados com exceções

## ⚠️ Tratamento de Erros

- `@ControllerAdvice` com `ApiError`
- Erros:
  - `ValidationException` (400)
  - `BusinessRuleException` (400)
  - `ResourceNotFoundException` (404)
  - `Exception` (500)

## 🧪 Testes

- `AlertaServiceTest` (valores críticos)
- `AuthIntegrationTest` (registro, login, token)
- `UsuarioServiceTest` (email duplicado)
- `DispositivoServiceTest` (nome duplicado)

## 🔍 Documentação

Acesse:
```
/swagger-ui.html
```

Exemplos incluídos nos endpoints de:
- Login
- Registro
- Criação de alerta

## 🛠️ Execução com Docker

```bash
./mvnw clean package
docker-compose up --build
```

## 🔓 Testes locais sem JWT

Ao executar com o perfil `dev`, a propriedade `security.disabled` fica ativada e
todos os endpoints podem ser acessados sem token. Útil para testar via Swagger
ou Postman em localhost.

## 📂 Estrutura

- `controller/` — Endpoints
- `dto/` — Validação
- `model/` — Entidades
- `service/` — Regra de negócio com logs
- `exception/` — Exceções customizadas
- `config/` — JWT, Swagger, CORS, Logs
- `resources/` — application-dev/prod + logback

## 👥 Equipe

- Thomaz Oliveira Vilas Boas Bartol – RM555323
- Vinicius Souza Carvalho – RM556089
- Gabriel Duarte – RM556972


