package com.example.zlyy.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlRootElement;

@Repository
@Data
@EqualsAndHashCode(callSuper = false)
public class PyModel {
    
    private Double value;
}
