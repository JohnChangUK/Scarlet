package com.changmaidman.scarlet.model;

import com.changmaidman.scarlet.annotation.SuperLikeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class SuperLike extends Sentiment {

    @SuperLikeHandler
    public void processSuperLike() {
        System.out.println("Super like class like");
    }

    @SuperLikeHandler
    public void storeSuperLikeInDatabase() {
        System.out.println("Super like action stored in database");
    }
}
