package flicker.covoid.coronavirus.utils;

import io.reactivex.Scheduler;

public interface IScheduler {
    Scheduler mainThread();

    Scheduler backgroundThread();
}
