# MVVM-Firebase
Projeto exemplo de união do mvvm com firebase Firestore.

Neste projeto, tento fazer uso da arquitetura MVVM aliada ao uso de uma base de dados com Firebase.
Também abordo uma temática onde podemos ter 3 aplicações com views distintas e que compartilham a mesma camada de modelo e algumas regras de negócio.

A ideia desse projeto é ter um compartilhamento da posição de alguns motoristas ( taxi / mototaxi ) para um usuário qualquer.


No projeto temos:

O Gerente:
- Aplicação responsável pro cadastrar, listar e visualizar motoristas

O Motorista
- Aplicação que recebe um código de validação gerado por um gerente, possibilitando ver seus dados cadastrados e ativar o modo "ON LINE"
onde sinaliza que o motorista está trabalhando e disponível

O Cliente (Em desenv)
- Aplicação que será usada pelo usuário, podendo ver todos os motoristas que estão ativos na redondeza de acordo com sua localização

