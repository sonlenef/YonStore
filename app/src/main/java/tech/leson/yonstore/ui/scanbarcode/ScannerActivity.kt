package tech.leson.yonstore.ui.scanbarcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerActivity : Activity(), ZXingScannerView.ResultHandler {

    companion object {
        const val RESULT_CODE = 7621
        const val BAR_CODE = "BAR_CODE"
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ScannerActivity::class.java).also { instance = it }
        }
    }

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)
        val formats: MutableList<BarcodeFormat> = ArrayList()
        formats.add(BarcodeFormat.CODE_128)
        mScannerView!!.setFormats(formats)
        setContentView(mScannerView)
    }

    override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.let {
            intent.putExtra(BAR_CODE, it.text)
            setResult(RESULT_CODE, intent)
            finish()
        }
        mScannerView!!.resumeCameraPreview(this)
    }
}
