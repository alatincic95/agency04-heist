package com.anteag04.springbootbackend.model;

import javax.persistence.*;

@Entity
@Table(name = "heist_members")
public class HeistMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long heistId;
    @Column
    private String memberName;

    public HeistMembers() {
    }

    public HeistMembers(Long heistId, String memberName) {
        this.heistId = heistId;
        this.memberName = memberName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHeistId() {
        return heistId;
    }

    public void setHeistId(Long heistId) {
        this.heistId = heistId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
