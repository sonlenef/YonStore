package tech.leson.yonstore.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil


class AppUtils {
    companion object {
        fun isPhoneNumberValid(phoneNumber: String): Boolean {
            val phoneUtil = PhoneNumberUtil.getInstance()
            var isValid = false
            try {
                val swissNumberProto = phoneUtil.parse(phoneNumber, "VN")
                isValid = phoneUtil.isValidNumber(swissNumberProto)
            } catch (e: NumberParseException) {
                Log.e("chitchat", "NumberParseException was thrown: $e")
            }
            return isValid
        }

        fun delayBtnOnClick(view: View) {
            view.isEnabled = false
            Handler(Looper.getMainLooper()).postDelayed({
                view.isEnabled = true
            }, 500)
        }
    }
}
