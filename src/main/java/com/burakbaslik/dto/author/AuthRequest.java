package com.burakbaslik.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record AuthRequest(String username, String password) {

}
