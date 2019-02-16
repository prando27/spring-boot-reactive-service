package com.prando.springbootreactiveservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@JsonDeserialize(builder = Person.Builder.class)
@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class Person {

    @Id
    private Integer id;
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

}
