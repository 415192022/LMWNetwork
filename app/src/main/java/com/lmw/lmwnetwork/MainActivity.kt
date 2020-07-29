package com.lmw.lmwnetwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lmw.lmwnetwork.net.*
import com.lmw.lmwnetwork.net.authorization.PersistentAuthor
import com.lmw.lmwnetwork.net.base.observer.MyObserver
import com.lmw.lmwnetwork.net.base.observer.TencentObserver
import com.lmw.lmwnetwork.net.base.retrofit.Test1NetworkApi
import com.lmw.lmwnetwork.net.base.retrofit.Test2NetworkApi
import com.lmw.lmwnetwork.net.pojo.bo.NewsListBo
import com.lmw.lmwnetwork.net.pojo.no.BaseNo
import com.lmw.lmwnetwork.net.pojo.no.UserLoginNo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRequest?.setOnClickListener {
            Test1NetworkApi.getService(NewsApiInterface::class.java)
                .getNewsList("1", "1", "1")
                .compose(
                    Test1NetworkApi.getInstance()
                        .applySchedulers(object :
                            TencentObserver<NewsListBo>() {

                            override fun onSuccess(t: NewsListBo?) {
                            }

                            override fun onFail(t: NewsListBo?, e: Throwable?) {
                            }
                        })
                )
        }

        tvRequest2?.setOnClickListener {
            Test2NetworkApi.getService(NewsApiInterface::class.java)
                .creationDelete("1")
                .compose(
                    Test2NetworkApi.getInstance().applySchedulers(object : MyObserver<BaseNo>() {

                        override fun onSuccess(t: BaseNo?) {
                        }

                        override fun onFail(t: BaseNo?, e: Throwable?) {
                        }
                    })
                )
        }

        tvLogin?.setOnClickListener {
            val map = HashMap<String, String>()
            map["mobileType"] = "+86"
            map["mobilePhone"] = "18100000003"
            map["smsCode"] = "191231"
            map["smsType"] = "2"
            val channel: String? = "A1"
            map["channel"] = channel ?: ""
            Test2NetworkApi.getService(NewsApiInterface::class.java)
                .userSmsLogin(map)
                .compose(
                    Test2NetworkApi.getInstance()
                        .applySchedulers(object : MyObserver<UserLoginNo>() {
                            override fun onSuccess(t: UserLoginNo?) {
                                PersistentAuthor.getInstance().clear()
                                PersistentAuthor.getInstance()
                                    .saveAuthor(t?.data?.token, t?.data?.userId ?: "")
                            }

                            override fun onFail(t: UserLoginNo?, e: Throwable?) {
                            }
                        })
                )
        }

        tvExitLogin?.setOnClickListener {
            PersistentAuthor.getInstance().clear()
        }
    }
}
