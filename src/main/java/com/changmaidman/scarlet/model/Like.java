package com.changmaidman.scarlet.model;

import com.changmaidman.scarlet.annotation.LikeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Like extends Sentiment {

    @LikeHandler
    public void processLike() {
        System.out.println("Like class like");
    }

    @LikeHandler
    public void storeLikeInDatabase() {
        System.out.println("Like action stored in database");
    }
}
