package com.gamevh.restcontroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;

import org.springframework.http.MediaType;
import org.eclipse.jetty.http.HttpStatus;

import com.gamevh.dto.ProductDTO;
import com.gamevh.dto.impl.ProductDTOImpl;
import com.gamevh.entities.Category;
import com.gamevh.entities.Feedback;
import com.gamevh.entities.OrderDetail;
import com.gamevh.entities.Product;
import com.gamevh.reponsitory.OrderDetailRepository;
import com.gamevh.service.CategoryService;
import com.gamevh.service.FeedbackService;
import com.gamevh.service.GoogleDriveService;
import com.gamevh.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductRC {
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;

	@Autowired
	OrderDetailRepository orderDetailsDAO;

	@Autowired
	FeedbackService feedBackService;
	
	@Autowired
	private GoogleDriveService driveService;
	
	@GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

	@GetMapping("getProductDTO/{productId}")
	public ResponseEntity<ProductDTO> getProductDTOById(@PathVariable("productId") Integer productId) {
		return ResponseEntity.ok(convertProductToProductDTO(productService.findById(productId)));
	}

	@GetMapping("getAllProductDTO")
	public ResponseEntity<List<ProductDTO>> getAllProductDTO() {
		List<ProductDTO> listProductFindAllDTO = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getAvailable()) {
				listProductFindAllDTO.add(convertProductToProductDTO(product));
			}
		}
		return ResponseEntity.ok(listProductFindAllDTO);
	}

	@GetMapping("getAllProductDTO/{type}")
	public ResponseEntity<List<ProductDTO>> getAllProductDTOByType(@PathVariable("type") String type) {
		List<ProductDTO> listProductFindAllDTO = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase(type)) {
				if (product.getAvailable()) {
					listProductFindAllDTO.add(convertProductToProductDTO(product));
				}
			}
		}
		return ResponseEntity.ok(listProductFindAllDTO);
	}

	@GetMapping("getAllProductDTO/{type}/{category}")
	public ResponseEntity<List<ProductDTO>> getAllProductDTOByTypeAndCategory(@PathVariable("type") String type,
			@PathVariable("category") String category) {
		List<ProductDTO> listProductFindAllDTO = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase(type)) {
				if (product.getCategory().getCategoryId().equalsIgnoreCase(category)) {
					if (product.getAvailable()) {
						listProductFindAllDTO.add(convertProductToProductDTO(product));
					}
				}

			}
		}
		return ResponseEntity.ok(listProductFindAllDTO);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Integer productId) {
		return ResponseEntity.ok(productService.findById(productId));
	}
	
	@GetMapping("topselling")
	public ResponseEntity<List<ProductDTO>> getTop6Selling() {
		// List Top Selling Products
		List<OrderDetail> listOrderDetail = orderDetailsDAO.findTopSellingProducts();
		List<ProductDTO> listTopSelling = new ArrayList<>();
		for (OrderDetail orderDetail : listOrderDetail) {
			if (listTopSelling.size() == 6) {
				break;
			} else {
				if (orderDetail.getProduct().getAvailable()) {
					listTopSelling.add(convertProductToProductDTO(orderDetail.getProduct()));
				}
			}
		}
		return ResponseEntity.ok(listTopSelling);
	}

	@GetMapping("newrelease")
	public ResponseEntity<List<ProductDTO>> getTop6NewRelease() {
		// List Product New Releases
		List<ProductDTO> listProductNewReleases = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getAvailable()) {
				listProductNewReleases.add(convertProductToProductDTO(product));
			}
		}
		// Order By Z-A CreateDate
		Comparator<ProductDTO> newReleasesComparator = Comparator.comparing(ProductDTO::getCreateDate);
		Collections.sort(listProductNewReleases, newReleasesComparator.reversed());
		// Get Limit 6 Product
		listProductNewReleases = listProductNewReleases.subList(0, Math.min(6, listProductNewReleases.size()));
		return ResponseEntity.ok(listProductNewReleases);
	}

	@GetMapping("toprate")
	public ResponseEntity<List<ProductDTO>> getTop6Rated() {
		// List Product TopRated
		List<ProductDTO> listProductTopRated = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getAvailable()) {
				listProductTopRated.add(convertProductToProductDTO(product));
			}
		}
		// Order By Z-A ratePoint
		Comparator<ProductDTO> newReleasesComparator = Comparator.comparing(ProductDTO::getRate);
		Collections.sort(listProductTopRated, newReleasesComparator.reversed());
		// Get Limit 6 Product

		listProductTopRated = listProductTopRated.subList(0, Math.min(6, listProductTopRated.size()));
		return ResponseEntity.ok(listProductTopRated);
	}

	@GetMapping("getListGame")
	public ResponseEntity<List<ProductDTO>> getListGame() {
		// List Product New Releases
		List<ProductDTO> listProductGame = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase("Game")) {
				if (product.getAvailable()) {
					listProductGame.add(convertProductToProductDTO(product));
				}

			}
		}

		return ResponseEntity.ok(listProductGame);
	}

	@GetMapping("getListAccessory")
	public ResponseEntity<List<ProductDTO>> getListAccessory() {
		// List Product New Releases
		List<ProductDTO> listProductAccessory = new ArrayList<>();
		List<Product> listProductFindAll = productService.findAll();
		for (Product product : listProductFindAll) {
			if (product.getType().equalsIgnoreCase("PK")) {
				if (product.getAvailable()) {
					listProductAccessory.add(convertProductToProductDTO(product));
				}
			}
		}

		return ResponseEntity.ok(listProductAccessory);
	}

	private ProductDTO convertProductToProductDTO(Product product) {
		if (product != null) {
			if (product.getAvailable()) {
				List<Feedback> listFeedBackByProduct = feedBackService.findByProductId(product.getId());
				Double sum = 0.0;
				Double avgStar = 0.0;
				Integer countFeedBack = 0;

				if (!listFeedBackByProduct.isEmpty()) {
					for (Feedback f : listFeedBackByProduct) {
						sum += f.getStar();
					}
					avgStar = sum / listFeedBackByProduct.size();
					countFeedBack = listFeedBackByProduct.size();
				}

				ProductDTO productDTO = new ProductDTOImpl(product.getId(), product.getName(), product.getPoster(), 
						product.getThumbnail(),product.getOriginPrice(), product.getSalePrice(), product.getOffer(),product.getAvailable(),
						product.getLink(), product.getSource(), product.getQty(), product.getStatus(), product.getDetails(),
						avgStar, countFeedBack, product.getCategory().getName(), product.getCategory().getCategoryId(),
						product.getType(), product.getCreateDate());
				return productDTO;
			}
		}
		return null;
	}
	
	@PostMapping("createProduct")
	public ResponseEntity<HttpStatus> createProduct(@Validated @RequestBody ProductDTOImpl dto) {
	    if (dto != null) {
	        // Tạo mới sản phẩm và lưu vào cơ sở dữ liệu
	        Product product = new Product();
	        product.setName(dto.getName());
	        product.setPoster(dto.getPoster());
	        product.setOriginPrice(dto.getOriginPrice());
	        product.setSalePrice(dto.getSalePrice());
	        product.setOffer(dto.getOffer());
	        product.setCreateDate(dto.getCreateDate());
	        product.setAvailable(dto.getAvailable());
	        product.setSource(dto.getSource());
	        product.setLink(dto.getLink());
	        product.setDetails(dto.getDetails());
	        product.setQty(dto.getQty());
	        product.setStatus(dto.getStatus());
	        product.setType(dto.getType());

	        Category category = new Category();
	        category.setId(dto.getId());
	        category.setCategoryId(dto.getCategoryId());
	        category.setName(dto.getCategoryName());
	        category.setType(dto.getType());
	        
	        product.setCategory(category);
	        
	        // Upload the image to Google Drive and get the file ID
	        String fileId = uploadImageToDrive(dto.getPoster());
	        product.setPoster(fileId);
	        
	        Product createdProduct = productService.createProduct(product);

	        // Lưu 3 hình ảnh thumbnail
	        String[] thumbnails = dto.getThumbnail().split("-\\*-");
	        for (int i = 0; i < thumbnails.length; i++) {
	            String thumbnail = thumbnails[i];
	            String fileName = saveThumbnailImage(thumbnail);
	            // Lưu đường dẫn thumbnail vào sản phẩm
	            addThumbnail(createdProduct, fileName);
	        }

	        // Cập nhật thông tin sản phẩm sau khi đã lưu đường dẫn thumbnail
	        productService.updateProduct(createdProduct);

	        return ResponseEntity.ok().build();
	    }
	    return ResponseEntity.badRequest().build();
	}
	
	private void addThumbnail(Product product, String fileName) {
	    String thumbnails = product.getThumbnail();
	    if (thumbnails == null || thumbnails.isEmpty()) {
	        thumbnails = fileName;
	    } else {
	        thumbnails += "-*-";
	        thumbnails += fileName;
	    }
	    product.setThumbnail(thumbnails);
	}
	
	private String uploadImageToDrive(String imageUrl) {
	    try {
	        URL url = new URL(imageUrl);
	        InputStream inputStream = url.openStream();
	        String fileName = UUID.randomUUID().toString(); // Generate a unique file name
	        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
	        String folderId = "1xbZ557bXhtiEG-sPP4TRXf007THuPsns"; // ID của thư mục trên Google Drive để lưu file

	        String fileId = driveService.uploadFile(inputStream, fileName, mimeType, folderId);
	        inputStream.close();
	        return fileId;
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR_500, "Error uploading image: " + e.getMessage(), e);
	    }
	}

	@PostMapping("upload")
	public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile image)
	        throws GeneralSecurityException {
	    try {
	        if (!image.isEmpty()) {
	        	
	            String fileName = image.getOriginalFilename();
	            String mimeType = image.getContentType();
	            String folderId = "1xbZ557bXhtiEG-sPP4TRXf007THuPsns"; // ID của thư mục trên Google Drive để lưu file
	            // URL example:
	            // https://drive.google.com/drive/folders/10VLW7dddQHqi4-f4ddSTqxjN9YmLFZWi
	            String fileId = driveService.uploadFile(image, fileName, mimeType, folderId);
//	            System.out.println(fileId);
	            // Xử lý thành công
	            String responseData = "{\"fileId\": \"" + fileId + "\"}";
				return ResponseEntity.ok(responseData);
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	        // Xử lý lỗi
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST_400).contentType(MediaType.APPLICATION_JSON).body("Lỗi upload hình: " + e.getMessage());
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST_400).contentType(MediaType.APPLICATION_JSON).body("Lỗi upload hình");
	    
	}
	
	
	


	private String saveThumbnailImage(String thumbnail) {
	    try {
	        // Define the target directory to store the thumbnail image
	        String targetDirectory = "thumbnails";
	        Path targetLocation = Paths.get(targetDirectory).toAbsolutePath().normalize();
	        Files.createDirectories(targetLocation);

	        // Generate a unique file name for the thumbnail
	        String fileName = UUID.randomUUID().toString() + ".jpg";
	        Path targetFile = targetLocation.resolve(fileName);

	        // Read the thumbnail image from the provided path
	        BufferedImage thumbnailImage = ImageIO.read(new File(thumbnail));

	        // Save the thumbnail image to the target directory using ImageIO
	        ImageIO.write(thumbnailImage, "jpg", targetFile.toFile());

	        // Return the file path after successful saving
	        return targetFile.toString();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        // Handle the error if necessary
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR_500, thumbnail, ex);
	    }
	}

	@PostMapping("updateProduct/{id}")
	public ResponseEntity<HttpStatus> updateProduct(@PathVariable Integer id, @Validated @RequestBody ProductDTOImpl dto) {
	    Product product = productService.findById(id);
	    if (product != null) {
	        // Update product theo productdtoimpl
	        product.setName(dto.getName());
	        product.setPoster(dto.getPoster());
	        product.setOriginPrice(dto.getOriginPrice());
	        product.setSalePrice(dto.getSalePrice());
	        product.setOffer(dto.getOffer());
	        product.setCreateDate(dto.getCreateDate());
	        product.setAvailable(dto.getAvailable());
	        product.setSource(dto.getSource());
	        product.setLink(dto.getLink());
	        product.setDetails(dto.getDetails());
	        product.setQty(dto.getQty());
	        product.setStatus(dto.getStatus());
	        product.setType(dto.getType());

	        // Update Category trong Product
//	        Category category = product.getCategory();
//	        if (category != null) {
//	            category.setCategoryId(dto.getCategoryId());
//	            category.setName(dto.getCategoryName());
//	            category.setType(dto.getType());
//	        } else {
//	            category = new Category();
//	            category.setCategoryId(dto.getCategoryId());
//	            category.setName(dto.getCategoryName());
//	            category.setType(dto.getType());
//	            product.setCategory(category);
//	        }
	        product.getCategory().setCategoryId(dto.getCategoryId());

	        productService.updateProduct(product);

	        return ResponseEntity.ok().build();
	    }
	    return ResponseEntity.notFound().build();
	}


	
}
