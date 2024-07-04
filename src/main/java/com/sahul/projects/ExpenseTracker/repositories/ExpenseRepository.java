package com.sahul.projects.ExpenseTracker.repositories;

import com.sahul.projects.ExpenseTracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    //this function should execute the following query
    //select*from expense where category = ?
    Page<Expense>findByUserIdAndCategory(Long UserId, String category, Pageable pageable);
    //select*from expense where name like %name%
    Page<Expense>findByUserIdAndNameContaining(Long UserId, String name, Pageable pageable);
    Page<Expense>findByUserIdAndDateBetween(Long UserId, Date startDate, Date endDate, Pageable pageable);
    Page<Expense>findByUserId(Long id, Pageable pageable);
    Optional<Expense>findByUserIdAndId(Long UserId, Long id);
}
