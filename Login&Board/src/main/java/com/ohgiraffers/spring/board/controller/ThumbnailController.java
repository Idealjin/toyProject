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

		/* 파일 저장경로가 존재하지 않는 경우 디렉토리를 생성한다. */
		if (!directory.exists() || !directory2.exists()) {

			/* 폴더를 한 개만 생성할거면 mkdir, 상위 폴더도 존재하지 않으면 한 번에 생성하란 의미로 mkdirs를 이용한다. */
			System.out.println("폴더 생성 : " + directory.mkdirs());
			System.out.println("폴더 생성 : " + directory2.mkdirs());
		}

		/* 업르도 파일 하나하나에 대한 정보를 담을 리스트 */
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
					System.out.println("변경한 이름 : " + savedFileName);

					paramFile.transferTo(new File(fileUploadDirectory + "/" + savedFileName));

					/* DB에 업로드한 파일의 정보를 저장하는 비지니스 로직 수행 */
					/* 필요한 정보를 Map에 담는다. */
					Map<String, String> fileMap = new HashMap<>();
					fileMap.put("originFileName", originFileName);
					fileMap.put("savedFileName", savedFileName);
					fileMap.put("savePath", fileUploadDirectory);

					/* 제목 사진과 나머지 사진을 구분하고 썸네일도 생성한다. */
					int width = 0;
					int height = 0;

					String fieldName = paramFile.getName();
					System.out.println("필드 name : " + fieldName);

					if ("thumbnailImg1".equals(fieldName)) {
						fileMap.put("fileType", "TITLE");

						/* 썸네일로 변환 할 사이즈를 지정한다. */
						width = 300;
						height = 150;
					} else {
						fileMap.put("fileType", "BODY");

						width = 120;
						height = 100;
					}

					/* 썸네일로 변환 후 저장한다. */
					Thumbnails.of(fileUploadDirectory + "/" + savedFileName).size(width, height)
							.toFile(thumbnailDirectory + "/thumbnail_" + savedFileName);

					/* 나중에 웹서버에서 접근 가능한 경로 형태로 썸네일의 저장 경로도 함께 저장한다. */
					fileMap.put("thumbnailPath", "/resources/upload/thumbnail/thumbnail_" + savedFileName);

					fileList.add(fileMap);
				}
			}
			
			System.out.println("fileList : " + fileList);

			/* 서비스를 요청할 수 있도록 BoardDTO에 담는다. */
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

			rttr.addFlashAttribute("message", "사진 게시판 등록에 성공하셨습니다.");

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();

			/* 어떤 종류의 Exception이 발생 하더라도실패 시 파일을 삭제해야 한다. */
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
				System.out.println("업로드에 실패한 모든 사진 삭제 완료!");
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
