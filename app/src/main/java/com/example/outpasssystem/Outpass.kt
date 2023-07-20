package com.example.outpasssystem

data class Outpass(val leave: String? = null, val arrive: String? = null,
                   val transport: String? = null, val purposeOfVisit: String? = null, val status: String? = "Pending"){

}
