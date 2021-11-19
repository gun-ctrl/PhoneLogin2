package com.example.phonelogin2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //用于判断是否分割
    private var shouldAutoSplit = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPhoneEditText.addTextChangedListener(object :LoginTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                //设置登录按钮是否可以点击
                mLoginBotton.isEnabled = s.toString().length==13

                //如果shouldAutoSplit为false 那么整个方法就结束了 不会再执行后面的方法了
                if (!shouldAutoSplit) return
                //调整号码显示格式 191 1206 9048
                s.toString().length.also {
                    if (it == 3||it == 8){
                        s?.append(' ')
                    }
                }
            }

            /**
             * 通过测试打印值的变化可以知道：
             * 当count=1，before=0时 正在进行输入操作
             * 当count=0，before=1时 正在进行删除操作
             * 所以可以通过count或者before的值来设置shouldAutoSplit的值
             * 在afterTextChanged方法中 就通过shouldAutoSplit的值来判断是输入还是删除操作
             */

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shouldAutoSplit = count==1
            }
        })
        //按钮点击事件
        mLoginBotton.setOnClickListener{
            Intent().apply {
                //跳转方向
                setClass(this@MainActivity,VerifyActivity::class.java)
                //配置跳转的数据
                putExtra("phone",getPhoneNumber(mPhoneEditText.text))
                //启动
                startActivity(this)
            }
        }
    }

    /**
     * 为什么需要创建一个新的对象 来进行操作？
     * 当输入数据进行格式化后 进行跳转 再次返回 那么数据就不再是格式化数据 不满足跳转条件
     * 所以在将格式化转化为正常数据的方法中 选哟创建一个新的对象来完成该功能
     * 而SpannableStringBuilder是Editable的实现类，有其所有方法，所以新对象就是该类型
     */
    //将格式化的数据转化为正常的数据
    private fun getPhoneNumber(editable: Editable):String{
        //创建一个新的对象 用于操作editable对象里面的内容
        SpannableStringBuilder(editable.toString()).apply {
            delete(3,4)
            delete(7,8)
            return this.toString()
        }
    }
}