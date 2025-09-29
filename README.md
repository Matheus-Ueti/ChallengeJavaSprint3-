# Challenge Java Sprint 3 - Sistema de Gerenciamento de Motos

Sistema web completo para gerenciamento de motos desenvolvido com Spring Boot, incluindo autenticação OAuth2 com GitHub, controle de usuários e CRUD completo de motocicletas.

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Thymeleaf** - Template engine para frontend
- **OAuth2** - Integração com GitHub para autenticação
- **H2 Database** - Banco de dados em memória
- **Flyway** - Controle de versão do banco de dados
- **Lombok** - Redução de código boilerplate
- **Bootstrap 5** - Framework CSS para interface
- **Gradle** - Gerenciador de dependências

## 📋 Funcionalidades

### ✅ Autenticação e Segurança
- Login via OAuth2 com GitHub
- Sistema de perfis de usuário
- Controle de acesso baseado em roles
- Logout seguro

### ✅ Gerenciamento de Motos
- **Criar** - Cadastro de novas motocicletas
- **Listar** - Visualização de todas as motos
- **Editar** - Atualização de informações das motos
- **Excluir** - Remoção de motos do sistema
- **Buscar** - Filtros por marca, modelo e status

### ✅ Gerenciamento de Usuários
- Perfis de usuário integrados com GitHub
- Histórico de atividades
- Informações do usuário GitHub (nome, username, avatar)

## 🛠️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **Java 17 ou superior**
- **Git** (para clonar o repositório)
- **Navegador web** (Chrome, Firefox, Edge, etc.)

## 📥 Instalação e Execução

### 1. Clone o repositório
```bash
git clone https://github.com/Matheus-Ueti/ChallengeJavaSprint3-.git
cd ChallengeJavaSprint3-
```

### 2. Execute a aplicação
```bash
# No Linux/Mac
./gradlew bootRun

# No Windows
gradlew.bat bootRun
```

### 3. Acesse a aplicação
Abra seu navegador e acesse:
```
http://localhost:8080
```

## 🔐 Configuração OAuth2 GitHub

A aplicação utiliza OAuth2 para autenticação com GitHub. As credenciais já estão configuradas no `application.properties` para desenvolvimento.

### Para configurar suas próprias credenciais:

1. Acesse [GitHub Developer Settings](https://github.com/settings/developers)
2. Crie uma nova OAuth App
3. Configure:
   - **Application name**: Challenge Java Sprint 3
   - **Homepage URL**: `http://localhost:8080`
   - **Authorization callback URL**: `http://localhost:8080/login/oauth2/code/github`
4. Substitua no `application.properties`:
   ```properties
   spring.security.oauth2.client.registration.github.client-id=SEU_CLIENT_ID
   spring.security.oauth2.client.registration.github.client-secret=SEU_CLIENT_SECRET
   ```

## 🗄️ Banco de Dados

### H2 Database
A aplicação utiliza H2 Database em memória para desenvolvimento:
- **URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (vazio)

### Console H2
Para acessar o console do banco:
```
http://localhost:8080/h2-console
```

### Flyway Migrations
O banco é criado automaticamente via Flyway migrations localizadas em:
```
src/main/resources/db/migration/
```

## 📱 Como Usar

### 1. Primeiro Acesso
1. Acesse `http://localhost:8080`
2. Clique em "Login com GitHub"
3. Autorize a aplicação no GitHub
4. Você será redirecionado para a página inicial

### 2. Gerenciar Motos
1. No menu, clique em "Gestão de Motos"
2. **Adicionar Nova Moto**: Clique no botão "Nova Moto"
3. **Editar Moto**: Clique no ícone de edição na lista
4. **Excluir Moto**: Clique no ícone de lixeira
5. **Filtrar**: Use os filtros por marca, modelo ou status

### 3. Perfil do Usuário
- Clique em "Perfil" para ver suas informações
- Os dados são sincronizados automaticamente com seu GitHub

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/challengejavasprint2/
│   │   ├── config/          # Configurações de segurança
│   │   ├── controller/      # Controladores MVC
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositórios de dados
│   │   ├── service/        # Serviços de negócio
│   │   └── util/          # Classes utilitárias
│   └── resources/
│       ├── db/migration/   # Scripts Flyway
│       ├── templates/      # Templates Thymeleaf
│       └── application.properties
└── test/                   # Testes unitários
```

## 🧪 Executar Testes

```bash
# Todos os testes
./gradlew test

# Ver relatório de testes
./gradlew test --info
```

## 🐛 Troubleshooting

### Erro "Whitelabel Error Page"
Se encontrar este erro, certifique-se de:
1. A aplicação está rodando na porta 8080
2. Fazer login via GitHub primeiro
3. Verificar se as URLs estão corretas

### Problema de Parâmetros
O projeto inclui configuração especial no `build.gradle`:
```gradle
compileJava {
    options.compilerArgs << '-parameters'
}
```

### Banco de Dados
Se houver problemas com migrations:
1. Pare a aplicação
2. Delete o diretório `build/`
3. Execute: `./gradlew clean bootRun`

## 📊 Status do Projeto

- ✅ Spring Boot configurado
- ✅ Autenticação OAuth2 funcionando
- ✅ CRUD completo de motos
- ✅ Interface responsiva
- ✅ Banco H2 configurado
- ✅ Flyway migrations
- ✅ Testes básicos


## 👨‍💻 Autor

**Matheus Ueti**
- GitHub: [@Matheus-Ueti](https://github.com/Matheus-Ueti)

---


**Versão da aplicação**: 0.0.1-SNAPSHOT  
**Última atualização**: Setembro 2025

**video**:https://youtu.be/7HKl4rCxgNE
