# CRUD Lançamento e Produtos

Esse sistema é resultante da avaliação de um processo seletivo de uma empresa de tecnologia. É responsável por inserir, atualizar e remover dados de lançamentos e produtos. Além disso, apresenta as funcionalidades de verificação de dois intervalos de valores e também o cálculo de números primos existentes entre os valores.

--------------
## Requisitos do projeto

* Crie um projeto Java Web, contendo as camadas MVC;
* Na camada de Modelo, deve-se mapear as classes fazendo uso de annotations do Hibernate;
* Na camada de View, deve-se fazer uso de JSF com Primefaces.

--------------

## Dependências do projeto

* Java 8
* Tomcat 8.5
* SGBD MySQL

Instalar as dependências do projeto utilizando o maven.

--------------
## Como rodar?

1.Faça o clone desse projeto para sua máquina
```
git clone https://github.com/luisbilecki/CRUDLancamento.git
```

2.Abra o eclipse

3.Clique em File > Import

4.Digite "Maven" no campo de pesquisa abaixo de "Select an import source"

5.Selecione "Existing Maven Projects" e clique em "Next"

6.Clique em "Browse" e selecione o diretório do projeto.

7.Clique em "Next"

8.Execute o script banco.sql no SGBD MySQL

9.Verifique e altere se necessário as configurações de acesso ao banco em src/main/java/META-INF/persistence.xml

10.Após a importação, clique com o botão direito no projeto e vá em Run As > Run on a Server.

11.Acesse o projeto em seu navegador através da URL
```
http://localhost:8080/CRUDLancamento/pages/home.xhtml
```
