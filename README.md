# Invext 

## Como rodar

- Execute a classe **InvextApplication** e automaticamente os containers do MySQL e RabbitMQ vão subir

## Como testar

Para fins de teste eu criei uma migration para inserir atendentes fictícios para cada 
setor e três clientes

Você pode verificar os dados no arquivo `invext/src/main/resources/db/migration/V2__insert_fictitious_datas.sql`

### A aplicação possui apenas um endpoint

> **POST** http://localhost:8080/ticket

**Payload**

```json
{
	"department": "enum(CREDIT_CARDS | LOANS | OTHERS) | Obrigatório",
	"title": "String | Título do ticket | Obrigatório",
	"description": "String | Descrição | Obrigatório",
	"customerId": "Integer | id do cliente | Obrigatório"
}
```

Este endpoint irá criar o ticket e procurar um atendente disponível

- Se encontrar um atendente, iria seguir o fluxo fictício de atendimento
- Caso não encontrar, o ticket ficará em uma fila do RabbitMQ até um atendente se 
  encontrar disponível (O ticket pode aparentar estar em looping, mas estará 
  aguardando um atendente)
- A única maneira atualmente de tirar um ticket pendente da fila é liberando algum 
  atendente manualmente pelo banco, pode fazer isso da seguinte forma:

1. `docker exec -it invext-mysql-1 mysql -u root -p`
2. Senha: "1234"
3. `USE INVEXT;`
4. `SELECT * FROM ticket;`
5. Escolha um ticket do mesmo departamento em que deseja liberar a fila e mude seu 
   `status` para **CLOSED**
6. `UPDATE ticket SET status = 'CLOSED' WHERE id = <id_do_ticket>;`
