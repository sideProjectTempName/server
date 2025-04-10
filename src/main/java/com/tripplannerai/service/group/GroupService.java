package com.tripplannerai.service.group;

import com.tripplannerai.dto.request.group.AddGroupRequest;
import com.tripplannerai.dto.response.group.DonateResponse;
import com.tripplannerai.dto.response.group.*;
import com.tripplannerai.entity.enroll.Enroll;
import com.tripplannerai.entity.group.Group;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.exception.enroll.NotFoundEnrollException;
import com.tripplannerai.exception.group.AlreadyParticipateException;
import com.tripplannerai.exception.group.InvalidPointException;
import com.tripplannerai.exception.group.NotFoundGroupException;
import com.tripplannerai.exception.group.NotParticipateException;
import com.tripplannerai.exception.member.NotAuthorizeException;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.mapper.enroll.EnrollFactory;
import com.tripplannerai.mapper.group.GroupFactory;
import com.tripplannerai.repository.enroll.EnrollRepository;
import com.tripplannerai.repository.group.GroupRepository;
import com.tripplannerai.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.tripplannerai.util.ConstClass.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final EnrollRepository enrollRepository;
    public AddGroupResponse addGroup(AddGroupRequest addGroupRequest, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = GroupFactory.from(addGroupRequest,member);
        Enroll enroll = EnrollFactory.from(member, group, true);
        groupRepository.save(group);
        enrollRepository.save(enroll);
        return AddGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,group);
    }

    public ParticipateGroupResponse participateGroup(Long groupId, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupException("not found group"));
        Optional<Enroll> optionalEnroll = enrollRepository.findByMemberAndGroup(member, group);
        if(optionalEnroll.isPresent()) {
            throw new AlreadyParticipateException("already participate");
        }
        Enroll enroll = EnrollFactory.from(member, group, false);
        enrollRepository.save(enroll);
        return ParticipateGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public LeaveGroupResponse leaveGroup(Long groupId, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupException("not found group"));
        Enroll enroll = enrollRepository.findByMemberAndGroupAndAccepted(member, group, true)
                .orElseThrow(() -> new NotParticipateException("member didn't participate group"));
        enrollRepository.delete(enroll);
        return LeaveGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public DonateResponse donateGroup(Long groupId, Integer point, Long id) {
        if(!checkPoint(point)) throw new InvalidPointException("point have to positive value!!");
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupException("not found group"));
        enrollRepository.findByMemberAndGroupAndAccepted(member, group, true)
                .orElseThrow(() -> new NotParticipateException("member didn't participate group"));
        group.changePoint(point);
        return DonateResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    private boolean checkPoint(Integer point) {
        return point>0;
    }

    public ApplyGroupResponse applyGroup(Long groupId, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Long memberId = member.getId();
        boolean authorize = group.getMember().getId() == memberId;

        if(!authorize) throw new NotAuthorizeException("not authorized");
        List<ApplyElement> applyElements = enrollRepository.findByGroupExceptCreated(group,memberId);
        return ApplyGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,applyElements);
    }

    public ParticipateGroupResponse permitGroup(Long groupId, Long id, Long enrollId) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundMemberException("not found Member!!"));
        Long memberId = member.getId();
        boolean authorize = group.getMember().getId() == memberId;

        if(!authorize) throw new NotAuthorizeException("not authorized");
        Enroll enroll = enrollRepository.findById(enrollId).orElseThrow(() -> new NotFoundEnrollException("not found Enroll!!"));
        boolean accepted = enroll.isAccepted();
        if(accepted) throw new AlreadyParticipateException("already participate");
        enroll.changeAccepted(true);
        return ParticipateGroupResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }


    //TODO : Group 여행일정 짜기
}
