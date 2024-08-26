package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.MemberJoinReqDto;
import salaba.dto.request.MemberModiReqDto;
import salaba.dto.request.Message;
import salaba.exception.AlreadyExistsException;
import salaba.exception.PasswordValidationException;
import salaba.dto.response.IdResDto;
import salaba.service.BoardService;
import salaba.service.MemberService;
import salaba.service.ReplyService;
import salaba.service.ReservationService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    private final ReplyService replyService;

    private final ReservationService reservationService;

    @GetMapping("validateNickname")
    public ResponseEntity<Message> validateNickname(@RequestParam String nickname) {
        if (memberService.validateNickname(nickname)) {
            return ResponseEntity.ok(new Message(HttpStatus.OK.value(), "사용 가능한 닉네임 입니다."));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임 입니다."));
    }

    @GetMapping("validateEmail")
    public ResponseEntity<Message> validateEmail(@RequestParam String email) {
        if (memberService.validateEmail(email)) {
            return ResponseEntity.ok(new Message(HttpStatus.OK.value(), "사용 가능한 이메일 입니다."));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(HttpStatus.CONFLICT.value(), "이미 사용중인 이메일 입니다."));
    }

    @PostMapping("join")
    public ResponseEntity<?> join(@RequestBody MemberJoinReqDto memberJoinReqDto) {
        try {
            return ResponseEntity
                    .ok(new IdResDto(memberService.join(memberJoinReqDto)));
        } catch (PasswordValidationException | AlreadyExistsException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Message(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
        }
    }

    @PutMapping("modify")
    public ResponseEntity<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {

        try {
            return ResponseEntity.ok(new IdResDto(memberService.modifyProfile(memberModiReqDto)));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(HttpStatus.NOT_FOUND.value(), "존재하지 않는 회원입니다."));
        }
    }

    @GetMapping("wrote/boards/{memberId}")
    public ResponseEntity<?> boardListByMember(@PathVariable Long memberId, Pageable pageable) {
        return ResponseEntity.ok(boardService.boardsByMember(memberId, pageable));
    }

    @GetMapping("wrote/replies/{memberId}")
    public ResponseEntity<?> replyListByMember(@PathVariable Long memberId, Pageable pageable) {
        return ResponseEntity.ok(replyService.repliesByMember(memberId, pageable));
    }

    @GetMapping("reservation/list/{memberId}")
    public ResponseEntity reservationList(@PathVariable Long memberId, Pageable pageable) {
        return ResponseEntity.ok(reservationService.getWithRentalHomeAndHost(memberId, pageable));
    }

}
