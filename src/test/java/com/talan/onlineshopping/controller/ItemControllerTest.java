package com.talan.onlineshopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.onlineshopping.dto.ItemCreateRequest;
import com.talan.onlineshopping.dto.ItemUpdateRequest;
import com.talan.onlineshopping.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Implements to perform Junit testing to cover code coverage
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class ItemControllerTest {
    private static final String PATH = "/api/v1";

    private MockMvc mockMvc;

    @Autowired
    private ItemController itemController;

    @Autowired
    ItemService itemService;

    @BeforeEach
    public void setup() {
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);
        itemService.saveItem(itemCreateRequest);
    }

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void testUpdateItemWith200StatusCode() throws Exception {

        String postUri = PATH + "/item";
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);

        String postInputJson = mapToJson(itemCreateRequest);
        mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(postInputJson)).andReturn();
        String uri = PATH + "/item/8";
        ItemUpdateRequest itemUpdateRequest = new ItemUpdateRequest();
        itemUpdateRequest.setPrice(9.0);
        itemUpdateRequest.setQuantity(7);

        String inputJson = mapToJson(itemUpdateRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testCreateItemWith201StatusCode() throws Exception {
        String uri = PATH + "/item";
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);

        String inputJson = mapToJson(itemCreateRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testCreateItemWith400ErrorMessage() throws Exception {
        String uri = PATH + "/item";
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        String inputJson = mapToJson(itemCreateRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testCreateListOfItemsWith201StatusCode() throws Exception {
        String uri = PATH + "/items";
        List<ItemCreateRequest> itemCreateRequestList = new ArrayList<>();
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);

        ItemCreateRequest itemCreateRequest1 = new ItemCreateRequest();
        itemCreateRequest1.setItemName("Banana");
        itemCreateRequest1.setPrice(5.0);
        itemCreateRequest1.setQuantity(5);

        itemCreateRequestList.add(itemCreateRequest);
        itemCreateRequestList.add(itemCreateRequest1);

        String inputJson = mapToJson(itemCreateRequestList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testUpdateItemWith404StatusCode() throws Exception {
        String uri = PATH + "/item/1";
        ItemUpdateRequest itemUpdateRequest = new ItemUpdateRequest();
        itemUpdateRequest.setPrice(9.0);
        itemUpdateRequest.setQuantity(7);

        String inputJson = mapToJson(itemUpdateRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testGetItemByIdWith200StatusCode() throws Exception {

        String postUri = PATH + "/item";
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);

        String postInputJson = mapToJson(itemCreateRequest);
        mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(postInputJson)).andReturn();
        String getUri = PATH + "/item/6";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testGetItemByIdWith404StatusCode() throws Exception {
        String getUri = PATH + "/item/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testGetAllItemsWith200StatusCode() throws Exception {

        String postUri = PATH + "/item";
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);

        String postInputJson = mapToJson(itemCreateRequest);
        mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(postInputJson)).andReturn();
        String getUri = PATH + "/items";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testGetAllItemsWithEmptyResponse() throws Exception {
        String getUri = PATH + "/items";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        assertNotNull(content);
    }


    @Test
    void testSearchItemWith200StatusCode() throws Exception {

        String postUri = PATH + "/item";
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);

        String postInputJson = mapToJson(itemCreateRequest);
        mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(postInputJson)).andReturn();
        String uri = PATH + "/search?itemName=Apple";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void testDeleteItemWith200StatusCode() throws Exception {

        String postUri = PATH + "/item";
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setItemName("Apple");
        itemCreateRequest.setPrice(10.0);
        itemCreateRequest.setQuantity(5);

        String postInputJson = mapToJson(itemCreateRequest);
        mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(postInputJson)).andReturn();
        String uri = PATH + "/item/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Item deleted :: 1", content);
    }

    @Test
    void testDeleteItemWith404StatusCode() throws Exception {
        String uri = PATH + "/item/100";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }
}
