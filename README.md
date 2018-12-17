# parking-app-if710

Especificação do projeto: https://docs.google.com/document/d/19hmpBc-LinIIbKQ4LA3Hy2T6wgUtJsM6EZVHnfo_l_w/edit?usp=sharing

Link para o mockup: https://ninjamock.com/s/HDJV9Tx

Source code da API utilizada: https://github.com/fms6/parking-api-if710

## Demo da aplicação
No vídeo abaixo é possível visulizar as telas da nossa aplicação assim como o serviço de geofence em funcionamento. O aplicativo já tem alguns estacionamentos (RioMar, Recife e Tacaruna, apenas para demonstração) registrados no banco de dados e quando o usuário entra no perímetro de algum deles o mesmo recebe uma notificação perguntando se ele de fato estacionou naquele local. Para simular a entrada no estacionamento a princípio a localização está apontando para um lugar aletório e depois é alterada para as coordenadas do Shopping RioMar, isso pode ser observado no vídeo. Após um período de tempo uma notificação chega para o usuário. Este período de tempo é para ter certeza que o usuário está dentro daquele perímetro, pois do contrário haveria muitos falso positivos. Por conta disso foi escolhida a transição "dwell" ao  invés de um simples "enter", pois o usuário pode entrar e sair do local em um curto período de tempo e cada vez uma notificação seria gerada.

[![IMAGE ALT TEXT](http://img.youtube.com/vi/TjFQ6KDjPhc/0.jpg)](http://www.youtube.com/watch?v=TjFQ6KDjPhc "Demo da aplicação")
