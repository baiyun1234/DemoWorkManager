package bai.yun.demoworkmanager

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Worker 定义工作单元，
 *
 * WorkRequest 则定义工作的运行方式和时间。任务可以是一次性的，也可以是周期性的。
 * -对于一次性 WorkRequest，请使用 OneTimeWorkRequest，
 * -对于周期性工作，请使用 PeriodicWorkRequest。
 * --WorkRequest 中还可以包含其他信息，例如任务在运行时应遵循的约束、工作输入、延迟，以及重试工作的退避时间政策
 */
class MainActivity : Activity(), LifecycleOwner {

    private val TAG = "MainActivity -> "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_simple_work.setOnClickListener {
            simpleWork()
        }

        btn_senior_work.setOnClickListener {
            seniorWork()
        }

    }

    override fun getLifecycle(): Lifecycle {
        return this.lifecycle
    }

    /**
     * 稍微高级一点的特性方法调用
     */
    @SuppressLint("RestrictedApi")
    private fun seniorWork() {

        //任务A
        val dataA = Data.Builder().putString("baiInputData", "我是data-A").build()
        val workRequestA = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(dataA)
            .build()
        //任务B
        val dataB = Data.Builder().putString("baiInputData", "我是data-B").build()
        val workRequestB = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .addTag("workRequestB")
            .setInputData(dataB)
            .build()
        //任务C
        val dataC = Data.Builder().putString("baiInputData", "我是data-C").build()
        val workRequestC = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(dataC)
            .build()
        //任务D
        val dataD = Data.Builder().putString("baiInputData", "我是data-D").build()
        val workRequestD = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(dataD)
            .build()
        //任务E
        val dataE = Data.Builder().putString("baiInputData", "我是data-E").build()
        val workRequestE = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(dataE)
            .build()

//      //任务链   先实行A，再执行B，再执行C
//        WorkManager.getInstance(this)
//            .beginWith(workRequestA)
//            .then(workRequestB)
//            .then(workRequestC)
//            .enqueue()
//      //任务链   执行A、B后，再执行C
//        WorkManager.getInstance(this)
//            .beginWith(listOf(workRequestA, workRequestB))
//            .then(workRequestC)
//            .enqueue()
        //任务链1和任务链2都执行完了，再执行任务B
        val workContinuation1 = WorkManager.getInstance(this).beginWith(workRequestA).then(workRequestB)
        val workContinuation2 = WorkManager.getInstance(this).beginWith(workRequestC).then(workRequestD)
        val workContinuation3 =
            WorkContinuation.combine(mutableListOf(workContinuation1, workContinuation2)).then(workRequestE)
        workContinuation3.enqueue()

//        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequestB.id)
//            .observe(this, Observer { workInfo ->
//                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
//                    Log.e(TAG, "Finish")
//                }
//            })
    }

    /**
     * 简单的调用
     */
    private fun simpleWork() {

        //入参数据
        val date = Data.Builder().putString("baiInputData", "baiTest--helloWorld").build()

        //约束条件
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)//联网时
            .setRequiresCharging(true)//充电条件
            .build()

        //任务
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(date)//添加入参
            .setConstraints(constraints)//添加约束条件
//            .setInitialDelay(10, TimeUnit.MINUTES)//添加延时
            .build()

        //执行任务
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
    }

}
