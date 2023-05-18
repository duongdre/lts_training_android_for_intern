package com.example.traning.dependency

import android.annotation.SuppressLint
import android.app.Application
import com.dds.istation.data.UserManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

class Authenticator(private val app: Application) : Authenticator {

    lateinit var apiService: ApiServiceAuth

    @SuppressLint("CheckResult")
    override fun authenticate(route: Route?, response: Response?): Request? {
        Timber.d("authenticate() called [401 error]")

        response?.let {
            Timber.d("Failed response count: ${responseCount(response)}")
            if (responseCount(response) >= 2) {
                return null
            }
        }

        var obtained = false

        /*apiService.refreshToken(UserManager.getAccessToken(app))
            .subscribeWith(
                { result ->
                    with(UserManager) {
                        setAccessToken(app, result.data?.token ?: "")
                    }

                    obtained = true
                },
                { httpError ->
                    Timber.d("Http Error: ${httpError.code}")
                    obtained = false
                },
                {
                    Timber.d("No network Error")
                    obtained = false
                }
            )*/

        if (obtained) return response?.let {
            response.request()
                .newBuilder()
                .addHeader("Authorization", UserManager.getAccessToken(app))
                .build()
        }

        //EventBus.getDefault().post(AuthFail())
        return null
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var failed: Response? = response

        while (failed?.priorResponse() != null) {
            result++
            failed = failed.priorResponse()
        }

        return result
    }
}