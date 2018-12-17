package br.ufpe.cin.if710.parkingapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
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
        val parkingName: TextView = itemView.txtParkingName
        val amountSpent: TextView = itemView.txtAmountSpent
        val checkOut: TextView = itemView.txtCheckOut
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingListAdapter.ParkingViewHolder {
        val itemView = inflater.inflate(R.layout.parking_details_item, parent, false)
        return ParkingViewHolder(itemView)
    }

    override fun getItemCount() = parkingsDetails.size

    override fun onBindViewHolder(holder: ParkingListAdapter.ParkingViewHolder, position: Int) {
        val current = parkingsDetails[position]
        holder.parkingName.text = current.name
        holder.amountSpent.text = String.format("R$ %.2f", current.amountSpent)
        holder.checkOut.text = DateFormat.format("dd/MM/yy hh:mm:ss", current.checkOut).toString()
    }

    internal fun setParkingsDetails(parkingsDetails: List<ParkingDetails>) {
        this.parkingsDetails = parkingsDetails
        notifyDataSetChanged()
    }
}