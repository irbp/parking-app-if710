# Architecture Components

Para a arquitetura do nosso aplicativo, nós optamos pelo padrão MVVM pois ele fornece uma separação de conceitos bastante interessante onde a View trata apensas do que diz respeito à interface e o ModelView faz a comunicação entre a View e o Model, tornando assim a aplicação muito mais simples para execução de testes.

Para a implementação do MVVM nós fizemos uso do conjunto de ferramentas presentes no [Architecture Components](https://developer.android.com/topic/libraries/architecture/) do Android. As ferramentas que utilizamos foram o [Room](https://developer.android.com/topic/libraries/architecture/room), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) e [LiveData](https://developer.android.com/topic/libraries/architecture/livedata). A combinação dessas três ferramentas nos forneceu uma implementação bastante simples e com quantidade de código reduzida, já que o objetivo de alguma delas e reduzir código boilerplate (o Room faz isso muito bem). O ViewModel junto com o LiveData trazem uma segurança maior à aplicação no que diz respeito à memory leaks já que eles são lifecycle aware tirando das mãos do programador a responsabilidade de verificar quando certos objetos devem ser destruídos ou não.

Abaixo segue uma imagem representando o padrão recomendado pelo Android e que nós utilizamos.

![alt text](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
