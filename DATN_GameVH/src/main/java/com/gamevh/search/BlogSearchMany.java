package com.gamevh.search;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.gamevh.entities.Blog;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;



// tìm kiếm title , name, ngày tạo >> có thể tìm nhiều hoặc ít hơn 3 điều kiện trên
public class BlogSearchMany {
	  public static Specification<Blog> hasTitle(final String tittle) {
	        return new Specification<Blog>() {
	            @Override
	            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
	                return criteriaBuilder.like(criteriaBuilder.lower(root.get("tittle")), "%" + tittle.toLowerCase() + "%");
	            }
	        };
	    }

	    public static Specification<Blog> hasUsername(final String username) {
	        return new Specification<Blog>() {
	            @Override
	            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
	                return criteriaBuilder.like(criteriaBuilder.lower(root.get("account").get("username")), "%" + username.toLowerCase() + "%");
	            }
	        };
	    }

	    public static Specification<Blog> hasCreateDate(final LocalDate createDate) {
	        return new Specification<Blog>() {
	            @Override
	            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
	                return criteriaBuilder.equal(root.get("createDate"), createDate);
	            }
	        };
	    }
}
