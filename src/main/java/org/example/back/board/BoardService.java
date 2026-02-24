package org.example.back.board;

import lombok.RequiredArgsConstructor;
import org.example.back.board.model.Board;
import org.example.back.board.model.BoardDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // 1. 작성
    public void register(BoardDto.Reg dto) {
        boardRepository.save(dto.toEntity());
    }

    // 2. 상세 조회
    public BoardDto.Res read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow(
                () -> new RuntimeException("게시글을 찾을 수 없습니다.")
        );
        return BoardDto.Res.from(board);
    }

    // 3. 목록 조회
    public List<BoardDto.Res> list() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().map(BoardDto.Res::from).toList();
    }

    // 4. 수정
    @Transactional
    public void update(Long idx, BoardDto.Reg dto) {
        Board board = boardRepository.findById(idx).orElseThrow(
                () -> new RuntimeException("수정할 게시글이 없습니다.")
        );
        board.update(dto.getTitle(), dto.getContents());
    }

    // 5. 삭제
    public void delete(Long idx) {
        boardRepository.deleteById(idx);
    }
}