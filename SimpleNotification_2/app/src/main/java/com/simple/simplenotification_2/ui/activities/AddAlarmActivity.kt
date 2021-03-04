package com.simple.simplenotification_2.ui.activities

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.simple.simplenotification_2.Alarm
import com.simple.simplenotification_2.data.AlarmModel
import com.simple.simplenotification_2.ui.receiver.AlarmReceiver
import com.simple.simplenotification_2.databinding.ActivityAddAlarmBinding
import java.util.*

class AddAlarmActivity : AppCompatActivity() {

    private lateinit var addAlarmBinding : ActivityAddAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addAlarmBinding = ActivityAddAlarmBinding.inflate(layoutInflater)
        setContentView(addAlarmBinding.root)


        addAlarmBinding.registerAdd.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendarDataSet(calendar)
            if(!checkCalendarData(calendar)) {
                workCalendar(calendar)
            }else{
                Toast.makeText(this, "현재 시간으로부터 과거로 맞추어져있습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        if(!addAlarmBinding.contentEditText.text.isEmpty()|| !addAlarmBinding.titleEditText.text.isEmpty()) {
            createAttentionDialog()
        }else {
            super.onBackPressed()
        }
    }

    private fun createAttentionDialog() {
            val alertDialog = AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("종료하시겠습니까?")
                    .setMessage("지금 종료하시면 작성된 내용이 유실됩니다. 종료하시겠습니까?")
                    .setPositiveButton("네") { dialog, which -> finish() }
                    .setNegativeButton("아니오") { dialog, which -> }
                    .create()

            alertDialog.show()
    }

    private fun workCalendar(calendar: Calendar) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)  // 1

        AlarmModel.listOfAlarm.add(Alarm("${addAlarmBinding.titleEditText.text}", "${addAlarmBinding.contentEditText.text}", calendar))

        val bundle = Bundle()
        bundle.putString("title", addAlarmBinding.titleEditText.text.toString())
        bundle.putString("content", addAlarmBinding.contentEditText.text.toString())

        intent.putExtras(bundle)

        val pendingIntent = PendingIntent.getBroadcast(     // 2
                this, AlarmModel.listOfAlarm.size, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val triggerTime = (calendar.timeInMillis)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        Toast.makeText(this, "알림이 등록되었습니다.", Toast.LENGTH_LONG).show()

        initLayout()
    }

    private fun initLayout(){
        val calendar = Calendar.getInstance()

        addAlarmBinding.titleEditText.text.clear()
        addAlarmBinding.contentEditText.text.clear()
        addAlarmBinding.datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        addAlarmBinding.timePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
        addAlarmBinding.timePicker.minute = calendar.get(Calendar.MINUTE)
    }

    private fun calendarDataSet(calendar : Calendar) {
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.YEAR, addAlarmBinding.datePicker.year)
        calendar.set(Calendar.MONTH, addAlarmBinding.datePicker.month)
        calendar.set(Calendar.DAY_OF_MONTH, addAlarmBinding.datePicker.dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, addAlarmBinding.timePicker.hour)
        calendar.set(Calendar.MINUTE, addAlarmBinding.timePicker.minute)
        calendar.set(Calendar.SECOND, 0)
    }
    private fun checkCalendarData(calendar : Calendar) : Boolean {
        if(Date(System.currentTimeMillis()).before(calendar.time)) {
            return false
        }
        return true
    }
}