package com.example.traning.model.remote

import android.util.SparseIntArray
import androidx.annotation.StringRes
import com.example.traning.R

object ApiErrorCode {
    private val sparseCode: SparseIntArray = SparseIntArray()

    init {
        sparseCode.put(1001, R.string.error_message_1001)
    }

    @StringRes
    fun parseMsg(code: Int): Int {
        val msgId = sparseCode.get(code)
        return if (msgId > 0) {
            msgId
        } else {
            R.string.error_message_1000
        }
    }
}