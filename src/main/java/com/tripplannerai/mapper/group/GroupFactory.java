package com.tripplannerai.mapper.group;

import com.tripplannerai.dto.request.group.AddGroupRequest;
import com.tripplannerai.entity.group.Group;
import com.tripplannerai.entity.member.Member;

public class GroupFactory {
    public static Group from(AddGroupRequest addGroupRequest, Member member) {
        return Group.builder()
                .title(addGroupRequest.getTitle())
                .description(addGroupRequest.getDescription())
                .startDate(addGroupRequest.getStartDate())
                .endDate(addGroupRequest.getEndDate())
                .member(member)
                .count(1)
                .maxCount(addGroupRequest.getMaxCount())
                .build();
    }
}
