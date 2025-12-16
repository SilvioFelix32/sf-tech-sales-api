package com.sftech.sales.application.service;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.application.dto.SaleItemDTO;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.entity.SaleItem;
import com.sftech.sales.domain.enums.PaymentMethod;
import com.sftech.sales.domain.exception.BadRequestException;
import com.sftech.sales.domain.exception.SaleNotFoundException;
import com.sftech.sales.application.port.out.SaleMapperPort;
import com.sftech.sales.domain.port.out.SaleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SaleService Tests")
class SaleServiceTest {

    @Mock
    private SaleRepositoryPort saleRepository;

    @Mock
    private SaleMapperPort saleMapper;

    @InjectMocks
    private SaleService saleService;

    private String companyId;
    private String userId;
    private String saleId;
    private Sale sale;
    private SaleDTO saleDTO;
    private SaleItemDTO saleItemDTO;

    @BeforeEach
    void setUp() {
        companyId = "company-123";
        userId = "user-456";
        saleId = "sale-789";

        saleItemDTO = createSaleItemDTO();
        saleDTO = createSaleDTO();
        sale = createSale();
    }

    private SaleItemDTO createSaleItemDTO() {
        SaleItemDTO item = new SaleItemDTO();
        item.setCategory_id("category-1");
        item.setProduct_id("product-1");
        item.setSku("SKU-001");
        item.setTitle("Product Title");
        item.setSubtitle("Product Subtitle");
        item.setDescription("Product Description");
        item.setUrl_banner("https://example.com/banner.jpg");
        item.setQuantity(2);
        item.setTotal_value(100.0);
        return item;
    }

    private SaleDTO createSaleDTO() {
        SaleDTO dto = new SaleDTO();
        dto.setTotal(100.0);
        dto.setPayment_method("CREDIT_CARD");
        dto.setDeliver_address("123 Main Street, City, State");
        List<SaleItemDTO> items = new ArrayList<>();
        items.add(saleItemDTO);
        dto.setItems(items);
        return dto;
    }

    private Sale createSale() {
        SaleItem saleItem = new SaleItem();
        saleItem.setSaleItemId("item-1");
        saleItem.setCategoryId("category-1");
        saleItem.setProductId("product-1");
        saleItem.setSku("SKU-001");
        saleItem.setTitle("Product Title");
        saleItem.setSubtitle("Product Subtitle");
        saleItem.setDescription("Product Description");
        saleItem.setUrlBanner("https://example.com/banner.jpg");
        saleItem.setQuantity(2);
        saleItem.setTotalValue(100.0);

        Sale s = new Sale();
        s.setSaleId(saleId);
        s.setCompanyId(companyId);
        s.setUserId(userId);
        s.setTotal(100.0);
        s.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        s.setDeliverAddress("123 Main Street, City, State");
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        s.setItems(List.of(saleItem));
        return s;
    }

    private SaleDTO createExpectedDTO() {
        SaleDTO dto = new SaleDTO();
        dto.setSale_id(saleId);
        dto.setCompany_id(companyId);
        dto.setUser_id(userId);
        dto.setTotal(100.0);
        dto.setPayment_method("CREDIT_CARD");
        dto.setDeliver_address("123 Main Street, City, State");
        return dto;
    }

