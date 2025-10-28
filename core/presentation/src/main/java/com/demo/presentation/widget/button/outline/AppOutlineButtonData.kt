package com.demo.presentation.widget.button.outline

import com.demo.presentation.R


sealed class AppOutlineButtonData {
    data object RecoveryEmail : AppOutlineButtonData()
    data object RecoveryPhone : AppOutlineButtonData()

    val icon: Int
        get() = when (this) {
            RecoveryEmail -> R.drawable.ic_recovery_email
            RecoveryPhone -> R.drawable.ic_recovery_phone
        }

    val text: Int
        get() = when (this) {
            RecoveryEmail -> R.string.recover_via_email
            RecoveryPhone -> R.string.recover_via_phone_number
        }

}
