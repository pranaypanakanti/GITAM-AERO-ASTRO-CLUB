package com.GAAC.GAAC.Backend.specification;

import com.GAAC.GAAC.Backend.model.User;
import com.GAAC.GAAC.Backend.model.dto.request.UserSearchCriteriaDTO;
import com.GAAC.GAAC.Backend.model.enums.RecruitmentStatusEnum;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filterAndSortUsers(UserSearchCriteriaDTO criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getRole() != null) {
                predicates.add(cb.equal(root.get("role"), criteria.getRole()));
            }

            if (criteria.getPosition() != null) {
                predicates.add(cb.equal(root.get("position"), criteria.getPosition()));
            }

            if (criteria.getRecruitmentStatus() != null) {
                predicates.add(cb.equal(root.get("recruitmentStatus"), criteria.getRecruitmentStatus()));
            }

            if (criteria.getYearOfStudy() != null) {
                predicates.add(cb.equal(root.get("yearOfStudy"), criteria.getYearOfStudy().toString()));
            }

            if (criteria.getTeam() != null) {
                predicates.add(cb.equal(root.get("team"), criteria.getTeam()));
            }

            if (StringUtils.hasText(criteria.getSearchTerm())) {
                String searchTerm = "%" + criteria.getSearchTerm().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), searchTerm),
                        cb.like(cb.lower(root.get("email")), searchTerm),
                        cb.like(cb.lower(root.get("collegeId")), searchTerm),
                        cb.like(cb.lower(root.get("branch")), searchTerm),
                        cb.like(root.get("mobileNumber"), "%" + criteria.getSearchTerm() + "%")
                ));
            }

            Expression<Integer> priorityExpression = cb.selectCase()
                    .when(cb.equal(root.get("recruitmentStatus"), RecruitmentStatusEnum.APPLIED), 1)
                    .when(cb.equal(root.get("recruitmentStatus"), RecruitmentStatusEnum.IN_PROGRESS), 2)
                    .when(cb.equal(root.get("recruitmentStatus"), RecruitmentStatusEnum.SELECTED), 3)
                    .when(cb.equal(root.get("recruitmentStatus"), RecruitmentStatusEnum.NOT_SELECTED), 4)
                    .otherwise(5)
                    .as(Integer.class);

            query.orderBy(cb.asc(priorityExpression));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}