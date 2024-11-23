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
   
   # Nível de log para a aplicação
    logging.level.root=INFO
    logging.level.com.example.desafio_votacao.controller=DEBUG
    
    # Padrão do log no console
    logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
    
    # Padrão do log em arquivo
    logging.file.name=logs/desafio_votacao.log
    logging.file.max-size=10MB
    logging.file.max-history=30

   ```

3. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```

---

Aqui está o **README** atualizado com as alterações que foram feitas no código:

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
**POST** `/api/votacao/sessao/abrir`  
**Body (JSON):**
```json
{
    "pautaId": 1,
    "duracaoEmMinutos": 30
}
```
**Nota:**
- `pautaId`: ID da pauta associada à sessão.
- `duracaoEmMinutos`: Duração da sessão em minutos (opcional, padrão: 1 minuto).

### **3. Registrar Voto**
**POST** `/api/v1/votos`  
**Body (JSON):**
```json
{
    "sessaoId": 1,
    "associadoId": "12345",
    "votoSim": true
}
```
**Nota:**
- `sessaoId`: ID da sessão em que o voto está sendo registrado.
- `associadoId`: ID do associado que está votando.
- `votoSim`: `true` para voto SIM, `false` para voto NÃO.

### **4. Contabilizar Resultado**
**GET** `/api/v1/votos/{sessaoId}/resultado`  
**Resposta (JSON):**
```json
{
    "votosSim": 10,
    "votosNao": 5
}
```

### **5. Validar CPF**
**GET** `/api/cpf/validar/{cpf}`  
**Resposta (JSON):**
```json
{
    "status": "CPF válido"
}
```
**Nota:**
- `{cpf}`: CPF a ser validado.

### **6. Listar Sessões**
**GET** `/api/sessoes`  
**Resposta (JSON):**
```json
[
    {
        "id": 1,
        "pauta": "Título da Pauta",
        "inicio": "2024-11-01T10:00:00",
        "fim": "2024-11-01T10:30:00"
    },
    {
        "id": 2,
        "pauta": "Outra Pauta",
        "inicio": "2024-11-02T10:00:00",
        "fim": "2024-11-02T10:30:00"
    }
]
```

### **7. Listar Sessões Abertas**
**GET** `/api/sessoes/abertas`  
**Resposta (JSON):**
```json
[
    {
        "id": 1,
        "pauta": "Título da Pauta",
        "inicio": "2024-11-01T10:00:00",
        "fim": "2024-11-01T10:30:00"
    }
]
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