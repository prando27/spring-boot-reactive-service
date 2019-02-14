package com.prando.springbootreactiveservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@JsonDeserialize(builder = Person.Builder.class)
@Value
@Builder(builderClassName = "Builder", toBuilder = true)
public class Person {

    @Id
    private final Integer id;
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

}
