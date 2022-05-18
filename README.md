# Cadastro de partidos e seus associados

API que realiza o cadastro de partidos e seus associados, respeitando regras lógicas aplicaveis nessa situação.

## 🚀 Começando

Para obter uma versão do projeto basta dar um clone neste repósitorio. Para rodar, os passos seguem abaixo.

### 📋 Pré-requisitos

De que coisas você precisa para instalar o software e como instalá-lo?

```
JDK na versão 11 ou superior.
Maven ou
IDE Java - com maven imbutido.
Git - para clonar o repósitorio, porém também é póssivel baixar como zip.
```

### 🔧 Instalação

Por ser uma API monolítica, a instalação consiste em:

Tendo os pre-requisitos instalados, basta seguir os passos abaixo:

```
Importe o projeto para a IDE escolhida.
Faça o build do projeto para que todas as dependencias sejam baixas.
Execute o projeto.
```

Com o projeto rodando, pode se testar de pois modos:

```
Utilizando o Postman, ou qualquer programa para realizar requisições.
Utilizando a documentação, acessível atraves do link http://localhost:8080/swagger-ui.html (o link pode mudar caso a url do projeto seja modificada, mas o final sempre será **/swagger-ui.html).
```

Exemplo de POST para um associado:

```json
{
  "birthDate": "dd/MM/yyyy",
  "gender": "Masculino",
  "name": "string",
  "politicalOffice": "Vereador",
  "politicalParty": 0
}
```

## ⚙️ Executando os testes

Os testes foram feitos utilizando modulo do Spring para tests com JUnit Jupiter, todos os testes foram feitos utilizando mocks. Então para executa-los basta estar em um sistema que seja possivel subir o ambiente spring.

### 🔩 Cobertura dos testes

Os testes cobrem a camada de serviços do projeto, e testam todas as funcionalidades lá inseridas.

## 🛠️ Construído com

Mencione as ferramentas que você usou para criar seu projeto

* [Maven](https://maven.apache.org/) - Gerente de Dependência
* [Spring Boot](https://spring.io/projects/spring-boot) - Criação e inicialização do projeto.
* [JUnit 5](https://junit.org/junit5/) - Utilizado para realizar os testes.
* [SpringFox](https://springfox.github.io/springfox/) - Criação da documentação, gerando uma documentação com Swagger.
* [Spring Tools 4](https://spring.io/tools) - IDE Utilizada em todas as etapas do projeto.

## ✒️ Autores

* [José Layrton](https://github.com/kiba28) - *Unico desenvolvedor*
