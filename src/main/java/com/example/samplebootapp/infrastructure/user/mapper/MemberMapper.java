package com.example.samplebootapp.infrastructure.user.mapper;

import com.example.samplebootapp.domain.user.model.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会員MyBatis Mapper.
 */
@Mapper
public interface MemberMapper {

    /**
     * 会員を登録します.
     *
     * @param member 会員
     */
    @Insert("INSERT INTO user_members (id, name, email, password) VALUES (#{id}, #{name}, #{email}, #{password})")
    void insert(Member member);

    /**
     * メールアドレスで会員を検索します.
     *
     * @param email メールアドレス
     * @return 会員
     */
    @org.apache.ibatis.annotations.Select("SELECT * FROM user_members WHERE email = #{email}")
    Member findByEmail(String email);

    /**
     * IDで会員を検索します.
     *
     * @param id ID
     * @return 会員
     */
    @org.apache.ibatis.annotations.Select("SELECT * FROM user_members WHERE id = #{id}")
    Member findById(String id);
}
