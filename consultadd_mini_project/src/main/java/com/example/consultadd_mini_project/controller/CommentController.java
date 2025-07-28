package com.example.consultadd_mini_project.controller;


import com.example.consultadd_mini_project.DTO.ResponseDTO;
import com.example.consultadd_mini_project.DTO.commentDTO.CommentRequestDTO;
import com.example.consultadd_mini_project.DTO.commentDTO.CommentResponseDTO;
import com.example.consultadd_mini_project.service.commentService.CreateCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CreateCommentService createCommentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<CommentResponseDTO>> createComment(@RequestBody CommentRequestDTO comment){
        return createCommentService.createComment(comment);
    }

}
