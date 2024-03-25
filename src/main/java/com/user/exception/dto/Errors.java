package com.user.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Errors implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Source source;
}
