#### Aplicação Tangle focado para Internet das Coisas

## A aplicação:
A atual versão do código está apenas com o teste "LoopBack" usando um node público da IOTA, ou seja a aplicação baseia-se em:
- Ouvir novas mensagens chegadas ao Broker
- Adicionar transação no ledge.
-Ler transação e decodificar a mensagem
- Exibir transação completa
Como rodar a aplicação.

### Requisitos
- Java 8.0
- Eclipse com Maven
- Mosquitto Broker

### Como rodar:
1. Dar Maven Build no projeto
2. Executar Aplicação java
3. Publicar no tópico "teste" em localhost a mensagem desejada EX: mosquitto_pub -h localhost -t teste -m "mensagem"


### Classes 
ControlerImpl: Responsável por dar o star no sistema, conectar e criar as instâncias do MQTT e do Listener.
ClientMQTT : Responsável por toda a conexão com o Mosquitto
Listener: "Escuta" novas mensagens do broker e as adiciona no Tangle. Responsável também por iniciar a API TANGLE.
