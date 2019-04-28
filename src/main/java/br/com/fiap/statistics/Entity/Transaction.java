package br.com.fiap.statistics.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @ApiModelProperty(notes = "The timestamp of transaction")
    private long timestamp;

    @ApiModelProperty(notes = "The total amount of transaction")
    private double amount;

}
