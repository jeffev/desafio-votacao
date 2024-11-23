# Desafio Votação

Esta é uma aplicação backend para gerenciar sessões de votação no contexto de cooperativas. Desenvolvida em Java com Spring Boot, a API permite cadastrar pautas, abrir sessões de votação, registrar votos e contabilizar resultados.

## **Funcionalidades**
1. **Cadastrar Pauta**  
   - Permite o registro de uma nova pauta para votação.
2. **Abrir Sessão de Votação**  
   - Abre uma sessão de votação para uma pauta, podendo ser configurada com um tempo customizado ou padrão de 1 minuto.
3. **Registrar Votos**  
   - Registra votos em uma pauta. Cada associado (identificado por um ID único) pode votar apenas uma vez em cada pauta. Os votos permitidos são "Sim" ou "Não".
4. **Contabilizar Resultados**  
   - Retorna o resultado da votação em uma pauta específica.

---

## **Tecnologias Utilizadas**
- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - PostgreSQL Driver
  - Spring Boot DevTools
  - Lombok
  - Validation
  - Spring Cache
  - Spring Boot Actuator

---

## **Requisitos**
- **Java 17** ou superior.
- **Maven** 3.6+.
- **Banco de Dados PostgreSQL**.

---

## **Configuração do Ambiente**

1. **Clone o repositório**  
   ```bash
   git clone https://github.com/jeffev/desafio-votacao.git
   cd desafio-votacao
   ```

2. **Configure o banco de dados**  
   Crie um banco de dados PostgreSQL e atualize as configurações no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## **Rotas Principais**
### **1. Cadastrar Pauta**
**POST** `/api/v1/pautas`  
**Body (JSON):**
```json
{
    "titulo": "Título da Pauta",
    "descricao": "Descrição detalhada da pauta"
}
```

### **2. Abrir Sessão de Votação**
**POST** `/api/votacao/sessao/abrir?pautaId=1&duracaoEmMinutos=30`  
**Body (JSON):**
```json
{
    "duracao": 5
}
```
**Nota:** `duracao` é o tempo da sessão em minutos (opcional, padrão: 1).

### **3. Registrar Voto**
**POST** `/api/v1/pautas/{id}/votos`  
**Body (JSON):**
```json
{
    "idAssociado": "12345",
    "voto": "SIM"
}
```

### **4. Contabilizar Resultado**
**GET** `/api/v1/pautas/{id}/resultado`  
**Resposta (JSON):**
```json
{
    "pauta": "Título da Pauta",
    "votosSim": 10,
    "votosNao": 5
}
```

---

## **Testes**
Execute os testes com o seguinte comando:
```bash
./mvnw test
```

---

## **Autor**
Desenvolvido por **[Jefferson Valandro]**.  
Entre em contato: [jeffev123@gmail.com](mailto:jeffev123@gmail.com)
```