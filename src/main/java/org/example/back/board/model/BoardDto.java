package org.example.back.board.model;

import lombok.*;

import java.util.List;

public class BoardDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reg {
        private String title;
        private String contents;

        public Board toEntity() {
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Res {
        private Long idx;
        private String title;
        private String contents;

        public static Res from(Board entity) {
            return Res.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Read {
        private Long idx;
        private String title;
        private String contents;
    }
}
