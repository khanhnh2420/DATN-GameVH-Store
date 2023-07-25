package com.gamevh.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
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
	public List<BlogDTO> findAll() {
		// Lấy danh sách các đối tượng Blog từ repository
		List<Blog> blogEntities = blogRepository.findAll();

		// Sắp xếp danh sách các đối tượng Blog theo ngày đăng mới nhất (giảm dần)
		blogEntities.sort(Comparator.comparing(Blog::getCreateDate, Comparator.nullsLast(Comparator.reverseOrder())));

		// Chuyển đổi danh sách các đối tượng Blog sang danh sách các đối tượng BlogDTO
		List<BlogDTO> blogDTOs = blogEntities.stream().map(blogEntity -> {
			BlogDTO dto = new BlogDTO();
			BeanUtils.copyProperties(blogEntity, dto);
			dto.setUsername(blogEntity.getAccount().getUsername());
			return dto;
		}).collect(Collectors.toList());

		// Trả về danh sách blogDTOs chứa các đối tượng BlogDTO đã sao chép và sắp xếp
		// theo ngày đăng mới nhất
		return blogDTOs;
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
			blogDTO.setTitle(blog.getTitle());
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
			blogDTO.setTitle(blog.getTitle());
			blogDTO.setContent(blog.getContent());
			blogDTO.setImage(blog.getImage());
			blogDTO.setCreateDate(blog.getCreateDate());
			blogDTO.setUsername(blog.getAccount().getFullname());
			blogDTO.setCommentCount(blog.getCommentCount());

			blogDTOs.add(blogDTO);
		}
		return blogDTOs;
	}

	@Override
	public BlogDTO findById(Integer id) {
		Blog blog = blogRepository.findByIdAndStatus(id, true).get();
		BlogDTO blogDTO = new BlogDTO();
		BeanUtils.copyProperties(blog, blogDTO);
		blogDTO.setUsername(blog.getAccount().getFullname());
		return blogDTO;
	}

}
