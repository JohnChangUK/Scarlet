package com.changmaidman.scarlet.model;

import com.changmaidman.scarlet.annotation.DislikeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Dislike extends Sentiment {

    @DislikeHandler
    public void processDislike() {
        System.out.println("Dislike handler invoked");
    }

    @DislikeHandler
    public void storeDislikeInDatabase() {
        System.out.println("Dislike action stored in database");
    }
}
