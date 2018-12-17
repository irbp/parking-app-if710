package br.ufpe.cin.if710.parkingapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import br.ufpe.cin.if710.parkingapp.viewmodel.ParkingListViewModel
import kotlinx.android.synthetic.main.activity_parking_list.*

private lateinit var viewModel: ParkingListViewModel

class ParkingListActivity : AppCompatActivity() {

    private lateinit var viewAdapter: ParkingListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_list)
        setSupportActionBar(toolbar_parking_list)

        viewAdapter = ParkingListAdapter(this)
        viewManager = LinearLayoutManager(this)

        recyclerview.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        viewModel = ViewModelProviders.of(this).get(ParkingListViewModel::class.java)
        viewModel.getUserParkings(1)
        viewModel.getParkingDetailsList().observe(this, Observer {
            it?.let { populateList(it) }
        })

        btn_reg_parking.setOnClickListener {
            startActivity(Intent(this@ParkingListActivity, SuggestParkingActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.preferences -> {
                // TODO ir para o SharedPreference
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun populateList(parkingDetailsList: List<ParkingDetails>) {
        viewAdapter.setParkingsDetails(parkingDetailsList)
    }
}
