package com.gamevh.restcontroller;





import java.io.IOException;



import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.http.MediaType;

import org.eclipse.jetty.http.HttpStatus;

import com.gamevh.dto.ProductAdminDTO;
import com.gamevh.dto.ProductDTO;
import com.gamevh.dto.impl.ProductAdminDTOImpl;
import com.gamevh.dto.impl.ProductDTOImpl;
import com.gamevh.entities.Category;

import com.gamevh.entities.Feedback;
import com.gamevh.entities.OrderDetail;
import com.gamevh.entities.Product;

import com.gamevh.repository.FeedbackRepository;
import com.gamevh.repository.OrderDetailRepository;
import com.gamevh.service.CategoryService;
import com.gamevh.service.FeedbackService;
import com.gamevh.service.GoogleDriveService;
import com.gamevh.service.ProductService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;




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
	FeedbackRepository feedbackRepository;

	@Autowired
	FeedbackService feedBackService;
	
	@Autowired
	private GoogleDriveService driveService;
	
	@GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
	
	@GetMapping("/getAllFeedback")
    public ResponseEntity<List<Feedback>> getAllfeedback() {
        List<Feedback> feedback = feedBackService.findAll();
        return ResponseEntity.ok(feedback);
    }
	
	@GetMapping("/getProduct/{productId}")
	public ResponseEntity<List<Feedback>> getAllFeedBackByProductId(@PathVariable("productId") Integer productId) {
		List<Feedback> listFeedBacks = feedBackService.findByAllProductId(productId);
		// Order By Z-A CreateDate
		Comparator<Feedback> dateComparator = Comparator.comparing(Feedback::getCreateDate);
		Collections.sort(listFeedBacks, dateComparator.reversed());
		return ResponseEntity.ok(listFeedBacks);
	}

	@GetMapping("getProductDTO/{productId}")
	public ResponseEntity<ProductDTO> getProductDTOById(@PathVariable("productId") Integer productId) {
		return ResponseEntity.ok(convertProductToProductDTO(productService.findById(productId)));
	}
	
	@GetMapping("getProductAdminDTO/{productId}")
	public ResponseEntity<ProductAdminDTO> getProductAdminDTOById(@PathVariable("productId") Integer productId) {
		return ResponseEntity.ok(convertProductToProductAdminDTO(productService.findById(productId)));
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
						product.getThumbnail(),product.getOriginPrice(), product.getSalePrice(), product.getOffer(), product.getDetails(),
						avgStar, countFeedBack, product.getCategory().getName(), product.getCategory().getCategoryId(),
						product.getType(), product.getCreateDate());
				return productDTO;
			}
		}
		return null;
	}
	
	
	
	@SuppressWarnings("unused")
	private ProductAdminDTO convertProductToProductAdminDTO(Product product) {
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

	            ProductAdminDTO productAdminDTO = new ProductAdminDTOImpl(
	                    product.getId(), 
	                    product.getName(), 
	                    product.getPoster(), 
	                    product.getThumbnail(),
	                    product.getOriginPrice(), 
	                    product.getSalePrice(), 
	                    product.getOffer(),
	                    product.getAvailable(),
	                    product.getSource(),  
	                    product.getLink(), 
	                    product.getQty(), 
	                    product.getStatus(), 
	                    product.getDetails(),
	                    avgStar, 
	                    countFeedBack, 
	                    product.getCategory().getName(), 
	                    product.getCategory().getCategoryId(),
	                    product.getType(), 
	                    product.getCreateDate()
	            );
	            return productAdminDTO;
	        }
	    }
	    return null;
	}
	
	@PutMapping("/updateFeedback")
	public ResponseEntity<Feedback> updateFeedbackStatus(@RequestBody Feedback feedback) {
	    
	    // Nếu feedback có ID cung cấp không tồn tại, trả về phản hồi không tìm thấy.
	    return ResponseEntity.notFound().build();
	}


	
	@PostMapping("createProduct")
	public ResponseEntity<HttpStatus> createProduct(@Validated @ModelAttribute ProductAdminDTOImpl dto,
	                                                @RequestParam("posters") List<MultipartFile> posters,
	                                                @RequestParam("thumbnails") List<MultipartFile> thumbnails) {
	    if (dto != null) {
	        // Tạo mới sản phẩm và lưu vào cơ sở dữ liệu
	        Product product = new Product();
	        product.setName(dto.getName());
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
	        category.setCategoryId(dto.getCategoryId());
	        category.setName(dto.getCategoryName());
	        category.setType(dto.getType());

	        product.setCategory(category);

	        try {
	            if (!posters.isEmpty()) {
	                MultipartFile poster = posters.get(0); // Get the first poster from the list
	                ResponseEntity<Object> response = uploadImage(poster);
	                if (String.valueOf(response.getStatusCode()).equals("200")) {
	                    String posterFileId = (String) response.getBody();
	                    product.setPoster(posterFileId);
	                } else {
	                    // Xử lý lỗi khi tải ảnh poster lên Google Drive
	                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
	                }
	            }
	            if (!thumbnails.isEmpty()) {
	                List<String> thumbnailFileIds = new ArrayList<>();
	                for (MultipartFile thumbnail : thumbnails) {
	                    ResponseEntity<Object> response = uploadImage(thumbnail);
	                    if (String.valueOf(response.getStatusCode()).equals("200")) {
	                        String thumbnailFileId = (String) response.getBody();
	                        if (thumbnailFileId != null) {
	                            thumbnailFileIds.add(thumbnailFileId);
	                        }
	                    } else {
	                        // Xử lý lỗi khi tải ảnh thumbnail lên Google Drive
	                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
	                    }
	                }
	                String thumbnailKeys = String.join("***", thumbnailFileIds);
	                product.setThumbnail(thumbnailKeys);
	            }
	        } catch (GeneralSecurityException e) {
	            // Xử lý lỗi khi tải ảnh lên Google Drive
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
	        }

	        productService.createProduct(product);

	        return ResponseEntity.ok().build();
	    }
	    return ResponseEntity.badRequest().build();
	}





	@PostMapping("upload")
	public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile image)
	        throws GeneralSecurityException {
	    try {
	        if (!image.isEmpty()) {
	        	
	            String fileName = image.getOriginalFilename();
	            String mimeType = image.getContentType();
	            String folderId = "1mMOXDZOQvQVs2MvJJF77UUpACbkfp5sv"; // ID của thư mục trên Google Drive để lưu file
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

	@PostMapping("updateProduct/{id}")
	public ResponseEntity<HttpStatus> updateProduct(@PathVariable Integer id, @Validated @RequestBody ProductAdminDTOImpl dto, @RequestParam(required = false) MultipartFile poster, @RequestParam(required = false) List<MultipartFile> thumbnails) {
	    Product product = productService.findById(id);
	    if (product != null) {
	        // Cập nhật các thuộc tính của sản phẩm dựa trên ProductAdminDTOImpl
	        product.setName(dto.getName());
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

	        // Cập nhật Category trong Product
	        product.getCategory().setCategoryId(dto.getCategoryId());

	        // Kiểm tra nếu người dùng thay đổi ảnh poster
	        if (poster != null) {
	            // Tải ảnh mới lên Google Drive và nhận lại ID của file
	            try {
	                String newPosterId = uploadImagedrive(poster);
	                product.setPoster(newPosterId);
	            } catch (Exception e) {
	                // Xử lý lỗi khi tải ảnh lên Google Drive
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
	            }
	        }

	        // Kiểm tra nếu người dùng thay đổi ảnh thumbnail
	        if (thumbnails != null && !thumbnails.isEmpty()) {
	            List<String> thumbnailIds = new ArrayList<>();
	            for (MultipartFile thumbnail : thumbnails) {
	                // Tải từng ảnh thumbnail lên Google Drive và nhận lại ID của file
	                try {
	                    String newThumbnailId = uploadImagedrive(thumbnail);
	                    thumbnailIds.add(newThumbnailId);
	                } catch (Exception e) {
	                    // Xử lý lỗi khi tải ảnh lên Google Drive
	                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
	                }
	            }
	            String thumbnailIdsString = String.join("***", thumbnailIds);
	            product.setThumbnail(thumbnailIdsString);
	        }


	        productService.updateProduct(product);

	        return ResponseEntity.ok().build();
	    }
	    return ResponseEntity.notFound().build();
	}

	@SuppressWarnings("unused")
	private String uploadImagedrive(MultipartFile image) throws Exception {
	    String fileName = image.getOriginalFilename();
	    String mimeType = image.getContentType();
	    String folderId = "1xbZ557bXhtiEG-sPP4TRXf007THuPsns"; // ID của thư mục trên Google Drive để lưu file

	    return driveService.uploadFile(image, fileName, mimeType, folderId);
	}
	
//	@GetMapping("/search")
//	public ResponseEntity<List<Product>> searchProducts(
//	        @RequestParam(value = "productName", required = false) String productName,
//	        @RequestParam(value = "productType", required = false) String productType,
//	        @RequestParam(value = "categoryName", required = false) String categoryName) {
//
//	    List<Product> searchResults = productService.searchByProductNameAndProductTypeAndCategoryName(productName, productType, categoryName);
//
//	    return ResponseEntity.ok(searchResults);
//	}

	
	@GetMapping("/downloadExcel")
	public void downloadExcel(HttpServletResponse response) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Report Sản phẩm");

			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = currentDate.format(formatter);
			
			// Tạo một CellStyle cho cột kiểu số (number)
			CellStyle numberCellStyle = workbook.createCellStyle();
			numberCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));

			List<Object[]> productList = productService.getReportProduct();
			int dataRowIndex = 5; // Start from row 5 after the headers

			Map<Integer, Object[]> data = new TreeMap<>();
			data.put(1, new Object[] { "Danh Sách Sản Phẩm của cửa hàng" });
			data.put(2, new Object[] { "Ngày tạo:", formattedDate });
			data.put(3, new Object[] {});
			data.put(4, new Object[] { "STT","Id Sản phẩm", "Tên Sản Phẩm","Trạng Thái", "Ngày tạo",
					"Nhãn Hàng", "Đường dẫn sản phẩm", "Thông tin sản phẩm", "Loại sản phẩm"});

			for (int i = 0; i < productList.size(); i++) {
				Object[] ProductData = productList.get(i);
				
				System.out.println("ProductData = " + Arrays.toString(ProductData));

				
				int productId = Integer.parseInt(String.valueOf(ProductData[0]));
				String Name = String.valueOf(ProductData[1]);
//				double originPrice = (double) ProductData[4];
//
//	
//				double salePrice = (double) ProductData[5];
//
//				
//				double offer = (double) ProductData[6];
				
				LocalDate startDate = null;
				String startDateString = String.valueOf(ProductData[7]);
				if (!startDateString.equalsIgnoreCase("null") && !startDateString.isEmpty()) {
					startDate = LocalDate.parse(startDateString);
				}
				
				String source = String.valueOf(ProductData[9]);

				String link = String.valueOf(ProductData[10]);
				String details = String.valueOf(ProductData[11]);
				String type = String.valueOf(ProductData[14]);
				
				
				boolean status = (boolean) ProductData[8];
				String statusString = status ? "Còn hàng" : "Hết hàng";


				data.put(dataRowIndex, new Object[] { dataRowIndex - 4, productId, Name, statusString,
						startDate,source,link,details,type });
				dataRowIndex++;
			}

			long totalEmployees = productList.size();
			long totalProductsAvailable = productList.stream()
				    .filter(ProductData -> Boolean.parseBoolean(String.valueOf(ProductData[8]))) // Lọc các sản phẩm có trạng thái true (còn hàng)
				    .count();

				long totalProductsOutOfStock = productList.stream()
				    .filter(ProductData -> !Boolean.parseBoolean(String.valueOf(ProductData[8]))) // Lọc các sản phẩm có trạng thái false (hết hàng)
				    .count();

			int lastDataRowIndex = dataRowIndex - 1;
			int startRow = lastDataRowIndex + 2;
			

			// Thêm dữ liệu vào trang tính (sheet)
			for (Map.Entry<Integer, Object[]> entry : data.entrySet()) {
			    Integer rowNum = entry.getKey();
			    Object[] rowData = entry.getValue();

			    Row row = sheet.createRow(rowNum);

			    for (int i = 0; i < rowData.length; i++) {
			        Cell cell = row.createCell(i);
			        Object value = rowData[i];

			        if (value == null) {
			            cell.setCellValue("");
			        } else if (value instanceof String) {
			            cell.setCellValue((String) value);
			        } else if (value instanceof Integer) {
			            cell.setCellValue((Integer) value);
			        } else if (value instanceof LocalDate) {
			            cell.setCellValue(java.sql.Date.valueOf((LocalDate) value));
			            CellStyle dateStyle = workbook.createCellStyle();
			            dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
			            cell.setCellStyle(dateStyle);
			        }

			        
			    }
			}

			// Điều chỉnh chiều rộng cột
			sheet.setColumnWidth(0, 4000);
			sheet.setColumnWidth(1, 4000); 
			sheet.setColumnWidth(2, 8000); 
			sheet.setColumnWidth(3, 4000); 
			sheet.setColumnWidth(4, 8000); 
			sheet.setColumnWidth(5, 6000); 
			sheet.setColumnWidth(6, 50000); 
			sheet.setColumnWidth(7, 50000); 
			sheet.setColumnWidth(8, 4000); 
			sheet.setColumnWidth(9, 6000); 
			sheet.setColumnWidth(10, 4000); 
			sheet.setColumnWidth(11, 6000); 

			// Thêm phần ghi kết quả tính vào file Excel
			int summaryRowIndex = startRow + 2;
			Row summaryRow = sheet.createRow(summaryRowIndex);
			summaryRow.createCell(0).setCellValue("Tổng số nhân viên:");
			summaryRow.createCell(2).setCellValue(totalEmployees);

			summaryRow = sheet.createRow(summaryRowIndex + 1);
			summaryRow.createCell(0).setCellValue("Tổng số sản phẩm còn hàng");
			summaryRow.createCell(2).setCellValue(totalProductsAvailable);
			
			summaryRow = sheet.createRow(summaryRowIndex + 2);
			summaryRow.createCell(0).setCellValue("Tổng số sản phẩm hết hàng");
			summaryRow.createCell(2).setCellValue(totalProductsOutOfStock);

			for (int i = 0; i <= lastDataRowIndex + 1; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j <= 9; j++) {
						Cell cell = row.getCell(j);
						if (cell != null) {
							XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							cell.setCellStyle(cellStyle);
						}
					}
				}
			}

			// Áp dụng kiểu cho hàng đầu và dòng dữ liệu
			XSSFCellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerStyle.setFont(headerFont);

			XSSFCellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setWrapText(true);

			// Ghi workbook vào luồng HttpServletResponse output
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=ReportNhanVien.xlsx");

			try (ServletOutputStream out = response.getOutputStream()) {
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
