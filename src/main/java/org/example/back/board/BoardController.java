package org.example.back.board;

import lombok.RequiredArgsConstructor;
import org.example.back.board.model.BoardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)

@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @PostMapping("/reg")
    public ResponseEntity register(@RequestBody BoardDto.Reg dto) {
        boardService.register(dto);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.Res dto = boardService.read(idx);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/list")
    public ResponseEntity list() {
        List<BoardDto.Res> dto = boardService.list();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity update(@PathVariable Long idx, @RequestBody BoardDto.Reg dto) {
        boardService.update(idx, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity delete(@PathVariable Long idx) {
        boardService.delete(idx);
        return ResponseEntity.ok("삭제 완료우");
    }
}

