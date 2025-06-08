# SafeCap - Sistema de Monitoramento de Temperatura e Umidade

O SafeCap é um sistema inteligente de monitoramento de temperatura e umidade, desenvolvido para garantir a segurança e qualidade de produtos sensíveis a variações climáticas. Projeto desenvolvido para a Global Solution da FIAP.

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Security
- JWT (JSON Web Tokens)
- JPA/Hibernate
- Oracle Database (FIAP)
- Maven

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven

## 🔧 Configuração do Ambiente

1. Clone o repositório
```bash
git clone https://github.com/SouzaEu/safecap.git
cd safecap
```

2. O arquivo `application.properties` já está configurado com as credenciais do banco de dados da FIAP

3. Execute o projeto
```bash
mvn spring-boot:run
```

## 📦 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/com/fiap/safecap/
│   │       ├── controller/    # Endpoints REST
│   │       ├── model/         # Entidades JPA
│   │       ├── repository/    # Repositórios JPA
│   │       ├── service/       # Lógica de negócios
│   │       └── config/        # Configurações
│   └── resources/
│       └── application.properties
```

## 📝 Documentação e Testes

### Documentação da API
A documentação completa da API está disponível através do Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

### Testes da API
Na raiz do projeto, você encontrará o arquivo `comandos postman.txt` com todos os comandos HTTP para testar a API.

## 📊 Funcionalidades

### Dispositivos
- Cadastro de dispositivos IoT
- Listagem de dispositivos
- Atualização de informações
- Remoção de dispositivos

### Alertas
- Monitoramento de temperatura (-10°C a 80°C)
- Monitoramento de umidade (0% a 100%)
- Geração automática de alertas
- Histórico de alertas

## 🛠️ Desenvolvimento

```bash
# Compilar o projeto
mvn clean install

# Executar os testes
mvn test

# Iniciar a aplicação
mvn spring-boot:run
```

## 🔄 Fluxo de Dados

1. Usuário se registra no sistema
2. Sistema criptografa a senha com BCrypt
3. Usuário faz login e recebe token JWT
4. Usuário cadastra dispositivos IoT
5. Dispositivos enviam dados de temperatura e umidade
6. Sistema gera alertas quando necessário

## ⚠️ Limites e Validações

- Temperatura: -10°C a 80°C
- Umidade: 0% a 100%
- Senha: mínimo 6 caracteres
- Email: formato válido

## 🔒 Segurança

- Senhas criptografadas com BCrypt
- Autenticação via JWT
- Validação de tokens em cada requisição
- Proteção contra SQL Injection
- Validação de dados de entrada

## 📈 Monitoramento

O sistema permite:
- Visualização em tempo real
- Histórico de alertas
- Relatórios personalizados
- Dashboard com métricas

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---


