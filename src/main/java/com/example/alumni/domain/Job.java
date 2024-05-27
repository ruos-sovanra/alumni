package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String position;
    private String companyName;
    private BigDecimal salary;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String poster;

    @ManyToOne
    @JoinColumn(name = "post_type_id")
    private Post postType;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "requirements")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> requirements;
}
