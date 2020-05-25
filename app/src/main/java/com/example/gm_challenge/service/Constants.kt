package com.example.gm_challenge.service

class Constants {
    interface ACTION {
        companion object {
            const val MAIN_ACTION = "com.example.gm_challenge.action.main"
            const val INIT_ACTION = "com.example.gm_challenge.action.init"
            const val PREV_ACTION = "com.example.gm_challenge.action.prev"
            const val PLAY_ACTION = "com.example.gm_challenge.action.play"
            const val PAUSE_ACTION = "com.example.gm_challenge.action.pause"
            const val NEXT_ACTION = "com.example.gm_challenge.action.next"
            const val STARTFOREGROUND_ACTION =
                "com.example.gm_challenge.action.startforeground"
            const val STOPFOREGROUND_ACTION =
                "com.example.gm_challenge.action.stopforeground"
        }
    }

    interface NOTIFICATION_ID {
        companion object {
            const val FOREGROUND_SERVICE = 1
        }
    }
}