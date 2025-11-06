# Instruções para criar e executar a imagem Docker

Este projeto é uma aplicação Spring Boot (Java 17). O Dockerfile incluído é multistage: primeiro constrói o JAR usando Gradle, depois copia o JAR para uma imagem runtime mínima.

Passos (PowerShell - Windows):

1. Entrar na pasta do projeto

```
cd ChallengeJavaSprint3-
```

2. (opcional) Gerar o JAR localmente usando o Gradle Wrapper

```
.\gradlew.bat clean bootJar -x test
```

3. Build da imagem Docker

```
docker build -t challenge-java-sprint3 .
```

4. Rodar a aplicação em um container (mapeando a porta 8080)

```
docker run --rm -p 8080:8080 challenge-java-sprint3
```

Observações:
- Se sua aplicação usa variáveis de ambiente (por exemplo, para conectar ao banco), passe-as no `docker run` com `-e` ou use um `docker-compose`.
- Se preferir produzir o JAR fora do Docker (mais rápido em ambiente de desenvolvimento), gere o JAR com `gradlew` e use uma imagem runtime que copie apenas o JAR.