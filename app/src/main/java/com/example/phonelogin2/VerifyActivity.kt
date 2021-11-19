package com.example.phonelogin2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyActivity : AppCompatActivity() {
    //保存所有显示验证码的textView
    private val verifyViews:Array<TextView> by lazy {
        arrayOf(mv1,mv2,mv3,mv4,mv5,mv6)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        //获取数据
        intent.getStringExtra("phone").also {
            //显示号码
            mPhone.text = it

        }
        //监听文本框的内容改变事件
        mOrigin.addTextChangedListener(object :LoginTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //将输入的内容拆分到每一个textView中
                //获取i对应的textView
                for ((i,item) in s?.withIndex()!!){
                    verifyViews[i].text = item.toString()
                }
                //如果位数小于6个 后面的显示的都为空
                for (i in s.length..5){
                    verifyViews[i].text =""
                }
                if (s.length==6){
                    BmobUtil.verifySMSCode(mPhone.text.toString(),s.toString()){
                        if (it==BmobUtil.SUCCESS){
                            //模拟验证成功 跳转到主页
                            startActivity(Intent(this@VerifyActivity,HomeActivity::class.java))
                        }else{
                            Toast.makeText(this@VerifyActivity,"验证码错误",Toast.LENGTH_LONG)
                            mOrigin.text.clear()
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        BmobUtil.requestSMSCode(mPhone.text.toString()){
            if (it==BmobUtil.SUCCESS){
                Toast.makeText(this,"短信请求成功",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"短信请求失败",Toast.LENGTH_LONG).show()
            }
        }
    }
}