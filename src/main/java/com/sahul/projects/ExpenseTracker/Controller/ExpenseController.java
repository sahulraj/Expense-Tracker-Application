package com.sahul.projects.ExpenseTracker.Controller;



import com.sahul.projects.ExpenseTracker.entity.Expense;
import com.sahul.projects.ExpenseTracker.service.ExpenseService;
import com.sahul.projects.ExpenseTracker.entity.Expense;


import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/Expenses")
    public List<Expense> getAllExpenses(Pageable page)
    {
        return expenseService.getAllExpenses(page).toList();
    }
    @GetMapping("/Expense")
    public Expense getExpenseById(@RequestParam("id") Long id)
    {

        return expenseService.getExpenseById(id);
    }
    @GetMapping("/Expense/{id}")
    public Expense getExpense(@PathVariable Long id)
    {
        return expenseService.getExpenseById(id);
    }
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/Expense")
    public void deleteExpenseById(@RequestParam("id") Long id)
    {
        expenseService.deleteExpenseById(id);
    }
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/Expense")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpenseDetails(expense);
    }
    @PutMapping("/Expense/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense)
    {
        return expenseService.updateExpense(id, expense);
    }
    @GetMapping("/Expenses/category")
    public List<Expense>getExpenseByCategory(@RequestParam String category, Pageable pageable)
    {
       return expenseService.readByCategory(category, pageable);

    }
    @GetMapping("/Expenses/username")
    public List<Expense>getExpenseByName(@RequestParam String key, Pageable pageable)
    {
        return expenseService.readByName(key, pageable);
    }
    @GetMapping("/Expenses/date")
    public List<Expense>getExpenseBetweenDates(@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, Pageable pageable)
    {
        return expenseService.findExpensesBetweenDates(startDate, endDate, pageable);
    }
}

