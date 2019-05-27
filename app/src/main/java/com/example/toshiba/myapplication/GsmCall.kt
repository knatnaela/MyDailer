package com.example.toshiba.myapplication

data class GsmCall(val status: Status, val displayName: String?) {

  enum class Status {
    CONNECTING,
    DIALING,
    RINGING,
    ACTIVE,
    DISCONNECTED,
    UNKNOWN
  }
}