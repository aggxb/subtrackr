# 🚀 SubTrackr - Backend (API)

![Status do Projeto](https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java_21-007396?style=for-the-badge&logo=java&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-000000?style=for-the-badge&logo=json&logoColor=white)

## 📌 Sobre o Projeto
O **SubTrackr** é um consolidador financeiro pessoal focado em gerenciar gastos recorrentes (assinaturas de software, streaming, academias).

Este repositório contém a **API RESTful (Backend)** do projeto. O grande objetivo de aprendizado aqui foi construir uma arquitetura limpa, onde **toda a regra de negócio e cálculos matemáticos estão centralizados no servidor**, entregando dados mastigados e formatados para o cliente (Frontend) exibir, utilizando o padrão DTO (Data Transfer Object) de forma estrita.

🌐 **Teste na prática:** Você pode ver esta API em ação sendo consumida pelo nosso frontend oficial, hospedado e rodando ao vivo aqui: **[SubTrackr Web (Vercel)](https://subtrackr-web.vercel.app/)**

## ⚙️ Arquitetura e Padrões (Diferenciais)
* **Arquitetura:** API REST com isolamento em 3 camadas (Controller, Service, Repository).
* **Design Patterns:** * **DTO (Data Transfer Object):** Isolamento total entre o modelo de banco de dados (Entidade) e a camada web utilizando `Java Records` para imutabilidade.
    * **Repository Pattern:** Abstração da camada de dados. Para fins de prototipagem rápida e foco em mapeamento, utilizamos um repositório *in-memory* estático.
* **Boas Práticas:** * Princípios SOLID (Single Responsibility).
    * Separação de *Payloads* (Requests independentes para POST e PUT).
    * Uso massivo da **Stream API** do Java 21 para cálculos ágeis, filtragens e ordenações limpas.
    * Uso adequado dos verbos HTTP e retornos de Status Code corretos (`201 Created` com header `Location`, `204 No Content`, etc).

## 🚀 Funcionalidades Principais
* [x] **Gestão de Assinaturas (CRUD):** Endpoints completos para listar, criar, editar e excluir assinaturas.
* [x] **Cálculo de Resumo Financeiro:** Endpoint específico (`/summary`) que calcula em tempo real o gasto mensal e anual, convertendo automaticamente planos anuais para custo mensal e vice-versa.
* [x] **Toggle de Status:** Endpoint inteligente (`PATCH`) exclusivo para alternar assinaturas entre Ativas e Canceladas sem necessidade de enviar o objeto inteiro.
* [x] **Mapeamento Automatizado:** Uso do **MapStruct** para converter Entidades em Records e vice-versa sem código *boilerplate* repetitivo.

## 💻 Tecnologias Utilizadas

### Backend
* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Mapeamento de Objetos:** MapStruct
* **Produtividade:** Lombok

### 🛠️ Ferramentas Auxiliares
* Maven (Gerenciamento de dependências)
* Postman / Insomnia (Teste de rotas)

---

## 🌐 Endpoints da API
Abaixo estão as rotas expostas pela aplicação (Base URL: `http://localhost:8080/api/v1/subscriptions`):

| Método | Endpoint | Descrição | Status Retorno |
|---|---|---|---|
| `GET` | `/` | Retorna a lista de todas as assinaturas (Aceita params `query` e `sort`). | `200 OK` |
| `GET` | `/summary` | Retorna os cálculos totais (gasto mensal, anual e qtd ativas). | `200 OK` |
| `POST` | `/` | Cria uma nova assinatura. | `201 CREATED` |
| `PUT` | `/{id}` | Atualiza todos os dados de uma assinatura existente. | `200 OK` |
| `PATCH` | `/toggle/{id}` | Inverte o status da assinatura (Cancela/Reativa). | `200 OK` |
| `DELETE` | `/{id}` | Remove a assinatura permanentemente. | `204 NO CONTENT` |

---

## 🛠️ Como Executar o Projeto Localmente

### Pré-requisitos
* Java 21 instalado na sua máquina
* Maven (embutido via Wrapper ou instalado localmente)

### Passo a Passo

```bash
# 1. Clone este repositório
$ git clone [git@github.com:aggxb/subtrackr-api.git]

# 2. Acesse a pasta do projeto
$ cd subtrackr-api

# 3. Baixe as dependências e compile o projeto usando Maven
$ mvn clean install

# 4. Inicie a aplicação Spring Boot
$ mvn spring-boot:run

# 5. A API estará rodando localmente na porta 8080
# Exemplo de teste: http://localhost:8080/api/v1/subscriptions