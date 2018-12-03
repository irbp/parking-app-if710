package br.ufpe.cin.if710.parkingapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import kotlinx.android.synthetic.main.parking_details_item.view.*

class ParkingListAdapter(context: Context): RecyclerView.Adapter<ParkingListAdapter.ParkingViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var parkingsDetails = emptyList<ParkingDetails>()

    inner class ParkingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parkingId: TextView = itemView.txtParkingId
        val amountSpent: TextView = itemView.txtAmountSpent
        val checkIn: TextView = itemView.txtCheckIn
        val checkOut: TextView = itemView.txtCheckOut
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingListAdapter.ParkingViewHolder {
        val itemView = inflater.inflate(R.layout.parking_details_item, parent, false)
        return ParkingViewHolder(itemView)
    }

    override fun getItemCount() = parkingsDetails.size

    override fun onBindViewHolder(holder: ParkingListAdapter.ParkingViewHolder, position: Int) {
        val current = parkingsDetails[position]
        holder.parkingId.text = current.id.toString()
        holder.amountSpent.text = current.amountSpent.toString()
        holder.checkIn.text = current.checkIn.toString()
        holder.checkOut.text = current.checkOut.toString()
    }

    internal fun setParkingsDetails(parkingsDetails: List<ParkingDetails>) {
        this.parkingsDetails = parkingsDetails
        notifyDataSetChanged()
    }
}