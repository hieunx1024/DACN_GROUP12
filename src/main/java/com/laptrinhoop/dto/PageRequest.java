package com.laptrinhoop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class PageRequest {

    private static final int MAX_PAGE_SIZE = 10;

    int page;
    int size;
    Sort.Direction direction;

    @JsonIgnore
    public final Pageable toPageable() {
        int pageSize = (size > 0 && size <= MAX_PAGE_SIZE) ? size : MAX_PAGE_SIZE;
        Sort sort = getSort();
        return org.springframework.data.domain.PageRequest.of(page, pageSize, sort);
    }

    @JsonIgnore
    protected abstract Sort getSort();
}
