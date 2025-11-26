package io.paymeter.assessment.shared;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class MessageResponse {
    String message;
    Integer code;
}
