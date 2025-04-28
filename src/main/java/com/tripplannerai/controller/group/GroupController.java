package com.tripplannerai.controller.group;

import com.tripplannerai.common.annotation.Id;
import com.tripplannerai.dto.request.group.AddGroupRequest;
import com.tripplannerai.dto.response.group.DonateResponse;
import com.tripplannerai.dto.response.group.AddGroupResponse;
import com.tripplannerai.dto.response.group.ApplyGroupResponse;
import com.tripplannerai.dto.response.group.LeaveGroupResponse;
import com.tripplannerai.dto.response.group.ParticipateGroupResponse;
import com.tripplannerai.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<AddGroupResponse> addGroup(@RequestBody AddGroupRequest addGroupRequest,@Id Long id){
        AddGroupResponse addGroupResponse = groupService.addGroup(addGroupRequest,id);
        return new ResponseEntity<>(addGroupResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{groupId}/participate")
    public ResponseEntity<ParticipateGroupResponse> participateGroup(@PathVariable Long groupId, @Id Long id){
        ParticipateGroupResponse participateGroupResponse = groupService.participateGroup(groupId,id);
        return new ResponseEntity<>(participateGroupResponse, HttpStatus.OK);
    }
    @PutMapping("/{groupId}/permit")
    public ResponseEntity<ParticipateGroupResponse> permitGroup(@PathVariable Long groupId, @Id Long id,@RequestParam Long enrollId){
        ParticipateGroupResponse participateGroupResponse = groupService.permitGroup(groupId,id,enrollId);
        return new ResponseEntity<>(participateGroupResponse, HttpStatus.OK);
    }
    @GetMapping("/{groupId}/apply")
    public ResponseEntity<?> applyGroup(@PathVariable Long groupId, @Id Long id){
        ApplyGroupResponse applyGroupResponse = groupService.applyGroup(groupId,id);
        return new ResponseEntity<>(applyGroupResponse, HttpStatus.OK);
    }

    @PutMapping("/{groupId}/leave")
    public ResponseEntity<LeaveGroupResponse> leaveGroup(@PathVariable Long groupId, @Id Long id){
        LeaveGroupResponse participateGroupResponse = groupService.leaveGroup(groupId,id);
        return new ResponseEntity<>(participateGroupResponse, HttpStatus.OK);
    }
    @PutMapping("/{groupId}/donate")
    public ResponseEntity<DonateResponse> donateGroup(@PathVariable Long groupId, @RequestParam Integer point, @Id Long id){
        DonateResponse donateResponse = groupService.donateGroup(groupId,point,id);
        return new ResponseEntity<>(donateResponse, HttpStatus.OK);
    }

}
