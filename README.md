Para esse projeto usamos os PDF que o professor disponibilizou no enturma para criar as coneões entre o Java e o banco de dados.
E para utilização do Vaadin em sua ultima versão, fizemos a leitura da documentação e seguimos o tutorial para chegar nesse resultado. Foi nos informado que para usar os componentes pagos podiamos acessar como estudante e que não iriamos precisar pagar. Por conta disso decidimos usar o componente CRUD que parecia mais simples e eficaz a sua implementação

## Casos de Uso

### Gerenciamento de Clientes e produto


O sistema de gerenciamento de clientes e produtos, que incluem ações como cadastrar, editar, atualizar e remover clientes e produtos. Esses casos de uso são essenciais para entender como o sistema funcionará e como os diferentes atores, como clientes e vendedores, interagem com ele.
<br>

### Cliente
- **Cadastrar Cliente**: Permite cadastrar um novo cliente no sistema.

- **Editar Cliente**: Permite editar informações de um cliente existente.

- **Atualizar Cliente**: Permite atualizar os dados de um cliente, como endereço ou número de telefone.

- **Remover Cliente**: Permite remover um cliente do sistema.

### Produto

- **Cadastrar Produto**: Permite cadastrar um novo produto no sistema.

- **Editar Produto**: Permite editar informações de um produto existente, como preço ou descrição.

- **Atualizar Produto**: Permite atualizar os dados de um produto, como estoque ou fornecedor.

- **Remover Produto**: Permite remover um produto do sistema.

