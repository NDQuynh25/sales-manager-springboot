package com.example.sales_manager.config;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.example.sales_manager.dto.FilterCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomSpecification<T> implements Specification<T> {

    private FilterCriteria criteria;

    public CustomSpecification(FilterCriteria criteria) {
        this.criteria = criteria;
    }
    public CustomSpecification() {
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThan(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("like")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                System.out.println(">>> key: " + criteria.getKey());
                System.out.println(">>> value: " + criteria.getValue());
                return criteriaBuilder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase(">=")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<=")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<>")) {
            return criteriaBuilder.notEqual(
                    root.get(criteria.getKey()), criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase("in")) {
            return root.get(criteria.getKey()).in(criteria.getValue());
        }

        return null;
    }
}
