package com.example.kitchenstreak

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.androidstudy.networkmanager.Tovuti
import com.example.kitchenstreak.databinding.ActivityMainBinding

@SuppressLint("SetJavaScriptEnabled")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var workerHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tovuti.from(this@MainActivity).monitor { _, isConnected, _ ->
            if (isConnected) {
                setUpMyWebView()
            } else {
                Constant.showToast(this, "Check your network and try later")
            }
        }

        binding.menuBtn.setOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_rate_us -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW, Uri
                                    .parse("market://details?id=" + Constant.applicationId)
                            )
                        )
                    } catch (ex: Exception) {
                        ex.printStackTrace()

                    }
                }
                R.id.nav_contact_us -> {
                    FeedbackUtils.startFeedbackEmail(this@MainActivity)
                }

                R.id.nav_share_app -> {

                    try {
                        val i = Intent(Intent.ACTION_SEND)
                        i.type = "text/plain"
                        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                        var sAux = "\nLet me recommend you this application\n\n"
                        sAux = """
                ${sAux}https://play.google.com/store/apps/details?id=${Constant.applicationId}
                """.trimIndent()
                        i.putExtra(Intent.EXTRA_TEXT, sAux)
                        startActivity(
                            Intent.createChooser(
                                i,
                                resources.getString(R.string.choose_one)
                            )
                        )
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }

                }

                R.id.nav_privacy -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://coreelgo.blogspot.com/privacy-policy")
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                R.id.nav_more_app -> {

                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW, Uri
                                    .parse("market://details?id=" + Constant.applicationId)
                            )
                        )
                    } catch (ex: Exception) {
                        ex.printStackTrace()

                    }

                }
            }
            true
        }

        workerHandler.postDelayed({
            binding.splasBg.visibility = View.GONE
        }, 2000)
    }

    private fun setUpMyWebView() {
        binding.webview.webViewClient = WebViewClient()
        binding.webview.loadUrl("https://kitchenstreak.com/")

        val webSettings = binding.webview.settings
        webSettings.javaScriptEnabled = true
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else {
            if (binding.webview.canGoBack()) {
                binding.webview.goBack()
            } else {
                super.onBackPressed()
            }
        }


    }
}