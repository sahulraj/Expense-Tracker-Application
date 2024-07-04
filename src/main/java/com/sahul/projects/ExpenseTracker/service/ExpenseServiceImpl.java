package com.sahul.projects.ExpenseTracker.service;

import com.sahul.projects.ExpenseTracker.entity.Expense;
import com.sahul.projects.ExpenseTracker.exception.ResourceNotFoundException;
import com.sahul.projects.ExpenseTracker.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserService userService;




    @Override
    public Page<Expense> getAllExpenses(Pageable pageable) {
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), pageable);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable p) {
        return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, p).toList();
    }

    @Override
    public Expense getExpenseById(Long id) {
        if(id <= 0)throw new RuntimeException("expense id will start from 1");
        Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
        if (expense.isPresent()) return expense.get();
        else throw new ResourceNotFoundException("expense not found with the given id "+id );
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);

            expenseRepository.delete(expense);

    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }


    @Override
    public Expense updateExpense(Long id, Expense expense) {
       Expense expense2 = getExpenseById(id);


            expense2.setAmount(expense.getAmount() != null?expense.getAmount():expense2.getAmount());
            expense2.setDate(expense.getDate() != null ? expense.getDate():expense2.getDate());
            expense2.setCategory(expense.getCategory() != null?expense.getCategory():expense2.getCategory());
            expense2.setName(expense.getName() != null ? expense.getName():expense2.getName());
            expense2.setDescription(expense.getDescription() != null?expense.getDescription():expense2.getDescription());
            expenseRepository.save(expense2);
            return expense2;


    }

    @Override
    public List<Expense> readByName(String name, Pageable pageable) {
        return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), name, pageable).stream().toList();
    }

    @Override
    public List<Expense> findExpensesBetweenDates(Date start, Date end, Pageable pageable) {
        if(start == null)
            start = new Date(0);
        if(end == null)
            end = new Date(System.currentTimeMillis());
        System.out.println(start);
        System.out.println(end);
        return expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), start, end, pageable).toList();
    }
}
