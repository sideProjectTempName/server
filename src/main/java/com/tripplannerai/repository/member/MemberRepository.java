package com.tripplannerai.repository.member;

import com.tripplannerai.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m left join fetch m.image i")
    Optional<Member> findByEmail(String email);
}
