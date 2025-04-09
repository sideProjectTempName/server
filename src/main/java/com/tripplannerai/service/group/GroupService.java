package com.tripplannerai.service.group;

import com.tripplannerai.dto.request.group.AddGroupRequest;
import com.tripplannerai.dto.response.DonateResponse;
import com.tripplannerai.dto.response.group.AddGroupResponse;
import com.tripplannerai.dto.response.group.LeaveGroupResponse;
import com.tripplannerai.dto.response.group.ParticipateGroupResponse;
import com.tripplannerai.entity.group.Group;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.exception.group.AlreadyParticipateException;
import com.tripplannerai.exception.group.InvalidPointException;
import com.tripplannerai.exception.group.NotFoundGroupException;
import com.tripplannerai.exception.group.NotParticipateException;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.mapper.group.GroupFactory;
import com.tripplannerai.repository.group.GroupRepository;
import com.tripplannerai.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tripplannerai.util.ConstClass.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    public AddGroupResponse addGroup(AddGroupRequest addGroupRequest,String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = GroupFactory.from(addGroupRequest);
        groupRepository.save(group);
        member.setGroup(group);
        return AddGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,group);
    }

    public ParticipateGroupResponse participateGroup(Long groupId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupException("not found group"));
        boolean participated = member.getGroup().getGroupId().equals(groupId);
        if(participated) throw new AlreadyParticipateException("already Participate this group!!");
        member.setGroup(group);
        return ParticipateGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public LeaveGroupResponse leaveGroup(Long groupId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupException("not found group"));
        boolean participated = member.getGroup().getGroupId().equals(groupId);
        if(!participated) throw new NotParticipateException("member didn't participate group");
        member.setGroup(null);
        return LeaveGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public DonateResponse donateGroup(Long groupId, Integer point, String email) {
        if(!checkPoint(point)) throw new InvalidPointException("point have to positive value!!");
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupException("not found group"));
        boolean participated = member.getGroup().getGroupId().equals(groupId);
        if(!participated) throw new NotParticipateException("member didn't participate group");
        group.changePoint(point);
        return DonateResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    private boolean checkPoint(Integer point) {
        return point>0;
    }


    //TODO : Group 여행일정 짜기
}
