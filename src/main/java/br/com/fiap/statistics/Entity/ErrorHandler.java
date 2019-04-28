package br.com.fiap.statistics.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
public class ErrorHandler {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")

    @ApiModelProperty(notes = "The timestamp of error")
    private long timestamp;

    @ApiModelProperty(notes = "The message of error")
    private String message;

    @ApiModelProperty(notes = "The description of error with more details")
    private String details;

    public ErrorHandler(long timestamp, String message, String details) {

        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;

    }

}
