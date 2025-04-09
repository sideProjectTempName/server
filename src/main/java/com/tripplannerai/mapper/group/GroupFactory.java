package com.tripplannerai.mapper.group;

import com.tripplannerai.dto.request.group.AddGroupRequest;
import com.tripplannerai.entity.group.Group;

public class GroupFactory {
    public static Group from(AddGroupRequest addGroupRequest) {
        return Group.builder()
                .name(addGroupRequest.getName())
                .build();
    }
}
