package com.europeandynamics.txttoxmlmanagement.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private List<String> chapter;
    private List<String> paragraph;
    private String line;

}
