package com.example.alumni.feature.user_detail;


import com.example.alumni.domain.Employ;
import com.example.alumni.domain.Generation;
import com.example.alumni.domain.StudyAbroad;
import com.example.alumni.domain.UserDetail;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UserDetailSpecification {
    public static Specification<UserDetail> hasGenType(String genType) {
        return (root, query, cb) -> {
            if (genType != null) {
                Join<UserDetail, Generation> genInfoJoin = root.join("generation");
                return cb.equal(genInfoJoin.get("genType"), genType);
            } else {
                return null;
            }
        };
    }

    public static Specification<UserDetail> hasGenNum(String numGen) {
        return (root, query, cb) -> {
            if (numGen != null) {
                Join<UserDetail, Generation> genInfoJoin = root.join("generation");
                return cb.equal(genInfoJoin.get("numGen"), numGen);
            } else {
                return null;
            }
        };
    }

    public static Specification<UserDetail> isGraduated(Boolean isGraduated) {
        return (root, query, cb) -> {
            if (isGraduated != null) {
                return cb.equal(root.get("isGraduated"), isGraduated);
            } else {
                return null;
            }
        };
    }

    public static Specification<UserDetail> isEmployed(Boolean isEmployed) {
        return (root, query, cb) -> {
            if (isEmployed != null) {
                return cb.equal(root.get("isEmployed"), isEmployed);
            } else {
                return null;
            }
        };
    }

    public static Specification<UserDetail> hasStudyAbroadCountry(String country) {
        return (root, query, cb) -> {
            if (country != null) {
                Join<UserDetail, StudyAbroad> studyAbroadJoin = root.join("studyAbroad");
                return cb.equal(studyAbroadJoin.get("country"), country);
            } else {
                return null;
            }
        };
    }


    public static Specification<UserDetail> hasEmployType(String employType) {
        return (root, query, cb) -> {
            if (employType != null) {
                Join<UserDetail, Employ> employTypeJoin = root.join("employ");
                return cb.equal(employTypeJoin.get("employType"), employType);
            } else {
                return null;
            }
        };
    }


}
