package br.com.fiap.statistics.Controller;

import br.com.fiap.statistics.Entity.ErrorHandler;
import br.com.fiap.statistics.Entity.Statistic;
import br.com.fiap.statistics.Entity.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Api(value = "Transaction", description = "Statistics management for transactions")
public class TransactionController {

    HttpStatus status;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    @PostMapping("/transactions")
    @ApiOperation(httpMethod = "POST", value = "This method are able to register a valid transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns an empty body and the transaction is registered"),
            @ApiResponse(code = 204, message = "Returns an empty body and the transaction is not registered")
    })
    public ResponseEntity registerTransaction(@RequestBody Transaction transaction) {

        long result;

        result = validateTransaction(transaction);

        if(result > 60) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.CREATED;
            this.transactions.add(transaction);
        }

        return new ResponseEntity(status);

    }

    @GetMapping("/statistics")
    @ApiOperation(httpMethod = "GET", value = "This method returns statistics about the last transactions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the statistics about the last transactions", response = Statistic.class),
            @ApiResponse(code = 404, message = "Returns an error when there is no transactions registered for the last 60 seconds", response = ErrorHandler.class)
    })
    public ResponseEntity<Statistic> retrieveTransactions() {

        status = HttpStatus.OK;
        Statistic statistics = null;
        statistics = filterTransactions();

        if(statistics.getCount() == 0) {
            ErrorHandler errorHandler = new ErrorHandler(System.currentTimeMillis(), "STATISTICS_NOT_FOUND", "There are no statistics for the last 60 seconds");
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(errorHandler, status);
        }

        return new ResponseEntity<>(statistics, status);

    }

    public long validateTransaction(Transaction transaction) {

        long now = System.currentTimeMillis();
        long result = TimeUnit.MILLISECONDS.toSeconds(now - transaction.getTimestamp());
        return result;

    }

    public Statistic filterTransactions() {

        long now = System.currentTimeMillis();
        Statistic statistics = new Statistic();

        List<Transaction> validTransactions = transactions.stream().filter(t -> (now - t.getTimestamp()) <= 60000).collect(Collectors.toList());

        statistics.setSum(validTransactions.stream().mapToDouble(Transaction::getAmount).sum());
        statistics.setMin(validTransactions.stream().mapToDouble(Transaction::getAmount).min().orElse(0.0));
        statistics.setMax(validTransactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0));
        statistics.setAvg(validTransactions.stream().mapToDouble(Transaction::getAmount).average().orElse(0.0));
        statistics.setCount(validTransactions.size());

        return statistics;

    }

}
