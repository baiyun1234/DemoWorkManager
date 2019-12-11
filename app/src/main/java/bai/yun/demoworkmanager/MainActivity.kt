package bai.yun.demoworkmanager

import android.app.Activity
import android.os.Bundle
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
class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_work.setOnClickListener {
            startWork()
        }

    }

    private fun startWork() {

//        UUID id,
//        Data inputData,
//        Collection<String> tags,
//        RuntimeExtras runtimeExtras,
//        @IntRange(from = 0) int runAttemptCount,
//        Executor backgroundExecutor,
//        TaskExecutor workTaskExecutor,
//        WorkerFactory workerFactory

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
