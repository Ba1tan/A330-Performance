package com.example.a330performance

import Aircraft
import Info
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson

class CalcFragment : Fragment() {
    private lateinit var userFlaps : EditText
    private lateinit var userGW : EditText
    private lateinit var userQNH : EditText
    private lateinit var userCG : EditText
    private lateinit var userTemperature : EditText
    private lateinit var userAntiIce : EditText
    private lateinit var userAirCond : EditText
    private lateinit var userRunawayCond : EditText
    private lateinit var userElevation : EditText
    private lateinit var resV2 : TextView
    private lateinit var resV1 : TextView
    private lateinit var resVR : TextView
    private lateinit var resTrim : TextView
    private lateinit var resFLEX : TextView
    private val aircraftV = AircraftV()
    private val calculation = Calculation()
    private val gson = Gson()

    private fun readJsonFromAssets(context: CalcFragment, fileName: String): String {
        return context.resources.assets.open(fileName).bufferedReader().use { it.readText() }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val view : View = inflater.inflate(R.layout.fragment_calc, container, false)
        userFlaps = view.findViewById(R.id.editTextFlaps)
        userGW = view.findViewById(R.id.editTextGW)
        userQNH = view.findViewById(R.id.editTextQNH)
        userCG = view.findViewById(R.id.editTextCG)
        userTemperature = view.findViewById(R.id.editTextTemperature)
        userAntiIce = view.findViewById(R.id.editTextAntiIce)
        userRunawayCond = view.findViewById(R.id.editTextRunawayCond)
        userElevation = view.findViewById(R.id.editTextRunawayElev)
        userAirCond = view.findViewById(R.id.editTextAirCond)
        resV2 = view.findViewById(R.id.textV2)
        resV1 = view.findViewById(R.id.textV1)
        resVR = view.findViewById(R.id.textVR)
        resFLEX = view.findViewById(R.id.textFLEX)
        resTrim = view.findViewById(R.id.textTHS)
        val calculateButton = view.findViewById<Button>(R.id.calculateButton)
        val jsonString = readJsonFromAssets(this, "index.json")
        val aircraft = gson.fromJson(jsonString, Aircraft::class.java)
        calculateButton.setOnClickListener {
            val inputData = UserData(
                userFlaps.text.toString(),
                userGW.text.toString().toInt(),
                userCG.text.toString().toDouble(),
                userQNH.text.toString().toInt(),
                userTemperature.text.toString().toInt(),
                userAntiIce.text.toString(),
                userAirCond.text.toString(),
                userRunawayCond.text.toString(),
                userElevation.text.toString().toInt()
            )
            val data = mapOf(
                "type" to aircraft.type,
                "info" to aircraft.info,
                "flightDetails" to mapOf(
                    "flapsConfiguration" to inputData.flaps,
                    "grossWeight" to inputData.grossWeight,
                    "QNH" to inputData.qnh,
                    "runwayCondition" to inputData.runawayCond,
                    "antiIce" to inputData.antiIce,
                    "airConditioning" to inputData.airCond
                ),
                "airport" to mapOf(
                    "elevation" to inputData.runawayElev,
                    "temperature" to inputData.temperature
                )
            )
            val v2 = calculation.calculateV2(data)
            val aircraftDetail = data["info"] as Info
            val v1 = calculation.calculateV1(v2, aircraftDetail.speeds.VSpeeds.v1factor)
            val vr = calculation.calculateVR(v2, aircraftDetail.speeds.VSpeeds.vrfactor)
            val trimInfo = aircraftDetail.trim
            val myTrim = aircraftV.calculateTrim(
                trimInfo.minCG.toDouble(),
                trimInfo.maxCG.toDouble(),
                trimInfo.max,
                trimInfo.min,
                userCG.text.toString().toDouble(),
                trimInfo.interpolation.toDouble()
            )
            val flexTemp = calculation.calculateFlexTemp(data)

            resV1.text = v1.toString()
            resVR.text = vr.toString()
            resV2.text = v2.toString()
            resTrim.text = myTrim
            resFLEX.text = flexTemp.toString()
        }
        return view
    }

}