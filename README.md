
>> 关注公众号安安安安卓，免费学知识git commit -m "first commit"

本文我们使用继承 Lazy 和 lazy 关键字实现 Viewbinding 的注入

## 继承 Lazy 方式

### 实现代码

```kt
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
```

### 使用方式

```kt
val binding: ActivityMainBinding by binding(ActivityMainBinding::inflate)
```

## lazy 关键字方式

### 实现代码

```kt
fun <T : ViewBinding> AppCompatActivity.bindingByLazy(inflate: (LayoutInflater) -> T)= lazy {
     inflate(layoutInflater).apply {

    }
}
```

### 使用方式

```kt
val binding: ActivityMainBinding by bindingByLazy(ActivityMainBinding::inflate)
```
