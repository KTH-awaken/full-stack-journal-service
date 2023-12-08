package com.example.journalservice.Core;

import com.example.journalservice.Entities.Condition;
import com.example.journalservice.View.ViewModels.ConditionVm;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static ConditionVm toConditionVm(Condition condition){
        return  ConditionVm.builder()
                .id(condition.getId())
                .timestamp(condition.getTimestamp())
                .diagnosis(condition.getDiagnosis())
                .build();
    }

    public static List<ConditionVm> toConditionVms(List<Condition> conditions){
        List<ConditionVm> conditionVms = new ArrayList<>();
        for(Condition condition : conditions){
            conditionVms.add(Mapper.toConditionVm(condition));
        }
        return conditionVms;
    }

}
