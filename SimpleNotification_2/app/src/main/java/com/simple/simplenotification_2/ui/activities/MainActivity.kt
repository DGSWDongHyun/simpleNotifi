package com.simple.simplenotification_2.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.simple.simplenotification_2.Alarm
import com.simple.simplenotification_2.ui.adapter.AlarmAdapter
import com.simple.simplenotification_2.data.AlarmModel
import com.simple.simplenotification_2.databinding.ActivityMainBinding
import com.simple.simplenotification_2.ui.adapter.listener.onClickItemListener

class MainActivity : AppCompatActivity() {

    private lateinit var alarmAdapter : AlarmAdapter
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        alarmAdapter = AlarmAdapter(this, object : onClickItemListener {
            override fun onClickItemListener(position: Int?, data: Alarm) {

            }
        })

        mainBinding.recyclerAlarm.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerAlarm.adapter = alarmAdapter

        mainBinding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddAlarmActivity::class.java))
        }
    }

    override fun onResume() {
        alarmAdapter.setData(AlarmModel.listOfAlarm)
        super.onResume()
    }
}