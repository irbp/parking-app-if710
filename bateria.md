# Bateria

Para a nossa aplicação a localização é algo fundamental, já que é necessário um rastreamento do usuário para saber se ele entrou em um determindado perímetro ou não. Para fazer isso de forma eficiente na questão energética, a primeira alternativa que optamos foi por Geofencing rodando como um serviço em background já que ele mesmo faz o gerenciamento dos pontos emitindo um broadcast quando o usuário entrar em determinado perímetro, porém com a chegada do Android Oreo as políticas de serviços executando em segundo plano se tornaram mais rigorosas, fazendo com que o sistema escalone tarefas relacionadas à localização apenas de tempos em tempos e esse intervalo é definido pelo próprio sistema, sendo assim não foi muito interessante para a nossa a aplicação já que o tempo de notificação era muito demorado quando o serviço estava rodando em background.

Outras alternativas foram utilizadas como JobScheduler e WorkManager, este último faz parte do Architecture Components. Esses dois métodos fornecem uma forma fácil de agendar tarefas que devem ser executadas em determinados intervalos e sob determinadas circunstâcias. O problema é que, novamente, o sistema age com bastante rigorosidade no que diz respeito à serviços em segundo plano, sendo assim, não conseguimos utilizar de forma decente essas ferramentas.

Como outra alternativa, testamos o serviço em foreground, porém o mesmo ficava utilizando o GPS do aparelho o tempo todo, além de que encontramos alguma dificuldade em encaixar ele em nossa arquitetura, o que acarretou alguns problemas no acesso do banco de dados.

Por último escolhemos rodar o serviço apenas quando o app está em primeiro plano. De longe não é a melhor solução, mas foi a que conseguimos utilizar de forma a notificar o usuário de forma rápida e ao mesmo tempo não drenar a bateria em segundo plano.

Essa parte de localização/consumo foi bastante interessante pra nós pois conseguimos utilizar diversas maneiras que o sistema fornece para criar serviços e ao mesmo tempo conseguimos aprender as restrições que as novas versões criam para que a experiência do usuário seja cada vez melhor e exista uma transparência.

Como nossa aplicação é relativamente simples, abaixo segue um print do consumo de energia no momento que ela está fazendo uso da localização do usuário.

![alt text](https://github.com/irbp/parking-app-if710/blob/master/img/print_energia.png)