## Query
```sql

Create database lojaGod;


CREATE TABLE IF NOT EXISTS cliente (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     nome VARCHAR(255) NOT NULL,
  dataNascimento DATE,
  cpf VARCHAR(14) NOT NULL,
  telefone VARCHAR(15),
  email VARCHAR(255),
  cep BIGINT(8),
  bairro VARCHAR(255),
  logradouro VARCHAR(255),
  numero INT,
  complemento VARCHAR(255),
  cidade VARCHAR(255),
  estado VARCHAR(2)
  );

-- Inserir registros

INSERT INTO cliente (nome, dataNascimento, cpf, telefone, email, cep, bairro, logradouro, numero, complemento, cidade, estado) VALUES
                                                                                                                                 ('João Silva', '1990-05-15', '123.456.789-01', '(47) 1234-5678', 'joao@email.com', 12345678, 'Bairro A', 'Rua 1', 123, 'Apto 2A', 'Cidade A', 'SP'),
                                                                                                                                 ('Maria Souza', '1985-02-20', '987.654.321-09', '(47) 9876-5432', 'maria@email.com', 87654321, 'Bairro B', 'Rua 2', 456, 'Casa 3B', 'Cidade B', 'RJ'),
                                                                                                                                 ('Carlos Santos', '1998-09-10', '234.567.890-34', '(47) 5555-7777', 'carlos@email.com', 55557777, 'Bairro C', 'Rua 3', 789, 'Apto 5C', 'Cidade C', 'MG'),
                                                                                                                                 ('Ana Oliveira', '1995-03-25', '567.890.123-45', '(47) 2222-8888', 'ana@email.com', 33334444, 'Bairro D', 'Rua 4', 101, 'Casa 6D', 'Cidade D', 'SC'),
                                                                                                                                 ('Pedro Fernandes', '2000-11-12', '876.543.210-98', '(47) 7777-9999', 'pedro@email.com', 99998888, 'Bairro E', 'Rua 5', 202, 'Apto 7E', 'Cidade E', 'RS'),
                                                                                                                                 ('Mariana Lima', '1992-07-30', '321.654.987-76', '(47) 3333-1111', 'mariana@email.com', 55556666, 'Bairro F', 'Rua 6', 303, 'Casa 9F', 'Cidade F', 'PR'),
                                                                                                                                 ('Paulo Sousa', '1980-12-05', '890.123.456-32', '(47) 9999-5555', 'paulo@email.com', 77776666, 'Bairro G', 'Rua 7', 404, 'Apto 11G', 'Cidade G', 'BA'),
                                                                                                                                 ('Isabela Ferreira', '1993-06-18', '765.432.109-87', '(47) 8888-2222', 'isabela@email.com', 11112222, 'Bairro H', 'Rua 8', 505, 'Casa 13H', 'Cidade H', 'GO'),
                                                                                                                                 ('Lucas Martins', '1987-09-03', '543.210.987-65', '(47) 4444-6666', 'lucas@email.com', 22223333, 'Bairro I', 'Rua 9', 606, 'Apto 15I', 'Cidade I', 'AM'),
                                                                                                                                 ('Raquel Pereira', '1996-04-14', '678.901.234-54', '(47) 7777-3333', 'raquel@email.com', 99995555, 'Bairro J', 'Rua 10', 707, 'Casa 17J', 'Cidade J', 'PA');

-- Cria tabela produto

CREATE TABLE IF NOT EXISTS produto (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     nome VARCHAR(255) NOT NULL,
  preco DECIMAL(10, 2) NOT NULL
  );

-- insert na tabela produto

INSERT INTO produto (nome, preco) VALUES
                                    ('Smartphone Galaxy S21', 999.99),
                                    ('Notebook Dell XPS 13', 1499.99),
                                    ('Smart TV Sony Bravia 55"', 899.99),
                                    ('Câmera Canon EOS 5D Mark IV', 2499.99),
                                    ('Console de Jogos PlayStation 5', 499.99),
                                    ('Forno Elétrico de Embutir Electrolux', 699.99),
                                    ('Máquina de Lavar Roupas LG 10kg', 599.99),
                                    ('Aspirador de Pó Dyson V11', 399.99),
                                    ('Cafeteira Nespresso Vertuo', 129.99),
                                    ('Liquidificador Osterizer', 49.99),
                                    ('Relógio Rolex Submariner', 7999.99),
                                    ('Fogão a Gás Brastemp 5 Bocas', 799.99),
                                    ('Bicicleta de Montanha Trek', 1499.99),
                                    ('Cadeira de Escritório Herman Miller', 699.99),
                                    ('Fone de Ouvido Bose QuietComfort 35 II', 249.99),
                                    ('Mesa de Jantar de Madeira Maciça', 1299.99),
                                    ('Mala de Viagem Samsonite Spinner', 149.99),
                                    ('Tênis Nike Air Max 270', 129.99),
                                    ('Óculos de Sol Ray-Ban Aviator', 149.99),
                                    ('Mochila para Laptop SwissGear', 79.99),
                                    ('Panela de Pressão Instant Pot', 99.99),
                                    ('Ventilador de Teto Hunter', 199.99),
                                    ('Monitor de Computador Dell Ultrasharp 27"', 399.99),
                                    ('Lavadora de Alta Pressão Kärcher', 249.99),
                                    ('Máquina de Cortar Cabelo Wahl', 59.99),
                                    ('Caixa de Som Bluetooth JBL Charge 4', 129.99),
                                    ('Laptop HP Envy x360', 799.99),
                                    ('Sofá de Couro Reclinável', 999.99),
                                    ('Batedeira KitchenAid', 199.99),
                                    ('Fone de Ouvido Apple AirPods Pro', 199.99),
                                    ('Colchão de Espuma de Memória Tempur-Pedic', 1499.99),
                                    ('Cadeira de Massagem Inada DreamWave', 4999.99),
                                    ('Câmera GoPro Hero 9 Black', 349.99),
                                    ('Máquina de Café Espresso Breville', 299.99),
                                    ('Secador de Cabelo Dyson Supersonic', 399.99),
                                    ('Luminária de Mesa Philips Hue', 79.99),
                                    ('Geladeira Side-by-Side LG', 1999.99),
                                    ('Teclado Mecânico Corsair K95 RGB', 149.99);
```

### Diagrama classes
![Diagrama de classes](src/main/resources/diagrama.png)

## Info sobre java , banco de dados, driver

Banco de dados : MySQL
<br>
JDBC : Mysql Connector 8.0.30
<br>
JAVA : OpenJDK 20