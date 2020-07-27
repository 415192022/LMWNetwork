package com.lmw.lmwnetwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lmw.lmwnetwork.lib.TecentNetworkApi
import com.lmw.lmwnetwork.lib.observer.BaseObserver
import com.lmw.lmwnetwork.net.NewsApiInterface
import com.lmw.lmwnetwork.net.NewsListBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRequest?.setOnClickListener {
            TecentNetworkApi.getService(NewsApiInterface::class.java)
                .getNewsList("mChannelId", "mChannelName", "1")
                .compose(
                    TecentNetworkApi.getInstance()
                        .applySchedulers(object :BaseObserver<NewsListBean>(){
                            override fun onNext(t: NewsListBean) {

                            }

                            override fun onError(e: Throwable) {
                            }
                        })
                )
        }
    }
}
