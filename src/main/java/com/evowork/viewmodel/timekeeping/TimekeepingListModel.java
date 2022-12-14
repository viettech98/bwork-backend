package com.evowork.viewmodel.timekeeping;

import com.evowork.viewmodel.timekeeping.wokingTime.WorkingTimeConfigModel;
import com.evowork.viewmodel.user.UserDTO;

import java.util.List;

public class TimekeepingListModel {

    private UserDTO user;
    private List<TimekeepingRecordModel> timekeepingRecord;
    private WorkingTimeConfigModel workingTimeConfig;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<TimekeepingRecordModel> getTimekeepingRecord() {
        return timekeepingRecord;
    }

    public void setTimekeepingRecord(List<TimekeepingRecordModel> timekeepingRecord) {
        this.timekeepingRecord = timekeepingRecord;
    }

    public WorkingTimeConfigModel getWorkingTimeConfig() {
        return workingTimeConfig;
    }

    public void setWorkingTimeConfig(WorkingTimeConfigModel workingTimeConfig) {
        this.workingTimeConfig = workingTimeConfig;
    }
}
