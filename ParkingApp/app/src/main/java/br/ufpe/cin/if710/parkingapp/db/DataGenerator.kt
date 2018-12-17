package br.ufpe.cin.if710.parkingapp.db

import br.ufpe.cin.if710.parkingapp.db.entity.Parking
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import br.ufpe.cin.if710.parkingapp.db.entity.User
import java.util.*

class DataGenerator {

    companion object {
        var parkingDetailsList = initParkingDetailsList()
        var parkingList = initParkingList()
        var userList = initUserList()

        private fun initParkingDetailsList(): MutableList<ParkingDetails> {
            val parkingsDetails = mutableListOf<ParkingDetails>()

            parkingsDetails.add(
                ParkingDetails(
                    "Shopping Recife",
                    "1",
                    15.00F,
                    Date(1279879379L),
                    Date(1279879879L),
                    "2",
                    "1"
                )
            )

            parkingsDetails.add(
                ParkingDetails(
                    "Shopping Recife",
                    "2",
                    32.00F,
                    Date(1271879379L),
                    Date(1272879879L),
                    "2",
                    "2"
                )
            )

            parkingsDetails.add(
                ParkingDetails(
                    "Shopping RioMar",
                    "3",
                    31.00F,
                    Date(1271879579L),
                    Date(1272879779L),
                    "1",
                    "1"
                )
            )

            parkingsDetails.add(
                ParkingDetails(
                    "Shopping Tacaruna",
                    "4",
                    00.00F,
                    Date(1271279579L),
                    Date(1272479779L),
                    "3",
                    "1"
                )
            )

            parkingsDetails.add(
                ParkingDetails(
                    "Shopping Recife",
                    "5",
                    00.00F,
                    Date(1271878379L),
                    Date(1272879879L),
                    "3",
                    "2"
                )
            )

            return parkingsDetails
        }

        private fun initParkingList(): MutableList<Parking> {
            val parkings = mutableListOf<Parking>()

            parkings.add(
                Parking(
                    "1",
                    "Shopping RioMar",
                    15,
                    25.00F,
                    Date(1271878379L),
                    Date(1272879879L),
                    -8.0864269,
                    -34.8949109,
                    220.0F
                )
            )

            parkings.add(
                Parking(
                    "2",
                    "Shopping Recife",
                    20,
                    20.00F,
                    Date(1271878379L),
                    Date(1272879879L),
                    -8.119113,
                    -34.9071229,
                    230.0F
                )
            )

            parkings.add(
                Parking(
                    "3",
                    "Shopping Tacaruna",
                    20,
                    15.00F,
                    Date(1271878379L),
                    Date(1272879879L),
                    -8.0377284,
                    -34.8738497,
                    150.0F
                )
            )

            return parkings
        }

        private fun initUserList(): MutableList<User> {
            val users = mutableListOf<User>()

            users.add(
                User(
                    "1",
                    "Ítalo Paulino",
                    "irbp@cin.ufpe.br"
                )
            )

            users.add(
                User(
                    "2",
                    "Filipe Melo",
                    "fms6@cin.ufpe.br"
                )
            )

            return users
        }
    }

}