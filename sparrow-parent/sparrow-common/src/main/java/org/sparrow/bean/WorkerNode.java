package org.sparrow.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Copyright© 2019
 * Author jie.han
 * Created on 2019-05-22
 */
@Data
@AllArgsConstructor
public class WorkerNode {
    private String ip;
    private Integer weight;
}