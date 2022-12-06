
# BootCamp - DevSuperior. 
## _**DsCatalog**_:
- Projeto feito a principio para cadastro de produtos e também categorias. Construido a partir das aulas mestradas pelo professor _**Nélio Alves**_, porém agregado também, com conhecimentos adquiridos durante meus estudos e práticas!     


## _**Stack utilizada**_:
- **_Back-end:_** 
    - Linguagem Java;
    - Banco de dados: H2;
    - Framework: SpringBoot. 
    
- **_Dependencias utilizadas_**:
    - Spring Data JPA;
    - H2 Database;
    - Spring-web;
    - Lombok.

## _**Routes and Methods**_
### _**Category**_:

- **New _Category_**:

```http
  POST /categories
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | Nome da nova categoria. |

```json
    {
        "name": "Nova categoria."
    }
```

- **Find by id _Category_**:

```http
  GET /categories/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | O ID da categoria que você busca. |

- **Find by page _Category_**:

```http
  GET /categories
```

| Parâmetro   | Value       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `page`      | `integer` | Página desejada. |
| `linesPerPage`      | `integer` | Quantidade de linhas por página. |
| `direction`      | `integer` | Descentende ou Ascendente. |
| `orderBy`      | `integer` | Ordena por ID as categorias encontradas. |

- **Update _Category_**:

```http
  PUT /categories/${id}
```

| Parâmetro   | Tipo  / Value     | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | O ID da categoria que você quer alterar. |
| `name`      | `string` | O nome da categoria que você irá alterar. |

```json
    {
        "name": "Categoria atualizada."
    }
```

- **Delete _Category_**:

```http
  DEL /categories/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | O ID da categoria que você quer deletar. |

### _**Product**_:

- **New _Product_**:

```http
  POST /products
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | Nome do produto. |
| `description` | `string` | Descrição do produto. |
| `price` | `float` | Preço do produto. |
| `imgUrl` | `string` | Url da imagem do produto. |
| `date` | `instant` | Data de criação no formato _**2022-07-20T10:00:00Z**_. |
| `categories` | `integer` | Objeto **categories** passando uma List de ID, das respectivas categorias. |

```json
    {
      "date": "2020-07-20T10:00:00Z",
      "description": "The new generation PS5 video game",
      "name": "PS5",
      "imgUrl": "",
      "price": 600.0,
      "categories": [
        {
          "id": 1
        },
        {
          "id": 3
        }
      ]
    }
```

- **Find by id _Product_**:

```http
  GET /products/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | O ID do produto que você busca. |

- **Find by page _Product_**:

```http
  GET /products
```

| Parâmetro   | Value       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `page`      | `integer` | Página desejada. |
| `linesPerPage`      | `integer` | Quantidade de linhas por página. |
| `direction`      | `integer` | Descentende ou Ascendente. |
| `orderBy`      | `integer` | Ordena por ID as categorias encontradas. |

- **Update _Product_**:

```http
  PUT /products/${id}
```

| Parâmetro   | Tipo  / Value     | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | O ID do produto que você quer alterar. |
| `name` | `string` | Novo nome do produto. |
| `description` | `string` | Nova descrição do produto. |
| `price` | `float` | Novo preço do produto. |
| `imgUrl` | `string` | Nova url da imagem do produto. |
| `categories` | `integer` | Nova categoria(as) do produto. |

```json
    {
      "name": "Alterando produto."
    }
```

- **Delete _Product_**:

```http
  DEL /products/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | O ID do produto que você quer deletar. |

## **_Collection_**:
[Collection Postman](/collection/BootCamp-DevSuperior.json).

## _**Autor**_:

- GitHub: https://github.com/TorHugo
- LinkedIn: https://www.linkedin.com/in/victorhugodev/
- Email: arruda.victorhugo@outlook.com

