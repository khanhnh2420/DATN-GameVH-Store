package com.gamevh.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.dto.BlogDTO;
import com.gamevh.entities.Blog;
import com.gamevh.repository.BlogRepository;
import com.gamevh.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	BlogRepository blogRepository;

	@Override
	public List<Blog> findAll() {
		return blogRepository.findAll();
	}

	@Override
	public Blog findById(Integer id) {
		return blogRepository.findById(id).get();
	}

	@Override
	public List<BlogDTO> findAllByStatus(Boolean status) {
		// Lấy danh sách blog có status = true
		List<Blog> blogs = blogRepository.findAllByStatus(status);
		// Chuyển đổi danh sách các đối tượng Blog thành danh sách BlogDTO
		List<BlogDTO> blogDTOs = new ArrayList<>();
		for (Blog blog : blogs) {
			BlogDTO blogDTO = new BlogDTO();
			// Thực hiện sao chép các thuộc tính từ đối tượng Blog sang đối tượng BlogDTO
			blogDTO.setId(blog.getId());
			blogDTO.setTittle(blog.getTittle());
			blogDTO.setContent(blog.getContent());
			blogDTO.setImage(blog.getImage());
			blogDTO.setCreateDate(blog.getCreateDate());
			blogDTO.setUsername(blog.getAccount().getFullname());
			blogDTO.setCommentCount(blog.getCommentCount());
			// Sao chép các thuộc tính khác tùy thuộc vào yêu cầu của bạn

			blogDTOs.add(blogDTO);
		}
		// Sắp xếp danh sách BlogDTO theo thời gian mới nhất
		Collections.sort(blogDTOs, new Comparator<BlogDTO>() {
			@Override
			public int compare(BlogDTO blogDTO1, BlogDTO blogDTO2) {
				return blogDTO2.getCreateDate().compareTo(blogDTO1.getCreateDate());
			}
		});
		return blogDTOs;
	}

	@Override
	public List<BlogDTO> findTop4BlogsByCommentCountAndStatus() {
		// Lấy danh sách blog có status = true
		List<Blog> blogs = blogRepository.findTop4BlogsByCommentCountAndStatus();
		// Chuyển đổi danh sách các đối tượng Blog thành danh sách BlogDTO
		List<BlogDTO> blogDTOs = new ArrayList<>();
		for (Blog blog : blogs) {
			BlogDTO blogDTO = new BlogDTO();
			// Thực hiện sao chép các thuộc tính từ đối tượng Blog sang đối tượng BlogDTO
			blogDTO.setId(blog.getId());
			blogDTO.setTittle(blog.getTittle());
			blogDTO.setContent(blog.getContent());
			blogDTO.setImage(blog.getImage());
			blogDTO.setCreateDate(blog.getCreateDate());
			blogDTO.setUsername(blog.getAccount().getFullname());
			blogDTO.setCommentCount(blog.getCommentCount());
			// Sao chép các thuộc tính khác tùy thuộc vào yêu cầu của bạn

			blogDTOs.add(blogDTO);
		}
		return blogDTOs;
	}

}
