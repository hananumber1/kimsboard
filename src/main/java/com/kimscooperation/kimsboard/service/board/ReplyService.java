package com.kimscooperation.kimsboard.service.board;

import javax.validation.Valid;

import com.kimscooperation.kimsboard.advice.exception.CNotOwnerException;
import com.kimscooperation.kimsboard.advice.exception.CResourceNotExistException;
import com.kimscooperation.kimsboard.advice.exception.CUserNotFoundException;
import com.kimscooperation.kimsboard.entity.board.Post;
import com.kimscooperation.kimsboard.entity.board.Reply;
import com.kimscooperation.kimsboard.entity.user.Users;
import com.kimscooperation.kimsboard.model.board.ParamsWriteReply;
import com.kimscooperation.kimsboard.repository.PostRepository;
import com.kimscooperation.kimsboard.repository.ReplyRepository;
import com.kimscooperation.kimsboard.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

    /**
     * 댓글을 등록합니다.
     * @param userNum 유저 번호
     * @param postId 게시글 번호
     * @param writeReply 유저에게 받은 댓글 객체
     * @return 등록하고 난 Reply 객체
     */
    public Reply writeReply(long userNum, Long postId, ParamsWriteReply writeReply) {
        final Post post = getPost(postId); // 게시글 가져오기
        final Users user = userRepository.findByUserNum(userNum).orElseThrow(CUserNotFoundException::new); //해당User를 가져옴
        Reply reply = new Reply(writeReply.getContent(), user.getName(), post, user);
        return replyRepository.save(reply);
    }

    /**
     * 댓글을 삭제합니다.
     * @param replyId 댓글 번호
     * @param userNum 사용자 번호
     * @return 삭제가 되었으면 true를 반환 
     */
	public boolean deleteReply(final long replyId, final String userNum) {
		final Reply reply = getReply(replyId);
		final Users user = reply.getUser();
		if(! (user.getUserNum() == Long.parseLong(userNum))){
			throw new CNotOwnerException();
		}
		replyRepository.delete(reply);
		return true;
	}


    /**
     * 게시글 번호를 가지고 해당하는 게시글의 정보를 가져옵니다.
     * @param postId 댓글을 작성할 때, 해당하는 게시글의 번호
     * @throws CResourceNotExistException 게시글 번호에 해당하는 게시글이 없을 때 해당 에러를 발생
     * @return 게시글 번호에 해당하는 POST 객체
     */
	public Post getPost(final long postId) {
		return postRepository.findById(postId).orElseThrow(CResourceNotExistException::new);
    }
    
    /**
     * 댓글 번호에 해당하는 댓글 정보를 가져옵니다.
     * @param replyId 댓글 번호
     * @return 댓글 번호에 해당하는 Reply 객체
     */
    public Reply getReply(final long replyId){
        return replyRepository.findById(replyId).orElseThrow(CResourceNotExistException::new);
    }
}
