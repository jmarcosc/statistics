package br.com.fiap.statistics.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {

    @ApiModelProperty(notes = "Returns the sum of the last transactions")
    private double sum;

    @ApiModelProperty(notes = "Returns the minimal amount of the last transactions")
    private double min;

    @ApiModelProperty(notes = "Returns the maximum amount of the last transactions")
    private double max;

    @ApiModelProperty(notes = "Returns the average of the last transactions")
    private double avg;

    @ApiModelProperty(notes = "Returns the quantity of the last transactions")
    private long count;

}
