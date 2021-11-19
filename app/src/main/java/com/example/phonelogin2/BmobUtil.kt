package com.example.phonelogin2

import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener

/**
 *@Description
 *@Author PC
 *@QQ 1578684787
 */
object BmobUtil {
    const val SUCCESS = 0
    const val FAILURE = 1
    //向服务器请求...发送验证码
    fun requestSMSCode(phone:String,callBack:(Int)->Unit){
        BmobSMS.requestSMSCode(phone,"",object :QueryListener<Int>(){
            override fun done(p0: Int?, p1: BmobException?) {
             if (p1 == null){
                 //短信发送成功
                 callBack(SUCCESS)
             }else{
                 //短信发送失败
                 callBack(FAILURE)
             }
            }
        })
    }

    //验证用户输入的验证码是否正确
    fun verifySMSCode(phone: String,code:String,callBack:(Int)->Unit){
        BmobSMS.verifySmsCode(phone,code,object :UpdateListener(){
            override fun done(p0: BmobException?) {
                if (p0==null){
                    //验证成功
                    callBack(SUCCESS)
                }else{
                    //验证失败
                    callBack(FAILURE)
                }
            }

        })
    }
}