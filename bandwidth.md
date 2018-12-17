# Bandwidth

Nosso aplicativo utiliza rede nos seguintes momentos:

### Autenticação ###

A autenticação utiliza pouca banda, pois para realizar é necessário apenas um pacote com login e senha.
  
![Alt Text](https://raw.githubusercontent.com/irbp/parking-app-if710/master/img/bandwidth_auth.png)

### Requisição de estacionamentos ao redor ###


  Esse tópico é um ponto crítico no uso de banda, pois o número de estacinamentos cadastrados no aplicativo pode ser muito alto. Para reduzir o número de estacionamentos que trafegam pela rede, implementamos um endpoint que filtra os estacionamentos em uma determinado raio no lado do servidor.
  
![Alt Text](https://raw.githubusercontent.com/irbp/parking-app-if710/master/img/bandwidth_parkings_around.png)


  Abaixo está o código utilizado para obter esses estacionamentos filtrados.
  ```kotlin 
      @GET("parkings")
      fun getAllParkingsAround(
          @Query("lng") longitude: Number,
          @Query("lat") latitude: Number,
          @Query("radius") radius: Number): Observable<Array<ParkingResponse>>
  ```
