package com.tripplannerai.repository.enroll;

import com.tripplannerai.dto.response.group.ApplyElement;
import com.tripplannerai.entity.enroll.Enroll;
import com.tripplannerai.entity.group.Group;
import com.tripplannerai.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByMemberAndGroupAndAccepted(Member member, Group group,boolean accepted);

    Optional<Enroll> findByMemberAndGroup(Member member, Group group);

    @Query("select new com.tripplannerai.dto.response.group.ApplyElement(e.enrollId,m.nickname,e.accepted) " +
            "from Enroll e " +
            "left join e.group g " +
            "left join e.member m where e.member.id != :memberId")
    List<ApplyElement> findByGroupExceptCreated(Group group, Long memberId);
}
