package br.edu.usp.javalibrary.javalibrary.service.repository;

import br.edu.usp.javalibrary.javalibrary.service.JsonService;
import br.edu.usp.javalibrary.javalibrary.service.domains.Loan;

import java.util.ArrayList;
import java.util.UUID;

public class LoanRepository {
    static final String loanFilePath = "loans.json";

    private ArrayList<Loan> loans;

    private static LoanRepository instance;
    public static LoanRepository getInstance(){
        if (instance == null) instance = new LoanRepository();
        return instance;
    }

    private LoanRepository(){}

    private void loadLoansFile() {
        try {
            loans = JsonService.getInstance().loadJson(loanFilePath);
        } catch (Exception e){
            loans = new ArrayList<>();
        }
    }

    private boolean saveLoansFile(){
        try {
            JsonService.getInstance().saveJson(loanFilePath, loans);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public ArrayList<Loan> getLoans() {
        if (loans == null) loadLoansFile();
        return loans;
    }

    public boolean saveLoan(Loan loan) {
        if (loans == null) loadLoansFile();
        loans.removeIf(it -> it.getId() == loan.getId());
        loans.add(loan);
        return saveLoansFile();
    }


    public boolean removeLoan(UUID id) {
        if (loans == null) loadLoansFile();
        loans.removeIf(loan -> loan.getId() == id);
        return saveLoansFile();
    }

}
