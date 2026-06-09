package fr.astek.enib.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFilterCriteria {

    private String author;
    private String titleKeyword;

}
