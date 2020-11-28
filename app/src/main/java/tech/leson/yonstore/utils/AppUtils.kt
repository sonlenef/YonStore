package tech.leson.yonstore.utils

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
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
                Log.e("yonstore", "NumberParseException was thrown: $e")
            }
            return isValid
        }

        fun delayBtnOnClick(view: View) {
            view.isEnabled = false
            Handler(Looper.getMainLooper()).postDelayed({
                view.isEnabled = true
            }, 500)
        }

        fun setColorFilter(@NonNull drawable: Drawable, @ColorInt color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
            } else {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
        }
    }
}
