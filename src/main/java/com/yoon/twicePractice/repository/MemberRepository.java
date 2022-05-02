package com.yoon.twicePractice.repository;

import com.yoon.twicePractice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
