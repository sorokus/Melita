package com.melita.ordermanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * @author sorokus.dev@gmail.com
 */

@Data
@AllArgsConstructor
public class EmailDetailsDto {

    private String subject;
    private String recipient;
    private String msgBody;
    private String attachment;

}
