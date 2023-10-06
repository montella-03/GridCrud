package com.example.store.Backend.enumerations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;

@Getter
public enum Role {
    MANAGER,
    USER;
}
