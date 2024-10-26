package com.learningpath;

import com.learningpath.activities.Activity;
import com.learningpath.activities.ActivityStatus;
import com.learningpath.users.Student;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Progress implements Serializable {
    private Student student;
    private LearningPath learningPath;
    private Date startDate;
    private Date completionDate;
    private Map<Activity, ActivityStatus> activityStatuses;

    public Progress(Student student, LearningPath learningPath) {
        this.student = student;
        this.learningPath = learningPath;
        this.startDate = new Date();
        this.activityStatuses = new HashMap<>();
        for (Activity activity : learningPath.getActivities()) {
            activityStatuses.put(activity, ActivityStatus.PENDING);
        }
    }

    public void updateActivityStatus(Activity activity, ActivityStatus status) {
        activityStatuses.put(activity, status);
    }

    public double calculateCompletionPercentage() {
        long totalMandatory = learningPath.getActivities().stream().filter(Activity::isMandatory).count();
        long completedMandatory = activityStatuses.entrySet().stream()
                .filter(e -> e.getValue() == ActivityStatus.COMPLETED && e.getKey().isMandatory())
                .count();
        return (double) completedMandatory / totalMandatory * 100;
    }
}

