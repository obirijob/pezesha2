import java.text.DecimalFormat;

public class LoanRepaymentCalculator {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public static void main(String[] args) {
        double loanAmount = 1000;
        int loanTermInMonths = 12;
        double annualInterestRate = 35;
        RepaymentFrequency repaymentFrequency = RepaymentFrequency.MONTHLY;

        calculateLoanRepayment(loanAmount, loanTermInMonths, annualInterestRate, repaymentFrequency);
    }

    public static void calculateLoanRepayment(double loanAmount, int loanTermInMonths, double annualInterestRate,
            RepaymentFrequency repaymentFrequency) {
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double totalInterestPaid = 0;
        double totalAmountRepaid = 0;
        double remainingBalance = loanAmount;
        double monthlyRepayment = calculateMonthlyRepayment(loanAmount, monthlyInterestRate, loanTermInMonths);
        System.out.println("\n\nLoan Calculator");
        System.out.println("Loan Amount: " + loanAmount);
        System.out.println("Loan Term (in months): " + loanTermInMonths);
        System.out.println("Annual Interest Rate: " + annualInterestRate + "%");
        System.out.println("Repayment Frequency: " + repaymentFrequency + "\n");
        System.out.println("Monthly Repayment: " + DECIMAL_FORMAT.format(monthlyRepayment) + "\n");
        System.out.println("Repayment Schedule:");
        System.out.println("Payment\t\tInterest\tPrincipal\tBalance");
        for (int i = 1; i <= loanTermInMonths; i++) {
            double interestPaid = monthlyInterestRate * remainingBalance;
            double principalPaid = monthlyRepayment - interestPaid;
            remainingBalance -= principalPaid;

            totalInterestPaid += interestPaid;
            totalAmountRepaid += monthlyRepayment;

            System.out.println(i + "\t\t" + DECIMAL_FORMAT.format(interestPaid) + "\t\t" +
                    DECIMAL_FORMAT.format(principalPaid) + "\t\t" + DECIMAL_FORMAT.format(remainingBalance));

            if (repaymentFrequency == RepaymentFrequency.BI_MONTHLY && i % 2 == 0) {
                remainingBalance -= monthlyRepayment;
            } else if (repaymentFrequency == RepaymentFrequency.WEEKLY) {
                remainingBalance += (interestPaid + principalPaid) / 4;
            }
        }

        System.out.println("\nTotal Interest Paid: " + DECIMAL_FORMAT.format(totalInterestPaid));
        System.out.println("Total Amount Repaid: " + DECIMAL_FORMAT.format(totalAmountRepaid));
    }

    private static double calculateMonthlyRepayment(double loanAmount, double monthlyInterestRate,
            int loanTermInMonths) {
        return loanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));
    }
}

enum RepaymentFrequency {
    MONTHLY,
    BI_MONTHLY,
    WEEKLY
}