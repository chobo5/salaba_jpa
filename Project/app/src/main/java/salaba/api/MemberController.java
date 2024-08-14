package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.MemberJoinDto;
import salaba.dto.MemberModifyDto;
import salaba.dto.Message;
import salaba.exception.AlreadyExistsException;
import salaba.exception.PasswordValidationException;
import salaba.response.CreateResponse;
import salaba.service.MemberService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

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
    public ResponseEntity<?> join(@RequestBody MemberJoinDto memberJoinDto) {
        try {
            return ResponseEntity
                    .ok(new CreateResponse(memberService.join(memberJoinDto)));
        } catch (PasswordValidationException | AlreadyExistsException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Message(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
        }
    }

    @PutMapping("modify")
    public ResponseEntity<?> changeProfile(@RequestBody MemberModifyDto memberModifyDto) {

        try {
            return ResponseEntity.ok(new CreateResponse(memberService.modifyProfile(memberModifyDto)));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(HttpStatus.NOT_FOUND.value(), "존재하지 않는 회원입니다."));
        }
    }

}
