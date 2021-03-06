package com.ohgiraffers.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ohgiraffers.spring.board.model.dto.AttachmentDTO;
import com.ohgiraffers.spring.board.model.dto.BoardDTO;
import com.ohgiraffers.spring.board.model.service.BoardService;
import com.ohgiraffers.spring.common.exception.board.ThumbnailRegistException;
import com.ohgiraffers.spring.member.model.dto.MemberDTO;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/thumbnail")
public class ThumbnailController {

	private final BoardService boardService;

	@Autowired
	public ThumbnailController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping("/list")
	public String selectAllThumbnailList(Model model) {
		List<BoardDTO> thumbnailList = boardService.selectAllThumbnailList();

		model.addAttribute("thumbnailList", thumbnailList);

		return "/thumbnail/thumbnailList";
	}

	@GetMapping("/regist")
	public void registThumbnail() {
	}

	@PostMapping("/regist")
	public String registThumbnail(@ModelAttribute BoardDTO thumbnail, HttpServletRequest request,
			@RequestParam("thumbnailImg1") MultipartFile thumbnailImg1,
			@RequestParam("thumbnailImg2") MultipartFile thumbnailImg2,
			@RequestParam("thumbnailImg3") MultipartFile thumbnailImg3,
			@RequestParam("thumbnailImg4") MultipartFile thumbnailImg4, RedirectAttributes rttr)
			throws UnsupportedEncodingException, ThumbnailRegistException {

		String rootLocation = request.getSession().getServletContext().getRealPath("resources");

		String fileUploadDirectory = rootLocation + "/upload/original";
		String thumbnailDirectory = rootLocation + "/upload/thumbnail";

		File directory = new File(fileUploadDirectory);
		File directory2 = new File(thumbnailDirectory);

		/* ?????? ??????????????? ???????????? ?????? ?????? ??????????????? ????????????. */
		if (!directory.exists() || !directory2.exists()) {

			/* ????????? ??? ?????? ??????????????? mkdir, ?????? ????????? ???????????? ????????? ??? ?????? ???????????? ????????? mkdirs??? ????????????. */
			System.out.println("?????? ?????? : " + directory.mkdirs());
			System.out.println("?????? ?????? : " + directory2.mkdirs());
		}

		/* ????????? ?????? ??????????????? ?????? ????????? ?????? ????????? */
		List<Map<String, String>> fileList = new ArrayList<>();

		List<MultipartFile> paramFileList = new ArrayList<>();
		paramFileList.add(thumbnailImg1);
		paramFileList.add(thumbnailImg2);
		paramFileList.add(thumbnailImg3);
		paramFileList.add(thumbnailImg4);
		
		try {
			for (MultipartFile paramFile : paramFileList) {
				if (paramFile.getSize() > 0) {
					String originFileName = paramFile.getOriginalFilename();
					System.out.println(originFileName);
					String ext = originFileName.substring(originFileName.lastIndexOf("."));
					String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;
					System.out.println("????????? ?????? : " + savedFileName);

					paramFile.transferTo(new File(fileUploadDirectory + "/" + savedFileName));

					/* DB??? ???????????? ????????? ????????? ???????????? ???????????? ?????? ?????? */
					/* ????????? ????????? Map??? ?????????. */
					Map<String, String> fileMap = new HashMap<>();
					fileMap.put("originFileName", originFileName);
					fileMap.put("savedFileName", savedFileName);
					fileMap.put("savePath", fileUploadDirectory);

					/* ?????? ????????? ????????? ????????? ???????????? ???????????? ????????????. */
					int width = 0;
					int height = 0;

					String fieldName = paramFile.getName();
					System.out.println("?????? name : " + fieldName);

					if ("thumbnailImg1".equals(fieldName)) {
						fileMap.put("fileType", "TITLE");

						/* ???????????? ?????? ??? ???????????? ????????????. */
						width = 300;
						height = 150;
					} else {
						fileMap.put("fileType", "BODY");

						width = 120;
						height = 100;
					}

					/* ???????????? ?????? ??? ????????????. */
					Thumbnails.of(fileUploadDirectory + "/" + savedFileName).size(width, height)
							.toFile(thumbnailDirectory + "/thumbnail_" + savedFileName);

					/* ????????? ??????????????? ?????? ????????? ?????? ????????? ???????????? ?????? ????????? ?????? ????????????. */
					fileMap.put("thumbnailPath", "/resources/upload/thumbnail/thumbnail_" + savedFileName);

					fileList.add(fileMap);
				}
			}
			
			System.out.println("fileList : " + fileList);

			/* ???????????? ????????? ??? ????????? BoardDTO??? ?????????. */
			thumbnail.setWriterMemberNo(((MemberDTO) request.getSession().getAttribute("loginMember")).getNo());

			thumbnail.setAttachmentList(new ArrayList<AttachmentDTO>());
			List<AttachmentDTO> list = thumbnail.getAttachmentList();
			for (int i = 0; i < fileList.size(); i++) {
				Map<String, String> file = fileList.get(i);

				AttachmentDTO tempFileInfo = new AttachmentDTO();
				tempFileInfo.setOriginalName(file.get("originFileName"));
				tempFileInfo.setSavedName(file.get("savedFileName"));
				tempFileInfo.setSavePath(file.get("savePath"));
				tempFileInfo.setFileType(file.get("fileType"));
				tempFileInfo.setThumbnailPath(file.get("thumbnailPath"));

				list.add(tempFileInfo);
			}

			System.out.println("thumbnail board : " + thumbnail);

			boardService.registThumbnail(thumbnail);

			rttr.addFlashAttribute("message", "?????? ????????? ????????? ?????????????????????.");

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();

			/* ?????? ????????? Exception??? ?????? ?????????????????? ??? ????????? ???????????? ??????. */
			int cnt = 0;
			for (int i = 0; i < fileList.size(); i++) {
				Map<String, String> file = fileList.get(i);

				File deleteFile = new File(fileUploadDirectory + "/" + file.get("savedFileName"));
				boolean isDeleted1 = deleteFile.delete();

				File deleteThumbnail = new File(thumbnailDirectory + "/thumbnail_" + file.get("savedFileName"));
				boolean isDeleted2 = deleteThumbnail.delete();

				if (isDeleted1 && isDeleted2) {
					cnt++;
				}
			}

			if (cnt == fileList.size()) {
				System.out.println("???????????? ????????? ?????? ?????? ?????? ??????!");
				e.printStackTrace();
			} else {
				e.printStackTrace();
			}
		}

		return "redirect:/thumbnail/list";
	}

	@GetMapping("/detail")
	public String selectThumbnailDetail(@RequestParam int no, Model model) {

		BoardDTO thumbnailDetail = boardService.selectThumbnailDetail(no);

		model.addAttribute("thumbnail", thumbnailDetail);

		return "/thumbnail/thumbnailDetail";
	}
}