    @Test
    @DisplayName("Should create sale successfully")
    void shouldCreateSaleSuccessfully() {
        SaleDTO expectedDTO = createExpectedDTO();
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(expectedDTO);

        SaleDTO result = saleService.createSale(companyId, userId, saleDTO);

        assertEquals(saleId, result.getSale_id());
        assertEquals(companyId, result.getCompany_id());
        assertEquals(userId, result.getUser_id());
        assertEquals(100.0, result.getTotal());
        assertEquals("CREDIT_CARD", result.getPayment_method());
        assertEquals("123 Main Street, City, State", result.getDeliver_address());
        verify(saleRepository).save(any(Sale.class));
        verify(saleMapper).toDTO(any(Sale.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should throw BadRequestException when companyId is invalid")
    void shouldThrowBadRequestExceptionWhenCompanyIdIsInvalid(String invalidCompanyId) {
        assertThrows(BadRequestException.class, () -> 
            saleService.createSale(invalidCompanyId, userId, saleDTO));
        verify(saleRepository, never()).save(any(Sale.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should throw BadRequestException when userId is invalid")
    void shouldThrowBadRequestExceptionWhenUserIdIsInvalid(String invalidUserId) {
        assertThrows(BadRequestException.class, () -> 
            saleService.createSale(companyId, invalidUserId, saleDTO));
        verify(saleRepository, never()).save(any(Sale.class));
    }

    @Test
    @DisplayName("Should throw BadRequestException when saleDTO is null")
    void shouldThrowBadRequestExceptionWhenSaleDTOIsNull() {
        assertThrows(BadRequestException.class, () -> 
            saleService.createSale(companyId, userId, null));
        verify(saleRepository, never()).save(any(Sale.class));
    }

    @Test
    @DisplayName("Should throw BadRequestException when saleDTO items is null or empty")
    void shouldThrowBadRequestExceptionWhenSaleDTOItemsIsInvalid() {
        saleDTO.setItems(null);
        assertThrows(BadRequestException.class, () -> 
            saleService.createSale(companyId, userId, saleDTO));

        saleDTO.setItems(new ArrayList<>());
        assertThrows(BadRequestException.class, () -> 
            saleService.createSale(companyId, userId, saleDTO));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should throw BadRequestException when categoryId is invalid")
    void shouldThrowBadRequestExceptionWhenCategoryIdIsInvalid(String invalidCategoryId) {
        saleItemDTO.setCategory_id(invalidCategoryId);
        assertThrows(BadRequestException.class, () -> 
            saleService.createSale(companyId, userId, saleDTO));
    }

    @Test
    @DisplayName("Should get sales by user from database")
    void shouldGetSalesByUserFromDatabase() {
        SaleDTO dto = createExpectedDTO();
        when(saleRepository.findSalesByUser(companyId, userId)).thenReturn(List.of(sale));
        when(saleMapper.toDTO(sale)).thenReturn(dto);

        List<SaleDTO> result = saleService.getSalesByUser(companyId, userId);

        assertEquals(1, result.size());
        assertEquals(saleId, result.get(0).getSale_id());
        verify(saleRepository).findSalesByUser(companyId, userId);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should throw BadRequestException when getting sales by user with invalid companyId")
    void shouldThrowBadRequestExceptionWhenGettingSalesByUserWithInvalidCompanyId(String invalidCompanyId) {
        assertThrows(BadRequestException.class, () -> 
            saleService.getSalesByUser(invalidCompanyId, userId));
        verify(saleRepository, never()).findSalesByUser(anyString(), anyString());
    }

    @Test
    @DisplayName("Should throw BadRequestException when getting sales by user with null userId")
    void shouldThrowBadRequestExceptionWhenGettingSalesByUserWithNullUserId() {
        assertThrows(BadRequestException.class, () -> 
            saleService.getSalesByUser(companyId, null));
        verify(saleRepository, never()).findSalesByUser(anyString(), anyString());
    }

    @Test
    @DisplayName("Should get sales by company from database")
    void shouldGetSalesByCompanyFromDatabase() {
        SaleDTO dto = createExpectedDTO();
        when(saleRepository.findByCompanyId(companyId)).thenReturn(List.of(sale));
        when(saleMapper.toDTO(sale)).thenReturn(dto);

        List<SaleDTO> result = saleService.getSalesByCompany(companyId);

        assertEquals(1, result.size());
        assertEquals(saleId, result.get(0).getSale_id());
        verify(saleRepository).findByCompanyId(companyId);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should throw BadRequestException when getting sales by company with invalid companyId")
    void shouldThrowBadRequestExceptionWhenGettingSalesByCompanyWithInvalidCompanyId(String invalidCompanyId) {
        assertThrows(BadRequestException.class, () -> 
            saleService.getSalesByCompany(invalidCompanyId));
        verify(saleRepository, never()).findByCompanyId(anyString());
    }

    @Test
    @DisplayName("Should get sale by id successfully")
    void shouldGetSaleByIdSuccessfully() {
        when(saleRepository.findSaleById(companyId, saleId)).thenReturn(Optional.of(sale));

        Sale result = saleService.getSaleById(companyId, saleId);

        assertEquals(saleId, result.getSaleId());
        assertEquals(companyId, result.getCompanyId());
        assertEquals(userId, result.getUserId());
        assertEquals(PaymentMethod.CREDIT_CARD, result.getPaymentMethod());
        assertEquals("123 Main Street, City, State", result.getDeliverAddress());
        verify(saleRepository).findSaleById(companyId, saleId);
    }

    @Test
    @DisplayName("Should throw SaleNotFoundException when sale not found")
    void shouldThrowSaleNotFoundExceptionWhenSaleNotFound() {
        when(saleRepository.findSaleById(companyId, saleId)).thenReturn(Optional.empty());

        assertThrows(SaleNotFoundException.class, () -> 
            saleService.getSaleById(companyId, saleId));
        verify(saleRepository).findSaleById(companyId, saleId);
    }

    @Test
    @DisplayName("Should delete sale successfully")
    void shouldDeleteSaleSuccessfully() {
        when(saleRepository.findSaleById(companyId, saleId)).thenReturn(Optional.of(sale));
        doNothing().when(saleRepository).delete(sale);

        saleService.deleteSale(companyId, saleId);

        verify(saleRepository).delete(sale);
    }

    @Test
    @DisplayName("Should throw SaleNotFoundException when deleting non-existent sale")
    void shouldThrowSaleNotFoundExceptionWhenDeletingNonExistentSale() {
        when(saleRepository.findSaleById(companyId, saleId)).thenReturn(Optional.empty());

        assertThrows(SaleNotFoundException.class, () -> 
            saleService.deleteSale(companyId, saleId));
        verify(saleRepository, never()).delete(any(Sale.class));
    }

    @Test
    @DisplayName("Should handle exception when creating sale")
    void shouldHandleExceptionWhenCreatingSale() {
        when(saleRepository.save(any(Sale.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> saleService.createSale(companyId, userId, saleDTO));
    }

    @Test
    @DisplayName("Should handle exception when getting sales by user")
    void shouldHandleExceptionWhenGettingSalesByUser() {
        when(saleRepository.findSalesByUser(companyId, userId)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> saleService.getSalesByUser(companyId, userId));
    }

    @Test
    @DisplayName("Should handle exception when getting sales by company")
    void shouldHandleExceptionWhenGettingSalesByCompany() {
        when(saleRepository.findByCompanyId(companyId)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> saleService.getSalesByCompany(companyId));
    }

    @Test
    @DisplayName("Should handle exception when getting sale by id")
    void shouldHandleExceptionWhenGettingSaleById() {
        when(saleRepository.findSaleById(companyId, saleId)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> saleService.getSaleById(companyId, saleId));
    }

    @Test
    @DisplayName("Should handle exception when deleting sale")
    void shouldHandleExceptionWhenDeletingSale() {
        when(saleRepository.findSaleById(companyId, saleId)).thenReturn(Optional.of(sale));
        doThrow(new RuntimeException("Database error")).when(saleRepository).delete(sale);
        assertThrows(RuntimeException.class, () -> saleService.deleteSale(companyId, saleId));
    }

    @Test
    @DisplayName("Should return empty list when getting sales by user with no results")
    void shouldReturnEmptyListWhenGettingSalesByUserWithNoResults() {
        when(saleRepository.findSalesByUser(companyId, userId)).thenReturn(new ArrayList<>());

        List<SaleDTO> result = saleService.getSalesByUser(companyId, userId);

        assertTrue(result.isEmpty());
        verify(saleRepository).findSalesByUser(companyId, userId);
    }

    @Test
    @DisplayName("Should return empty list when getting sales by company with no results")
    void shouldReturnEmptyListWhenGettingSalesByCompanyWithNoResults() {
        when(saleRepository.findByCompanyId(companyId)).thenReturn(new ArrayList<>());

        List<SaleDTO> result = saleService.getSalesByCompany(companyId);

        assertTrue(result.isEmpty());
        verify(saleRepository).findByCompanyId(companyId);
    }

    @Test
    @DisplayName("Should create sale with DEBIT_CARD payment method")
    void shouldCreateSaleWithDebitCardPaymentMethod() {
        saleDTO.setPayment_method("DEBIT_CARD");
        Sale saleWithDebitCard = createSale();
        saleWithDebitCard.setPaymentMethod(PaymentMethod.DEBIT_CARD);
        SaleDTO expectedDTO = createExpectedDTO();
        expectedDTO.setPayment_method("DEBIT_CARD");
        
        when(saleRepository.save(any(Sale.class))).thenReturn(saleWithDebitCard);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(expectedDTO);

        SaleDTO result = saleService.createSale(companyId, userId, saleDTO);

        assertEquals("DEBIT_CARD", result.getPayment_method());
        verify(saleRepository).save(argThat(s -> s.getPaymentMethod() == PaymentMethod.DEBIT_CARD));
    }

    @Test
    @DisplayName("Should create sale with PIX payment method")
    void shouldCreateSaleWithPixPaymentMethod() {
        saleDTO.setPayment_method("PIX");
        Sale saleWithPix = createSale();
        saleWithPix.setPaymentMethod(PaymentMethod.PIX);
        SaleDTO expectedDTO = createExpectedDTO();
        expectedDTO.setPayment_method("PIX");
        
        when(saleRepository.save(any(Sale.class))).thenReturn(saleWithPix);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(expectedDTO);

        SaleDTO result = saleService.createSale(companyId, userId, saleDTO);

        assertEquals("PIX", result.getPayment_method());
        verify(saleRepository).save(argThat(s -> s.getPaymentMethod() == PaymentMethod.PIX));
    }

    @Test
    @DisplayName("Should create sale with BANK_SLIP payment method")
    void shouldCreateSaleWithBankSlipPaymentMethod() {
        saleDTO.setPayment_method("BANK_SLIP");
        Sale saleWithBankSlip = createSale();
        saleWithBankSlip.setPaymentMethod(PaymentMethod.BANK_SLIP);
        SaleDTO expectedDTO = createExpectedDTO();
        expectedDTO.setPayment_method("BANK_SLIP");
        
        when(saleRepository.save(any(Sale.class))).thenReturn(saleWithBankSlip);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(expectedDTO);

        SaleDTO result = saleService.createSale(companyId, userId, saleDTO);

        assertEquals("BANK_SLIP", result.getPayment_method());
        verify(saleRepository).save(argThat(s -> s.getPaymentMethod() == PaymentMethod.BANK_SLIP));
    }

    @Test
    @DisplayName("Should handle case-insensitive payment method conversion")
    void shouldHandleCaseInsensitivePaymentMethodConversion() {
        saleDTO.setPayment_method("credit_card");
        Sale saleWithLowercase = createSale();
        saleWithLowercase.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        SaleDTO expectedDTO = createExpectedDTO();
        expectedDTO.setPayment_method("CREDIT_CARD");
        
        when(saleRepository.save(any(Sale.class))).thenReturn(saleWithLowercase);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(expectedDTO);

        SaleDTO result = saleService.createSale(companyId, userId, saleDTO);

        assertEquals("CREDIT_CARD", result.getPayment_method());
        verify(saleRepository).save(argThat(s -> s.getPaymentMethod() == PaymentMethod.CREDIT_CARD));
    }

    @Test
    @DisplayName("Should handle invalid payment method by setting it to null")
    void shouldHandleInvalidPaymentMethodBySettingItToNull() {
        saleDTO.setPayment_method("INVALID_METHOD");
        Sale saleWithNullPayment = createSale();
        saleWithNullPayment.setPaymentMethod(null);
        SaleDTO expectedDTO = createExpectedDTO();
        expectedDTO.setPayment_method(null);
        
        when(saleRepository.save(any(Sale.class))).thenReturn(saleWithNullPayment);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(expectedDTO);

        SaleDTO result = saleService.createSale(companyId, userId, saleDTO);

        assertNull(result.getPayment_method());
        verify(saleRepository).save(argThat(s -> s.getPaymentMethod() == null));
    }

    @Test
    @DisplayName("Should handle null payment method")
    void shouldHandleNullPaymentMethod() {
        saleDTO.setPayment_method(null);
        Sale saleWithNullPayment = createSale();
        saleWithNullPayment.setPaymentMethod(null);
        SaleDTO expectedDTO = createExpectedDTO();
        expectedDTO.setPayment_method(null);
        
        when(saleRepository.save(any(Sale.class))).thenReturn(saleWithNullPayment);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(expectedDTO);

        SaleDTO result = saleService.createSale(companyId, userId, saleDTO);

        assertNull(result.getPayment_method());
        verify(saleRepository).save(argThat(s -> s.getPaymentMethod() == null));
    }
}

