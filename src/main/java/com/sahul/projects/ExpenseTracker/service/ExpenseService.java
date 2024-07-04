package com.sahul.projects.ExpenseTracker.service;

import com.sahul.projects.ExpenseTracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Date;
import java.util.List;

public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable pageable);

    List<Expense>readByCategory(String category, Pageable p);
    Expense getExpenseById(Long id);
    void deleteExpenseById(Long id);
    Expense saveExpenseDetails(Expense e);
    Expense updateExpense(Long id, Expense expense);
    List<Expense>readByName(String name, Pageable pageable );
    List<Expense>findExpensesBetweenDates(Date start, Date end, Pageable pageable);
}
