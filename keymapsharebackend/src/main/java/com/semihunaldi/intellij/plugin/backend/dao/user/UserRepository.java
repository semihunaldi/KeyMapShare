package com.semihunaldi.intellij.plugin.backend.dao.user;

import com.semihunaldi.intellij.plugin.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String >
{
    @Query("select user from User user where user.userName = :userName and user.deleted='0'")
    public User findAllDeploymentPeopleOnDuties(@Param("userName") String userName);
}
