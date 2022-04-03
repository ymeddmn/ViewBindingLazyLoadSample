package com.ananananzhuo.viewbindinglazyloadsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.ananananzhuo.viewbindinglazyloadsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    val binding: ActivityMainBinding by binding(ActivityMainBinding::inflate)
    val binding: ActivityMainBinding by bindingByLazy(ActivityMainBinding::inflate)
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            binding.btn.text = "点击加一：${++count}"
        }
    }
}

//通过lazy关键字实现委托
fun <T : ViewBinding> AppCompatActivity.bindingByLazy(inflate: (LayoutInflater) -> T)= lazy {
     inflate(layoutInflater).apply {

    }
}
//通过lazy关键字实现委托


//通过继承Lazy实现委托
fun <T : ViewBinding> AppCompatActivity.binding(inflate: (LayoutInflater) -> T): Lazy<T> {
    return MainLazy(this, inflate)
}

class MainLazy<T : ViewBinding>(
    val activity: AppCompatActivity,
    val inflate: (LayoutInflater) -> T
) : Lazy<T> {
    var cache: T? = null
    override val value: T
        get() {
            if (cache == null) {
                cache = inflate(activity.layoutInflater)
            }
            return cache!!
        }

    override fun isInitialized(): Boolean = cache != null

}
//通过继承Lazy实现委托