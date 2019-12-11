package bai.yun.demoworkmanager;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 继承自Worker的自定义类
 * -后台任务类
 */
public class MyWorker extends Worker {
    private String TAG = "MyWorker -> ";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * doWork() 方法在 WorkManager 提供的后台线程上同步运行。
     *
     * @return 返回的 Result 会通知 WorkManager 任务是否：
     * -已成功完成：Result.success()
     * -已失败：Result.failure()
     * -需要稍后重试：Result.retry()
     */
    @NonNull
    @Override
    public Result doWork() {

        //接收传进来的参数
        String str = this.getInputData().getString("baiInputData");
        Log.d(TAG, "doWork, 传进来的参数：" + str);

        //做具体任务
        doSomeThing();

        return Result.success();
    }

    private void doSomeThing() {

    }

}
