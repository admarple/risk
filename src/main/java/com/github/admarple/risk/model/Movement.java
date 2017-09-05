package com.github.admarple.risk.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Movement {
    @NonNull private Army source;
    @NonNull private Army destination;

    public void move() {
        source.join(destination);
    }
}
